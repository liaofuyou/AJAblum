package me.aj.ablum.utils;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.aj.ablum.R;

/**
 * Created by Aj Liao
 */
public class ImageViewWarper {

    public static void display(ImageView imageView, Uri uri) {
        Glide.with(imageView.getContext())
                .load(uri)
                .centerCrop()
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .into(imageView);
    }
}
