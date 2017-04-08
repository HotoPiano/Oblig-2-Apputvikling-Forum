package app.forum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Thread class that holds the title, and postlists, and the owner of the thread
 */
public class Thread
{
    final static String TABLE_NAME = "thread";
    final static String KOL_NAME = "name";
    final static String KOL_USER = "Users_username";

    String owner;
    String title;
    ArrayList<Post> postList;
    public static final int POSTS_PER_PAGE = 10;

    public Thread(String owner, String title, Post post)
    {
        this.owner = owner;
        this.title = title;
        this.postList = new ArrayList<Post>();
        addPost(post);
    }

    public Thread(String owner, String title, String postText)
    {
        this.owner = owner;
        this.title = title;
        this.postList = new ArrayList<Post>();
        Post post = new Post(owner, postText);
        addPost(post);
    }

    public int getLastPage()
    {
        return ((this.getPostList().size()-1)/POSTS_PER_PAGE)+1;
    }

    public List<Post> getPostsAtPage(int page)
    {
        if(page <= getLastPage() && page > 0)
        {
            page--;
            int startPosition = POSTS_PER_PAGE*page;
            int endPosition = Math.min(POSTS_PER_PAGE*page + POSTS_PER_PAGE, this.getPostList().size());
            return this.getPostList().subList(startPosition, endPosition);
        }
        else
            return null;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public ArrayList<Post> getPostList()
    {
        return postList;
    }

    public void setPostList(ArrayList<Post> postList)
    {
        this.postList = postList;
    }

    public void addPost(Post post)
    {
        int lastPos = this.postList.size();
        post.setId(lastPos+1);
        this.postList.add(post);
    }

    public void addPost(String user, String postText)
    {
        Post post = new Post(user, postText);
        int lastPos = this.postList.size();
        post.setId(lastPos+1);
        this.postList.add(post);
    }


    public Thread(JSONObject jsonObject)
    {
        owner = jsonObject.optString(KOL_USER);
        title = jsonObject.optString(KOL_NAME);
        postList = new ArrayList<>();
        postList.add(new Post(owner, title));
    }

    public static ArrayList<Thread> makeThreadList(String data) throws JSONException
    {
        ArrayList<Thread> threadList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);
        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonCat = (JSONObject) jsonArray.get(i);
            Thread cur = new Thread(jsonCat);
            threadList.add(cur);
        }
        return threadList;
    }
}
