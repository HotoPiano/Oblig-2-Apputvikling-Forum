package app.forum;

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
    public static ArrayList<Category> categories;
    public static SubCategory currentSubCategory;
    public static User currentUser;
    public static Thread currentThread;
    public static Fragment currentFragment;
    static final String DATABASEURL = "http://itfag.usn.no/~142840/forum_api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        swapFragment(new FrontpageFragment());

        categories = new ArrayList<Category>();

        // TODO fetch from database instead
        categories.addAll(generateTestData());
    }


    public static void swapFragment(Fragment newFragment)
    {
        transaction = fm.beginTransaction();
        transaction.replace(R.id.main_fragment, newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
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


    private ArrayList<Category> generateTestData()
    {
        ArrayList<Category> categoryList = new ArrayList<Category>();
        ArrayList<SubCategory> list1 = new ArrayList<SubCategory>();
        SubCategory subcat1 = new SubCategory("Baking", "Discuss recipes and content about bread, pies, cakes, deserts... Everything that has to do with baking!");

        User user = new User("Børre Balle", "lol@troll.nub", "lettpassord");
        currentUser = user;

        Thread thread = new Thread(user, "Cheesecake", "I prefer cheesecake, what about you?");
        thread.addPost(user, "Actually, I think cheesecake is the best!");
        thread.addPost(user, "You know what, chocolate cake is pretty nice too...");
        thread.addPost(user, "But have you tried banana-pie?");
        thread.addPost(user, "Nevermind, you should have a cheesecake.");

        subcat1.addThread(thread);
        list1.add(subcat1);
        SubCategory subcat2 = new SubCategory("Cooking", "Meat, fish, vegetables... Do you have a great dinner idea, or do you need help finding one? Check in here.");
        list1.add(subcat2);
        Category c1 = new Category("Food", list1);
        categoryList.add(c1);

        ArrayList<SubCategory> list2 = new ArrayList<SubCategory>();
        SubCategory subcat3 = new SubCategory("Series", "Want to know which new show to watch?");
        list2.add(subcat3);
        SubCategory subcat4 = new SubCategory("Movies", "Discuss films and cinematography!");
        list2.add(subcat4);
        Category c2 = new Category("Media", list2);
        categoryList.add(c2);
        return categoryList;
    }
}