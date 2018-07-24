package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JournalComptableTest {

    JournalComptable journalComptable = new JournalComptable();

    @Test
    public void testSetAndGet() {
        journalComptable.setCode("AC");
        journalComptable.setLibelle("yo");
        String code = journalComptable.getCode();
        Assert.assertEquals(code,"AC");
        Assert.assertEquals(journalComptable.getLibelle(),"yo");
    }
    @Test
    public void getByCode() {
        journalComptable.setCode("AC");
        journalComptable.setLibelle("yo");
        JournalComptable journalComptable2 = new JournalComptable();
        journalComptable2.setCode("AB");
        journalComptable2.setLibelle("ZE");
        List<JournalComptable> liste =new ArrayList<>();
        liste.add(journalComptable);
        liste.add(journalComptable2);

        Assert.assertEquals(JournalComptable.getByCode(liste,"AC").getLibelle(),"yo");
    }

    @Test
    public void testToString() {
        getByCode();
        String tostring = "JournalComptable{"+
                "code='AC'"+", libelle='yo'}";
        Assert.assertEquals(journalComptable.toString(),tostring);

    }

}
