package jp.honkot.exercize.basic.wwword;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import javax.inject.Inject;

import jp.honkot.exercize.basic.wwword.dao.WordDao;
import jp.honkot.exercize.basic.wwword.databinding.ActivityListWordBinding;
import jp.honkot.exercize.basic.wwword.model.Word;
import jp.honkot.exercize.basic.wwword.model.Word_Selector;

public class WordListActivity extends BaseActivity {

    CustomAdapter mAdapter;
    ActivityListWordBinding mBinding;

    @Inject
    WordDao wordDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_word);
        mAdapter = new CustomAdapter(wordDao.findAll());
        mBinding.list.setAdapter(mAdapter);
        mBinding.list.setOnItemClickListener(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private class CustomAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

        Word_Selector selector;

        public CustomAdapter(Word_Selector selector) {
            super();
            this.selector = selector;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            Word word = getItem(position);
            TextView text = (TextView) view.findViewById(android.R.id.text1);
            text.setText(word.getWord());

            return view;
        }

        @Override
        public int getCount() {
            return selector.count();
        }

        @Override
        public Word getItem(int position) {
            return selector.getOrNull(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), WordEditActivity.class);
            intent.putExtra(WordEditActivity.EXTRA_WORD_ID, getItemId(position));
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent intent = new Intent(this, WordEditActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
