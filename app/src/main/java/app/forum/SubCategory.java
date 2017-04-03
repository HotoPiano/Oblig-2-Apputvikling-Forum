package app.forum;

import org.json.JSONObject;

import java.util.ArrayList;

public class SubCategory
{
    String title;
    String description;
    ArrayList<Thread> threadList;

    public SubCategory(String title, String description)
    {
        this(title, description, null);
    }

    public SubCategory(String title, String description, ArrayList<Thread> threadList)
    {
        this.title = title;
        this.description = description;
        if(threadList != null)
            this.threadList = threadList;
        else
            this.threadList = new ArrayList<Thread>();
    }

    public SubCategory(JSONObject jsonObject){
        title = jsonObject.optString(Category.KOL_NAME_CATEGORY);
        description = jsonObject.optString(Category.KOL_NAME_DESCRIPTION);
        threadList = new ArrayList<>();
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ArrayList<Thread> getThreadList()
    {
        return threadList;
    }

    public void setThreadList(ArrayList<Thread> threadList)
    {
        this.threadList = threadList;
    }

    public void addThread(Thread thread)
    {
        this.threadList.add(thread);
    }
}
