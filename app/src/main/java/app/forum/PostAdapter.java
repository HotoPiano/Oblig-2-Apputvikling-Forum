package app.forum;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
        userTextView.setText(page.get(position).getUser());

        // Sets the imageview's image to the users image id, let it be default if id is 0
        ImageView userImageView = (ImageView)convertView.findViewById(R.id.post_userimage);
        // TODO? load image
        //if(page.get(position).getUser().getImage() != 0)
        //    userImageView.setImageResource(page.get(position).getUser().getImage());

        TextView idTextView = (TextView)convertView.findViewById(R.id.post_postid);
        idTextView.setText(page.get(position).getRowId() + ".");

        final ImageButton editButton = (ImageButton)convertView.findViewById(R.id.post_edit);
        final ImageButton deleteButton = (ImageButton)convertView.findViewById(R.id.post_delete);

        // Editbutton trigger
        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditPostFragment fragment = new EditPostFragment();
                fragment.setPost(page.get(position));
                MainActivity.addFragment(fragment);
            }
        });
        //Deletebutton trigger
        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(thread.getPostList().size() > 1)
                {
                    // Remove post from DB
                    RestDbActions.removePost(page.get(position).getId() + "");
                    PostAdapter.this.remove(page.get(position));

                    // Refresh to previous page if last post in page was deleted
                    if(page.size() < 1)
                    {
                        PostFragment fragment = new PostFragment();
                        fragment.setThread(thread, thread.getLastPage());
                        MainActivity.swapFragment(fragment, true);
                    }
                }
                // If only 1 post in thread, remove thread
                else
                {
                    // Remove thread from DB TODO Fix
                    RestDbActions.removeThread(thread.getTitle());
                    CategoryFragment fragment = new CategoryFragment();
                    fragment.setSubcategory(MainActivity.currentSubCategory);
                    MainActivity.removeFragment(fragment);
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
                    if(!MainActivity.userName.isEmpty() && MainActivity.userName.equals(page.get(position).getUser()))
                    {
                        editButton.setVisibility(View.VISIBLE);
                        // If last post, set deletebutton triggerable)
                        if(page.get(position).getRowId() == thread.getPostList().size())
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
