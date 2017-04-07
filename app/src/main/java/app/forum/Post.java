package app.forum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Post
{
    final static String TABLE_NAME = "post";
    final static String KOL_TEXT = "post";
    final static String KOL_USER = "User_username";
    final static String KOL_ID = "postNr";

    String user;
    String text;
    int id;
    int rowId;

    public Post(String user, String text)
    {
        this.user = user;
        this.text = text;
    }

    public Post(JSONObject jsonObject, int row)
    {
        text = jsonObject.optString(KOL_TEXT);
        id = jsonObject.optInt(KOL_ID);
        rowId = row;
        user = jsonObject.optString(KOL_USER);
    }


    public static ArrayList<Post> makePostList(String data, int page) throws JSONException
    {
        ArrayList<Post> postList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);
        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonCat = (JSONObject) jsonArray.get(i);
            Post cur = new Post(jsonCat, ((i+1) + ((page-1) * Thread.POSTS_PER_PAGE)));
            postList.add(cur);
        }
        return postList;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getRowId()
    {
        return this.rowId;
    }

    public int getPage()
    {
        return (getRowId() / Thread.POSTS_PER_PAGE) + 1;
    }
}
