package me.aj.ablum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.aj.ablum.utils.AblumConstants;
import me.aj.ablum.utils.TempFileHelper;

/**
 * 功能：负责选择图片
 * 包括从相册和相机获取
 */
public class SelectImageActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    SelectImageAdapter selectImageAdapter;
    SelectType selectType;
    boolean isCrop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        initVariable();

        initView();
    }

    private void initVariable() {
        isCrop = getIntent().getBooleanExtra(AblumConstants.EXTRA_IS_CROP, false);
        selectType = (SelectType) getIntent().getSerializableExtra(AblumConstants.EXTRA_SELECT_TYPE);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        selectImageAdapter = new SelectImageAdapter(this, 9, selectType);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new SelectImageDecoration(2, 2));
        recyclerView.setAdapter(selectImageAdapter);
        selectImageAdapter.scanDeviceImage(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == AblumConstants.REQUEST_CODE_OPEN_CAMERA) {//请求相机返回

            File tempCameraFile = TempFileHelper.getTempCameraFile(this);
            if (tempCameraFile == null || !tempCameraFile.exists()) return;

            Uri uri = Uri.fromFile(tempCameraFile);

            if (selectType == SelectType.SINGLE) {//单图
                if (isCrop) {
                    toCropImage(uri);
                } else {
                    Intent intent = getIntent().putExtra(AblumConstants.EXTRA_URI, uri);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            } else if (selectType == SelectType.MILTIPLE) {//多图
                ArrayList<Uri> list = new ArrayList<>(1);
                list.add(uri);
                Intent cameraIntent = getIntent().putParcelableArrayListExtra(AblumConstants.EXTRA_URIS, list);
                setResult(RESULT_OK, cameraIntent);
                finish();
            }
        } else if (requestCode == AblumConstants.REQUEST_CODE_PREVIEW) {
            if (isCrop && selectType == SelectType.SINGLE) {
                Uri uri = data.getParcelableExtra((AblumConstants.EXTRA_URI));
                toCropImage(uri);
            } else {
                setResult(RESULT_OK, data);
                finish();
            }
        } else if (requestCode == AblumConstants.REQUEST_CODE_CROP) {

            File tempCropFile = TempFileHelper.getTempCropFile(this);
            if (tempCropFile == null || !tempCropFile.exists()) return;

            Uri uri = Uri.fromFile(tempCropFile);

            Intent intent = getIntent().putExtra(AblumConstants.EXTRA_URI, uri);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * 请求裁剪图片
     */
    public void toCropImage(Uri uri) {

        File tempCropFile = TempFileHelper.getTempCropFile(this);
        if (tempCropFile == null || !tempCropFile.exists()) return;

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("output", Uri.fromFile(tempCropFile));
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        startActivityForResult(intent, AblumConstants.REQUEST_CODE_CROP);
    }

}
