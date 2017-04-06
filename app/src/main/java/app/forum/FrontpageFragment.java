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
import android.widget.ListView;
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

public class FrontpageFragment extends Fragment
{
    ListView categoryListView;
    public static CategoryAdapter categoryAdapter;
    public static ArrayList<Category> categories;
    private final String DBCOMMAND = "?action=get_frontPage";


    public FrontpageFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_frontpage, container, false);
        categoryListView = (ListView)view.findViewById((R.id.category_listView));

        categoryAdapter = new CategoryAdapter(this.getContext(), MainActivity.categories);

        categoryListView.setAdapter(categoryAdapter);

        // Inflate the layout for this fragment
        return view;
    }


    private ArrayList<Category> generateTestData()
    {
        ArrayList<Category> categoryList = new ArrayList<Category>();
        ArrayList<SubCategory> list1 = new ArrayList<SubCategory>();
        SubCategory subcat1 = new SubCategory("Baking", "Discuss recipes and content about bread, pies, cakes, deserts... Everything that has to do with baking!");

        User user = new User("Børre Balle", "lol@troll.nub", "lettpassord");

        Thread thread = new Thread(user, "Cheesecake", "I prefer cheesecake, what about you?");
        thread.addPost(user, "Actually, I think cheesecake is the best!");
        thread.addPost(user, "You know what, chocolate cake is pretty nice too...");
        thread.addPost(user, "But have you tried banana-pie?");
        thread.addPost(user, "Nevermind, you should have a cheesecake.");

        subcat1.addThread(thread);
        list1.add(subcat1);
        SubCategory subcat2 = new SubCategory("Cooking", "Meat, fish, vegetables... Do you have a great dinner idea, or do you need help finding one? Check in here.");
        list1.add(subcat2);
        Category c1 = new Category("Food", list1);
        categoryList.add(c1);

        ArrayList<SubCategory> list2 = new ArrayList<SubCategory>();
        SubCategory subcat3 = new SubCategory("Series", "Want to know which new show to watch?");
        list2.add(subcat3);
        SubCategory subcat4 = new SubCategory("Movies", "Discuss films and cinematography!");
        list2.add(subcat4);
        Category c2 = new Category("Media", list2);
        categoryList.add(c2);
        return categoryList;
    }

    public void loadFrontPage(){
        if (isOnline()){
            FrontPageLoader frontPageLoader = new FrontPageLoader();
            frontPageLoader.execute(MainActivity.DATABASEURL+DBCOMMAND);
        }
        else {
            Toast.makeText(getActivity(), "Ingen nettverkstilgang. Kan ikke laste varer.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void updateFrontPage(ArrayList<Category> newList){
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(),newList);
        categoryListView.setAdapter(categoryAdapter);
    }

    private class FrontPageLoader extends AsyncTask<String,Void,Long>{


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
                    String frontPageData = sb.toString();
                    categories = Category.makeCategoryList(frontPageData);
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
                updateFrontPage(categories);
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
