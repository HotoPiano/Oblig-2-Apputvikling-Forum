package app.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category>
{
    private Context c;
    private ArrayList<Category> categories;

    public CategoryAdapter(Context c, ArrayList<Category> categories)
    {
        super(c, R.layout.category, categories);
        this.c = c;
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(c).inflate(R.layout.category, null);
        }
        convertView.setTag(position);
        TextView categoryTextView = (TextView)convertView.findViewById((R.id.category_title));
        categoryTextView.setText(categories.get(position).getTitle());

        ListView categoryListView = (ListView)convertView.findViewById((R.id.subcategory_listView));
        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(c, categories.get(position).getSubCategories());
        categoryListView.setAdapter(subCategoryAdapter);
        return convertView;
    }
}
