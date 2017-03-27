package app.forum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class FrontpageFragment extends Fragment
{
    ListView categoryListView;
    public static CategoryAdapter categoryAdapter;
    public static ArrayList<Category> categories;


    public FrontpageFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_frontpage, container, false);
        categoryListView = (ListView)view.findViewById((R.id.category_listView));

        categories = new ArrayList();

        // TODO fetch from database instead
        categories.addAll(generateTestData());

        categoryAdapter = new CategoryAdapter(this.getContext(), categories);

        categoryListView.setAdapter(categoryAdapter);


        // Inflate the layout for this fragment
        return view;
    }



    private ArrayList<Category> generateTestData()
    {
        ArrayList<Category> categoryList = new ArrayList<Category>();
        ArrayList<SubCategory> list1 = new ArrayList<SubCategory>();
        list1.add(new SubCategory("Baking", "Discuss recipes and content about bread, pies, cakes, deserts... Everything that has to do with baking!"));
        list1.add(new SubCategory("Cooking", "Meat, fish, vegetables... Do you have a great dinner idea, or do you need help finding one? Check in here."));
        Category c1 = new Category("Food", list1);
        categoryList.add(c1);

        ArrayList<SubCategory> list2 = new ArrayList<SubCategory>();
        list2.add(new SubCategory("Series", "Want to know which new show to watch?"));
        list2.add(new SubCategory("Movies", "Discuss films and cinematography!"));
        Category c2 = new Category("Media", list2);
        categoryList.add(c2);
        return categoryList;
    }

}
