package com.example.test1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName;
    Switch switch_;
    Button btnAddData;
    Button btnviewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.editText);
        btnAddData = (Button)findViewById(R.id.button_add);
        switch_ = (Switch)findViewById(R.id.switch1);
        btnviewAll = (Button)findViewById(R.id.button_viewAll);
        //      btnDelete= (Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
    }
    public  void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String statusSwitch;
                        if (switch_.isChecked())
                            statusSwitch = "yes";
                        else
                            statusSwitch = "no";

                        boolean isInserted = myDb.insertData(editName.getText().toString(),statusSwitch );


                        if(isInserted == true) {
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            editName.setText(null);
                            switch_.setChecked(false);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                            editName.setText(null);
                            switch_.setChecked(false);
                        }
                    }
                }
        );
    }
    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Food Name :"+ res.getString(1)+"\n");
                            buffer.append("Easy to cook ?"+ res.getString(2)+"\n");

                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
