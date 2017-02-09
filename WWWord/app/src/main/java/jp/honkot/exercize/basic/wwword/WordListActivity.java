package jp.honkot.exercize.basic.wwword;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.gfx.android.orma.Inserter;

import javax.inject.Inject;

import jp.honkot.exercize.basic.wwword.dao.WordDao;
import jp.honkot.exercize.basic.wwword.databinding.ActivityListWordBinding;
import jp.honkot.exercize.basic.wwword.databinding.RowWordBinding;
import jp.honkot.exercize.basic.wwword.model.OrmaDatabase;
import jp.honkot.exercize.basic.wwword.model.Word;
import jp.honkot.exercize.basic.wwword.model.Word_Selector;
import jp.honkot.exercize.basic.wwword.util.Debug;

public class WordListActivity extends BaseActivity {

    RecyclerAdapter mAdapter;
    ActivityListWordBinding mBinding;

    private static final int REQUEST_CODE = 1;
    public static final int RESULT_SUCCEEDED = 1;

    @Inject
    WordDao wordDao;

    @Inject
    OrmaDatabase orma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_word);

        // For debug
        if (Debug.isDBG && wordDao.findAll().isEmpty()) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    Inserter<Word> sth = orma.prepareInsertIntoWord();
                    for (int i = 0; i < 50; i++) {
                        Word word = new Word();
                        word.setWord("Word #" + i);
                        word.setMeaning("Meaning #" + i);
                        word.setDetail("Detail #" + i);
                        word.setMemo("Memo #" + i);
                        sth.execute(word);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initialize();
                        }
                    });
                }
            };
            thread.start();

        } else {
            initialize();
        }
    }

    private void initialize() {
        mAdapter = new RecyclerAdapter();
        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        mBinding.list.setLayoutManager(new LinearLayoutManager(this));
        mBinding.list.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_SUCCEEDED) {
            initialize();
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

        private Word_Selector mData;

        private RecyclerAdapter() {
            mData = wordDao.findAll();
        }

        @Override
        public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            RowWordBinding itemBinding = RowWordBinding.inflate(layoutInflater, parent, false);
            return new MyViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            Word item = getItemForPosition(position);
            holder.binding.setWord(item);
            holder.binding.rowRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerClicked(holder.binding.getRoot(), holder.getLayoutPosition());
                }
            });
        }

        @Override
        public int getItemCount() {
            if (mData != null) {
                return mData.count();
            } else {
                return 0;
            }
        }

        private Word getItemForPosition(int position) {
            return mData.get(position);
        }

        private void onRecyclerClicked(View view, int position) {
            Intent intent = new Intent(getApplicationContext(), WordEditActivity.class);
            intent.putExtra(WordEditActivity.EXTRA_WORD_ID, getItemForPosition(position).getId());
            startActivity(intent);
        }

        // ViewHolder(固有ならインナークラスでOK)
        protected class MyViewHolder extends RecyclerView.ViewHolder {

            private final RowWordBinding binding;

            private MyViewHolder(RowWordBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
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
                startActivityForResult(intent, REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
