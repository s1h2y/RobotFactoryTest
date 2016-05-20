package com.alpha.smart.factorytest.beans;

import com.alpha.smart.factorytest.utils.Result;

/**
 * Created by shy on 16-5-20.
 */
public class ResultBean {

    public String key;
    public int type;
    public int value;
    public boolean status;

    public ResultBean(String key, int type, int value, boolean status) {
        this.key = key;
        this.type = type;
        this.value = value;
        this.status = status;
    }

}
