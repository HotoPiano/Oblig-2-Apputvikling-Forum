package app.forum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ListView categoryListView;
    public static CategoryAdapter categoryAdapter;
    public static ArrayList<Category> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryListView = (ListView)findViewById((R.id.category_listView));

        categories = new ArrayList();

        // TODO fetch from database instead
        categories.addAll(generateTestData());

        categoryAdapter = new CategoryAdapter(this, categories);

        categoryAdapter.addAll(categories);

        categoryListView.setAdapter(categoryAdapter);
    }

    private ArrayList<Category> generateTestData()
    {
        ArrayList<Category> categoryList = new ArrayList();
        ArrayList<SubCategory> list1 = new ArrayList();
        SubCategory sub1 = new SubCategory("Baking", "Discuss recipes and content about bread, pies, cakes, deserts... Everything that has to do with baking!");
        list1.add(sub1);
        SubCategory sub2 = new SubCategory("Cooking", "Meat, fish, vegetables... Do you have a great dinner idea, or do you need help finding one? Check in here.");
        list1.add(sub2);
        Category c1 = new Category("Food", list1);
        categoryList.add(c1);

        ArrayList<SubCategory> list2 = new ArrayList();
        SubCategory sub3 = new SubCategory("Series", "Want to know which new show to watch?");
        list2.add(sub3);
        SubCategory sub4 = new SubCategory("Movies", "Discuss films and cinematography!");
        list2.add(sub4);
        Category c2 = new Category("Media", list2);
        categoryList.add(c2);
        return categoryList;
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

}
