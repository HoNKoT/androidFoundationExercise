package jp.honkot.exercize.basic.wwword.model;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.Getter;
import com.github.gfx.android.orma.annotation.Setter;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class Preference extends BaseModel {

    @Column(defaultExpr = "10000")
    private long notifycationInterval;

    @Column(defaultExpr = "0")
    private int vib;

    @Column(defaultExpr = "0")
    private int ring;

    @Getter
    public long getNotifycationInterval() {
        return notifycationInterval;
    }

    @Setter
    public void setNotifycationInterval(long notifycationInterval) {
        this.notifycationInterval = notifycationInterval;
    }

    @Getter
    public int getVib() {
        return vib;
    }

    @Setter
    public void setVib(int vib) {
        this.vib = vib;
    }

    @Getter
    public int getRing() {
        return ring;
    }

    @Setter
    public void setRing(int ring) {
        this.ring = ring;
    }

    public boolean isNotify() {
        return this.notifycationInterval != 0;
    }

    public boolean isVib() {
        return this.vib != 0;
    }

    public boolean isRing() {
        return this.ring != 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Preference{");
        super.append(sb);
        sb.append(", notifycationInterval=").append(notifycationInterval);
        sb.append(", vib=").append(vib);
        sb.append(", ring=").append(ring);
        sb.append('}');
        return sb.toString();
    }
}
