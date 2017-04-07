package app.forum;

public class User
{

    String username;
    String mail;
    String image;

    public User(String username, String mail)
    {
        this(username, mail, null);
    }

    public User(String username, String mail, String image)
    {
        this.username = username;
        this.mail = mail;
        this.image = image;
    }

    public User(String username)
    {
        this.username = username;
        this.mail = null;
        image = null;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }
}
