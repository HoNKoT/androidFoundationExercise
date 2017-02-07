package jp.honkot.exercize.basic.wwword;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import javax.inject.Inject;

import jp.honkot.exercize.basic.wwword.dao.WordDao;
import jp.honkot.exercize.basic.wwword.databinding.ActivityEditWordBinding;
import jp.honkot.exercize.basic.wwword.model.Word;

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
}
