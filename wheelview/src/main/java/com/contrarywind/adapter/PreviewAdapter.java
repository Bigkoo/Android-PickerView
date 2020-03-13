package com.contrarywind.adapter;

import java.util.ArrayList;

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2020/03/12
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class PreviewAdapter implements WheelAdapter<String> {

    private ArrayList<String> itemList = new ArrayList<>();

    public PreviewAdapter() {
        for (int i = 0; i < 5; i++) {
            itemList.add("Item " + i);
        }
    }

    @Override
    public int getItemsCount() {
        return itemList.size();
    }

    @Override
    public String getItem(int index) {
        return itemList.get(index);
    }

    @Override
    public int indexOf(String o) {
        return itemList.indexOf(o);
    }
}
