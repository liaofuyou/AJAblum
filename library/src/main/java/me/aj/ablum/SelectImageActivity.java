package me.aj.ablum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class SelectImageActivity extends AppCompatActivity {

    public static String EXTRA_SELECT_TYPE = "EXTRA_SELECT_TYPE";
    public static int REQUEST_CODE_OPEN_CAMERA = 1;

    RecyclerView recyclerView;
    SelectImageAdapter selectImageAdapter = null;
    SelectType selectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        selectType = (SelectType) getIntent().getSerializableExtra(EXTRA_SELECT_TYPE);

        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        selectImageAdapter = new SelectImageAdapter(this, 9, selectType);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        //recyclerView.addItemDecoration(new GalleyItemDecoration(2, 2));
        recyclerView.setAdapter(selectImageAdapter);
        selectImageAdapter.scanDeviceImage(this);
    }
}
