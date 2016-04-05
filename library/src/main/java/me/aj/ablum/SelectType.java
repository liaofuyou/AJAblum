package me.aj.ablum;

/**
 * Created by Aj Liao
 */
public enum SelectType {
    SINGLE(1),//单图
    MILTIPLE(2);//多图

    public int code;

    SelectType(int code) {
        this.code = code;
    }

    public static SelectType find(int code) {
        for (SelectType type : SelectType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return SINGLE;
    }
}
