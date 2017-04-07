package app.forum;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class that holds the category
 */
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
        title = jsonCat.optString(KOL_NAME_CATEGORY);
        subCategories = new ArrayList<>();
    }

    public static ArrayList<Category> makeCategoryList(String data) throws JSONException
    {
        ArrayList<Category> categoryList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);
        Category cur = null;
        Log.d("a", jsonArray.length() + "");
        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonCat = (JSONObject) jsonArray.get(i);

            if (jsonCat.optString(KOL_NAME_CATEGORY).equals(""))
            {
                if(cur != null)
                {
                    categoryList.add(cur);
                    cur = null;
                }
            }
            else
            {
                if(cur == null)
                    cur = new Category(jsonCat);
                SubCategory subCategory = new SubCategory(jsonCat);
                cur.subCategories.add(subCategory);
            }
        }
        return categoryList;
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
