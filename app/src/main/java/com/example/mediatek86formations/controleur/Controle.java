package com.example.mediatek86formations.controleur;

import android.content.Context;

import com.example.mediatek86formations.modele.AccesDistant;
import com.example.mediatek86formations.modele.AccesLocal;
import com.example.mediatek86formations.modele.Formation;

import java.text.Normalizer;
import java.util.ArrayList;

/**
 * Contrôleur de l'application.
 */
public class Controle {

    private static Controle instance = null;
    private ArrayList<Formation> lesFormations = new ArrayList<>();
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
    public static final Controle getInstance(Context context) {
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
    public void setLesFormations(ArrayList<Formation> lesFormations) {
        this.lesFormations = lesFormations;
    }

    /**
     * Obtient la liste des formations.
     *
     * @return Liste de formations.
     */
    public ArrayList<Formation> getLesFormations() {
        return lesFormations;
    }

    /**
     * Retourne les formations dont le titre contient le filtre
     *
     * @param lesFormations Liste dans laquelle chercher
     * @param filtre        Element à filtrer.
     * @return Liste de formations filtrées.
     */
    public ArrayList<Formation> filtreFormations(ArrayList<Formation> lesFormations, String filtre) {
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
     * @param formation
     */
    public void metEnFavori(Formation formation) {
        accesLocal.ajout(formation);
    }

    /**
     * Supprime une formation des favoris de la bdd locale.
     *
     * @param formation
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
    public ArrayList<Formation> getFavoris() {
        ArrayList<Formation> lesFavoris = new ArrayList<>();
        ArrayList<Integer> lesIdsFavoris = accesLocal.getIdsFavoris();
        for (Formation formation : lesFormations) {
            if (lesIdsFavoris.contains(formation.getId())) {
                lesFavoris.add(formation);
            }
        }
        return lesFavoris;
    }

    // --------------- Elements définis durant la navigation ----------------
    private Formation formation = null;
    private boolean navigFavoris = false;

    /**
     * Définit la formation sélectionnée par l'utilisateur.
     *
     * @param formation formation cliquée par l'utilisateur.
     */
    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    /**
     * Obtient la formation sélectionnée par l'utilisateur
     *
     * @return Formation sélectionnée.
     */
    public Formation getFormation() {
        return formation;
    }


    /**
     * Retourne un booléen correspondant à l'onglet actuellement navigué par l'utilisateur.
     * @return True si l'utilisateur navigue dans les favoris, false sinon.
     */
    public boolean isNavigFavoris() {
        return navigFavoris;
    }

    /**
     * Définit un booléen en fonction de l'onglet actuellement navigué par l'utilisateur.
     * @param navigFavoris True si l'utilisateur navigue dans les favoris, false sinon.
     */
    public void setNavigFavoris(boolean navigFavoris) {
        this.navigFavoris = navigFavoris;
    }


}
