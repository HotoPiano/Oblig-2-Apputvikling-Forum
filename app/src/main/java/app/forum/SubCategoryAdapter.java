package app.forum;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        subCategoryView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(getContext(), "ehh", Toast.LENGTH_SHORT).show();

                CategoryFragment fragment = new CategoryFragment();
                fragment.setSubcategory(subCategories.get(position));
                MainActivity.swapFragment(fragment);

                //MainActivity.swapFragment(fragment);


                //int position = (Integer)view.getTag();
                //Intent intent = new Intent(getContext(), CategoryActivity.class);
                //c.startActivity(intent);
            }
        });

        return convertView;
    }
}
