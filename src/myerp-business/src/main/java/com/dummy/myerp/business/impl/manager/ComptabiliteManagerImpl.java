package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Comptabilite manager implementation.
 */
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

    // ==================== Attributs ====================


    // ==================== Constructeurs ====================
    /**
     * Instantiates a new Comptabilite manager.
     */
    public ComptabiliteManagerImpl() {
    }


    // ==================== Getters/Setters ====================
    @Override
    public List<CompteComptable> getListCompteComptable() {
        return getDaoProxy().getComptabiliteDao().getListCompteComptable();
    }


    @Override
    public List<JournalComptable> getListJournalComptable() {
        return getDaoProxy().getComptabiliteDao().getListJournalComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public List<SequenceEcritureComptable> getListSequenceEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListSequenceEcritureComptable();
    }

    @Override
    public SequenceEcritureComptable getLastSequenceEcritureComptable(int year, String code) throws FunctionalException {
        return getDaoProxy().getComptabiliteDao().getLastSequenceEcritureComptable(year,code);
    }

    @Override
    public void newSequence(SequenceEcritureComptable pSequenceEcritureComptable, String code) throws FunctionalException {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(pSequenceEcritureComptable, code);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    @Override
    public void updateSequence(SequenceEcritureComptable pSequenceEcritureComptable, String code) throws FunctionalException {

        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable(pSequenceEcritureComptable,  code);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }
    /**
     * {@inheritDoc}
     */
    // TODO à tester
    @Override
    public synchronized void addReference(EcritureComptable pEcritureComptable) throws FunctionalException {
        // TODO à implémenter

      Date year = pEcritureComptable.getDate();

      Calendar cal = Calendar.getInstance();
        cal.setTime(year);
        int yy = cal.get(Calendar.YEAR);

        System.out.println(yy);

try {
    SequenceEcritureComptable seq = getLastSequenceEcritureComptable(yy, pEcritureComptable.getJournal().getCode());

    String y = pEcritureComptable.getReference();
    y = y.substring(y.length()-5, y.length());

    String reff = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(pEcritureComptable.getReference()).getReference();
    reff = reff.substring(reff.length()-5, reff.length());
    System.out.println("ref en base : "+ reff);


    System.out.println("derniere ref : "+y);
    int nouvelleValeur = Integer.parseInt(y);
    System.out.print(nouvelleValeur);
    int x;
    if(seq== null) {
       x=1;
    }
    else {

        x = seq.getDerniereValeur()+1;
    }


    String ref = pEcritureComptable.getReference();

    SequenceEcritureComptable nouvelleSeq = new SequenceEcritureComptable();
    nouvelleSeq.setAnnee(yy);
    nouvelleSeq.setDerniereValeur(x);
    nouvelleValeur++;

    List<EcritureComptable> listeEc = getDaoProxy().getComptabiliteDao().getListEcritureComptable();

    for(EcritureComptable ecriture : listeEc)
    {
        String refEcritureEnCours = ecriture.getReference();
        String refBoucle =refEcritureEnCours.substring(refEcritureEnCours.length()-5, refEcritureEnCours.length());
        int refBis = Integer.parseInt(refBoucle);
        Date dateBoucleNew = ecriture.getDate();
        Date dateBoucleRef = pEcritureComptable.getDate();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(dateBoucleNew);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(dateBoucleRef);
        int yearBoucleNew = cal2.get(Calendar.YEAR);
        int yearBoucleRef = cal1.get(Calendar.YEAR);

        System.out.println(" derniere reference = " +ecriture.getReference());
        System.out.println(" nouvelle valeur = " +nouvelleValeur);

        if(yearBoucleNew == yearBoucleRef &&
                ecriture.getJournal().getCode().equals(pEcritureComptable.getJournal().getCode())
                && refBis == nouvelleValeur)
        {
             nouvelleValeur++;
            System.out.println(" derniere reference = " +ecriture.getReference());
            System.out.println(" nouvelle valeur = " +nouvelleValeur);
        }
    }
    String nvelleRef = pEcritureComptable.getJournal().getCode() + "-" + yy + "/"+ String.format("%05d",nouvelleValeur);


    pEcritureComptable.setReference(nvelleRef);

    updateEcritureComptable(pEcritureComptable);

    if(x==1) {
        newSequence(nouvelleSeq,pEcritureComptable.getJournal().getCode());
    }
    else {
        updateSequence(nouvelleSeq,pEcritureComptable.getJournal().getCode());
    }



}
catch(Exception e) {
    System.out.print(e);
}

        // Bien se réferer à la JavaDoc de cette méthode !
        /* Le principe :
                1.  Remonter depuis la persitance la dernière valeur de la séquence du journal pour l'année de l'écriture
                    (table sequence_ecriture_comptable)
                2.  * S'il n'y a aucun enregistrement pour le journal pour l'année concernée :
                        1. Utiliser le numéro 1.
                    * Sinon :
                        1. Utiliser la dernière valeur + 1
                3.  Mettre à jour la référence de l'écriture avec la référence calculée (RG_Compta_5)
                4.  Enregistrer (insert/update) la valeur de la séquence en persitance
                    (table sequence_ecriture_comptable)
         */



    }

    /**
     * {@inheritDoc}
     */
    // TODO à tester
    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    // TODO tests à compléter
    public void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
                                          new ConstraintViolationException(
                                              "L'écriture comptable ne respecte pas les contraintes de validation",
                                              vViolations));
        }

        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        if (!pEcritureComptable.isEquilibree()) {
            throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
        }

        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }
        }
        // On test le nombre de lignes car si l'écriture à une seule ligne
        //      avec un montant au débit et un montant au crédit ce n'est pas valable
        if (pEcritureComptable.getListLigneEcriture().size() < 2
            || vNbrCredit < 1
            || vNbrDebit < 1) {
            throw new FunctionalException(
                "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        }

        // TODO ===== RG_Compta_5 : Format et contenu de la référence
        // vérifier que l'année dans la référence correspond bien à la date de l'écriture, idem pour le code journal...


        String anneeString = pEcritureComptable.getReference().substring(3,7);
        int anneeInt = Integer.parseInt(anneeString);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String yearRef = sdf.format(pEcritureComptable.getDate());
        int yearInt = Integer.parseInt(yearRef);

        if (anneeInt != yearInt) {
            throw new FunctionalException("L'année de la référence doit correspondre à la date de l'écriture.");
        }

        String codeRef = pEcritureComptable.getReference().substring(0,2);

        if(!codeRef.equals(pEcritureComptable.getJournal().getCode())) {
            throw new FunctionalException("a référence doit avoir le même code que le code journal de l'Ecriture");
        }
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
     * (unicité de la référence, année comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    public void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            try {
                // Recherche d'une écriture ayant la même référence
                EcritureComptable vECRef = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(
                    pEcritureComptable.getReference());

                // Si l'écriture à vérifier est une nouvelle écriture (id == null),
                // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
                // c'est qu'il y a déjà une autre écriture avec la même référence
                if (pEcritureComptable.getId() == null
                    || !pEcritureComptable.getId().equals(vECRef.getId())) {
                    throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
                }
            } catch (NotFoundException vEx) {
                // Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la même référence.
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    @Override
    public void insertCompteComptable(CompteComptable pCompteComptable) throws FunctionalException {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertCompteComptable(pCompteComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEcritureComptable(Integer pId) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }
}
