package com.example.mediatek86formations.controleur;

import android.content.Context;

import com.example.mediatek86formations.modele.AccesDistant;
import com.example.mediatek86formations.modele.AccesLocal;
import com.example.mediatek86formations.modele.Formation;

import java.text.Normalizer;
import java.util.ArrayList;

public class Controle {

    private static Controle instance = null;
    private ArrayList<Formation> lesFormations = new ArrayList<>();
    private Formation formation = null;
    private static AccesLocal accesLocal;

    public boolean isNavigFavoris() {
        return navigFavoris;
    }

    public void setNavigFavoris(boolean navigFavoris) {
        this.navigFavoris = navigFavoris;
    }

    private boolean navigFavoris = false;

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

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public ArrayList<Formation> getLesFormations() {
        return lesFormations;
    }

    /**
     * retourne les formations dont le titre contient le filtre
     *
     * @param filtre
     * @return
     */
//    public ArrayList<Formation> getLesFormationFiltre(String filtre) {
//        return filtreFormations(this.lesFormations, filtre);
//    }
//
//    public ArrayList<Formation> getFavorisFiltres(String filtre){
//        return filtreFormations(getFavoris(), filtre);
//    }

    public ArrayList<Formation> filtreFormations(ArrayList<Formation> lesFormations, String filtre){
        ArrayList<Formation> lesFormationsFiltre = new ArrayList<>();
        for (Formation uneFormation : lesFormations) {
            if (uneFormation.getTitle().toUpperCase().contains(filtre.toUpperCase())) {
                lesFormationsFiltre.add(uneFormation);
            }
        }
        return lesFormationsFiltre;
    }

    public void setLesFormations(ArrayList<Formation> lesFormations) {
        this.lesFormations = lesFormations;
    }

    public void metEnFavori(Formation formation) {
        accesLocal.ajout(formation);
    }

    public void supprimeDesFavoris(Formation formation){
        accesLocal.suppr(formation);
    }

    public boolean estFavori(Formation formation) {
        return accesLocal.existe(formation);
    }

    public ArrayList<Formation> getFavoris(){
        ArrayList<Formation> lesFavoris = new ArrayList<>();
        ArrayList<Integer> lesIdsFavoris = accesLocal.getIdsFavoris();
        for(Formation formation : lesFormations){
            if(lesIdsFavoris.contains(formation.getId())){
                lesFavoris.add(formation);
            }
        }
        return lesFavoris;
    }

}
