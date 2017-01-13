package com.sapient.mymusicalbum.db.helper;

import com.sapient.mymusicalbum.album.details.AlbumDetails;
import com.sapient.mymusicalbum.constants.MusicAlbumConstants;
import com.sapient.mymusicalbum.user.UserDetails;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by kshan5 on 1/10/2017.
 */

public class DatabaseHelper {

    DatabaseConfig databaseConfig;
    boolean registerFlag = false;
    boolean loginFlag = false;

    public DatabaseHelper (DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }

    // register user details to database and return true if the user register successfully

    public boolean registerUserData(UserDetails userDetails){

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", "1001");
        contentValues.put(MusicAlbumConstants.USERS_FIRST_NAME, userDetails.getFirstName());
        contentValues.put(MusicAlbumConstants.USERS_LAST_NAME, userDetails.getLastName());
        contentValues.put(MusicAlbumConstants.USERS_EMAIL, userDetails.getEmailId());
        contentValues.put(MusicAlbumConstants.USERS_PASSWORD, userDetails.getPassword());
        contentValues.put(MusicAlbumConstants.USERS_MOBILE, userDetails.getMobileNumber());
        contentValues.put(MusicAlbumConstants.USERS_GENDER, userDetails.getGender());
        contentValues.put(MusicAlbumConstants.USERS_LANGUAGE, userDetails.getLanguage());
        contentValues.put(MusicAlbumConstants.USERS_GENDER_GRP, userDetails.getAgeGroup());

        long result = databaseConfig.getWriteableDB().insert(MusicAlbumConstants.TABLE_NAME, null, contentValues);

        if(result == -1){
            registerFlag = true;
        }
        return registerFlag;
    }


    // allow user to login tho the home page

    public boolean loginUser(String emailId, String password){

        Cursor cursor  = databaseConfig.getWriteableDB().query
            (
                MusicAlbumConstants.USERS_TABLE,
                new String[]{MusicAlbumConstants.USERS_PASSWORD},
                MusicAlbumConstants.USERS_EMAIL+"=?",
                new String[]{emailId},
                null,
                null,
                null,
                null
            );

        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            String pwd = cursor.getString(cursor.getColumnIndex("PASSWORD"));
            if(pwd.equals(password)){
               loginFlag = true;
            }
        }
        return loginFlag;
    }

    public boolean storeAlbums(ContentValues contentValues){

        long result = databaseConfig.getWriteableDB().insert("ALBUMS", null, contentValues);

        if(result == -1)
        {
            return false;
        }else
        {
            return true;
        }
    }
    // Get album details form databae

    public ArrayList<AlbumDetails> getAlbumDetails(String albumId){

        ArrayList<AlbumDetails> albumList= null;

        Cursor cursor  = databaseConfig.getWriteableDB().query
                (
                        MusicAlbumConstants.ALBUMS_TABLE,
                        new String[]
                        {
                                MusicAlbumConstants.ALBUM_ID,
                                MusicAlbumConstants.ALBUM_NAME,
                                MusicAlbumConstants.ALBUM_ARTIST,
                                MusicAlbumConstants.ALBUM_RELEASE_DATE,
                                MusicAlbumConstants.ALBUM_PRODUCER,
                                MusicAlbumConstants.ALBUM_GENRES,
                                MusicAlbumConstants.ALBUM_SONGS,
                                MusicAlbumConstants.ALBUM_RATING,
                                MusicAlbumConstants.ALBUM_AWARDS
                        },
                        MusicAlbumConstants.ALBUM_ID+"=?",
                        new String[]{albumId},
                        null,
                        null,
                        null,
                        null
                );
        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {

            AlbumDetails albumDetails = new AlbumDetails();
            albumList = new ArrayList<AlbumDetails>();

            albumDetails.setAlbumId(cursor.getString(cursor.getColumnIndex(MusicAlbumConstants.ALBUM_ID)));
            albumDetails.setAlbumName(cursor.getString(cursor.getColumnIndex(MusicAlbumConstants.ALBUM_NAME)));
            albumDetails.setArtist(cursor.getString(cursor.getColumnIndex(MusicAlbumConstants.ALBUM_ARTIST)));
            albumDetails.setReleaseDate(cursor.getString(cursor.getColumnIndex(MusicAlbumConstants.ALBUM_RELEASE_DATE)));
            albumDetails.setProducer(cursor.getString(cursor.getColumnIndex(MusicAlbumConstants.ALBUM_PRODUCER)));
            albumDetails.setGenres(cursor.getString(cursor.getColumnIndex(MusicAlbumConstants.ALBUM_GENRES)));
            albumDetails.setSongs(cursor.getString(cursor.getColumnIndex(MusicAlbumConstants.ALBUM_SONGS)));
            albumDetails.setRating(cursor.getString(cursor.getColumnIndex(MusicAlbumConstants.ALBUM_RATING)));
            albumDetails.setAwards(cursor.getString(cursor.getColumnIndex(MusicAlbumConstants.ALBUM_AWARDS)));
            albumList.add(albumDetails);

        }
        return albumList;
    }


    // Check user availabilty in database

    public int checkUserAvailabilty(String emailId){

        Cursor cursor  = databaseConfig.getWriteableDB().query
                (
                        MusicAlbumConstants.USERS_TABLE,
                        new String[]{MusicAlbumConstants.USERS_EMAIL},
                        MusicAlbumConstants.USERS_EMAIL+"=?",
                        new String[]{emailId},
                        null,
                        null,
                        null,
                        null
                );

        cursor.moveToFirst();
        return cursor.getCount();
    }


    // Close db connection
    public void dbClose(){
        if(databaseConfig!=null){
            databaseConfig.close();
        }
    }

}
