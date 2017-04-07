package app.forum;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    static FragmentTransaction transaction;
    static FragmentManager fm;
    public SharedPreferences preferences;
    public static String userName;
    public static SubCategory currentSubCategory;
    public static Thread currentThread;
    static final String DATABASEURL = "http://itfag.usn.no/~142840/forum_api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prepare fragment switch and set first fragment to frontpageFragment
        fm = getSupportFragmentManager();
        FrontpageFragment frontpageFragment = new FrontpageFragment();
        swapFragment(frontpageFragment, false);

        // Load logged in user if sharedPreference is found
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userName = preferences.getString("userName", "");
    }

    public static void swapFragment(Fragment newFragment, boolean popBackStack)
    {
        transaction = fm.beginTransaction();
        transaction.replace(R.id.main_fragment, newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        // Remove it from the back button list if parameter is true
        if(popBackStack)
            fm.popBackStack();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void addFragment(Fragment newFragment)
    {
        transaction = fm.beginTransaction();
        transaction.add(R.id.main_fragment, newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void removeFragment(Fragment fragment)
    {
        transaction = fm.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
        // Remove it from the back button list
        fm.popBackStack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
    }
}