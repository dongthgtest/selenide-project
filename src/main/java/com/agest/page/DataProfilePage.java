package com.agest.page;

import com.agest.model.DataProfileTableHeader;
import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;

public class DataProfilePage extends BasePage {
    public List<String> getDataProfiles() {
        List<SelenideElement> dataProfiles = $$x("//table[@class='GridView']//tr/td[" +
                DataProfileTableHeader.DATA_PROFILE.getColumnIndex() + "][not(a)]");
        List<String> result = new ArrayList<>();
        dataProfiles.forEach(profile -> {
            result.add(profile.getText().replace(" ", " "));
        });
        return result;
    }
}
