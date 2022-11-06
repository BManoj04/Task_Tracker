package com.example.tasktrackerfinal;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import androidx.appcompat.app.AppCompatActivity;

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



        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_multiple_choice,list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                ArrayList<Integer> ittodel = new ArrayList<>();
                int temp = 0;
                System.out.println(list.size());
                System.out.println(listView.getCount());
                for (int itm = 0; itm<list.size();itm++){

                    if(listView.isItemChecked(itm)) {
                        ittodel.add(itm);
                    }
                }
                System.out.println(ittodel);
                System.out.println(ittodel.size());
                for (int i = 0; i<ittodel.size(); i++){
                    list.remove(ittodel.get(i)-temp);
                    temp=temp+1;
                }
                ittodel.clear();
                Store();
                listView.setAdapter(adapter);
                if(temp==0){
                    Toast.makeText(MainActivity.this, "Select The Task", Toast.LENGTH_SHORT).show();

                }
                else {Toast.makeText(MainActivity.this, "Task Finished", Toast.LENGTH_SHORT).show();
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