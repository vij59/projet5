package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CompteComptableTest {

    CompteComptable compteComptable = new CompteComptable();

    @Test
    public void testSetAndGet() {
        compteComptable.setNumero(1);
        compteComptable.setLibelle("yo");
        int num = compteComptable.getNumero();
        Assert.assertEquals(num,1);
        Assert.assertEquals(compteComptable.getLibelle(),"yo");
    }
    @Test
    public void getByNumero() {
        compteComptable.setNumero(1);
        compteComptable.setLibelle("yo");
       CompteComptable compteComptable2 = new CompteComptable();
        compteComptable2.setNumero(2);
        compteComptable2.setLibelle("ZE");
        List<CompteComptable> liste =new ArrayList<>();
        liste.add(compteComptable);
        liste.add(compteComptable2);

        Assert.assertEquals(CompteComptable.getByNumero(liste,1).getLibelle(),"yo");
    }

    @Test
    public void testToString() {
        getByNumero();
        String tostring = "CompteComptable{"+
                "numero=1"+", libelle='yo'}";
        Assert.assertEquals(compteComptable.toString(),tostring);

    }

}
