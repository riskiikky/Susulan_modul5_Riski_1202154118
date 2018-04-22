package com.iki.android.riski_1202154118_modul5;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import android.app.Dialog;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private todoHelper dbHelper;
    private AdapterTodo adapter;
    private String filter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        populaterecyclerView(filter);

        swipe swipeToDelete = new swipe(MainActivity.this, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        swipeToDelete.setLeftBackgroundColor(R.color.colorAccent);
        swipeToDelete.setRightBackgroundColor(R.color.colorPrimary);
        swipeToDelete.setLeftImg(R.drawable.ic_launcher_background);
        swipeToDelete.setRightImg(R.drawable.ic_launcher_background);
        swipeToDelete.setSwipetoDismissCallBack(getCallback(adapter));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDelete);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddNewRecord.class);
                startActivity(i);
            }
        });
    }

    private void populaterecyclerView(String filter) {
        dbHelper = new todoHelper(this);
        adapter = new AdapterTodo(dbHelper.todoList(filter), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private swipe.SwipetoDismissCallBack getCallback(final AdapterTodo adapter) {
        return new swipe.SwipetoDismissCallBack() {
            @Override
            public void onSwipedLeft(RecyclerView.ViewHolder viewholder) {
                adapter.remove(viewholder.getAdapterPosition());
            }

            @Override
            public void onSwipedRight(RecyclerView.ViewHolder viewHolder) {
                Toast.makeText(MainActivity.this, "Another or same action", Toast.LENGTH_SHORT).show();
            }
        };
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.activity_setting_color);
            dialog.setTitle("Change");
            dialog.setCancelable(true);
            // there are a lot of settings, for dialog, check them all out!
            // set up radiobutton
            final RadioButton rdRed =  dialog.findViewById(R.id.RadioRed);
            final RadioButton rdBlue = dialog.findViewById(R.id.RadioBlue);
            final RadioButton rdGreen = dialog.findViewById(R.id.RadioGreen);
            Button btnChange = dialog.findViewById(R.id.ChangeBtn);
            btnChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rdRed.isChecked()){
                        mRecyclerView.setBackgroundResource(R.color.red);
                        Toast.makeText(view.getContext(),"Change to Red",Toast.LENGTH_SHORT).show();
                    }
                    if (rdBlue.isChecked()){
                        mRecyclerView.setBackgroundResource(R.color.blue);
                        Toast.makeText(view.getContext(),"Change to Blue",Toast.LENGTH_SHORT).show();
                    }
                    if (rdGreen.isChecked()){
                        mRecyclerView.setBackgroundResource(R.color.green);
                        Toast.makeText(view.getContext(),"Change to green",Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(view.getContext(),"Color Has Been Changed",Toast.LENGTH_SHORT).show();
                }
            });

            // now that the dialog is set up, it's time to show it
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

}