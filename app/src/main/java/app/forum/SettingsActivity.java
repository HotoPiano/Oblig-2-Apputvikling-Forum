package app.forum;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity
{
    boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(loggedIn)
            setContentView(R.layout.activity_settings);
        else
            setContentView(R.layout.fragment_login);
    }
}
