package com.bigkoo.pickerviewdemo.bean;

import com.bigkoo.pickerview.view.IPickData;

/**
 * Created by Administrator on 2016/7/13.
 */
public class PickData implements IPickData {
    private String content;

    public PickData(String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }
}
