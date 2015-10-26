package teamrenaissance.foodbasket.admin;


import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

/**
 *  Implementation of the PHPConnectorInterface
 *
 */
public class PHPConnector implements PHPConnectorInterface{

    //IP address of server
    // static String ipAddr = "http://54.186.228.154/foodvengers/";

    static String ipAddr = "http://192.168.0.103:8888/";
    //static String ipAddr = "http://10.27.153.118:8888/";

    // localhost IP (for emulator)
    //static String ipAddr = "http://10.0.2.2:8888/";

    public JSONObject verifyLogin (List<NameValuePair> params) {
        JSONConnector jParser = new JSONConnector();
        JSONObject json = jParser.makeHttpRequest(ipAddr+"login.php", "POST", params);
        return json;
    }

    public JSONObject addAccountToDB (List<NameValuePair> params) {
        JSONConnector jParser = new JSONConnector();
        JSONObject json = jParser.makeHttpRequest(ipAddr+"create_account.php", "POST", params);
        return json;
    }

    public JSONObject addEntryToDB (List<NameValuePair> params) {
        JSONConnector jParser = new JSONConnector();
        JSONObject json = jParser.makeHttpRequest(ipAddr+"create_entry.php", "POST", params);
        return json;
    }

    public JSONObject updateEntryInDB (List<NameValuePair> params) {
        JSONConnector jParser = new JSONConnector();
        JSONObject json = jParser.makeHttpRequest(ipAddr+"update_entry.php", "POST", params);
        return json;
    }

    public JSONObject getEntriesFromDB (List<NameValuePair> params) {
        JSONConnector jParser = new JSONConnector();
        JSONObject json = jParser.makeHttpRequest(ipAddr+"view_entries.php", "POST", params);
        return json;
    }

    public JSONObject getProductFromDB (List<NameValuePair> params) {
        JSONConnector jParser = new JSONConnector();
        JSONObject json = jParser.makeHttpRequest(ipAddr+"get_product.php", "POST", params);
        return json;
    }

    public JSONObject getRecipesListFromDB (List<NameValuePair> params) {
        JSONConnector jParser = new JSONConnector();
        JSONObject json = jParser.makeHttpRequest(ipAddr+"get_recipes_list.php", "POST", params);
        return json;
    }

    public JSONObject getRecipeDetailFromDB (List<NameValuePair> params) {
        JSONConnector jParser = new JSONConnector();
        JSONObject json = jParser.makeHttpRequest(ipAddr+"get_recipe.php", "POST", params);
        return json;
    }
}