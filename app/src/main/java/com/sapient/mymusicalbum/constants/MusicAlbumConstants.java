package com.sapient.mymusicalbum.constants;

/**
 * Created by kshan5 on 1/10/2017.
 */

public class MusicAlbumConstants {

    /* User related Constants */
    public static final String DATABASE_NAME="MUSICSHOP";
    public static final String TABLE_NAME="USERS";
    public static final String USERS_ID="ID";
    public static final String USERS_PASSWORD="PASSWORD";
    public static final String USERS_EMAIL="EMAIL";
    public static final String USERS_FIRST_NAME="FIRST_NAME";
    public static final String USERS_LAST_NAME="LAST_NAME";
    public static final String USERS_GENDER="GENDER";
    public static final String USERS_LANGUAGE="LANGUAGE";
    public static final String USERS_GENDER_GRP="GENDER_GROUP";
    public static final String USERS_MOBILE="MOBILE";
    public static final String USERS_TABLE="USERS";

    /*Album related constants*/
    public static final String ALBUMS_TABLE="ALBUMS";
    public static final String ALBUM_ID="ID";
    public static final String ALBUM_NAME="ALBUM_NAME";
    public static final String ALBUM_ARTIST="ARTIST";
    public static final String ALBUM_RELEASE_DATE="ALBUM_RELEASE_DATE";
    public static final String ALBUM_PRODUCER="PRODUCER";
    public static final String ALBUM_GENRES="GENRES";
    public static final String ALBUM_SONGS="SONGS";
    public static final String ALBUM_RATING="RATING";
    public static final String ALBUM_AWARDS="AWARDS";

    public static final String GET_STATES_URL="http://services.groupkt.com/state/get/IND/all";
   // public static final String GET_JOB_LIST="";

    /* Database queries */

    public static final String CREATE_USERS_TABLE="CREATE TABLE "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "FIRST_NAME TEXT NOT NULL, LAST_NAME TEXT, LANGUAGE TEXT NOT NULL, " +
            USERS_EMAIL+ " TEXT NOT NULL UNIQUE, "+USERS_PASSWORD+" TEXT NOT NULL, "+USERS_MOBILE+" TEXT NOT NULL, GENDER CHARACTER(1) NOT NULL, GENDER_GROUP TEXT, APP_RATING INTEGER )";


    public static final String CREATE_ALBUM_TABLE="CREATE TABLE ALBUMS (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
            " ALBUM_NAME TEXT NOT NULL, ARTIST TEXT NOT NULL, ALBUM_RELEASE_DATE TEXT NOT NULL, PRODUCER TEXT , " +
            "GENRES TEXT, SONGS TEXT NOT NULL, RATING DOUBLE, AWARDS TEXT)";

    public static final String DROP_USERS_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;

    public static final String DROP_ALBUM_TABLE="DROP TABLE IF EXISTS ALUMBS";

}
