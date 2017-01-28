package jp.honkot.exercize.basic.quiz1;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        initializeData();
        mAdapter = new MyAdapter(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                mData
        );

        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(mAdapter);
        list.setOnItemLongClickListener(mAdapter);
    }

    private ArrayList<Container> mData = new ArrayList();
    private MyAdapter mAdapter;

    private void initializeData() {
        while (mData.size() > 0) {
            mData.remove(0);
        }

        for (int i = 0; i < 20; i++) {
            mData.add(new Container("Persian", "White", i));
        }
    }

    private class MyAdapter extends ArrayAdapter<Container> implements AdapterView.OnItemLongClickListener {

        public MyAdapter(Context context, int resource, int textViewResourceId, List<Container> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            TextView main = (TextView)view.findViewById(android.R.id.text1);
            main.setText(getItem(position).breed);

            TextView sub = (TextView)view.findViewById(android.R.id.text2);
            sub.setText(getItem(position).color + ", " + getItem(position).age);

            return view;
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            StringBuffer buf = new StringBuffer();
            buf.append("Do you want to delete this?\n\n")
                    .append(getItem(position).breed).append(", ")
                    .append(getItem(position).color).append(", ")
                    .append(getItem(position).age);

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("CONFIRM")
                    .setMessage(buf.toString())
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mData.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        }
    }

    private class Container {
        String breed;
        String color;
        int age;

        Container (String breed, String color, int age) {
            this.breed = breed;
            this.color = color;
            this.age = age;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
