package com.example.pr_idi.mydatabaseexample;


import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Visualitzacio extends AppCompatActivity {

    private RecyclerView filmlist;
    private FilmData filmdata;
    private FilmAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualitzacio);
        filmlist = (RecyclerView) findViewById(R.id.llista);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        filmlist.setLayoutManager(manager);
        CrearLlista();

        FloatingActionButton fab =(FloatingActionButton) findViewById(R.id.fbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Visualitzacio.this);
                builder.setTitle("ADD FILM");
                final View v1 = LayoutInflater.from(Visualitzacio.this).inflate(R.layout.dialog,null);
                builder.setView(v1);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText title = (EditText) v1.findViewById(R.id.title);
                        EditText director = (EditText) v1.findViewById(R.id.director);
                        EditText year = (EditText) v1.findViewById(R.id.year);
                        EditText country = (EditText) v1.findViewById(R.id.country);
                        EditText protagonist = (EditText) v1.findViewById(R.id.protagonist);
                        EditText critics_rate = (EditText) v1.findViewById(R.id.critics_rate);

                        String t = title.getText().toString();
                        String d = director.getText().toString();
                        int y = Integer.parseInt(year.getText().toString());
                        String c = country.getText().toString();
                        String p = protagonist.getText().toString();
                        int cr = Integer.parseInt(critics_rate.getText().toString());

                        filmdata.createFilm(t,d,y,c,p,cr, 0);
                        adapter.updatedata(filmdata.getAllFilms());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }

    private void CrearLlista(){
        filmdata = new FilmData(this);
        filmdata.open();
        List<Film> films = filmdata.getAllFilms();

        films = filmdata.getAllFilms();
        Collections.sort(films, new Comparator<Film>() {
            @Override
            public int compare(Film lhs, Film rhs) {
                return Integer.compare(lhs.getYear(), rhs.getYear());
            }
        });
        adapter = new FilmAdapter(this,films);

        filmlist.setAdapter(adapter);
    }

    public void onListClick (int position){
        final Film film;
        film = filmdata.getAllFilms().get(position);

        new AlertDialog.Builder(this)
                .setTitle("Delete Films")
                .setMessage("Are you sure you want to delete this film?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filmdata.deleteFilm(film);
                        adapter.updatedata(filmdata.getAllFilms());
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}

