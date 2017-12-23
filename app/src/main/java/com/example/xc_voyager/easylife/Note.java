package com.example.xc_voyager.easylife;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.lang.reflect.Method;

public class Note extends AppCompatActivity
{
    private EditText noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        noteContent = (EditText) findViewById(R.id.note_content);
    }

    @Override//为了让toolbar的菜单中显示图标
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if (menu != null)
        {
            if (menu.getClass().getSimpleName().equals("MenuBuilder"))
            {
                try
                {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e)
                {

                }
            }

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override//创建toolbar以及menu
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.note_confirm)
        {
            new AlertDialog.Builder(Note.this)
                    .setTitle("Save note").setCancelable(false).setMessage(
                    "Do you want to save the note?\n"
            ).setPositiveButton("Confirm", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    noteContent.setText("");
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            }).show();
        }
        if (id == R.id.note_back)
        {
            if (!(noteContent.getText().toString().equals("")))
                new AlertDialog.Builder(Note.this)
                        .setTitle("Go back").setCancelable(true).setMessage(
                        "Do you want to go back to main menu without saving?\n"
                ).setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(Note.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                }).show();
            else
            {
                Intent intent = new Intent(Note.this, MainActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
