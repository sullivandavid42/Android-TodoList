package com.example.david_h.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvItems;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdaptater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readFile();
        itemsAdaptater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdaptater);
        setupListViewListener();
    }

    protected void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.elNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdaptater.add(itemText);
        etNewItem.setText("");
        writeFile();
    }

    protected void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemsAdaptater.notifyDataSetChanged();
                        writeFile();
                        return true;
                    }
                });
    }

    protected void readFile() {
        File filedir = getFilesDir();
        File todoFile = new File(filedir, "todo.txt");
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<>();
        }
    }

    protected void writeFile() {
        File filedor = getFilesDir();
        File todoFile = new File(filedor, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
