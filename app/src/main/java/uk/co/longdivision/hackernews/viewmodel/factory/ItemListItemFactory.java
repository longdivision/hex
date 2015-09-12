package uk.co.longdivision.hackernews.viewmodel.factory;

import java.util.ArrayList;
import java.util.Iterator;

import uk.co.longdivision.hackernews.model.Item;
import uk.co.longdivision.hackernews.model.Story;
import uk.co.longdivision.hackernews.viewmodel.ItemListItem;

public class ItemListItemFactory {

    public static ArrayList<ItemListItem> createItemListItems(ArrayList<Item> items) {
        ArrayList<ItemListItem> itemListItems = new ArrayList<>();
        Iterator itemsIterator = items.iterator();

        while (itemsIterator.hasNext()) {
            Item item = (Item) itemsIterator.next();

            if (item instanceof Story) {
                itemListItems.add(createItemListItemForStory((Story) item));
            }
        }

        return itemListItems;
    }

    public static ItemListItem createItemListItemForStory(Story story) {
        return new ItemListItem(story.getTitle(), story.getUser(), story.getDomain(),
                story.getScore(), story.getCommentCount());
    }

}
