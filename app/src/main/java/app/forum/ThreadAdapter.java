package app.forum;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreadAdapter extends ArrayAdapter<Thread>
{
    private Context c;
    private ArrayList<Thread> threads;

    public ThreadAdapter(Context c, ArrayList<Thread> threads)
    {
        super(c, R.layout.thread, threads);
        this.c = c;
        this.threads = threads;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(c).inflate(R.layout.thread, null);
        }
        convertView.setTag(position);
        TextView threadTitleView = (TextView)convertView.findViewById(R.id.thread_title);
        threadTitleView.setText(threads.get(position).getTitle());

        final LinearLayout ThreadView = (LinearLayout)convertView.findViewById(R.id.thread_layout);

        // Set odd even color
        if(position % 2 == 0)
            ThreadView.setBackgroundColor(ContextCompat.getColor(c, R.color.colorSubCategory1));
        else
            ThreadView.setBackgroundColor(ContextCompat.getColor(c, R.color.colorSubCategory2));

        // In subcategory, thread onclick
        ThreadView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Set current thread, swap to the fragment that shows the post in that thread
                PostFragment fragment = new PostFragment();
                // TODO Remove - //MainActivity.currentThread = threads.get(position);
                fragment.setThread(threads.get(position), 1);
                MainActivity.swapFragment(fragment, false);
            }
        });

        return convertView;
    }
}
