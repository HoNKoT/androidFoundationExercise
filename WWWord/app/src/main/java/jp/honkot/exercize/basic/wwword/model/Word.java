package jp.honkot.exercize.basic.wwword.model;

import com.android.annotations.NonNull;
import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.Getter;
import com.github.gfx.android.orma.annotation.Setter;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class Word extends BaseModel {

    @Column
    @NonNull
    private String word;

    @Column
    @NonNull
    private String meaning;

    @Column
    @NonNull
    private String detail;

    @Column
    @NonNull
    private String memo;

    public Word() {
        this.word = new String();
        this.meaning = new String();
        this.detail = new String();
        this.memo = new String();
    }

    @Getter
    public String getWord() {
        return word;
    }

    @Setter
    public void setWord(String word) {
        this.word = word;
    }

    @Getter
    public String getMeaning() {
        return meaning;
    }

    @Setter
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Getter
    public String getDetail() {
        return detail;
    }

    @Setter
    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Getter
    public String getMemo() {
        return memo;
    }

    @Setter
    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean allowRegister() {
        return !getWord().isEmpty() && !getMeaning().isEmpty();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Word{");
        super.append(sb);
        sb.append(", word='").append(word).append('\'');
        sb.append(", meaning='").append(meaning).append('\'');
        sb.append(", detail='").append(detail).append('\'');
        sb.append(", memo='").append(memo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}