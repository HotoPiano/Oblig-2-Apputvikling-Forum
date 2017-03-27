package app.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Post>
{
    private Context c;
    private Thread thread;

    public PostAdapter(Context c, Thread thread)
    {
        super(c, R.layout.post, thread.getPostList());
        this.c = c;
        this.thread = thread;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(c).inflate(R.layout.post, null);
        }
        convertView.setTag(position);
        TextView postTextView = (TextView)convertView.findViewById(R.id.post_text);
        postTextView.setText(thread.getPostList().get(position).getText());


        TextView userTextView = (TextView)convertView.findViewById(R.id.post_username);
        userTextView.setText(thread.getPostList().get(position).getUser().getUsername());

        // Sets the imageview's image to the users image id, let it be default if id is 0
        ImageView userImageView = (ImageView)convertView.findViewById(R.id.post_userimage);
        if(thread.postList.get(position).getUser().getImage() != 0)
            userImageView.setImageResource(thread.postList.get(position).getUser().getImage());

        TextView idTextView = (TextView)convertView.findViewById(R.id.post_postid);
        idTextView.setText(thread.getPostList().get(position).getIdTxt());

        final LinearLayout ThreadView = (LinearLayout)convertView.findViewById(R.id.post_layout);

        // A post is clicked
        ThreadView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //CategoryFragment fragment = new CategoryFragment();
                //fragment.setSubcategory(subCategories.get(position));
                //MainActivity.swapFragment(fragment);
            }
        });

        return convertView;
    }
}
