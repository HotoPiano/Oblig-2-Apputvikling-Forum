package app.forum;

import java.util.ArrayList;

public class Category
{

    String title;
    ArrayList<SubCategory> subCategories;

    public Category(String title, ArrayList<SubCategory> subCategories)
    {
        this.title = title;
        this.subCategories = subCategories;
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
