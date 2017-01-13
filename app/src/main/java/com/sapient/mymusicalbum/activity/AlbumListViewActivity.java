package com.sapient.mymusicalbum.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlbumListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    int [] albums = {
            R.drawable.a1001,
            R.drawable.a1002,
            R.drawable.a1003,
            R.drawable.a1004,
            R.drawable.a1005,
            R.drawable.a1006,
            R.drawable.a1007,
            R.drawable.a1008
    };

    String[] titles = new String[]{
            "Nothing But The Beat",
            "Never Hate Me",
            "UnPlugged",
            "Ignition",
            "ADELE2",
            "ORION",
            "Believe",
            "Lady Gaga"
    };

    String[] songs = new String[]{
            "I Will Be With You",
            "Where the Lost Ones Go",
            "Let It Rain",
            "Never Ever",
            "Hey Hey Hey",
            "Show Me The Meaning",
            "Hey Good Bye",
            "Turn Me On"
    };

    String[] ratings = new String[]{
            "1.5",
            "2.8",
            "1.0",
            "3.5",
            "4.0",
            "2.5",
            "1.0",
            "4.5"
    };
    int nextImgIndex = 0;
    ListView albumListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list_view);

        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0; i<8; i++)
        {
            HashMap<String, String> listViewElements = new HashMap<String, String>();
            listViewElements.put("Title", titles[i].toString());
            listViewElements.put("Song", songs[i].toString());
            listViewElements.put("Album", Integer.toString(albums[i]));

            aList.add(listViewElements);

            String [] keys = {"Album", "Title", "Song"};
            int[] view = {R.id.imgView, R.id.list_alb_title, R.id.list_alb_song};

            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), aList, R.layout.activity_row_listview, keys, view);
            ListView listView = (ListView)findViewById(R.id.mainListView);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long pointer = parent.getItemIdAtPosition(position);
        Intent detailedViewIntent = new Intent(getApplicationContext(), AlbumDetailedView.class);
        detailedViewIntent.putExtra("selectedId", pointer);
        startActivity(detailedViewIntent);

    }
}
