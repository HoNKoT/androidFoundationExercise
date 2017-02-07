package jp.honkot.exercize.basic.wwword.dao;

import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import jp.honkot.exercize.basic.wwword.model.OrmaDatabase;
import jp.honkot.exercize.basic.wwword.model.Word;
import jp.honkot.exercize.basic.wwword.model.Word_Relation;
import jp.honkot.exercize.basic.wwword.model.Word_Selector;

@Singleton
public class WordDao {
    OrmaDatabase orma;

    @Inject
    public WordDao(OrmaDatabase orma) {
        this.orma = orma;
    }

    public Word_Relation relation() {
        return orma.relationOfWord();
    }

    @Nullable
    public Word findById(long id) {
        return relation().selector().idEq(id).valueOrNull();
    }

    public Word_Selector findAll() {
        return relation().selector();
    }

    public long insert(final Word value) {
        return orma.insertIntoWord(value);
    }

    public long remove(final Word value) {
        return relation().deleter()
                .idEq(value.getId())
                .execute();
    }

    public long update(final Word value) {
        return orma.updateWord()
                .idEq(value.getId())
                .status(value.getStatus())
                .execute();
    }
}
