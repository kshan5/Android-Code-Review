package com.sapient.mymusicalbum.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sapient.mymusicalbum.album.details.AlbumDetails;
import com.sapient.mymusicalbum.db.helper.DatabaseConfig;
import com.sapient.mymusicalbum.db.helper.DatabaseHelper;


import java.util.ArrayList;

public class AlbumDetailedView extends AppCompatActivity{

    ImageView imageView;
    TextView alName, alArtist, alRelDate, alProducer, alGenres, alSongs, alRatings, alAwards;
    Button jobOpening;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detailed_view);

        initializeDetailedViewElements();
        databaseHelper = new DatabaseHelper(DatabaseConfig.getInstance(getApplicationContext()));
        long selectedId = getIntent().getLongExtra("selectedId", 0);
        String albumId = Long.toString(selectedId);
        if(albumId!=null){

            if(albumId.equals("0") || albumId.equals("2")|| albumId.equals("4")){
                setImageView(albumId);
                setAlbumDetailedView(databaseHelper.getAlbumDetails("1001"));
                databaseHelper.dbClose();
            }else if(albumId.equals("1")|| albumId.equals("3") || albumId.equals("5") ){
                setImageView(albumId);
                setAlbumDetailedView(databaseHelper.getAlbumDetails("1002"));
                databaseHelper.dbClose();

            }else{
                setImageView(albumId);
                setAlbumDetailedView(databaseHelper.getAlbumDetails("1003"));
                databaseHelper.dbClose();
            }
        }
    }

    public void initializeDetailedViewElements(){
        imageView = (ImageView) findViewById(R.id.album_poster);
        alName = (TextView)findViewById(R.id.al_name);
        alArtist = (TextView)findViewById(R.id.al_artist);
        alRelDate = (TextView)findViewById(R.id.al_rel_date);
        alProducer = (TextView)findViewById(R.id.al_producer);
        alGenres = (TextView)findViewById(R.id.al_geners);
        alSongs = (TextView)findViewById(R.id.al_songs);
        alRatings = (TextView)findViewById(R.id.al_ratings);
        alAwards = (TextView)findViewById(R.id.al_awards);
        //jobOpening = (Button) findViewById(R.id.jobOpening);
        //jobOpening.setOnClickListener(this);
    }

    public void setImageView(String imgId){

        switch (imgId){
            case "0":
                imageView.setImageResource(R.drawable.a1001);
                break;
            case "1":
                imageView.setImageResource(R.drawable.a1002);
                break;
            case "2":
                imageView.setImageResource(R.drawable.a1003);
                break;
            case "3":
                imageView.setImageResource(R.drawable.a1004);
                break;
            case "4":
                imageView.setImageResource(R.drawable.a1005);
                break;
            case "5":
                imageView.setImageResource(R.drawable.a1006);
                break;
            case "6":
                imageView.setImageResource(R.drawable.a1007);
                break;
            case "7":
                imageView.setImageResource(R.drawable.a1008);
                break;
            default:
                imageView.setImageResource(R.drawable.album9);
        }
    }

    public void setAlbumDetailedView(ArrayList<AlbumDetails> viewDetails){

        AlbumDetails albumDetails = viewDetails.get(0);
        alName.setText("Album Name : "+albumDetails.getAlbumName());
        alArtist.setText("Artist : "+albumDetails.getArtist());
        alRelDate.setText("Album Release Date : "+albumDetails.getReleaseDate());
        alProducer.setText("Producer : "+albumDetails.getProducer());
        alGenres.setText("Genres : "+albumDetails.getGenres());
        alSongs.setText("Songs : "+albumDetails.getSongs());
        alRatings.setText("Ratings : "+albumDetails.getRating());
        alAwards.setText("Awards : "+albumDetails.getAwards());

    }

    /*@Override
    public void onClick(View v) {
        startActivity(new Intent(this.getApplicationContext(), JobSearchActivity.class));
    }*/
}
