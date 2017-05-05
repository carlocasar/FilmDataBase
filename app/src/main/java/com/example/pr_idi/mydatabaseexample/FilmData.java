package com.example.pr_idi.mydatabaseexample;

/**
 * FilmData
 * Created by pr_idi on 10/11/16.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FilmData {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    // Here we only select Title and Director, must select the appropriate columns
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_DIRECTOR};

    public FilmData(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Film createFilm(String title, String director, int year, String country, String protagonist, int critics_rate ) {
        ContentValues values = new ContentValues();
        Log.d("Creating", "Creating " + title + " " + director);

        // Add data: Note that this method only provides title and director
        // Must modify the method to add the full data
        values.put(MySQLiteHelper.COLUMN_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_DIRECTOR, director);
        values.put(MySQLiteHelper.COLUMN_COUNTRY, country);
        values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, year);
        values.put(MySQLiteHelper.COLUMN_PROTAGONIST, protagonist);
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, critics_rate);

        // Actual insertion of the data using the values variable
        long insertId = database.insert(MySQLiteHelper.TABLE_FILMS, null,
                values);

        // Main activity calls this procedure to create a new film
        // and uses the result to update the listview.
        // Therefore, we need to get the data from the database
        // (you can use this as a query example)
        // to feed the view.

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Film newFilm = cursorToFilm(cursor);

        // Do not forget to close the cursor
        cursor.close();

        // Return the book
        return newFilm;
    }

    public void deleteFilm(Film film) {
        long id = film.getId();
        System.out.println("Film deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_FILMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Film> getAllFilms() {
        List<Film> comments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Film cursorToFilm(Cursor cursor) {
        Film film = new Film();
        film.setId(cursor.getLong(0));
        film.setTitle(cursor.getString(1));
        film.setDirector(cursor.getString(2));
        return film;
    }

    public List<Film> getAllActorFilms(String name){
        List<Film> comments = new ArrayList<>();
        String[] whereArgs = new String[] {name};
        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_PROTAGONIST + " = ?", whereArgs, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public List<String> getAllActors()
    {
        List<String> comments = new ArrayList<>();
        String[] oneColumn = { MySQLiteHelper.COLUMN_PROTAGONIST};
        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                oneColumn, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String comment = cursor.getString(0);
            if(!comments.contains(comment)) comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;


    }
    public Integer getNotaFilm(String filmName){
        filmName = filmName.trim();
        String[] whereArgs = new String[] {filmName};
        String[] oneColumn = { MySQLiteHelper.COLUMN_CRITICS_RATE};
        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                oneColumn , MySQLiteHelper.COLUMN_TITLE + " = ?", whereArgs, null, null, null);
        cursor.moveToFirst();
        int nota = -1;
        if (!cursor.isAfterLast()) {
            nota = cursor.getInt(0);
            System.out.println(cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();
        return nota;
    }

    public int modificaNota(String filmName, String nota){
        filmName = filmName.trim();
        String[] whereArgs = new String[] {filmName};
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, nota);
        return database.update(MySQLiteHelper.TABLE_FILMS,values,  MySQLiteHelper.COLUMN_TITLE + " = ?",whereArgs);

    }
}