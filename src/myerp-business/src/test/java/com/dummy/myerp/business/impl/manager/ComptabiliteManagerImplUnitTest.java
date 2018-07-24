package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.dummy.myerp.model.bean.comptabilite.*;
import org.junit.Assert;
import org.junit.Test;
import com.dummy.myerp.technical.exception.FunctionalException;

import static org.testng.Assert.assertThrows;


public class ComptabiliteManagerImplUnitTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();


    @Test
    public void checkEcritureComptableUnit() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable();

        vEcritureComptable.setJournal(new JournalComptable("AC", "Achats"));
        vEcritureComptable.setDate(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String yearRef = sdf.format(vEcritureComptable.getDate());
        vEcritureComptable.setReference("AC-"+yearRef+"/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);

    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }


    @Test
    public void checkEcritureComptableUnitRG2() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        assertThrows(FunctionalException.class,()->{
            vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
            vEcritureComptable.setDate(new Date());
            vEcritureComptable.setLibelle("Libelle");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                    null, new BigDecimal(1234),
                    null));
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                    null, null,
                    new BigDecimal(354)));
            manager.checkEcritureComptableUnit(vEcritureComptable);
        });
        Assert.assertFalse(vEcritureComptable.isEquilibree());
    }

    @Test
    public void checkEcritureComptableUnitRG3() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        assertThrows(FunctionalException.class,()->{
            vEcritureComptable.setJournal(new JournalComptable("VV", "RTE"));
            vEcritureComptable.setDate(new Date());
            vEcritureComptable.setLibelle("Libelle");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                    null, new BigDecimal(123),
                    null));
            manager.checkEcritureComptableUnit(vEcritureComptable);
        });
        Assert.assertFalse(vEcritureComptable.getListLigneEcriture().size()>1);
    }


    /*
    @Test
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }
    */

}
