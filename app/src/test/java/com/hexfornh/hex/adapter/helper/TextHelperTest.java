package com.hexfornh.hex.adapter.helper;

import com.hexforhn.hex.adapter.helper.TextHelper;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TextHelperTest {

    @Test
    public void removesTrailingNewlinesFromText() throws Exception {
        assertEquals("", TextHelper.removeTrailingNewlinesFromText(null));
        assertEquals("", TextHelper.removeTrailingNewlinesFromText(""));
        assertEquals("Text", TextHelper.removeTrailingNewlinesFromText("Text"));
        assertEquals("Text", TextHelper.removeTrailingNewlinesFromText("Text\n"));
        assertEquals("Text", TextHelper.removeTrailingNewlinesFromText("Text\n\n"));
        assertEquals("Text\n\nText", TextHelper.removeTrailingNewlinesFromText("Text\n\nText"));
        assertEquals("\nText", TextHelper.removeTrailingNewlinesFromText("\nText"));
    }
}
