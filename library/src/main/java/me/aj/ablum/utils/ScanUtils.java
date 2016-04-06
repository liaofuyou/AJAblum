package me.aj.ablum.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aj Liao
 */
public class ScanUtils {

    /**
     * 获取所有缩略图图片
     */
    public static List<Uri> scanDeviceImage(Context context) throws Throwable {
        List<Uri> uriList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = MediaStore.Images.Media.query(context.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Thumbnails.DATA
            });

            final String[] projectionPhotos = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DATE_TAKEN
            };
            cursor = MediaStore.Images.Media.query(context.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    , projectionPhotos, "", null, MediaStore.Images.Media.DATE_TAKEN + " DESC");

            if (cursor != null) {

                while (cursor.moveToNext()) {

                    String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                    File file = new File(imagePath);

                    //文件是否有效
                    if (!file.isFile()) continue;

                    //过滤尺寸
                    if (!fliterSize(imagePath)) continue;

                    uriList.add(Uri.fromFile(file));
                }
            }
        } catch (Throwable t) {
            throw t;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return uriList;
    }

    /**
     * 过滤尺寸
     */
    private static boolean fliterSize(String imagePath) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);

        return !(options.outWidth < 300 || options.outHeight < 300);
    }
}
