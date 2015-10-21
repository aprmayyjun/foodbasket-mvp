package teamrenaissance.foodbasket.data;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Entity class. The object that represents a journal entry.
 *
 */
public class Entry {

    // sql data: PRODUCTS_TABLE (id, product_id, product_name, category, capacity, capacity_units, picture)
    // sql data: INVENTORY_TABLE (id, household_id, product_id, expiry_date)
    private String householdID;
    private String pname;
    private String category;
    private float capacity;
    private String capacityUnits;
    private String imageUrl;
    private Date expiryDate;
    private int id;

    // constructor for entries read from database for viewing purposes
    @SuppressLint("SimpleDateFormat")
    public Entry (String householdID, String pname, String cat, float cap, String units, String img, String dateStr, int id) {

        this.householdID = householdID;
        this.pname = pname;
        this.category = cat;
        this.capacity = cap;
        this.capacityUnits = units;
        this.imageUrl = img;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        try {
            this.expiryDate = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.id = id;
    }


    /**
     * Getters and Setters
     */
    public String getHouseholdID() {
        return this.householdID;
    }

    public void setHouseholdID(String id) {
        this.householdID = id;
    }

    public String getPName() {
        return this.pname;
    }

    public void setPName(String pname) {
        this.pname = pname;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String cat) {
        this.category = cat;
    }

    public String getCapacity() {
        return (this.capacity + this.capacityUnits);
    }

    public float getCapacityFloat() {
        return this.capacity;
    }

    public String getCapacityUnits() {
        return this.capacityUnits;
    }

    public void setCapacityFloat(float cap) {
        this.capacity = cap;
    }

    public void setCapacityUnits(String units) {
        this.capacityUnits = units;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String img) {
        this.imageUrl = img;
    }

    public Date getExpiryDate() {
        return this.expiryDate;
    }

    public String getExpiryDateStr() {
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
        String dateStr = sdf2.format(this.expiryDate);
        return dateStr;
    }

    public void setExpiryDate(Date date) {
        this.expiryDate = date;
    }

    public String getId() {
        return String.valueOf(this.id);
    }

    public void setId(int id) {
        this.id = id;
    }


    /*
     * @see java.lang.Object#toString()
     * Override toString so that this is displayed in ListViews instead of the default implementation
     */
    public String toString() {
        String str;
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
        str = sdf2.format(this.expiryDate);
        //return this.pname+"; Created on "+str;
        return this.pname;
    }

}
