package app.forum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Elevates the popupwindow to add a new post
 */
public class NewPostFragment extends Fragment
{
    Thread thread;

    public NewPostFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_post, container, false);

        final EditText editText = (EditText)view.findViewById(R.id.new_postText);

        ImageButton addPost = (ImageButton)view.findViewById(R.id.new_postButton);

        final Fragment fragment = this;
        // Addpost button clicked on, if theres any content - add it to the post list
        addPost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!MainActivity.userName.isEmpty())
                {
                    if(editText.getText().toString().isEmpty())
                    {
                        Toast.makeText(getContext(), "Textfield is empty, cannot post", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String text = editText.getText().toString();
                        // Add post to DB
                        RestDbActions.insertPost(text, MainActivity.userName, thread.getTitle());

                        // Remove the add post fragment
                        MainActivity.removeFragment(fragment);


                        // Swap to the fragment that shows the post in that thread
                        ThreadFragment fragment = new ThreadFragment();
                        fragment.setThread(thread, thread.getLastPage());
                        MainActivity.swapFragment(fragment, false);
                    }
                }
            }
        });

        return view;
    }

    public void setThread(Thread thread)
    {
        this.thread = thread;
    }
}
