package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import org.junit.Test;
import org.testng.Assert;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertThat;


public class ComptabiliteManagerImplTestIntegration extends BusinessTestCase {

   private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @Test
    public void testAddReference() throws Exception {
        EcritureComptable vEcritureComptable;

        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(-4);
        vEcritureComptable.setJournal(new JournalComptable("VE", "Vente"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vEcritureComptable.setDate(sdf.parse("2016-12-28 00:00:00"));

        EcritureComptable ecriture = null ;
        List<EcritureComptable> listeEcrit = getBusinessProxy().getComptabiliteManager().getListEcritureComptable();
        for( EcritureComptable ecrit : listeEcrit) {
            if (ecrit.getId()==-4) {
               ecriture = ecrit;
            }
        }

        String ref = ecriture.getReference();

        vEcritureComptable.setReference(ref);
        vEcritureComptable.setLibelle("TMA Appli Yyy");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(706),
                null, null,
                new BigDecimal(2500)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                null, new BigDecimal(3000),
                null));


        manager.addReference(vEcritureComptable);
        ref = ref.substring(ref.length()-5, ref.length());

       int refInt = Integer.parseInt(ref)+1;
        String refFinale = "VE" + "-" + 2016 + "/"+ String.format("%05d",refInt);

        Assert.assertEquals(vEcritureComptable.getReference(),refFinale );
    }

    @Test
    public void getListCompteComptable()  throws Exception {
        List<CompteComptable> listeComptesOrigine =getBusinessProxy().getComptabiliteManager().getListCompteComptable();

        CompteComptable compteComptable = new CompteComptable();

        int num = getBusinessProxy().getComptabiliteManager().getListCompteComptable().get(listeComptesOrigine.size()-1).getNumero();
        if(num == 4455 ) { num = 4457;}
        compteComptable.setNumero(num+1);
        compteComptable.setLibelle("tartaruga"+1);
        listeComptesOrigine.add(compteComptable);
        getBusinessProxy().getComptabiliteManager().insertCompteComptable(compteComptable);

        List<CompteComptable> listeComptesFinal =getBusinessProxy().getComptabiliteManager().getListCompteComptable();
        Assert.assertTrue(listeComptesOrigine.size() == listeComptesFinal.size() );

        for(int i=0; i< listeComptesFinal.size();i++) {
            boolean equalLibelle = listeComptesOrigine.get(i).getLibelle().equals(listeComptesFinal.get(i).getLibelle());
            boolean equalNumero = listeComptesOrigine.get(i).getNumero().equals(listeComptesFinal.get(i).getNumero());

            Assert.assertTrue(equalLibelle);
            Assert.assertTrue(equalNumero);
        }
       // assertThat(listeComptesOrigine, samePropertyValuesAs(listeComptesFinal));
     // (listeComptesOrigine, (listeComptesFinal));
        Assert.assertEquals(compteComptable.getLibelle(), listeComptesFinal.get(listeComptesFinal.size()-1).getLibelle());
    }

}
