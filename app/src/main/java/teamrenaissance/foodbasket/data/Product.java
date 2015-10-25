package teamrenaissance.foodbasket.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Product {

    // sql data: PRODUCTS_TABLE (id, product_id, product_name, category, barcode, capacity, capacity_units, picture)
    private String productID;
    private String pname;
    private String category;
    private long barcode;
    private float capacity;
    private String capacityUnits;
    private String imageUrl;

    // constructor for product read from database for entry purposes
    public Product (JSONObject jResp) {
//            String pID, String pname, String cat, long bc, float cap, String units, String img) {

        JSONObject jObject;

        try {
            JSONArray jArray = jResp.getJSONArray("product");

            // jArray contains an array of product JSONObjects. We're only taking the 1st object for now (should not have >1)
            jObject = jArray.getJSONObject(0);

            this.productID = jObject.getString("product_id");
            this.pname = jObject.getString("product_name");
            this.category = jObject.getString("category");
            this.barcode = jObject.getLong("barcode");
            this.capacity = (float) jObject.getDouble("capacity");
            this.capacityUnits = jObject.getString("capacity_units");
            this.imageUrl = jObject.getString("picture");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Getters and Setters
     */
    public String getProductID() {
        return this.productID;
    }

    public void setProductID(String id) {
        this.productID = id;
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

    public long getBarcode() {
        return this.barcode;
    }

    public void setBarcode(long bc) {
        this.barcode = bc;
    }

    public String getCapacity() {
        return (this.capacity + this.capacityUnits);
    }

    public float getCapacityFloat() {
        return this.capacity;
    }

    public String getCapacityFloatStr() {
        return (this.capacity+"");
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

}
