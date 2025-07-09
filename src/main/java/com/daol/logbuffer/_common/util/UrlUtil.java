package com.daol.logbuffer._common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {

    public static List<String> extractUrls(String content) {
        List<String> imageUrls = new ArrayList<>();
        String regex = "<img[^>]+src=[\"']([^\"']+)[\"']";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            imageUrls.add(matcher.group(1));
        }
        return imageUrls;
    }
}