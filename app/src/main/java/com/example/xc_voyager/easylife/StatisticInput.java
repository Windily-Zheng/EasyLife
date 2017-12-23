package com.example.xc_voyager.easylife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StatisticInput extends AppCompatActivity {

    ArrayList<String> chart_data = new ArrayList<String>();
    int chart_data_num;
    int edit_id_array[] = {
            R.id.x0_input,
            R.id.y0_input,
            R.id.x1_input,
            R.id.y1_input,
            R.id.x2_input,
            R.id.y2_input,
            R.id.x3_input,
            R.id.y3_input,
            R.id.x4_input,
            R.id.y4_input,
            R.id.x5_input,
            R.id.y5_input
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_input);
        setTitle("Enter data");
        final EditText editText = (EditText)findViewById(R.id.datanum_input);
        editText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        final TextView textView = (TextView)findViewById(R.id.showdatanum);
        editText.addTextChangedListener(new TextWatcher() {
            int data_num;
            int last_data_num = 0;
            int id_array[] = new int[12];
            TableRow tb;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                id_array[0] = R.id.tableRowx0;
                id_array[1] = R.id.tableRowy0;
                id_array[2] = R.id.tableRowx1;
                id_array[3] = R.id.tableRowy1;
                id_array[4] = R.id.tableRowx2;
                id_array[5] = R.id.tableRowy2;
                id_array[6] = R.id.tableRowx3;
                id_array[7] = R.id.tableRowy3;
                id_array[8] = R.id.tableRowx4;
                id_array[9] = R.id.tableRowy4;
                id_array[10] = R.id.tableRowx5;
                id_array[11] = R.id.tableRowy5;
                EditText ed;
                for(int i = 0; i < 12; i++){
                    ed = (EditText)findViewById(edit_id_array[i]);
                    ed.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count != 0)
                    data_num = Integer.parseInt(s.toString());
                else
                    data_num = 0;
                textView.setText("There are " + data_num + " groups of data.");
            }

            @Override
            public void afterTextChanged(Editable s) {
                for(int i = 0; i < 2 * last_data_num; i++){
                    tb = (TableRow)findViewById(id_array[i]);
                    tb.setVisibility(View.INVISIBLE);
                }
                for(int i = 0; i < 2 * data_num; i++){
                    tb = (TableRow)findViewById(id_array[i]);
                    tb.setVisibility(View.VISIBLE);
                }
                last_data_num = data_num;
                chart_data_num = data_num;
            }
        });
        Button button = (Button)findViewById(R.id.statistic_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText;
                for(int i = 0; i < 2 * chart_data_num; i++){
                    editText = (EditText)findViewById(edit_id_array[i]);
                    chart_data.add(editText.getText().toString());
                }
                Intent intent  = new Intent(getApplicationContext(), DrawChart.class);
                intent.putStringArrayListExtra("chart_data", chart_data);
                startActivity(intent);
            }
        });
    }
}
