package me.aj.ablum.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Aj Liao
 */
public class TempFileHelper {

    private static String tempCameraPhotoName = "temp_camera_photo.png";

    /**
     * 生成临时文件
     */
    public static File geneTempCameraFile() throws IOException {

        File tempCameraFile = new File(getAblumTempDir() + tempCameraPhotoName);

        if (!tempCameraFile.exists()) {
            if (!checkSDCardAvailable()) {
                throw new IOException();
            }
            tempCameraFile.createNewFile();
        }
        return tempCameraFile;
    }

    /**
     * 取得临时文件
     */
    public static File getTempCameraFile(){
        return new File(getAblumTempDir() + tempCameraPhotoName);
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
