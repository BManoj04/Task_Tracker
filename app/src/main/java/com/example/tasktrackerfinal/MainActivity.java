package com.example.tasktrackerfinal;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText et;
    Button add,update,delete;
    ArrayList<String> list = new ArrayList<>();
    public Integer indexval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.ls);
        add = findViewById(R.id.button);
        update = findViewById(R.id.button2);
        delete = findViewById(R.id.button3);
        et = findViewById(R.id.task);
        load();



        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                indexval = null;
                et.setText("");
                et.setText(list.get(position));
                listView.setAdapter(adapter);
                indexval = list.indexOf(et.getText().toString());
            }
        });

        View.OnClickListener buttonadd = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.contains(et.getText().toString())){
                    Toast.makeText(MainActivity.this, "Task Already Exists",Toast.LENGTH_SHORT).show();
                }
                else if (et.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Enter a Task To Add",Toast.LENGTH_SHORT).show();
                }
                else {
                    list.add(et.getText().toString());
                    et.setText("");
                    Store();

                    listView.setAdapter(adapter);
                    Toast.makeText(MainActivity.this, "One Task Added", Toast.LENGTH_SHORT).show();
                }
            }

        };
        add.setOnClickListener(buttonadd);



        View.OnClickListener buttonupdate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indexval != null) {
                    list.set(indexval, et.getText().toString());

                    et.setText("");
                    indexval = null;
                    Store();

                    listView.setAdapter(adapter);
                    Toast.makeText(MainActivity.this, "Task Updated", Toast.LENGTH_SHORT).show();
                }
                else if (et.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Enter The Task",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Select Any " +
                            "Task To Update", Toast.LENGTH_SHORT).show();
                }
            }
        };
        update.setOnClickListener(buttonupdate);

        View.OnClickListener buttondelete = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indexval != null) {
                    list.remove(indexval.intValue());
                    et.setText("");
                    indexval = null;
                    Store();

                    listView.setAdapter(adapter);
                    Toast.makeText(MainActivity.this, "One Task Finished", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Select The Task", Toast.LENGTH_SHORT).show();
                }
            }
        };
        delete.setOnClickListener(buttondelete);




        }
    private void Store() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DATA",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("task_data",json);
        editor.apply();
    }
    private void load(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DATA",MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("task_data",null);
        Type type = new TypeToken<ArrayList>(){
        }.getType();
        list=gson.fromJson(json,type);
        if(list==null){
            list = new ArrayList<>();
            et.setText("");
        }

        }
    }



