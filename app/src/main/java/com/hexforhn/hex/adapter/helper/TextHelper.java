package com.hexforhn.hex.adapter.helper;

public class TextHelper {

    public static CharSequence removeTrailingNewlinesFromText(CharSequence text) {
        if (text == null || text.length() < 1) {
            return "";
        }

        if (text.charAt(text.length() - 1) == "\n".charAt(0)) {
            return removeTrailingNewlinesFromText(text.subSequence(0, text.length() - 1));
        }

        return text;
    }
}
