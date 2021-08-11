package sg.edu.rp.webservices.a19002765_c302_magic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Colour> adapter;
    private ArrayList<Colour> list;
    private AsyncHttpClient client;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv);
        list = new ArrayList<Colour>();
        adapter = new ArrayAdapter<Colour>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        client = new AsyncHttpClient();

        //loginId and apiKey from SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String loginId = prefs.getString("loginId", "");
        String apikey = prefs.getString("apikey", "");
        role = prefs.getString("role", "");

        if (loginId.equalsIgnoreCase("") || apikey.equalsIgnoreCase("")) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }



        RequestParams params = new RequestParams();
        params.add("loginId", loginId);
        params.add("apikey", apikey);

        client.post("http://10.0.2.2/C302_Assignment/19002765_getColours.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObj = response.getJSONObject(i);

                        int colourID = jsonObj.getInt("colourId");
                        String colourName = jsonObj.getString("colourName");


                        Colour col = new Colour(colourID + "", colourName);
                        list.add(col);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }
                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Colour selected = list.get(i);

                Intent a = new Intent(getApplicationContext(), CardActivity_19002765.class);
                a.putExtra("colourId", selected.getColourId());
                startActivity(a);
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
            finish();
            startActivity(getIntent());
        }
        return super.onOptionsItemSelected(item);
    }


}