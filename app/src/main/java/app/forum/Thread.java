package app.forum;


import java.util.ArrayList;

public class Thread
{
    User owner;
    String title;
    ArrayList<Post> postList;

    public Thread(User owner, String title, Post post)
    {
        this.owner = owner;
        this.title = title;
        this.postList = new ArrayList<Post>();
        addPost(post);
    }

    public Thread(User owner, String title, String postText)
    {
        this.owner = owner;
        this.title = title;
        this.postList = new ArrayList<Post>();
        Post post = new Post(owner, postText);
        addPost(post);
    }

    public Thread(User owner, String title, ArrayList<Post> postList)
    {
        this.owner = owner;
        this.title = title;
        this.postList = postList;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
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

    public void addPost(User user, String postText)
    {
        Post post = new Post(user, postText);
        int lastPos = this.postList.size();
        post.setId(lastPos+1);
        this.postList.add(post);
    }
}
