package app.forum;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post>
{
    private Context c;
    private Thread thread;
    private List<Post> page;

    public PostAdapter(Context c, Thread thread, List<Post> page)
    {
        super(c, R.layout.post, page);
        this.c = c;
        this.thread = thread;
        this.page = page;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(c).inflate(R.layout.post, null);
        }
        convertView.setTag(page.get(position).getId());
        TextView postTextView = (TextView)convertView.findViewById(R.id.post_text);
        postTextView.setText(page.get(position).getText());


        TextView userTextView = (TextView)convertView.findViewById(R.id.post_username);
        userTextView.setText(page.get(position).getUser().getUsername());

        // Sets the imageview's image to the users image id, let it be default if id is 0
        ImageView userImageView = (ImageView)convertView.findViewById(R.id.post_userimage);
        //if(page.get(position).getUser().getImage() != 0)
        //    userImageView.setImageResource(page.get(position).getUser().getImage());

        TextView idTextView = (TextView)convertView.findViewById(R.id.post_postid);
        idTextView.setText(page.get(position).getIdTxt());

        final ImageButton editButton = (ImageButton)convertView.findViewById(R.id.post_edit);
        final ImageButton deleteButton = (ImageButton)convertView.findViewById(R.id.post_delete);

        // Editbutton trigger
        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditPostFragment fragment = new EditPostFragment();
                MainActivity.currentPost = page.get(position);
                MainActivity.addFragment(fragment);
            }
        });
        //Deletebutton trigger
        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // TODO also remove post from db
                //thread.getPostList().remove(page.get(position));
                PostAdapter.this.remove(page.get(position));
                if(thread.getPostList().size() > 0)
                {
                    // Check if it was the last post in the page, if so refresh fragment
                    if(page.size() < 1)
                    {
                        PostFragment fragment = new PostFragment();
                        fragment.setThread(thread, thread.getLastPage());
                        MainActivity.swapFragment(fragment, true);
                    }

                    // TODO Fix goback navigation ?
                    PostAdapter.this.notifyDataSetChanged();
                    //MainActivity.removeFragment(fragment);
                    //MainActivity.addFragment(fragment);
                }
                // If only 1 post in thread, remove thread
                else
                {
                    // TODO also remove thread from db
                    MainActivity.currentSubCategory.removeThread(thread);
                    CategoryFragment fragment = new CategoryFragment();
                    fragment.subcategory = MainActivity.currentSubCategory;
                    //MainActivity.removeFragment(fragment);
                    MainActivity.swapFragment(fragment, true);
                }
            }
        });
        deleteButton.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);


        final LinearLayout ThreadView = (LinearLayout)convertView.findViewById(R.id.post_layout);

        // Set odd even color
        if(position % 2 == 0)
            ThreadView.setBackgroundColor(ContextCompat.getColor(c, R.color.colorSubCategory1));
        else
            ThreadView.setBackgroundColor(ContextCompat.getColor(c, R.color.colorSubCategory2));


        // A post is clicked, show editbutton/deletebutton if condition is met
        ThreadView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(editButton.getVisibility() == View.GONE)
                {
                    // If it's this owners post, set editbutton triggerable
                    if(MainActivity.currentUser != null && MainActivity.currentUser.getUsername().equals(page.get(position).getUser().getUsername()))
                    {
                        editButton.setVisibility(View.VISIBLE);
                        // If last post, set deletebutton triggerable)
                        if(page.get(position).getId() == thread.getPostList().size())
                        {
                            deleteButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else
                {
                    deleteButton.setVisibility(View.GONE);
                    editButton.setVisibility(View.GONE);
                }
            }
        });

        return convertView;
    }
}
