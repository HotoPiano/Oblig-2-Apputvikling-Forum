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

public class PostFragment extends Fragment
{
    final static String DBCOMMAND = "?action=get_thread&thread=";

    Thread thread;
    ListView threadListView;

    public PostFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);


        TextView threadTitleView = (TextView)view.findViewById(R.id.post_title);
        threadTitleView.setText(thread.getTitle());

        threadListView = (ListView)view.findViewById(R.id.post_listView);
        PostAdapter postAdapter = new PostAdapter(view.getContext(), thread);
        threadListView.setAdapter(postAdapter);

        ImageButton newPostButton = (ImageButton)view.findViewById(R.id.post_newpost);

        // Newpostbutton clicked, popup post fragment
        newPostButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NewPostFragment fragment = new NewPostFragment();
                MainActivity.addFragment(fragment);
            }
        });

        return view;
    }

    public void setPost(Thread thread)
    {
        this.thread = thread;
    }

    public void loadThread(String thread){
        if (isOnline()){
            postLoader postLoader = new postLoader();
            postLoader.execute(MainActivity.DATABASEURL+DBCOMMAND+thread);
        }
        else {
            Toast.makeText(getActivity(), "Ingen nettverkstilgang.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void updateThreads(){
        PostAdapter postAdapter = new PostAdapter(getContext(),thread);
        threadListView.setAdapter(postAdapter);
    }

    private class postLoader extends AsyncTask<String,Void,Long> {


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
                    String postData = sb.toString();
                    thread.postList = Post.makePostList(postData);
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
                updateThreads();
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
