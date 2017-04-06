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

    User user;
    String text;
    int id;

    public Post(User user, String text)
    {
        this.user = user;
        this.text = text;
    }

    public Post(JSONObject jsonObject){
        //user = jsonObject.optString(KOL_USER);
        text = jsonObject.optString(KOL_TEXT);
        id = jsonObject.optInt(KOL_ID);
    }

    public static ArrayList<Post> makePostList(String data) throws JSONException{
        ArrayList<Post> postList = new ArrayList<>();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonArray = jsonData.optJSONArray(TABLE_NAME);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonCat = (JSONObject) jsonArray.get(i);
            Post cur = new Post(jsonCat);
            postList.add(cur);
        }
        return postList;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
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

    public String getIdTxt()
    {
        return this.id + ".";
    }
}
