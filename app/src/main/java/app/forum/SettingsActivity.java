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

import java.util.concurrent.ExecutionException;

public class SettingsActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mail = preferences.getString("userName", "");

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
                    if(!loginText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty())
                    {
                        String userName = loginText.getText().toString();
                        String password = passwordText.getText().toString();

                        Toast.makeText(getBaseContext(), userName + password, Toast.LENGTH_SHORT).show();
                        try
                        {
                            if(RestDbActions.login(userName, password))
                            {
                                // Save value from textfield in preferences "userName"
                                preferences.edit().putString("userName", userName).apply();

                                Toast.makeText(getBaseContext(), "Logged in as " + userName, Toast.LENGTH_SHORT).show();
                                MainActivity.userName = userName;
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(getBaseContext(), "Incorrect username or password.", Toast.LENGTH_SHORT).show();
                        } catch (ExecutionException e)
                        {
                            e.printStackTrace();
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                        Toast.makeText(getBaseContext(), "Fill both fields.", Toast.LENGTH_SHORT).show();
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
                    preferences.edit().remove("userName").apply();
                    MainActivity.userName = "";

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
