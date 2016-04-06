package me.aj.ablum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import me.aj.ablum.utils.AblumConstants;
import me.aj.ablum.utils.DeviceUtils;
import me.aj.ablum.utils.TempFileHelper;
import me.aj.ablum.utils.ImageViewWarper;
import me.aj.ablum.utils.ScanUtils;

/**
 * Created by Aj Liao
 */
public class SelectImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Uri> uris = new ArrayList<>();
    ArrayList<Uri> miltipleImageResult = new ArrayList<>();
    SelectType selectType = SelectType.SINGLE;
    Activity activity;
    int maxSelectCount = 1;

    public SelectImageAdapter(Activity activity, int maxSelectCount, SelectType selectType) {
        this.activity = activity;
        this.maxSelectCount = maxSelectCount;
        this.selectType = selectType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_image, parent, false);
        return new ItemViewHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;

        if (position == 0) {
            //holder.imageView.setImageResource(R.drawable.ic);
            holder.checkBox.setVisibility(View.GONE);
            holder.uri = Uri.EMPTY;
            holder.position = position;
            ImageViewWarper.display(holder.imageView, holder.uri);
        } else {
            Uri uri = uris.get(position - 1);
            holder.uri = uri;
            holder.position = position;
            ImageViewWarper.display(holder.imageView, holder.uri);
            if (selectType == SelectType.MILTIPLE) {
                holder.checkBox.setVisibility(View.VISIBLE);
                if (miltipleImageResult.contains(uri)) {
                    holder.checkBox.setChecked(true);
                } else {
                    holder.checkBox.setChecked(false);
                }
            } else {
                holder.checkBox.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return uris.size() + 1;
    }

    public void setUris(List<Uri> uris) {
        this.uris = uris;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        CheckBox checkBox;
        Uri uri;
        int position;

        public ItemViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(final View itemView) {
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            int imageSize = (DeviceUtils.getScreenWidth(imageView.getContext())) / 3;
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(imageSize, imageSize));

            checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        launchCamera();//启动相机
                    } else {
                        toPreviewImage(uri);//启动预览
                    }
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (miltipleImageResult.contains(uri)) {//取消
                        miltipleImageResult.remove(uri);
                        checkBox.setChecked(false);
                    } else {//选中
                        if (miltipleImageResult.size() == maxSelectCount) {
                            ((CheckBox) v).setChecked(false);
                        } else {
                            miltipleImageResult.add(uri);
                            checkBox.setChecked(true);
                        }
                    }
                }
            });
        }
    }

    public void scanDeviceImage(Context context) {
        new ScanImageTask(context, this).execute();
    }


    /**
     * 启动相机
     */
    private void launchCamera() {

        File tempCameraFile;

        try {
            tempCameraFile = TempFileHelper.geneTempCameraFile();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "请插入SD卡", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 打开相机
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempCameraFile));
        activity.startActivityForResult(intent, AblumConstants.REQUEST_CODE_OPEN_CAMERA);
    }

    /**
     * 去预览界面
     */
    private void toPreviewImage(Uri uri) {
        Intent intent = new Intent(activity, PreviewImageActivity.class);
        intent.putExtra(AblumConstants.EXTRA_PREVIEW_URL, uri);
        activity.startActivityForResult(intent, AblumConstants.REQUEST_CODE_PREVIEW);
    }


    private static class ScanImageTask extends AsyncTask<Void, Void, List<Uri>> {

        WeakReference<SelectImageAdapter> selectImageAdapterWeakReference;
        WeakReference<Context> contextWeakReference;

        public ScanImageTask(Context context, SelectImageAdapter selectImageAdapter) {

            selectImageAdapterWeakReference = new WeakReference<>(selectImageAdapter);
            contextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected List<Uri> doInBackground(Void... params) {

            List<Uri> uris;

            if (contextWeakReference == null) return null;

            try {
                uris = ScanUtils.scanDeviceImage(contextWeakReference.get());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            }
            return uris;
        }

        @Override
        protected void onPostExecute(List<Uri> uris) {

            //表示界面可能已经销毁了
            if (selectImageAdapterWeakReference == null || contextWeakReference == null) return;

            if (uris == null) {
                Toast.makeText(contextWeakReference.get(), "无法加载..", Toast.LENGTH_SHORT).show();
                return;
            }

            selectImageAdapterWeakReference.get().setUris(uris);
            selectImageAdapterWeakReference.get().notifyDataSetChanged();
        }
    }
}
