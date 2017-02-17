package jp.honkot.exercize.basic.wwword.dao;

import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import jp.honkot.exercize.basic.wwword.model.OrmaDatabase;
import jp.honkot.exercize.basic.wwword.model.Preference;
import jp.honkot.exercize.basic.wwword.model.Preference_Relation;

@Singleton
public class PreferenceDao {
    OrmaDatabase orma;

    @Inject
    public PreferenceDao(OrmaDatabase orma) {
        this.orma = orma;
    }

    public Preference_Relation relation() {
        return orma.relationOfPreference();
    }

    @Nullable
    public Preference findById(long id) {
        return relation().selector().idEq(id).valueOrNull();
    }

    public long insert(final Preference value) {
        return orma.insertIntoPreference(value);
    }

    public long remove(final Preference value) {
        return relation().deleter().idEq(value.getId()).execute();
    }

    public void updateNotificatonInterval(final Preference value) {
        orma.transactionNonExclusiveSync(new Runnable() {
            @Override
            public void run() {
                orma.updatePreference()
                        .idEq(value.getId())
                        .notificationInterval(value.getNotificationInterval())
                        .execute();
            }
        });
    }

    public long update(final Preference value) {
        return orma.updatePreference()
                .idEq(value.getId())
                .status(value.getStatus())
                .notificationInterval(value.getNotificationInterval())
                .vib(value.getVib())
                .ring(value.getRing())
                .execute();
    }
}
