package app.forum;

public class User
{

    String username;
    String mail;
    String password;
    int image;

    public User(String username, String mail, String password)
    {
        this(username, mail, password, 0);
    }

    public User(String username, String mail, String password, int image)
    {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.image = image;
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

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getImage()
    {
        return image;
    }

    public void setImage(int image)
    {
        this.image = image;
    }
}
