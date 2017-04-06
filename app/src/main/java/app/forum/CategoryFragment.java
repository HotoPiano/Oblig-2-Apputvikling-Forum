package app.forum;


import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CategoryFragment extends Fragment
{
    final static String DBCOMMAND = "?action=get_subCat&subcat=";

    SubCategory subcategory;
    ListView threadListView;

    public CategoryFragment()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        TextView categoryTitleView = (TextView)view.findViewById(R.id.subcategory_title);
        categoryTitleView.setText(subcategory.getTitle());

        TextView categoryTextView = (TextView)view.findViewById((R.id.subcategory_description));
        categoryTextView.setText(subcategory.getDescription());

        final ListView threadListView = (ListView)view.findViewById(R.id.thread_listView);
        ThreadAdapter threadAdapter = new ThreadAdapter(view.getContext(), subcategory.getThreadList());
        threadListView.setAdapter(threadAdapter);

        ImageButton newThreadButton = (ImageButton)view.findViewById(R.id.thread_newthread);

        newThreadButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(MainActivity.currentUser != null)
                {
                    NewThreadFragment fragment = new NewThreadFragment();
                    fragment.setSubCategory(subcategory);
                    MainActivity.addFragment(fragment);
                    //TODO remove //MainActivity.currentSubCategory = subcategory;
                }
                else
                    Toast.makeText(getContext(), "You must be logged in to post", Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void setSubcategory(SubCategory subcategory)
    {
        this.subcategory = subcategory;
    }

    public void loadSubCat(String subcat){
        if (isOnline()){
            threadLoader threadLoader = new threadLoader();
            threadLoader.execute(MainActivity.DATABASEURL+DBCOMMAND+subcat);
        }
        else {
            Toast.makeText(getActivity(), "Ingen nettverkstilgang. Kan ikke laste varer.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void updateThreads(ArrayList<Thread> newList){
        ThreadAdapter threadAdapter = new ThreadAdapter(getContext(),newList);
        threadListView.setAdapter(threadAdapter);
    }

    private class threadLoader extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {
            HttpURLConnection connection = null;
            try {
                URL frontPageURL = new URL(params[0]);
                connection = (HttpURLConnection) frontPageURL.openConnection();
                connection.connect();
                int status = connection.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK){
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String responseString;
                    StringBuilder sb = new StringBuilder();
                    while ((responseString = reader.readLine()) != null){
                        sb = sb.append(responseString);
                    }
                    String threadData = sb.toString();
                    subcategory.threadList = Thread.makeThreadList(threadData);
                    return 0l;
                }
                else {
                    return 1l;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return 1l;
            } catch (IOException e) {
                e.printStackTrace();
                return 1l;
            } catch (JSONException e) {
                e.printStackTrace();
                return 1l;
            }
        }

        @Override
        protected void onPostExecute(Long aLong) {
            if (aLong==0){
                updateThreads(subcategory.getThreadList());
            }
            else {
                Toast.makeText(getContext(),"ERROR during load from database",Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Sjekker om nettverkstilgang
    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
