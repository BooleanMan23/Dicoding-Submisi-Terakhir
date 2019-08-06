package com.example.dicoding_submisi2.contentProvider;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.widget.Switch;
import android.widget.Toast;

import java.util.HashMap;


public class ContactProvider extends ContentProvider {
//unique name untuk aplikasi dapat
    static final String PROVIDER_NAME = "com.example.dicoding_submisi2.contentProvider.ContactProvider";
    //URL yang selalu dimulai dengan content://
    //Favorit ialah direktori
    public static final String URL = "content://"+PROVIDER_NAME+"/favorit";
    public static final Uri CONTENT_URL = Uri.parse(URL);
    public static final String title = "title";
    public static final String overview = "overview";
    public static final String posterLink = "posterLink";
    public static final String releaseDate = "releaseDate";
    public static final String id = "id";
    public static final String isMovie = "isMovie";


    private SQLiteDatabase sqlDB;
    static final String DATABASE_NAME = "favoritProvider";
    static final String TABLE_NAME = "favoritProvider";
    static final int uriCode = 1;
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE = "CREATE TABLE "+TABLE_NAME + "(title VARCHAR, overview VARCHAR, posterLink VARCHAR, releaseDate VARCHAR,  id DOUBLE, isMovie DOUBLE );";


    static final  UriMatcher uriMatcher;
    static {
        uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "favorit", 1);

    }

    @Override
    public boolean onCreate() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        sqlDB = dbHelper.getWritableDatabase();
        if (sqlDB != null){
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public Cursor query(Uri uri,  String[] projection, String selection,  String[] selectionArgs,  String sortOrder) {
//        projection ialah kolom kolom apa yang ingin diambil dari query
//        selection seperti WHERE id =
//        selection argument 1,2,3
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)){
            case uriCode:
                break;
                default:
                    throw new IllegalArgumentException("Unknown URI "+uri);
        }

            Cursor cursor = sqLiteQueryBuilder.query(sqlDB, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public String getType( Uri uri) {
        switch (uriMatcher.match(uri)){
            case uriCode:
                return "vnd.android.cursor.dir/favorit";

            default:
                throw new IllegalArgumentException("unsupported  URI "+uri);
        }
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {
       long rowID = sqlDB.insert(TABLE_NAME, null, values);
       if (rowID > 0){
           Uri _uri = ContentUris.withAppendedId(CONTENT_URL, rowID);
           getContext().getContentResolver().notifyChange(_uri, null);

           Toast.makeText(getContext(), " insert success", Toast.LENGTH_LONG)
                   .show();
           return uri;
       }
       else {
           Toast.makeText(getContext(), "insert failed", Toast.LENGTH_SHORT).show();
           return null;
       }
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        int rowsDeleted = 0;
        switch (uriMatcher.match(uri)){
            case uriCode:
              rowsDeleted = sqlDB.delete(TABLE_NAME, selection, selectionArgs);
              break;

            default:
                throw new IllegalArgumentException("Unknownn  URI "+uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;

    }

    @Override
    public int update(Uri uri,  ContentValues values, String selection,  String[] selectionArgs) {
        int rowsUpdated = 0;
        switch (uriMatcher.match(uri)){
            case uriCode:
                rowsUpdated = sqlDB.update(TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("unsupported  URI "+uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase sqlDB) {
            sqlDB.execSQL(CREATE_DB_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqlDB, int oldVersion, int newVersion) {
            sqlDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqlDB);

        }
    }
}
