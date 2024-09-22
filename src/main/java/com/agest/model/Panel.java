package com.agest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Panel implements Comparable<Panel> {
    @EqualsAndHashCode.Include
    private String displayName;
    private Series series;

    public Panel(String displayName) {
        this.displayName = displayName;
    }

    public static List<Panel> getChartsPanel() {
        return new LinkedList<>(Arrays.asList(
                new Panel("Action Implementation By Status"),
                new Panel("Test Case Execution Failure Trend"),
                new Panel("Test Case Execution Results"),
                new Panel("Test Case Execution Trend"),
                new Panel("Test Module Execution Failure Trend"),
                new Panel("Test Module Execution Results"),
                new Panel("Test Module Execution Trend"),
                new Panel("Test Module Implementation By Priority"),
                new Panel("Test Module Implementation By Status"),
                new Panel("Test Module Status per Assigned Users")
        ));
    }

    public static List<Panel> getIndicatorsPanel() {
        return Arrays.asList(
                new Panel("Test Case Execution"),
                new Panel("Test Module Execution"),
                new Panel("Test Objective Execution")
        );
    }

    public static List<Panel> getReportsPanel() {
        return Arrays.asList(
                new Panel("Test Module Execution Results Report")
        );
    }

    public static List<Panel> getHeatMapsPanel() {
        return Arrays.asList(
                new Panel("Test Case Execution History"),
                new Panel("Test Module Execution History")
        );
    }

    @Override
    public int compareTo(Panel o) {
        return this.displayName.compareTo(o.displayName);
    }
}
