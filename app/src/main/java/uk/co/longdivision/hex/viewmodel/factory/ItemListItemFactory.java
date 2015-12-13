package uk.co.longdivision.hex.viewmodel.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.longdivision.hex.model.Item;
import uk.co.longdivision.hex.model.Story;
import uk.co.longdivision.hex.viewmodel.ItemListItemViewModel;

public class ItemListItemFactory {

    public static ArrayList<ItemListItemViewModel> createItemListItems(List<? extends Item> items) {
        ArrayList<ItemListItemViewModel> itemListItems = new ArrayList<>();

        if (items == null) {
            return itemListItems;
        }


        Iterator itemsIterator = items.iterator();

        while (itemsIterator.hasNext()) {
            Item item = (Item) itemsIterator.next();

            if (item instanceof Story) {
                itemListItems.add(createItemListItemForStory((Story) item));
            }
        }

        return itemListItems;
    }

    public static ItemListItemViewModel createItemListItemForStory(Story story) {
        return new ItemListItemViewModel(story.getTitle(), story.getDomain(),
                story.getScore(), story.getCommentCount(), story.getDate());
    }

}
