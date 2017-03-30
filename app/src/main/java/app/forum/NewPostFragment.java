package app.forum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;

public class NewPostFragment extends Fragment
{
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
                if(MainActivity.currentUser != null)
                {
                    if(editText.getText().toString().isEmpty())
                    {
                        Toast.makeText(getContext(), "Textfield is empty, cannot post", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Post post = new Post(MainActivity.currentUser, editText.getText().toString());
                        // Add the new post to the current thread
                        MainActivity.currentThread.addPost(post);

                        // TODO feil?
                        // Remove the add post fragment
                        MainActivity.removeFragment(fragment);
                    }
                }
            }
        });

        return view;
    }
}
