package teamrenaissance.foodbasket.admin;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;


/**
 *  Data Access Object Interface that defines methods that can be used to access the database.
 *  All functionality that the back end provides is specified here.
 *
 */
public interface PHPConnectorInterface {


    /**
     * Used to verify the credentials of a user logging in.
     *
     * Returns a JSONObject containing the response from the backend server.
     * The params argument should be complete and contain all necessary information
     * required to complete the request.
     *
     * @param params username and password key value pairs
     * @return the JSONObject response from the server
     */
    public JSONObject verifyLogin (List<NameValuePair> params);

    /**
     * Used when a new user registers a new account
     * @param params
     * @return
     */
    public JSONObject addAccountToDB (List<NameValuePair> params);

    /**
     * Used when a user creates a new journal entry
     * @param params
     * @return the JSONObject response from the server
     */
    public JSONObject addEntryToDB (List<NameValuePair> params);


    /**
     * Used when a user edits an existing journal entry
     * @param params
     * @return the JSONObject response from the server
     */
    public JSONObject updateEntryInDB (List<NameValuePair> params);

    /**
     * Used to retrieve entries from the database
     * @param params
     * @return the JSONObject response from the server
     */
    public JSONObject getEntriesFromDB (List<NameValuePair> params);

    /**
     * Used to retrieve product info from the database
     * @param params
     * @return the JSONObject response from the server
     */
    public JSONObject getProductFromDB (List<NameValuePair> params);

    /**
     * Used to retrieve entries from the database
     * @param params
     * @return the JSONObject response from the server
     */
    public JSONObject getRecipesListFromDB (List<NameValuePair> params);

    /**
     * Used to retrieve entries from the database
     * @param params
     * @return the JSONObject response from the server
     */
    public JSONObject getRecipeDetailFromDB (List<NameValuePair> params);


}
