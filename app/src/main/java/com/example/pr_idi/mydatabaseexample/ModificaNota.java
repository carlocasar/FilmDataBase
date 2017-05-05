package com.example.pr_idi.mydatabaseexample;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class ModificaNota extends AppCompatActivity {
    private FilmData filmData;
    private EditText et1;
    private Spinner spinner1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_nota);

        filmData = new FilmData(this);
        filmData.open();

        spinner1 = (Spinner) findViewById(R.id.spinner);
        et1=(EditText)findViewById(R.id.editText2);

        List<Film> values = filmData.getAllFilms();
        ArrayAdapter<Film> adapter = new ArrayAdapter<Film>(this,android.R.layout.simple_spinner_item, values);
        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selec=spinner1.getSelectedItem().toString();
                String[] parts = selec.split("-");
                int aux = filmData.getNotaFilm(parts[0]);
                et1.setText(Integer.toString(aux));
                return;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                et1=(EditText)findViewById(R.id.editText2);
                String nota = et1.getText().toString();
                spinner1 = (Spinner) findViewById(R.id.spinner);
                String filmName = spinner1.getSelectedItem().toString();
                String[] parts = filmName.split("-");
                if (filmData.modificaNota(parts[0],nota) != 0) {
                    Toast notificacion=Toast.makeText(this,"Se ha modificado la nota correctamente!.",Toast.LENGTH_LONG);
                    notificacion.show();
                }
                else {
                    Toast notificacion=Toast.makeText(this,"Error: Nota no modificada.",Toast.LENGTH_LONG);
                    notificacion.show();
                }
                break;
            case R.id.button3:
                finish();
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
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
