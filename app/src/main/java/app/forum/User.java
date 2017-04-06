package app.forum;

public class User
{

    String username;
    String mail;
    String password;
    String image;

    public User(String username, String mail, String password)
    {
        this(username, mail, password, null);
    }

    public User(String username, String mail, String password, String image)
    {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.image = image;
    }

    public User(String username){
        this.username = username;
        this.mail = null;
        this.password = null;
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

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
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
