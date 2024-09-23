package com.agest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Page {
    @NonNull
    private String pageName;
    private Page parentPage;
    private int numberOfColumns = 2;
    private Page displayAfter;
    private boolean isPublic;

    public Page(@NonNull String pageName, boolean isPublic) {
        this.pageName = pageName;
        this.isPublic = isPublic;
    }

    public String getPageNameFormat() {
        return this.pageName.replace(" ", " ");
    }
}
