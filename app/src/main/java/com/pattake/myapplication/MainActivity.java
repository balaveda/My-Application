package com.pattake.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
    EditText editId,editName,editPrice;
    Button btnAdd,btnDelete,btnModify,btnView,btnViewAll,btnShowInfo;
    SQLiteDatabase db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editId=(EditText)findViewById(R.id.editId);
        editName=(EditText)findViewById(R.id.editName);
        editPrice=(EditText)findViewById(R.id.editPrice);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnModify=(Button)findViewById(R.id.btnModify);
        btnView=(Button)findViewById(R.id.btnView);
        btnViewAll=(Button)findViewById(R.id.btnViewAll);
        btnShowInfo=(Button)findViewById(R.id.btnShowInfo);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        btnShowInfo.setOnClickListener(this);
        db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,marks VARCHAR);");
    }
    public void onClick(View view)
    {
        if(view==btnAdd)
        {
            if(editId.getText().toString().trim().length()==0||
                    editName.getText().toString().trim().length()==0||
                    editPrice.getText().toString().trim().length()==0)
            {
                //showMessage("Error", "Please enter all values");
                Toast.makeText(getBaseContext(), "Error! Please Enter All Values",Toast.LENGTH_LONG).show();
                return;
            }
            db.execSQL("INSERT INTO student VALUES('" + editId.getText() + "','" + editName.getText() +
                    "','" + editPrice.getText() + "');");
            Toast.makeText(getBaseContext(), "Success! Record Added",Toast.LENGTH_LONG).show();
            //showMessage("Success", "Record added");
            clearText();
        }
        if(view==btnDelete)
        {
            if(editId.getText().toString().trim().length()==0)
            {
                //showMessage("Error", "Please enter Rollno");
                Toast.makeText(getBaseContext(), "Error! Please Enter ID",Toast.LENGTH_LONG).show();
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editId.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM student WHERE rollno='" + editId.getText() + "'");
                //showMessage("Success", "Record Deleted");
                Toast.makeText(getBaseContext(), "Success! Record Deleted",Toast.LENGTH_LONG).show();
            }
            else
            {
                //showMessage("Error", "Invalid Rollno");
                Toast.makeText(getBaseContext(), "Error! Invalid ID",Toast.LENGTH_LONG).show();
            }
            clearText();
        }
        if(view==btnModify)
        {
            if(editId.getText().toString().trim().length()==0)
            {
                //showMessage("Error", "Please enter Rollno");
                Toast.makeText(getBaseContext(), "Error! Please enter ID",Toast.LENGTH_LONG).show();
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editId.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("UPDATE student SET name='" + editName.getText() + "',marks='" + editPrice.getText() +
                        "' WHERE rollno='" + editId.getText() + "'");
                //showMessage("Success", "Record Modified");
                Toast.makeText(getBaseContext(), "Success! Record Modified",Toast.LENGTH_LONG).show();
            }
            else
            {
                //showMessage("Error", "Invalid Rollno");
                Toast.makeText(getBaseContext(), "Error! Invalid ID",Toast.LENGTH_LONG).show();
            }
            clearText();
        }
        if(view==btnView)
        {
            if(editId.getText().toString().trim().length()==0)
            {
                //showMessage("Error", "Please enter Rollno");
                Toast.makeText(getBaseContext(), "Error! Please Enter ID",Toast.LENGTH_LONG).show();
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editId.getText()+"'", null);
            if(c.moveToFirst())
            {
                editName.setText(c.getString(1));
                editPrice.setText(c.getString(2));
            }
            else
            {
                //showMessage("Error", "Invalid Rollno");
                Toast.makeText(getBaseContext(), "Error! Invalid ID",Toast.LENGTH_LONG).show();
                clearText();
            }
        }
        if(view==btnViewAll)
        {
            Cursor c=db.rawQuery("SELECT * FROM student", null);
            if(c.getCount()==0)
            {
                //showMessage("Error", "No records found");
                Toast.makeText(getBaseContext(), "Error! No Records Found",Toast.LENGTH_LONG).show();
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("Rollno: "+c.getString(0)+"\n");
                buffer.append("Name: "+c.getString(1)+"\n");
                buffer.append("Marks: "+c.getString(2)+"\n\n");
            }
            showMessage("Pizza Details", buffer.toString());
            //Toast.makeText(getBaseContext(), "Success! Record Added",Toast.LENGTH_LONG).show();
        }
        if(view==btnShowInfo)
        {
            //showMessage("Student Management Application", "Developed By Azim");
            Toast.makeText(getBaseContext(), "PIZZA App Developed by Team 6",Toast.LENGTH_LONG).show();
        }
    }
    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        editId.setText("");
        editName.setText("");
        editPrice.setText("");
        editId.requestFocus();
    }
}
