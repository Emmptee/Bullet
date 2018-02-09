package com.souha.bullet.data;

public class NewEle {

    public static final String TAG_P = "p";
    public static final String TAG_IFRAME = "iframe";
    public static final String TAG_STRONG = "strong";

    public static final String IMG = "img";
    public static final String SRC = "src";

    public static final int TYPE_TEXT = 1000;
    public static final int TYPE_IMG = 1001;
    public static final int TYPE_VIDEO = 1002;

    public int type;
    public String text;
    public boolean isBold;
    public String imgUrl;
    public String videoUrl;
    public String html;
}
