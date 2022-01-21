package com.tv.service.auto_service;

import com.google.auto.service.AutoService;
import com.tv.service.basic_live.ContentService;

/**
 * 通过autoService自动生成/META-INF/services
 *
 * @author guyushu
 */
@AutoService(ContentService.class)
public class ContentServiceImpl implements ContentService {
    @Override
    public String getTitle() {
        return "the title from app module";
    }
}
