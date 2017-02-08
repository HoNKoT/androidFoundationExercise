package jp.honkot.exercize.basic.wwword;

import android.content.Context;
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
import android.widget.TextView;

import javax.inject.Inject;

import jp.honkot.exercize.basic.wwword.dao.WordDao;
import jp.honkot.exercize.basic.wwword.databinding.ActivityListWordBinding;
import jp.honkot.exercize.basic.wwword.model.Word_Selector;

public class WordListActivity extends BaseActivity {

    RecyclerAdapter mAdapter;
    ActivityListWordBinding mBinding;

    private static final int REQUEST_CODE = 1;
    public static final int RESULT_SUCCEEDED = 1;

    @Inject
    WordDao wordDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_word);
        initialize();
    }

    private void initialize() {
        mAdapter = new RecyclerAdapter(this, wordDao.findAll());
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

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private LayoutInflater mInflater;
        private Word_Selector mData;
        private Context mContext;

        private RecyclerAdapter(Context context, Word_Selector data) {
            mInflater = LayoutInflater.from(context);
            mContext = context;
            mData = data;
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(mInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            final int position = viewHolder.getAdapterPosition();

            if (mData != null && mData.count() > position) {
                viewHolder.textView.setText(mData.get(position).getWord());
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerClicked(v, position);
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

        private void onRecyclerClicked(View view, int position) {

        }

        // ViewHolder(固有ならインナークラスでOK)
        protected class ViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            private ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(android.R.id.text1);
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
