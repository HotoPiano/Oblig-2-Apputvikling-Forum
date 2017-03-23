package app.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SubCategoryAdapter extends ArrayAdapter<Category>
{
    private Context c;
    private ArrayList<SubCategory> subCategories;

    public SubCategoryAdapter(Context c, ArrayList<SubCategory> subCategories)
    {
        super(c, R.layout.subcategory);
        this.c = c;
        this.subCategories = subCategories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.subcategory, null);
        }

        Button categoryButton = (Button)convertView.findViewById(R.id.subcategory_title);
        categoryButton.setText(subCategories.get(position).getTitle());

        TextView categoryTextView = (TextView)convertView.findViewById((R.id.subcategory_description));
        categoryTextView.setText(subCategories.get(position).getDescription());


        categoryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //int position = (Integer)view.getTag();
                //Intent intent = new Intent(getContext(), CategoryActivity.class);
                //c.startActivity(intent);
            }
        });
        /*
        TextView key_textView = (TextView)convertView.findViewById(R.id.notes_textView);
        key_textView.setText(items.get(position).getTitle());
        //ImageView img = (ImageView)convertView.findViewById(R.id.notes_imageView);
        //img.setImageResource(items.get(position).getImage());

        RelativeLayout btButton = (RelativeLayout) convertView.findViewById(R.id.notes_Layout);
        btButton.setTag(position);

        // Click event handler attached for each note. When a note is pressed you are sent to
        // the NoteActivity class with the corresponding position of the note in the table.
        btButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = (Integer)view.getTag();

                Intent intent = new Intent(getContext(), NoteActivity.class);
                intent.putExtra("note", position);
                c.startActivity(intent);
            }
        });

        ImageButton removeItemBtn = (ImageButton)convertView.findViewById(R.id.imageButton_RemoveNote);
        removeItemBtn.setTag(position);

        // Click event handler for "remove note" button. Update the noteAdapter in MainActivity,
        // remove the note from the notelist.
        removeItemBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = (Integer)view.getTag();
                MainActivity.noteAdapter.remove(items.get(position));
                items.remove(items.get(position));
                MainActivity.noteAdapter.notifyDataSetChanged();
            }
        });
*/
        return convertView;
    }
}
