package app.forum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

/**
 * Classa that loads the popup window to edit a post
 */
public class EditPostFragment extends Fragment
{
    Post post;
    static EditText editText;
    Thread thread;
    List<Post> page;

    public EditPostFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_post, container, false);

        editText = (EditText)view.findViewById(R.id.edit_postText);
        editText.setText(post.getText());

        ImageButton addPost = (ImageButton)view.findViewById(R.id.edit_postButton);

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
                        post.setText(text);

                        // DB edit post
                        RestDbActions.updatePost(text, post.getId() + "");

                        // Remove the edit post fragment
                        MainActivity.removeFragment(fragment);

                        // Move back to the thread fragment
                        ThreadFragment newFragment = new ThreadFragment();
                        newFragment.setThread(thread, post.getPage());
                        MainActivity.swapFragment(newFragment, true);

                    }
                }
            }
        });
        return view;
    }



    public void setEdit(Thread thread, Post post)
    {
        this.thread = thread;
        this.post = post;
    }
}
