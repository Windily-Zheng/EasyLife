package com.example.xc_voyager.easylife;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.xc_voyager.easylife.TaskList.Tasks;
/**
 * 备忘录列表类，提供数据展示
 */
public class ScheduleActivity extends ListActivity {

    // 菜单项常量
    private static final int NEW = 1;
    private static final int DEL = 2;
    private static int theme;
    // 查询列数组
    private static final String[] PROJECTION = new String[] {
            Tasks._ID, 		// 0
            Tasks.CONTENT, 	// 1
            Tasks.CREATED, 	// 2
            Tasks.ALARM, 	// 3
            Tasks.DATE1, 	// 4
            Tasks.TIME1, 	// 5
            Tasks.ON_OFF 	// 6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        theme = getIntent().getIntExtra("theme",0);
        // 获得Intent
        setContentView(R.layout.schedule_main);
        //调用main视图
        final Intent intent = getIntent();
        // 设置Uri
        if (intent.getData() == null) {
            intent.setData(Tasks.CONTENT_URI);
        }
        // 获得ListView
        ListView listView = getListView();

        // 查询所有备忘录信息
        Cursor cursor = managedQuery(getIntent().getData(), PROJECTION, null,
                null, Tasks.DEFAULT_SORT_ORDER);
        // 创建Adapter
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor,
                new String[] {Tasks.DATE1, Tasks.CONTENT },
                new int[] { android.R.id.text1,android.R.id.text2 });
        // 将备忘录信息显示到ListView
        setListAdapter(adapter);


        // 为ListView添加单击事件监听器
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position,
                                    long id) {
                // 通过ID查询备忘录信息
                Uri uri = ContentUris.withAppendedId(Tasks.CONTENT_URI, id);
                Cursor cursor = managedQuery(uri, PROJECTION, null,
                        null, Tasks.DEFAULT_SORT_ORDER);

                if(cursor.moveToNext()){
                    int id1 = cursor.getInt(0);
                    String content = cursor.getString(1);
                    String created = cursor.getString(2);
                    int alarm = cursor.getInt(3);
                    String date1 = cursor.getString(4);
                    String time1 = cursor.getString(5);
                    int on_off = cursor.getInt(6);
                    Bundle b = new Bundle();
                    b.putInt("id", id1);
                    b.putString("content", content);
                    b.putString("created", created);

                    b.putInt("alarm", alarm);
                    b.putString("date1", date1);
                    b.putString("time1", time1);

                    b.putInt("on_off", on_off);
                    b.putInt("theme", theme);
                    // 将备忘录信息添加到Intent
                    intent.putExtra("b", b);

                    // 启动备忘录详细信息Activity
                    intent.setClass(ScheduleActivity.this, TaskDetailActivity.class);
                    startActivity(intent);

                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v,
                                           final int position, long id) {
                final long id_s = id;
                AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);

                builder.setMessage("确认删除吗？");
                builder.setTitle("提示");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        arg0.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(), "删除成功！",
                                Toast.LENGTH_SHORT).show();
                        // 通过ID查询备忘录信息
                        Uri uri = ContentUris.withAppendedId(Tasks.CONTENT_URI, id_s);
                        Cursor cursor = managedQuery(uri, PROJECTION, null,
                                null, Tasks.DEFAULT_SORT_ORDER);
                        if(cursor.moveToNext()) {
                            int id1 = cursor.getInt(0);
                            Uri urd = ContentUris.withAppendedId(Tasks.CONTENT_URI, id1);
                            getContentResolver().delete(urd, null, null);
                        }
                        arg0.dismiss();
                    }
                });
                builder.create().show();
                return true;     // 这里一定要改为true，代表长按自己消费掉了，若为false，触发长按事件的同时，还会触发点击事件
            }
        });


    }

    // 创建选项菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, NEW, 0, "新建");
        menu.add(0, DEL, 0, "删除");
        return true;
    }
    // 现象菜单项单击方法
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case NEW:
                // 启动备忘录详细信息Activity
                Intent intent = new Intent();
                intent.setClass(this, TaskDetailActivity.class);
                startActivity(intent);
                return true;
            case DEL:
                Toast.makeText(getApplicationContext(), "长按条目进行删除",
                        Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

}
