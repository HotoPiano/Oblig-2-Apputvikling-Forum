package app.forum;

public class Post
{
    User user;
    String text;
    int id;

    public Post(User user, String text)
    {
        this.user = user;
        this.text = text;
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
