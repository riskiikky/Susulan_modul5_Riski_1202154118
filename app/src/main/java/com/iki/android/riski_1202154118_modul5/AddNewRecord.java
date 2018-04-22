package com.iki.android.riski_1202154118_modul5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddNewRecord extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mDescEditText;
    private EditText mPrioEditText;
    private Button mAddBtn;

    private todoHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record); //set konten dari activity add new record

        mNameEditText = (EditText)findViewById(R.id.id_name); //find by id dari id_name di activity add new record xml
        mDescEditText = (EditText)findViewById(R.id.id_desc); //find by id dari id_desc di activity add new record xml
        mPrioEditText = (EditText)findViewById(R.id.id_prio);//find by id dari id_prio di activity add new record xml
        mAddBtn = (Button)findViewById(R.id.id_addtodo);

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTodo();
            }
        });
    }
    private void saveTodo(){
        String name = mNameEditText.getText().toString().trim();
        String description = mDescEditText.getText().toString().trim();
        String priority = mPrioEditText.getText().toString().trim();
        dbHelper = new todoHelper(this);

        if(name.isEmpty()){
            //error jika kosong
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show(); //muncul pesan popup
        }

        if(description.isEmpty()){
            //error jika deskripsi kosong
            Toast.makeText(this, "You must enter an age", Toast.LENGTH_SHORT).show(); //muncul pesan popuop
        }

        if(priority.isEmpty()){
            //error jika priority kosong
            Toast.makeText(this, "You must enter an occupation", Toast.LENGTH_SHORT).show(); //muncul pesan popup
        }


        //buat new person
        Todo todo = new Todo(name, description, priority);
        dbHelper.saveNewTodo(todo);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(AddNewRecord.this, MainActivity.class));
    }
}
