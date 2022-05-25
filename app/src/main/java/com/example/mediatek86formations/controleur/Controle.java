package com.example.mediatek86formations.controleur;

import android.content.Context;

import com.example.mediatek86formations.modele.AccesDistant;
import com.example.mediatek86formations.modele.AccesLocal;
import com.example.mediatek86formations.modele.Formation;

import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur de l'application.
 */
public class Controle {

    private static Controle instance = null;
    private List<Formation> lesFormations = new ArrayList<>();
    private static AccesLocal accesLocal;

    /**
     * constructeur privé
     */
    private Controle() {
        super();
    }

    /**
     * récupération de l'instance unique de Controle
     *
     * @return instance
     */
    public static Controle getInstance(Context context) {
        if (Controle.instance == null) {
            Controle.instance = new Controle();
            AccesDistant accesDistant = new AccesDistant();
            accesDistant.envoi("tous", null);
            accesLocal = new AccesLocal(context);
        }
        return Controle.instance;
    }

    /**
     * Définit les formations.
     *
     * @param lesFormations Liste de formations.
     */
    public void setLesFormations(List<Formation> lesFormations) {
        this.lesFormations = lesFormations;
    }

    /**
     * Obtient la liste des formations.
     *
     * @return Liste de formations.
     */
    public List<Formation> getLesFormations() {
        return lesFormations;
    }

    /**
     * Retourne les formations dont le titre contient le filtre
     *
     * @param lesFormations Liste dans laquelle chercher
     * @param filtre        Element à filtrer.
     * @return Liste de formations filtrées.
     */
    public List<Formation> filtreFormations(List<Formation> lesFormations, String filtre) {
        ArrayList<Formation> lesFormationsFiltre = new ArrayList<>();
        for (Formation uneFormation : lesFormations) {
            if (uneFormation.getTitle().toUpperCase().contains(filtre.toUpperCase())) {
                lesFormationsFiltre.add(uneFormation);
            }
        }
        return lesFormationsFiltre;
    }

    /**
     * Met un formation en favori dans la bdd locale.
     *
     * @param formation Formation à ajouter aux favoris.
     */
    public void metEnFavori(Formation formation) {
        accesLocal.ajout(formation);
    }

    /**
     * Supprime une formation des favoris de la bdd locale.
     *
     * @param formation Formation à supprimer des favoris.
     */
    public void supprimeDesFavoris(Formation formation) {
        accesLocal.suppr(formation);
    }

    /**
     * Vérifie si une formation fait partie des favoris.
     *
     * @param formation Formation dont on recherche si elle est dans les favoris.
     * @return True si la formation est enregistrée dans les favoris, sinon faux.
     */
    public boolean estFavori(Formation formation) {
        return accesLocal.existe(formation);
    }

    /**
     * Retourne la liste des formations enregistrées dans les vaoris.
     *
     * @return Liste des formations favorites.
     */
    public List<Formation> getFavoris() {
        List<Formation> lesFavoris = new ArrayList<>();
        List<Integer> lesIdsFavoris = accesLocal.getIdsFavoris();
        for (Formation uneFormation : lesFormations) {
            if (lesIdsFavoris.contains(uneFormation.getId())) {
                lesFavoris.add(uneFormation);
            }
        }
        return lesFavoris;
    }
}
