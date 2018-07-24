package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

public class SequenceEcritureComptableTest {

    SequenceEcritureComptable seq = new SequenceEcritureComptable();

    @Test
    public void testSetAndGet() {
        seq.setDerniereValeur(10);
        seq.setAnnee(2000);
        int der = seq.getDerniereValeur();
        int date = seq.getAnnee();
        Assert.assertEquals(der,10);
        Assert.assertEquals(date,2000);
    }

    @Test
    public void testToString() {
        seq.setDerniereValeur(10);
        seq.setAnnee(2000);
        int der = seq.getDerniereValeur();
        int date = seq.getAnnee();

        String tostring = "SequenceEcritureComptable{annee=2000, derniereValeur=10}";
        Assert.assertEquals(seq.toString(),tostring);
    }

}
