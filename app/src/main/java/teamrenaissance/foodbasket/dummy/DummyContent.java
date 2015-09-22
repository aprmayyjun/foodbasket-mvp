package teamrenaissance.foodbasket.dummy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem("1", "Fresh Milk", "Description", "January 2, 2010", "March 2, 2010"));
        addItem(new DummyItem("2", "Bread", "Description", "January 2, 2010", "March 2, 2010"));
        addItem(new DummyItem("3", "Waffles", "Description", "January 2, 2010", "March 2, 2010"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String name;
        public String description;
        public Date dateBought;
        public Date dateExpired;

        public DummyItem(String id, String name, String description, String dateBoughtString, String dateExpiredString) {
            this.id = id;
            this.name = name;
            this.description = description;

            //String string = "January 2, 2010";
            Date dateBoughtTemp = null, dateExpiredTemp = null;
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            try {
                dateBoughtTemp = format.parse(dateBoughtString);
                dateExpiredTemp = format.parse(dateExpiredString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            this.dateBought = dateBoughtTemp;
            this.dateExpired = dateExpiredTemp;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
