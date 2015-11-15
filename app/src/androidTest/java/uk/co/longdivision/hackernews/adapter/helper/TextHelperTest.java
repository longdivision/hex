package uk.co.longdivision.hackernews.adapter.helper;

import junit.framework.TestCase;

public class TextHelperTest extends TestCase {

    public void testRemoveTrailingNewlinesFromText() throws Exception {
        assertEquals("", TextHelper.removeTrailingNewlinesFromText(null));
        assertEquals("", TextHelper.removeTrailingNewlinesFromText(""));
        assertEquals("Text", TextHelper.removeTrailingNewlinesFromText("Text"));
        assertEquals("Text", TextHelper.removeTrailingNewlinesFromText("Text\n"));
        assertEquals("Text", TextHelper.removeTrailingNewlinesFromText("Text\n\n"));
        assertEquals("Text\n\nText", TextHelper.removeTrailingNewlinesFromText("Text\n\nText"));
        assertEquals("\nText", TextHelper.removeTrailingNewlinesFromText("\nText"));
    }
}
