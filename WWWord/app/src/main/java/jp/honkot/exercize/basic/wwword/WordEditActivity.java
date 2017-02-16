package jp.honkot.exercize.basic.wwword;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import jp.honkot.exercize.basic.wwword.dao.WordDao;
import jp.honkot.exercize.basic.wwword.databinding.ActivityEditWordBinding;
import jp.honkot.exercize.basic.wwword.model.OxfordDictionary;
import jp.honkot.exercize.basic.wwword.model.Word;
import jp.honkot.exercize.basic.wwword.util.Debug;

public class WordEditActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_WORD_ID = "EXTRA_WORD_ID";
    private ActivityEditWordBinding mBinding;
    private Word mWord;

    @Inject
    WordDao wordDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_word);
        initialize();
    }

    private void initialize() {
        // Get and set initial values
        long id = getIntent().getLongExtra(EXTRA_WORD_ID, 0);
        if (id != 0) {
            mWord = wordDao.findById(id);
        }
        if (mWord == null) {
            mWord = new Word();
        }
        mBinding.setWord(mWord);
        updateButtonState();

        // Set some listeners
        mBinding.wordEditText.addTextChangedListener(mTextWatcher);
        mBinding.meaningEditText.addTextChangedListener(mTextWatcher);
        mBinding.detailEditText.addTextChangedListener(mTextWatcher);
        mBinding.memoEditText.addTextChangedListener(mTextWatcher);
        mBinding.registerButton.setOnClickListener(this);
        mBinding.getButton.setOnClickListener(this);
    }

    private void updateButtonState() {
        mBinding.registerButton.setEnabled(
                !mBinding.wordEditText.getText().toString().isEmpty()
                && !mBinding.meaningEditText.getText().toString().isEmpty());
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            updateButtonState();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getButton:
                getByOxfordDic();
                break;
            case R.id.registerButton:
                register();
                break;
        }
    }

    private void register() {
        mWord.setWord(mBinding.wordEditText.getText().toString());
        mWord.setMeaning(mBinding.meaningEditText.getText().toString());
        mWord.setDetail(mBinding.detailEditText.getText().toString());
        mWord.setMemo(mBinding.memoEditText.getText().toString());

        // Check the error just in case
        if (mWord.allowRegister()) {
            long result = 0;
            if (mWord.getId() == 0) {
                result = wordDao.insert(mWord);
            } else {
                result = wordDao.update(mWord);
            }

            if (result != 0) {
                setResult(WordListActivity.RESULT_SUCCEEDED);
                finish();
            } else {
                // error
            }
        }
    }

    private void getByOxfordDic() {
        String url = dictionaryEntries();
        Debug.Log(url);
        new CallbackTask().execute(url);
    }

    private String dictionaryEntries() {
        final String language = "en";
        final String word = "positive";
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
    }

    //in android calling network requests on the main thread forbidden by default
    //create class to do async job
    private class CallbackTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            Debug.Log("doInBackground");
            final String app_id = "50974be7";
            final String app_key = "38da4c6505d9dab21093068b480c7097";
            try {
                URL url = new URL(params[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("app_id",app_id);
                urlConnection.setRequestProperty("app_key",app_key);

                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                JSONObject jsonObj = new JSONObject(stringBuilder.toString());
                OxfordDictionary dic = OxfordDictionary.getInstance(jsonObj);
                Debug.Log(dic.toString());


                return stringBuilder.toString();

            }
            catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            System.out.println(result);
        }
    }
}
