package me.aj.ablum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import me.aj.ablum.utils.AblumConstants;
import me.aj.ablum.utils.ImageViewWarper;

public class PreviewImageActivity extends AppCompatActivity {

    SelectType selectType;
    Uri previewImageUrl;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);

        previewImageUrl = getIntent().getParcelableExtra(AblumConstants.EXTRA_PREVIEW_URL);
        selectType = (SelectType) getIntent().getSerializableExtra(AblumConstants.EXTRA_SELECT_TYPE);

        imageView = (ImageView) findViewById(R.id.image_view);
        ImageViewWarper.display(imageView, previewImageUrl);

        if (selectType == SelectType.SINGLE) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent().putExtra(AblumConstants.EXTRA_URI, previewImageUrl);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageView.setImageResource(0);
        imageView = null;
    }
}
