package jp.honkot.exercize.basic.wwword.dao;

import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import jp.honkot.exercize.basic.wwword.model.OrmaDatabase;
import jp.honkot.exercize.basic.wwword.model.Test;
import jp.honkot.exercize.basic.wwword.model.Test_Relation;
import jp.honkot.exercize.basic.wwword.model.Test_Selector;

@Singleton
public class TestDao {
    OrmaDatabase orma;

    @Inject
    public TestDao(OrmaDatabase orma) {
        this.orma = orma;
    }

    public Test_Relation relation() {
        return orma.relationOfTest();
    }

    @Nullable
    public Test findById(long id) {
        return relation().selector().idEq(id).valueOrNull();
    }

    public Test_Selector findAll() {
        return relation().selector();
    }

    public long insert(final Test test) {
        return orma.insertIntoTest(test);
    }

    public long remove(final Test test) {
        return relation().deleter()
                .idEq(test.getId())
                .execute();
    }

    public long update(final Test test) {
        return orma.updateTest()
                .idEq(test.getId())
                .status(test.getStatus())
                .execute();
    }
}
