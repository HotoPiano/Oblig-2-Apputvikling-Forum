package app.forum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryFragment extends Fragment
{
    SubCategory subcategory;

    public CategoryFragment()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        TextView categoryTitleView = (TextView)view.findViewById(R.id.subcategory_title);
        categoryTitleView.setText(subcategory.getTitle());

        TextView categoryTextView = (TextView)view.findViewById((R.id.subcategory_description));
        categoryTextView.setText(subcategory.getDescription());

        final ListView threadListView = (ListView)view.findViewById(R.id.thread_listView);
        ThreadAdapter threadAdapter = new ThreadAdapter(view.getContext(), subcategory.getThreadList());
        threadListView.setAdapter(threadAdapter);

        ImageButton newThreadButton = (ImageButton)view.findViewById(R.id.thread_newthread);

        newThreadButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(MainActivity.currentUser != null)
                {
                    NewThreadFragment fragment = new NewThreadFragment();
                    fragment.setSubCategory(subcategory);
                    MainActivity.addFragment(fragment);
                    //TODO remove //MainActivity.currentSubCategory = subcategory;
                }
                else
                    Toast.makeText(getContext(), "You must be logged in to post", Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void setSubcategory(SubCategory subcategory)
    {
        this.subcategory = subcategory;
    }

}
