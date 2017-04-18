package app.forum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Elevates the popupwidnow to create a new thread
 */
public class NewThreadFragment extends Fragment
{
    SubCategory subCategory;


    public NewThreadFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_new_thread, container, false);

        final EditText editText = (EditText)view.findViewById(R.id.new_threadText);

        final EditText threadTitle = (EditText)view.findViewById(R.id.new_threadTitle);

        ImageButton addThread = (ImageButton)view.findViewById(R.id.new_threadButton);

        final Fragment fragment = this;

        // Addthread button clicked on, if theres any content - add it to the thread list
        addThread.setOnClickListener(new View.OnClickListener()
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
                    else if(threadTitle.getText().toString().isEmpty())
                    {
                        Toast.makeText(getContext(), "Title is empty, cannot post", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Add to db
                        String title = threadTitle.getText().toString();
                        String text = editText.getText().toString();
                        RestDbActions.insertThread(title, text, MainActivity.currentSubCategory.getTitle(), MainActivity.userName);
                        Thread thread = new Thread(MainActivity.userName, title, text);

                        // Remove the popup addthread window
                        MainActivity.removeFragment(fragment);

                        // Swap to the fragment that shows the post in that thread
                        ThreadFragment fragment = new ThreadFragment();
                        fragment.setThread(thread, thread.getLastPage());
                        MainActivity.swapFragment(fragment, true);
                    }
                }
            }
        });
        return view;
    }

    public void setSubCategory(SubCategory subCategory)
    {
        this.subCategory = subCategory;
    }
}
