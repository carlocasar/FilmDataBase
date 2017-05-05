package com.example.pr_idi.mydatabaseexample;

import android.app.ListActivity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;
import java.util.Random;

public class CercaPerActor extends AppCompatActivity {
    private FilmData filmData;
    private EditText et1;
    private Spinner spinner1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerca_per_actor);

        filmData = new FilmData(this);
        filmData.open();

        spinner1 = (Spinner) findViewById(R.id.spinner3);

        List<String> values = filmData.getAllActors();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, values);
        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setLista();
                return;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    public void setLista(){
        String selec=spinner1.getSelectedItem().toString();
        String[] parts = selec.split("-");
        List<Film> values2 = filmData.getAllActorFilms(parts[0].trim());
        ArrayAdapter<Film> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values2);
        ListView lv = (ListView)findViewById(R.id.list2);
        lv.setAdapter(adapter2);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                finish();
                break;

        }
    }

    @Override
    protected void onResume() {
        filmData.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        filmData.close();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.pr_idi.mydatabaseexample/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.pr_idi.mydatabaseexample/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
