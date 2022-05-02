package fr.iut.InfoFilm.model;

import android.content.Context;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import java.io.File;
import java.util.ArrayList;

public class DaoHistorique  extends SQLiteOpenHelper{
    private ArrayList<Film> historique;
    private static final String DB_PATH="/data/data/fr.el2d.epi/databases/";
    private static final String DATABASE_NAME="histo.db";
    private static final int DATABASE_VERSION=1;
    private SQLiteDatabase base;
    private final Context context;

    public DaoHistorique(Context c) {
        super(c,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=c;
        historique = new ArrayList<>();

    }
    public void addHisto(Film f){
        this.historique.add(f);
    }

    public boolean checkDataBase(){
        SQLiteDatabase checkDB;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteCantOpenDatabaseException e){
            Log.i("DATABASE","Database not found");
            return false;
        }

        if (checkDB!=null){
            Log.i("DATABASE","Database found");
            checkDB.close();
            return true;
        }else{
            Log.i("DATABASE","Database not found");
            return false;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.i("Database","Database already exist");
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                System.out.println("Error copying data" +  e.getMessage());
            }
        }
    }

    private void copyDataBase() throws IOException
    {
        Log.i("DATABASE","Copie de la BD");
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0)
        {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    @Override
    public synchronized void close() {
        if(base != null)
            base.close();
        super.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
