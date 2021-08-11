package sg.edu.rp.webservices.a19002765_c302_magic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CreateCardActivity_19002765 extends AppCompatActivity {

    EditText etName, etColourID, etTypeID, etPrice, etQty;
    Button btnAdd;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.etName);
        etColourID = findViewById(R.id.etColourID);
        etTypeID = findViewById(R.id.etTypeID);
        etPrice = findViewById(R.id.etPrice);
        etQty = findViewById(R.id.etQty);

        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().isEmpty()) {
                    Toast.makeText(CreateCardActivity_19002765.this, "Name cannot be blank", Toast.LENGTH_SHORT).show();
                } else if (etColourID.getText().toString().isEmpty()) {
                    Toast.makeText(CreateCardActivity_19002765.this, "Colour ID cannot be blank", Toast.LENGTH_SHORT).show();
                } else if (etTypeID.getText().toString().isEmpty()) {
                    Toast.makeText(CreateCardActivity_19002765.this, "Type ID cannot be blank", Toast.LENGTH_SHORT).show();
                } else if (etPrice.getText().toString().isEmpty()) {
                    Toast.makeText(CreateCardActivity_19002765.this, "Price cannot be blank", Toast.LENGTH_SHORT).show();
                } else if (etQty.getText().toString().isEmpty()) {
                    Toast.makeText(CreateCardActivity_19002765.this, "Quantity cannot be blank", Toast.LENGTH_SHORT).show();
                } else {
                    //CODE HERE FOR ADDING

                    int colID = Integer.parseInt(etColourID.getText().toString());
                    int typeID = Integer.parseInt(etTypeID.getText().toString());
                    if (colID < 1 || colID > 5) {
                        Toast.makeText(CreateCardActivity_19002765.this, "Colour ID must be 1 to 5", Toast.LENGTH_SHORT).show();
                    } else if (typeID < 1 || typeID > 4) {
                        Toast.makeText(CreateCardActivity_19002765.this, "Type ID must be 1 to 4", Toast.LENGTH_SHORT).show();

                    } else {

                        //READY TO ADD

                        client = new AsyncHttpClient();
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String loginId = prefs.getString("loginId", "");
                        String apikey = prefs.getString("apikey", "");

                        RequestParams params = new RequestParams();

                        params.add("loginId", loginId);
                        params.add("apikey", apikey);
                        params.add("name", etName.getText().toString());
                        params.add("colourID", etColourID.getText().toString());
                        params.add("typeID", etTypeID.getText().toString());
                        params.add("price", etPrice.getText().toString());
                        params.add("qty", etQty.getText().toString());

                        client.post("http://10.0.2.2/C302_Assignment/19002765_createCard.php", params, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                try {

                                    Log.i("JSON Results", response.toString());
                                    Toast.makeText(getApplicationContext(), "Card Added Successfully", Toast.LENGTH_LONG).show();
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }


                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.submain, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Intent b = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(b);
            return true;
        } else if (id == R.id.menuAddCard) {
            Intent b = new Intent(getApplicationContext(), CreateCardActivity_19002765.class);
            startActivity(b);
        } else if (id == R.id.menuCardByColour) {
            Intent b = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(b);
        }
        return super.onOptionsItemSelected(item);
    }
}
