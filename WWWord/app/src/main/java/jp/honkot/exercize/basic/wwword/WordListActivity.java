package jp.honkot.exercize.basic.wwword;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import jp.honkot.exercize.basic.wwword.model.Word;
import jp.honkot.exercize.basic.wwword.model.Word_Selector;

public class WordListActivity extends BaseActivity {
    


    private class CustomAdapter extends SimpleAdapter {

        Word_Selector selector;

        public CustomAdapter(Context context, int resource) {
            super(context, null, resource, null, null);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            return super.getView(position, convertView, parent);
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
    }
}
