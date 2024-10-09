package com.agest.model.tiki;

import lombok.Getter;
import org.apache.commons.lang3.RandomUtils;

@Getter
public enum LeftMenuItem {
    NHA_SACH_TIKI("Nhà Sách Tiki"),
    THE_THAO_DA_NGOAI("Thể Thao - Dã Ngoại"),
    NHA_CUA_DOI_SONG("Nhà Cửa - Đời Sống"),
    DIEN_GIA_DUNG("Điện Gia Dụng");
    private final String title;

    LeftMenuItem(String title) {
        this.title = title;
    }

    public static LeftMenuItem getRandomLeftMenuItem() {
        return LeftMenuItem.values()[RandomUtils.nextInt(0, values().length)];
    }
}
