package app.forum;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends Activity
{
    boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(!loggedIn)
        {
            setContentView(R.layout.activity_settings);
        }
        else
        {
            setContentView(R.layout.fragment_login);

            final EditText loginText = (EditText)findViewById(R.id.login_username);

            final EditText passwordText = (EditText)findViewById(R.id.login_password);

            Button loginButton = (Button)findViewById(R.id.login_button);

            loginButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // TODO db check, if login correct MainActivity.CurrentUser = user that corresponds with db
                    if(!loginText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty() && MainActivity.currentUser != null)
                    {
                        Toast.makeText(getBaseContext(), "Logged in as " + MainActivity.currentUser.getUsername(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "Incorrect username or password.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
