package com.dummy.myerp.model.bean.comptabilite;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EcritureComptableTest {


    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }

    @Test
    public void isEquilibree() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
    }


    @Test
    public void getTotalDebit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "300"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));

        for (LigneEcritureComptable vLigneEcritureComptable : vEcriture.getListLigneEcriture()) {
            if (vLigneEcritureComptable.getDebit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getDebit());
            }
        }
        BigDecimal valDouble = new BigDecimal(340);
        Assert.assertTrue(vRetour.equals(valDouble));
        Assert.assertFalse(!vRetour.equals(valDouble));
    }

    @Test
    public void getTotalCredit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));


        for (LigneEcritureComptable vLigneEcritureComptable : vEcriture.getListLigneEcriture()) {
            if (vLigneEcritureComptable.getCredit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getCredit());
            }
        }
        BigDecimal valDouble = new BigDecimal(33);
        Assert.assertTrue(vRetour.equals(valDouble));
    }

    @Pattern(regexp = "\\d{1,5}-\\d{4}/\\d{5}")
    private String reference = "reference";

    @Test
    public void testGetters() {
        String ref = "reference";
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.setLibelle(ref);
        vEcriture.getLibelle();

        Assert.assertTrue(vEcriture.getLibelle().equals("reference"));

        vEcriture.setDate(new Date());
        Assert.assertTrue(vEcriture.getDate().equals(new Date()));

        vEcriture.setReference("tartiflette");
        Assert.assertTrue(vEcriture.getReference().equals("tartiflette"));

        vEcriture.setId(1);
        Assert.assertTrue(vEcriture.getId().equals(1));
    }

    @Test
    public void testToString() {
        EcritureComptable vEcritureComptable = new EcritureComptable();

        vEcritureComptable.setId(1);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achats"));
        Date dateNew = new Date();
        vEcritureComptable.setDate(dateNew);
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

    String tostring =    "EcritureComptable{id=1, " +
                "journal=JournalComptable{code='AC'" +
                ", libelle='Achats'}, reference='AC-2018/00001', date="+dateNew+
                ", libelle='Libelle', totalDebit=123, totalCredit=123" +
                ", listLigneEcriture=[\n" +
                "LigneEcritureComptable{compteComptable=CompteComptable{numero=1, libelle='null'}, libelle='null', debit=123, credit=null}\n" +
                "LigneEcritureComptable{compteComptable=CompteComptable{numero=2, libelle='null'}, libelle='null', debit=null, credit=123}\n" +
                "]}";

        Assert.assertTrue(vEcritureComptable.toString().equals(tostring));
    }
}
