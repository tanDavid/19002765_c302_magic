package sg.edu.rp.webservices.a19002765_c302_magic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CardActivity_19002765 extends AppCompatActivity {

    private ListView lvDisplay;
    private ArrayAdapter<Card> aaDisplay;
    private ArrayList<Card> alDisplay;
    private AsyncHttpClient client;

    private String colourId, loginId, apikey, role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        lvDisplay = (ListView) findViewById(R.id.lvCards);
        alDisplay = new ArrayList<Card>();
        aaDisplay = new ArrayAdapter<Card>(this, android.R.layout.simple_list_item_1, alDisplay);
        lvDisplay.setAdapter(aaDisplay);

        client = new AsyncHttpClient();
        Intent a = getIntent();
        colourId = a.getStringExtra("colourId");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        loginId = prefs.getString("loginId", "");
        apikey = prefs.getString("apikey", "");
        role = prefs.getString("role", "");


        if (loginId.equalsIgnoreCase("") || apikey.equalsIgnoreCase("")) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        alDisplay.clear();

        RequestParams params = new RequestParams();
        params.add("loginId", loginId);
        params.add("apikey", apikey);
        params.add("colId", colourId);

        client.post("http://10.0.2.2/C302_Assignment/19002765_getCardsByColour.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObj = response.getJSONObject(i);

                            int cardId = jsonObj.getInt("cardId");
                            String name = jsonObj.getString("cardName");
                            int colourId = jsonObj.getInt("colourId");
                            int typeId = jsonObj.getInt("typeId");
                            Double price = jsonObj.getDouble("price");
                            int qty = jsonObj.getInt("quantity");

                            Card items = new Card(cardId,colourId, typeId, qty, name, price);
                            alDisplay.add(items);
                        }
                        aaDisplay = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, alDisplay);
                        lvDisplay.setAdapter(aaDisplay);

                    } else {
                        Toast.makeText(getApplicationContext(), "No Items under this category", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if(role.equalsIgnoreCase("admin")){
            getMenuInflater().inflate(R.menu.submain, menu);
        }else if(role.equalsIgnoreCase("customer")){
            getMenuInflater().inflate(R.menu.main, menu);
        }


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
        }else if (id == R.id.menuAddCard){
            Intent b = new Intent(getApplicationContext(), CreateCardActivity_19002765.class);
            startActivity(b);
        }else if(id == R.id.menuCardByColour){
            Intent b = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(b);
        }
        return super.onOptionsItemSelected(item);
    }
}
