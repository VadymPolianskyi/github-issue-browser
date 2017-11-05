package com.piedpiper.gib.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringUtil {


    public List<String> extractPrUrls(String body) {
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+(github.com/)+([\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)/pull/([\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*))";

        return extractUrls(body, urlRegex);
    }

    public List<String> extractIssuesUrls(String body) {
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+(github.com/)+([\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)/issues/([\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*))";

        return extractUrls(body, urlRegex);

    }


    private List<String> extractUrls(String text, String urlRegex) {
        List<String> containedUrls = new ArrayList<>();

        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }
}
