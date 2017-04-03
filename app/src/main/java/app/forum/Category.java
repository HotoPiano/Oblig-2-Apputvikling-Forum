package app.forum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Category
{
    static final String TABLE_NAME = "category";
    static final String KOL_NAME_NAME = "name";
    static final String KOL_NAME_DESCRIPTION = "description";
    static final String KOL_NAME_CATEGORY = "Category_name";

    String title;
    ArrayList<SubCategory> subCategories;

    public Category(String title, ArrayList<SubCategory> subCategories)
    {
        this.title = title;
        this.subCategories = subCategories;
    }

    public Category(JSONObject jsonCat){
        
    }

    public static ArrayList<Category> makeCategoryList(String data) throws JSONException{
        ArrayList<Category> categoryList = new ArrayList<>();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonArray = jsonData.optJSONArray(TABLE_NAME);
        Category cur = null;
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonCat = (JSONObject) jsonArray.get(i);
            if (jsonCat.optString(KOL_NAME_CATEGORY) == null){
                cur = new Category(jsonCat);
            }
        }
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public ArrayList<SubCategory> getSubCategories()
    {
        return subCategories;
    }

    public void setSubCategories(ArrayList<SubCategory> subCategories)
    {
        this.subCategories = subCategories;
    }

}
