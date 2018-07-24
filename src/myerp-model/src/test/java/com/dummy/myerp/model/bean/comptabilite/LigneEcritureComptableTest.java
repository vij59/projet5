package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class LigneEcritureComptableTest {

    LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
    CompteComptable compteComptable = new CompteComptable();

    @Test
    public void testSetAndGet()  {
        compteComptable.setNumero(1);
        compteComptable.setLibelle("yo");
        ligneEcritureComptable.setCompteComptable(compteComptable);
        int num = ligneEcritureComptable.getCompteComptable().getNumero();
        Assert.assertEquals(num,1);

        ligneEcritureComptable.setLibelle("bite");
        Assert.assertEquals(ligneEcritureComptable.getLibelle(),"bite");

        ligneEcritureComptable.setDebit(new BigDecimal(10));
        ligneEcritureComptable.setCredit(new BigDecimal(11));
        Assert.assertEquals(ligneEcritureComptable.getDebit(),new BigDecimal(10));
        Assert.assertEquals(ligneEcritureComptable.getCredit(),new BigDecimal(11));
    }

    @Test
    public void testToString() {
        testSetAndGet();
        String tostring = "LigneEcritureComptable{compteComptable=CompteComptable{numero=1, libelle='yo'}, libelle='bite', debit=10, credit=11}";

        Assert.assertEquals(ligneEcritureComptable.toString(), tostring);
    }
    /*
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }
    public BigDecimal getDebit() {
        return debit;
    }
    public void setDebit(BigDecimal pDebit) {
        debit = pDebit;
    }
    public BigDecimal getCredit() {
        return credit;
    }
    public void setCredit(BigDecimal pCredit) {
        credit = pCredit;
    }
*/
}
