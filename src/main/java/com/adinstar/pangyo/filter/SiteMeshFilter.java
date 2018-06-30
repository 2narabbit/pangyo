package com.adinstar.pangyo.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SiteMeshFilter extends ConfigurableSiteMeshFilter {
    private static final String DECORATOR_DEFAULT = "/decorator/default";
    private static final String DECORATOR_GNB = "/decorator/gnb";
    private static final String DECORATOR_BACKMENU = "/decorator/backMenu";

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/*", DECORATOR_DEFAULT);

        List<String> onlyGnbPathList = new ArrayList<>(Arrays.asList("/campaign", "/star"));
        for (String path : onlyGnbPathList) {
            builder.addDecoratorPaths(path, DECORATOR_DEFAULT, DECORATOR_GNB);
        }

        List<String> gnbAndBackPathList = new ArrayList<>(Arrays.asList("/campaign/*", "/star/*", "/member/*", "/fanClub/*"));
        for (String path : gnbAndBackPathList) {
            builder.addDecoratorPaths(path, DECORATOR_DEFAULT, DECORATOR_BACKMENU, DECORATOR_GNB);
        }

        List<String> onlyBackPathList = new ArrayList<>(Arrays.asList("/fanClub/*/post/*", "/fanClub/*/campaign-candidate", "/fanClub/*/campaign-candidate/*"));
        for (String path : onlyBackPathList) {
            builder.addDecoratorPaths(path, DECORATOR_DEFAULT, DECORATOR_BACKMENU);
        }
    }
}
