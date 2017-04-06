package app.forum;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SubCategoryAdapter extends ArrayAdapter<SubCategory>
{
    private Context c;
    private ArrayList<SubCategory> subCategories;

    public SubCategoryAdapter(Context c, ArrayList<SubCategory> subCategories)
    {
        super(c, R.layout.subcategory, subCategories);
        this.c = c;
        this.subCategories = subCategories;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(c).inflate(R.layout.subcategory, null);
        }
        convertView.setTag(position);
        TextView categoryTitleView = (TextView)convertView.findViewById(R.id.subcategory_title);
        categoryTitleView.setText(subCategories.get(position).getTitle());

        TextView categoryTextView = (TextView)convertView.findViewById((R.id.subcategory_description));
        categoryTextView.setText(subCategories.get(position).getDescription());

        final LinearLayout subCategoryView = (LinearLayout)convertView.findViewById(R.id.subcategory_layout);

        // Set odd even color
        if(position % 2 == 0)
            subCategoryView.setBackgroundColor(ContextCompat.getColor(c, R.color.colorSubCategory1));
        else
            subCategoryView.setBackgroundColor(ContextCompat.getColor(c, R.color.colorSubCategory2));

        // Subcategory onclick
        subCategoryView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Set current subcategory, swap to subcategory fragment
                CategoryFragment fragment = new CategoryFragment();
                // TODO remove - //MainActivity.currentSubCategory = subCategories.get(position);
                fragment.setSubcategory(subCategories.get(position));
                MainActivity.swapFragment(fragment, false);
                MainActivity.currentSubCategory = subCategories.get(position);
            }
        });

        return convertView;
    }
}
