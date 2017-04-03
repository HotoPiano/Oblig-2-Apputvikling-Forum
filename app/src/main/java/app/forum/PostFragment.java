package app.forum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class PostFragment extends Fragment
{
    Thread thread;

    public PostFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);


        TextView threadTitleView = (TextView)view.findViewById(R.id.post_title);
        threadTitleView.setText(thread.getTitle());

        ListView threadListView = (ListView)view.findViewById(R.id.post_listView);
        PostAdapter postAdapter = new PostAdapter(view.getContext(), thread);
        threadListView.setAdapter(postAdapter);

        ImageButton newPostButton = (ImageButton)view.findViewById(R.id.post_newpost);

        // Newpostbutton clicked, popup post fragment
        newPostButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NewPostFragment fragment = new NewPostFragment();
                MainActivity.addFragment(fragment);
            }
        });

        return view;
    }

    public void setPost(Thread thread)
    {
        this.thread = thread;
    }

}
