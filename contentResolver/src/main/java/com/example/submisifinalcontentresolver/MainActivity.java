package com.example.submisifinalcontentresolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

//    target content provider
    static final Uri CONTENT_URL = Uri.parse("content://com.example.dicoding_submisi2.contentProvider.ContactProvider/favorit");

//    com.example.dicoding_submisi2.contentProvider.ContactProvider
    TextView contactsTextView = null;
    EditText deleteIDEditText, idLookupEditText, addNameEditText;
    CursorLoader cursorLoader;
    ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = getContentResolver();
        contactsTextView = findViewById(R.id.contactsTextView);
        contactsTextView = (TextView) findViewById(R.id.contactsTextView);
        deleteIDEditText = (EditText) findViewById(R.id.deleteIDEditText);
        idLookupEditText = (EditText) findViewById(R.id.idLookupEditText);
        addNameEditText = (EditText) findViewById(R.id.addNameEditText);
        getContact();
    }

    public void getContact(){
         String [] projection = new String[]{"title", "overview","posterLink", "releaseDate", "id", "isMovie"};
         Cursor cursor = resolver.query(CONTENT_URL, projection, null, null, null);
         String contactList = "";
         if (cursor.moveToFirst()){
             do{
                 Log.i("getContact", "==========");
                 String title = cursor.getString(cursor.getColumnIndex("title"));
                 String overview = cursor.getString(cursor.getColumnIndex("overview"));
                 String posterLink = cursor.getString(cursor.getColumnIndex("posterLink"));
                 String releaseDate = cursor.getString(cursor.getColumnIndex("releaseDate"));
                 String id = cursor.getString(cursor.getColumnIndex("id"));
                 String isMovie = cursor.getString(cursor.getColumnIndex("isMovie"));
                 Log.i("getContact-title", title);
                 Log.i("getContact-overview", overview);
                 Log.i("getContact-posterLink", posterLink);
                 Log.i("getContact-releaseDate", releaseDate);
                 Log.i("getContact-id", id);
                 Log.i("getContact-isMovie", isMovie);






             }while (cursor.moveToNext());
         }
         contactsTextView.setText(contactList);
    }

    public void deleteContact(View view) {

        String idToDelete = deleteIDEditText.getText().toString();

        // Use the resolver to delete ids by passing the content provider url
        // what you are targeting with the where and the string that replaces
        // the ? in the where clause
        long idDeleted = resolver.delete(CONTENT_URL,
                "id = ? ", new String[]{idToDelete});

        getContact();

    }

    public void lookupContact(View view) {

        // The id we want to search for
        String idToFind = idLookupEditText.getText().toString();

        // Holds the column data we want to retrieve
        String[] projection = new String[]{"title", "overview","posterLink", "releaseDate", "id", "isMovie"};

        // Pass the URL for Content Provider, the projection,
        // the where clause followed by the matches in an array for the ?
        // null is for sort order
        Cursor cursor = resolver.query(CONTENT_URL,
                projection, "id = ? ", new String[]{idToFind}, null);

        String contactList = "";

        // Cycle through our one result or print error
        if(cursor.moveToFirst()){

            String title = cursor.getString(cursor.getColumnIndex("title"));
            String overview = cursor.getString(cursor.getColumnIndex("overview"));
            String posterLink = cursor.getString(cursor.getColumnIndex("posterLink"));
            String releaseDate = cursor.getString(cursor.getColumnIndex("releaseDate"));
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String isMovie = cursor.getString(cursor.getColumnIndex("isMovie"));
            contactList = contactList + title + " : " + overview + "\n";

        } else {

            Toast.makeText(this, "Contact Not Found", Toast.LENGTH_SHORT).show();

        }

        contactsTextView.setText(contactList);

    }

    public void showContacts(View view) {

        getContact();

    }


}
