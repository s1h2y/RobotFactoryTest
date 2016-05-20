package com.alpha.smart.factorytest.beans;

import android.app.Fragment;

/**
 * Created by shy on 16-5-17.
 */
public class FragmentBean {
    public int image;
    public int greyImage;
    public int title;
    public String tag;
    public String className;

    public FragmentBean(int image, int greyImage, int title, String tag, String className) {
        this.image = image;
        this.greyImage = greyImage;
        this.title = title;
        this.tag = tag;
        this.className = className;
    }
}
