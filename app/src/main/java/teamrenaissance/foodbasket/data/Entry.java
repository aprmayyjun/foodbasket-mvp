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

    private String username;
    private int age;
    private String pname;
    private String description;
    private String rating;
    private String imageUrl;
    private Date date;
    private int id;

    // constructor for entries read from database for viewing purposes
    @SuppressLint("SimpleDateFormat")
    public Entry (String uname, int yob, String pname, String desc, String rating, String img, String dateStr, int id) {

        this.username = uname;

        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        this.age = yearNow - yob;

        this.pname = pname;
        this.description = desc;
        this.rating = rating;
        this.imageUrl = img;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        try {
            this.date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.id = id;
    }


    /**
     * Getters and Setters
     */
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String uname) {
        this.username = uname;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPName() {
        return this.pname;
    }

    public void setPName(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getRating() {
        return this.rating;
    }

    public int getRatingInt() {
        return Integer.valueOf(this.rating);
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String img) {
        this.imageUrl = img;
    }

    public Date getDate() {
        return this.date;
    }

    public String getDateStr() {
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
        String dateStr = sdf2.format(this.date);
        return dateStr;
    }

    public void setDate(Date date) {
        this.date = date;
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
        str = sdf2.format(this.date);
        //return this.pname+"; Created on "+str;
        return this.pname;
    }

}
