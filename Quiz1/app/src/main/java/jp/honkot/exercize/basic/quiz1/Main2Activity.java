package jp.honkot.exercize.basic.quiz1;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

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

            new AlertDialog.Builder(Main2Activity.this)
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            initializeData();
            mAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Initialized", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_add) {
            final View view = getLayoutInflater().inflate(R.layout.row_list, null);
            final EditText breed = (EditText)view.findViewById(R.id.breed);
            final EditText color = (EditText)view.findViewById(R.id.color);
            final EditText age = (EditText)view.findViewById(R.id.age);
            final Button button = (Button)view.findViewById(R.id.btn);
            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    button.setEnabled(
                            !breed.getText().toString().isEmpty()
                                    && !color.getText().toString().isEmpty()
                                    && !age.getText().toString().isEmpty());
                }
            };

            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Input information")
                    .setMessage("Fill out whole contents")
                    .setView(view)
                    .create();

            breed.addTextChangedListener(watcher);
            color.addTextChangedListener(watcher);
            age.addTextChangedListener(watcher);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Container newOne = new Container(
                            breed.getText().toString(),
                            color.getText().toString(),
                            Integer.parseInt(age.getText().toString()));
                    mData.add(0, newOne);
                    mAdapter.notifyDataSetChanged();

                    Toast.makeText(Main2Activity.this, "Add it into top", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            dialog.show();

        }

        return super.onOptionsItemSelected(item);
    }
}
