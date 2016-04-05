package me.aj.ablum;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

/**
 * Created by Aj Liao
 */
public class FileUtils {

    /**
     * 生成临时文件
     */
    public static File geneTempCameraFile() throws IOException {

        File tempCameraFile = new File(getAblumTempDir() + "temp_camera_photo.png");

        if (!tempCameraFile.exists()) {
            if (!checkSDCardAvailable()) {
                throw new IOException();
            }
            tempCameraFile = new File(getAblumTempDir() + "temp_camera_photo.png");
            tempCameraFile.createNewFile();
        }
        return tempCameraFile;
    }

    /**
     * 取得相册临时目录
     */
    private static final String getAblumTempDir() {
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
    private static final void geneNomediaFile(String dir) {
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
