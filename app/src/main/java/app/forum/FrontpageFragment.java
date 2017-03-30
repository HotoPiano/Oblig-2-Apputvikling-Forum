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


    public FrontpageFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_frontpage, container, false);
        categoryListView = (ListView)view.findViewById((R.id.category_listView));

        categoryAdapter = new CategoryAdapter(this.getContext(), MainActivity.categories);

        categoryListView.setAdapter(categoryAdapter);

        // Inflate the layout for this fragment
        return view;
    }

}
