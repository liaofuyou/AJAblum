package me.aj.ablum.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by Aj Liao
 */
public class TempFileHelper {

    private static String tempCameraPhotoName = "temp_camera_photo.png";
    private static String tempCropPhotoName = "temp_crop_photo.png";

    /**
     * 生成临时文件
     */
    private static File geneTempFile(Context context, String tempFileName) {

        File tempFile = new File(getAblumTempDir() + tempFileName);

        if (!tempFile.exists()) {
            if (!checkSDCardAvailable()) {
                Toast.makeText(context, "请插入SD卡", Toast.LENGTH_SHORT).show();
                return null;
            }
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "无法创建临时文件", Toast.LENGTH_SHORT).show();
            }
        }
        return tempFile;
    }

    /**
     * 取得临时文件:相机
     */
    public static File getTempCameraFile(Context context) {
        return geneTempFile(context, tempCameraPhotoName);
    }

    /**
     * 取得临时文件:裁剪
     */
    public static File getTempCropFile(Context context) {
        return geneTempFile(context, tempCropPhotoName);
    }

    /**
     * 取得相册临时目录
     */
    private static String getAblumTempDir() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "ajablum" + File.separator + "temp" + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            geneNomediaFile(path);
        }
        return path;
    }

    /**
     * 生成Nomedia
     */
    private static void geneNomediaFile(String dir) {
        try {
            File nomedia = new File(dir + ".nomedia");
            nomedia.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SD卡是否可用
     */
    private static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
}
