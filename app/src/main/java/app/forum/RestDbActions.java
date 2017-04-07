package app.forum;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestDbActions {

    private static final String INSERTTHREAD = "insert_thread", UPDATETHREAD = "update_thread",
                                REMOVETHREAD = "remove_thread", INSERTPOST = "insert_post",
                                UPDATEPOST = "update_post", REMOVEPOST = "remove_post",
                                LOGIN = "login";

    private static AsyncRestAdapter adapter = new AsyncRestAdapter();

    public static void insertThread(String thread, String post, String subcat, String user){
        adapter.execute(INSERTTHREAD,thread,post,subcat,user);
    }

    public static void updateThread(String thread, String post){
        adapter.execute(UPDATETHREAD,thread,post);
    }

    public static void removeThread(String thread){
        adapter.execute(REMOVETHREAD,thread);
    }

    public static void insertPost(String post, String user, String thread){
        adapter.execute(INSERTPOST,post,user,thread);
    }

    public static void updatePost(String post, String postNr){
        adapter.execute(UPDATEPOST,post,postNr);
    }

    public static void removePost(String postNr){
        adapter.execute(REMOVEPOST,postNr);
    }

    public static boolean login(String user, String password){
        // Refresh so that you can call it more than once
        adapter = new AsyncRestAdapter();
        String login = adapter.execute(LOGIN,user,password).toString();
        return (login.equals("true"));
    }

    private static class AsyncRestAdapter extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            String URI = MainActivity.DATABASEURL+"?action="+params[0]+"&";
            switch (params[0]){
                case INSERTTHREAD: URI += "thread=" + params[1] + "&post=" + params[2]
                        + "&subcat=" + params[3] + "&user=" + params[4];
                    break;
                case UPDATETHREAD: URI += "thread=" + params[1] + "&post=" + params[2];
                    break;
                case REMOVETHREAD: URI += "thread=" + params[1];
                    break;
                case INSERTPOST: URI += "post=" + params[1] + "&user=" + params[2]
                        + "&thread=" + params[3];
                    break;
                case UPDATEPOST:
                {
                    int postNr = Integer.parseInt(params[2]);
                    URI += "post=" + params[1] + "&postNr=" + postNr;
                }
                    break;
                case REMOVEPOST:
                {
                    int postNr = Integer.parseInt(params[1]);
                    URI += "postNr=" + postNr;
                }
                    break;
                case LOGIN: URI += "user=" + params[1] + "&password=" + params[2];
                    break;
            }
            try {
                URL url = new URL(URI);
                connection = (HttpURLConnection) url.openConnection();
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
                    String response = sb.toString();
                    if (params[0].equals(LOGIN) && response.equals(true)) response = LOGIN;
                    return response;
                }
                else {
                    return "";
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "";
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals(LOGIN)){
                //todo login
                Log.v("loggedin", s);
            }
            else if (s.equals("true")){
                //everything fine
                Log.v("true", s);
            }
            else if (s.equals("false")){
                //something went wrong
                Log.v("false", s);
            }
            else {
                //something went wrong
                Log.v("something went wrong", s);
            }
        }
    }
}
