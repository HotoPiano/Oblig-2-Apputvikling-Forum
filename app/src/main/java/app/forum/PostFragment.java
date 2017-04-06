package app.forum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PostFragment extends Fragment
{
    Thread thread;
    int page;

    public PostFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);


        // TODO fix ?
        //Thread threadPage = new Thread(thread, page);

        //PostAdapter postAdapter = new PostAdapter(view.getContext(), thread, page);
        final PostAdapter postAdapter = new PostAdapter(view.getContext(), thread, thread.getPostsAtPage(page));

        final ListView threadListView = (ListView)view.findViewById(R.id.post_listView);
        threadListView.setAdapter(postAdapter);


        Toast.makeText(this.getContext(), thread.getLastPage() + " " + thread.getPostList().get(thread.getPostList().size()-1).getId(), Toast.LENGTH_SHORT).show();
        Button firstPageButton = (Button)view.findViewById(R.id.thread_firstPage);
        if(this.page > 1)
        {
            firstPageButton.setText("1");
            // Firstpage clicked
            firstPageButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // TODO
                    // Swap to the fragment that shows the post in that thread
                    //page = 1;
                    //postAdapter.setPage(thread.getPostsAtPage(1));
                    PostFragment fragment = new PostFragment();
                    fragment.setThread(thread, 1);
                    MainActivity.swapFragment(fragment, true);
                }
            });
        }

        Button lastPageButton = (Button)view.findViewById(R.id.thread_lastPage);
        if(this.page < this.thread.getLastPage())
        {
            lastPageButton.setText(this.thread.getLastPage() + "");
            // Lastpage clicked
            lastPageButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // Swap to the fragment that shows the post in that thread
                    PostFragment fragment = new PostFragment();
                    fragment.setThread(thread, thread.getLastPage());
                    MainActivity.swapFragment(fragment, true);
                    //postAdapter.setPage(thread.getPostsAtPage(thread.getLastPage()));
                }
            });
        }

        TextView currentPageText = (TextView) view.findViewById(R.id.thread_currentPage);
        currentPageText.setText("Page " + page);

        ImageButton previousPageButton = (ImageButton)view.findViewById(R.id.thread_previousPage);

        // Previouspage clicked
        previousPageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(page > 1)
                {
                    // Swap to the fragment that shows the post in that thread
                    PostFragment fragment = new PostFragment();
                    fragment.setThread(thread, page-1);
                    MainActivity.swapFragment(fragment, true);
                }
            }
        });
        ImageButton nextPageButton = (ImageButton)view.findViewById(R.id.thread_nextPage);
        // NextPageButton clicked
        nextPageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(page < thread.getLastPage())
                {
                    // Swap to the fragment that shows the post in that thread
                    PostFragment fragment = new PostFragment();
                    fragment.setThread(thread, page+1);
                    MainActivity.swapFragment(fragment, true);
                }
            }
        });


        TextView threadTitleView = (TextView)view.findViewById(R.id.post_title);
        threadTitleView.setText(thread.getTitle());

        ImageButton newPostButton = (ImageButton)view.findViewById(R.id.post_newpost);

        // Newpostbutton clicked, popup post fragment
        newPostButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(MainActivity.currentUser != null)
                {
                    NewPostFragment fragment = new NewPostFragment();
                    fragment.setThread(thread);
                    MainActivity.addFragment(fragment);
                }
                else
                    Toast.makeText(getContext(), "You must be logged in to post", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void setThread(Thread thread, int page)
    {
        this.thread = thread;
        this.page = page;
    }

}
