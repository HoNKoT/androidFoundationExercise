package jp.honkot.exercize.basic.wwword.model;

import com.github.gfx.android.orma.annotation.Table;

@Table
public class Test extends BaseModel {
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Test{");
        super.append(sb);
        sb.append('}');
        return sb.toString();
    }
}
