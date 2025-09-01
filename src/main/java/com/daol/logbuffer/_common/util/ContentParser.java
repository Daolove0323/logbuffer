package com.daol.logbuffer._common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentParser {

    private static final String IMAGE_URL_REGEX = "<img[^>]+src=[\"']([^\"']+)[\"']";

    public static List<String> ParseImageUrls(String content) {
        List<String> imageUrls = new ArrayList<>();
        Pattern pattern = Pattern.compile(IMAGE_URL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            imageUrls.add(matcher.group(1));
        }
        return imageUrls;
    }
}