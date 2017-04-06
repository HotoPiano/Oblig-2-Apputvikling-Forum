package app.forum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditPostFragment extends Fragment
{
    Post post;
    static EditText editText;

    public EditPostFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        post = MainActivity.currentPost;

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
                if(MainActivity.currentUser != null)
                {
                    if(editText.getText().toString().isEmpty())
                    {
                        Toast.makeText(getContext(), "Textfield is empty, cannot post", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // TODO also edit text in the database
                        post.setText(editText.getText().toString());

                        // Remove the edit post fragment
                        MainActivity.removeFragment(fragment);
                    }
                }
            }
        });
        return view;
    }



    public void setPost(Post post)
    {
        this.post = post;
    }
}
