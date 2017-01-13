package com.sapient.mymusicalbum.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sapient.mymusicalbum.constants.MusicAlbumConstants;

/**
 * Created by kshan5 on 12/29/2016.
 */

public class DatabaseConfig extends SQLiteOpenHelper {

    private static DatabaseConfig dbInstance;

    private final Context context;

    private static SQLiteDatabase sqLiteDatabase;


    private DatabaseConfig(Context context) {
        super(context, MusicAlbumConstants.DATABASE_NAME, null, 8);
        this.context = context;
    }

    public static synchronized DatabaseConfig getInstance(Context context){

        if(null == dbInstance)
        {
            dbInstance = new DatabaseConfig(context);
        }
        return dbInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(MusicAlbumConstants.CREATE_USERS_TABLE);
        db.execSQL(MusicAlbumConstants.CREATE_ALBUM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MusicAlbumConstants.DROP_USERS_TABLE);
        db.execSQL(MusicAlbumConstants.DROP_ALBUM_TABLE);
        onCreate(db);
    }

    public SQLiteDatabase getWriteableDB()
    {
        if((null == sqLiteDatabase) || !(sqLiteDatabase.isOpen()))
        {
            sqLiteDatabase = this.getWritableDatabase();
        }
    return sqLiteDatabase;
    }


    @Override
    public void close()
    {
        super.close();
        if(sqLiteDatabase!=null)
        {
            sqLiteDatabase.close();
        }
    }
}
