package app.forum;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mail = preferences.getString("mail", "");

        super.onCreate(savedInstanceState);
        if(mail.isEmpty())
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
                    if(!loginText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty())
                    {
                        preferences.edit().putString("mail", loginText.getText().toString()).apply();

                        // Find the corresponding user from the mainactivity userlist and set currentuser to that
                        for(User u : MainActivity.userList)
                        {
                            if(u.getMail().equals(loginText.getText().toString()))
                            {
                                MainActivity.currentUser = u;
                                Toast.makeText(getBaseContext(), "Logged in as " + MainActivity.currentUser.getUsername(), Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "Incorrect username or password.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            setContentView(R.layout.activity_settings);

            Button logOutButton = (Button)findViewById(R.id.settings_logoutButton);

            logOutButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    preferences.edit().remove("mail").apply();
                    MainActivity.currentUser = null;

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

            Button cancelButton = (Button)findViewById(R.id.settings_cancelButton);

            cancelButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
