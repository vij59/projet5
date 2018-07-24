package com.dummy.myerp.testconsumer.impl.dao;


import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.testconsumer.impl.dao.ConsumerTestCase;
import org.junit.Assert;
import org.junit.Test;


import java.util.Date;
import java.util.List;


public class ComptabiliteDaoImplTest extends ConsumerTestCase {


     //ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();

       CompteComptable  compteComptable = new CompteComptable();



    @Test
    public void getListeCompteComptable() throws Exception {

        List <CompteComptable> listeComptes = getDaoProxy().getComptabiliteDao().getListCompteComptable();
        CompteComptable compteComptable= new CompteComptable();
        int max = 0;
        for(CompteComptable compte : listeComptes) {
            int num = compte.getNumero();
            if(num>max)
            {
                max = num;
            }
        }
        max++;
        compteComptable.setLibelle("tortue" + max);
        compteComptable.setNumero(max);
        listeComptes.add(compteComptable);
       getDaoProxy().getComptabiliteDao().insertCompteComptable(compteComptable);

       Assert.assertEquals(listeComptes.get(listeComptes.size()-1).getLibelle(),
               getDaoProxy().getComptabiliteDao().getListCompteComptable().
                       get(getDaoProxy().getComptabiliteDao().getListCompteComptable()
                               .size()-1).getLibelle());

       Assert.assertEquals(listeComptes.size(),getDaoProxy().getComptabiliteDao().getListCompteComptable().size());
    }

    @Test
    public void getListeJournalComptable() throws Exception {

        List <JournalComptable> listeJournaux = getDaoProxy().getComptabiliteDao().getListJournalComptable();
        Assert.assertEquals(listeJournaux.get(listeJournaux.size()-1).getCode(),
                "OD" );
        Assert.assertEquals(listeJournaux.size(),4 );
    }

    @Test
    public void getListSequenceEcritureComptable() throws Exception {

        List <SequenceEcritureComptable> listeSeq = getDaoProxy().getComptabiliteDao().getListSequenceEcritureComptable();
        SequenceEcritureComptable sequenceEcritureComptable= new SequenceEcritureComptable();
        int max = 2020;
        for(SequenceEcritureComptable seq : listeSeq) {
            int num = seq.getDerniereValeur();
            if(num>max)
            {
                max = num;
            }
        }
        max++;
        sequenceEcritureComptable.setAnnee(max);
        sequenceEcritureComptable.setDerniereValeur(max);
        listeSeq.add(sequenceEcritureComptable);
        getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(sequenceEcritureComptable,"AC");

        Assert.assertEquals(listeSeq.get(listeSeq.size()-1).getDerniereValeur(),
                getDaoProxy().getComptabiliteDao().getListSequenceEcritureComptable().
                        get(getDaoProxy().getComptabiliteDao().getListSequenceEcritureComptable()
                                .size()-1).getDerniereValeur());

        Assert.assertEquals(listeSeq.size(),getDaoProxy().getComptabiliteDao().getListSequenceEcritureComptable().size());

        System.out.println(getDaoProxy().getComptabiliteDao().getListSequenceEcritureComptable());

        // UPDATE now

        listeSeq.remove(sequenceEcritureComptable);
        sequenceEcritureComptable.setDerniereValeur(max+1);
        listeSeq.add(sequenceEcritureComptable);
        getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable(sequenceEcritureComptable,"AC");

        System.out.println(getDaoProxy().getComptabiliteDao().getListSequenceEcritureComptable());
        Assert.assertEquals(listeSeq.get(listeSeq.size()-1).getDerniereValeur(),
                getDaoProxy().getComptabiliteDao().getListSequenceEcritureComptable().
                        get(getDaoProxy().getComptabiliteDao().getListSequenceEcritureComptable()
                                .size()-1).getDerniereValeur());

        Assert.assertEquals(listeSeq.size(),getDaoProxy().getComptabiliteDao().getListSequenceEcritureComptable().size());

        Assert.assertEquals(getDaoProxy().getComptabiliteDao().
                        getLastSequenceEcritureComptable(max,"AC").getDerniereValeur(),
                sequenceEcritureComptable.getDerniereValeur());
    }

    @Test
    public void getListeEcritureComptable() throws Exception {

        List<EcritureComptable> listeEcriture = getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        EcritureComptable ecritureComptable = new EcritureComptable();
        int id = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("AC-2016/00001").getId();
        Assert.assertEquals(id,-1);

        String ref = getDaoProxy().getComptabiliteDao().getEcritureComptable(-1).getReference();
        Assert.assertEquals(ref,"AC-2016/00001");


        ecritureComptable.setDate(new Date());
        ecritureComptable.setReference(ref);
        ecritureComptable.setLibelle("huge");
        JournalComptable journalComptable = new JournalComptable();
        journalComptable.setCode("AC");
        journalComptable.setLibelle("Achat");
        ecritureComptable.setJournal(journalComptable);

        getDaoProxy().getComptabiliteDao().insertEcritureComptable(ecritureComptable);
        EcritureComptable lastEcr = getDaoProxy().getComptabiliteDao().getListEcritureComptable().
                get(getDaoProxy().getComptabiliteDao().getListEcritureComptable().size()-1);

        Assert.assertEquals(lastEcr.getReference(),ecritureComptable.getReference());

        //UPDATE
        ecritureComptable.setReference(getDaoProxy().getComptabiliteDao().
                getListEcritureComptable().get(getDaoProxy().getComptabiliteDao().
                getListEcritureComptable().size()-1).getReference()+1);

        getDaoProxy().getComptabiliteDao().updateEcritureComptable(ecritureComptable);

        Assert.assertEquals(getDaoProxy().getComptabiliteDao().
                getListEcritureComptable().get(getDaoProxy().getComptabiliteDao().
                getListEcritureComptable().size()-1).getReference(),
                ecritureComptable.getReference());


        int sizeListe = getDaoProxy().getComptabiliteDao().getListEcritureComptable().size();
        //DELETE
        int id2 = getDaoProxy().getComptabiliteDao().
                getListEcritureComptable().get(getDaoProxy().getComptabiliteDao().
                getListEcritureComptable().size()-1).getId();

        getDaoProxy().getComptabiliteDao().deleteEcritureComptable(id2);
        Assert.assertNotEquals(getDaoProxy().getComptabiliteDao().
                getListEcritureComptable().size(), sizeListe);


    }

/*

    void loadListLigneEcriture(EcritureComptable pEcritureComptable);


*/

    @Test
    public void loadListLigneEcriture() throws Exception {

        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setLibelle("TMA Appli Xxx");
       getDaoProxy().getComptabiliteDao().loadListLigneEcriture(ecritureComptable);

    }

}

