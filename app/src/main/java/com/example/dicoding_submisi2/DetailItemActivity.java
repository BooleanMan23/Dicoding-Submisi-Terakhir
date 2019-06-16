package com.example.dicoding_submisi2;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailItemActivity extends AppCompatActivity {

    ImageView posterImageView;
    TextView judulTextView;
    TextView deskripsiTextView;
    TextView tanggalRilisTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        posterImageView = (ImageView) findViewById(R.id.detailPosterImageView);
        judulTextView = (TextView) findViewById(R.id.detailJudulFilmTextView);
        deskripsiTextView = (TextView) findViewById(R.id.detailDeskripsiTextView);
        tanggalRilisTextView = (TextView) findViewById(R.id.detailTanggalRilisFilmTextView);
        Item item = getIntent().getExtras().getParcelable("Item");
        posterImageView.setImageResource(item.getPoster());
        judulTextView.setText(item.getJudul());
        deskripsiTextView.setText(item.getDeskripsi());
        tanggalRilisTextView.setText(item.getTanggalRilis());
    }
}
