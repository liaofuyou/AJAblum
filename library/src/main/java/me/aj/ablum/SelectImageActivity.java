package me.aj.ablum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import me.aj.ablum.utils.AblumConstants;
import me.aj.ablum.utils.TempFileHelper;

public class SelectImageActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    SelectImageAdapter selectImageAdapter;
    SelectType selectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        selectType = (SelectType) getIntent().getSerializableExtra(AblumConstants.EXTRA_SELECT_TYPE);

        initView();
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

            File tempCameraFile = TempFileHelper.getTempCameraFile();
            if (!tempCameraFile.exists()) return;
            Uri uri = Uri.fromFile(tempCameraFile);

            if (selectType == SelectType.SINGLE) {//单图
                Intent cameraIntent = new Intent().putExtra(AblumConstants.EXTRA_URI, uri);
                setResult(RESULT_OK, cameraIntent);
                finish();
            } else if (selectType == SelectType.MILTIPLE) {//多图
                ArrayList<Uri> list = new ArrayList<>(1);
                list.add(uri);
                Intent cameraIntent = new Intent().putParcelableArrayListExtra(AblumConstants.EXTRA_URIS, list);
                setResult(RESULT_OK, cameraIntent);
                finish();
            }
        } else if (requestCode == AblumConstants.REQUEST_CODE_PREVIEW) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
