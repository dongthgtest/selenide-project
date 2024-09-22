package com.agest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Page {
    private String pageName;
    private Page parentPage;
    private int numberOfColumns = 2;
    private Page displayAfter;
    private boolean isPublic;

    public Page(String pageName, boolean isPublic) {
        this.pageName = pageName;
        this.isPublic = isPublic;
    }

    public String getPageNameFormat() {
        return this.pageName.replace(" ", " ");
    }
}
