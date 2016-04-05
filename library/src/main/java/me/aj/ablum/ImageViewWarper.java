package me.aj.ablum;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Aj Liao
 */
public class ImageViewWarper {

    public static void display(ImageView imageView, Uri uri) {
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        Glide.with(imageView.getContext())
                .load(uri)
                .centerCrop()
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .override(layoutParams.width, layoutParams.height)
                .into(imageView);
    }
}
