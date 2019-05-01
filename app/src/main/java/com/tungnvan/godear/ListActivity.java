package com.tungnvan.godear;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity {
    RecyclerView recordView;
    RecyclerViewAdapter recordAdapter;
    List<String> data;
    private Button play_button;
    private SeekBar record_seek;

    @Override
    public void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recordView = (RecyclerView) findViewById(R.id.recordList);
        play_button = (Button) findViewById(R.id.play_button);
        record_seek = (SeekBar) findViewById(R.id.record_seek);

        data = new ArrayList<>();

        //lấy danh sách bản ghi từ thư mục lưu trữ
        String dir_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GodEar/";
            File dir_folder = new File(dir_path);
            if (dir_folder.isDirectory()) {
                String[] record = dir_folder.list();
                for (String recordname : record) {
                    data.add(recordname);
                }
            }

        recordAdapter = new RecyclerViewAdapter(data);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recordView.setLayoutManager(layoutManager);
        recordView.setAdapter(recordAdapter);
        recordAdapter.setOnItemClickedListener(new RecyclerViewAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(String username) {
                Toast.makeText(ListActivity.this, username, Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void playButtonClicked(View view) {

    }



}
