package me.aj.ablum.utils;

/**
 * Created by Aj Liao
 */
public class AblumConstants {

    /**
     * 请求打开相机
     */
    public static int REQUEST_CODE_OPEN_CAMERA = 1;

    /**
     * 请求预览图片
     */
    public static int REQUEST_CODE_PREVIEW = 2;

    /**
     * 请求单张图片返回的值，会返回一个Uri对象
     */
    public static String EXTRA_URI = "EXTRA_URI";

    /**
     * 请求多张图片返回的值，会返回一个List<Uri>对象
     */
    public static String EXTRA_URIS = "EXTRA_URIS";

    /**
     * 图片选择的类型，当选 or 多选,会返回一个SelectType对象
     */
    public static String EXTRA_SELECT_TYPE = "EXTRA_SELECT_TYPE";

    /**
     * 要预览的图片的URL
     */
    public static String EXTRA_PREVIEW_URL = "EXTRA_PREVIEW_URL";
}
