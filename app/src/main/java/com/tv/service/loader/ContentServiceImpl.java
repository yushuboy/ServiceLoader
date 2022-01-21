package com.tv.service.loader;

import com.tv.service.basic_live.ContentService;

/**
 * @author guyushu
 */
public class ContentServiceImpl implements ContentService {


    private int num=0;

    @Override
    public String getTitle() {
        num++;
        return "the title from app module "+num;
    }
}
