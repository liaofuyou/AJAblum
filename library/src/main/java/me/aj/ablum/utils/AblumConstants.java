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
     * 选择图片
     */
    public static int REQUEST_CODE_SELECT_IMAGE = 3;

    /**
     * 裁剪图片
     */
    public static int REQUEST_CODE_CROP = 4;

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

    /**
     * 是否需要裁剪，对于“多选”情况无效
     */
    public static String EXTRA_IS_CROP = "EXTRA_IS_CROP";
}
