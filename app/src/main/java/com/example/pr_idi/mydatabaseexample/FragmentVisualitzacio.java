package com.example.pr_idi.mydatabaseexample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentVisualitzacio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentVisualitzacio extends Fragment implements callback{
    public static final String ARG_PAGE = "ARG_PAGE";
    private RecyclerView filmlist;
    private FilmData filmdata;
    private FilmAdapter adapter;
    private int mPage;

    public static FragmentVisualitzacio newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentVisualitzacio fragment = new FragmentVisualitzacio();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_visualitzacio, container, false);

        filmlist = (RecyclerView) view.findViewById(R.id.llista);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        filmlist.setLayoutManager(manager);

        if(mPage == 1){ //pendent movies
            ListPendents();
        }
        else if(mPage == 2) { //seen movies
            ListSeen();
        }

        FloatingActionButton fab =(FloatingActionButton) view.findViewById(R.id.fbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("ADD FILM");
                final View v1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
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
        adapter.setListener(this);

        return view;
    }

    private void ListPendents(){
        filmdata = new FilmData(getActivity());
        filmdata.open();
        List<Film> films = filmdata.getPendentFilms();

        Collections.sort(films, new Comparator<Film>() {
            @Override
            public int compare(Film lhs, Film rhs) {
                return Integer.compare(lhs.getYear(), rhs.getYear());
            }
        });
        adapter = new FilmAdapter( getActivity(),films);

        filmlist.setAdapter(adapter);
    }
    private void ListSeen(){
        filmdata = new FilmData(getActivity());
        filmdata.open();
        List<Film> films = filmdata.getSeenFilms();

        Collections.sort(films, new Comparator<Film>() {
            @Override
            public int compare(Film lhs, Film rhs) {
                return Integer.compare(lhs.getYear(), rhs.getYear());
            }
        });
        adapter = new FilmAdapter( getActivity(),films);

        filmlist.setAdapter(adapter);
    }

    public void onListClick (int position){
        final Film film;

        if(mPage == 1) film = filmdata.getPendentFilms().get(position);
        else film = filmdata.getSeenFilms().get(position);


        new AlertDialog.Builder(getActivity())
                .setTitle("Delete Films")
                .setMessage("Are you sure you want to delete this film?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filmdata.deleteFilm(film);
                        if(mPage == 1) adapter.updatedata(filmdata.getPendentFilms());
                        else adapter.updatedata(filmdata.getSeenFilms());
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }

    public void onDoubleClickList (int position){
        System.out.println("holaaa");
        final Film film;
        int auxValue = (mPage == 1) ? 1 : 0;
        if(mPage == 1) film = filmdata.getPendentFilms().get(position);
        else film = filmdata.getSeenFilms().get(position);
        filmdata.ChangePendentSeen(film.getTitle(), auxValue);
        if(mPage == 1) adapter.updatedata(filmdata.getPendentFilms());
        else adapter.updatedata(filmdata.getSeenFilms());
    }

}