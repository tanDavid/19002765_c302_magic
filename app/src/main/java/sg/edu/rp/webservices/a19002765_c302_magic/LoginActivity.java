package sg.edu.rp.webservices.a19002765_c302_magic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {


    private EditText etLoginID, etPassword;
    private Button btnSubmit;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginID = (EditText) findViewById(R.id.editTextLoginID);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        client = new AsyncHttpClient();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etLoginID.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Login failed. Please enter username.", Toast.LENGTH_LONG).show();

                } else if (password.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Login failed. Please enter password.", Toast.LENGTH_LONG).show();

                } else {
                    //proceed to authenticate user
                    RequestParams params = new RequestParams();
                    params.add("username", etLoginID.getText().toString());
                    params.add("password", etPassword.getText().toString());

                     client.post("http://10.0.2.2/C302_Assignment/doLogin.php", params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                String checkLogin = response.get("authenticated").toString();
                                if (checkLogin.equalsIgnoreCase("true")) {
                                    Log.d("TAG", response.get("authenticated").toString());
                                    Log.d("TAG", response.get("apikey").toString());


                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("loginId", response.get("id").toString());
                                    editor.putString("apikey", response.get("apikey").toString());
                                    editor.putString("role", response.get("role").toString());
                                    editor.commit();


                                    Intent i = new Intent(getApplicationContext(), BuyActivity.class);
                                    i.putExtra("role", response.get("role").toString());
                                    startActivity(i);
                                } else {
                                    Log.d("TAG", "FAILED");
                                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    });
                }
            }
        });

    }
}
