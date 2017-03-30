package app.forum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment
{


    public LoginFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final EditText loginText = (EditText)view.findViewById(R.id.login_username);

        final EditText passwordText = (EditText)view.findViewById(R.id.login_password);

        Button loginButton = (Button)view.findViewById(R.id.login_button);

        final Fragment fragment = this;
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // TODO db check, if login correct MainActivity.CurrentUser = user that corresponds with db
                if(!loginText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty() && MainActivity.currentUser != null)
                {
                    Toast.makeText(getContext(), "Logged in as " + MainActivity.currentUser, Toast.LENGTH_SHORT).show();
                    MainActivity.removeFragment(fragment);
                }
                else
                {
                    Toast.makeText(getContext(), "Incorrect username or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
