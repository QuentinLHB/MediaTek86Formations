package com.example.mediatek86formations.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mediatek86formations.outils.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant d'effectuer des actions sur la base de donnée locale SQLite de l'application.
 */
public class AccesLocal {
    private final MySQLiteOpenHelper accesBD;
    private SQLiteDatabase bd;
    private static final String TABLEFAVORIS = "favoris";

    /**
     * constructeur : valorise l'accès à la BDD
     *
     * @param context Contexte
     */
    public AccesLocal(Context context) {
        String nomBase = "bdMediatek.sqlite";
        int versionBase = 1;
        accesBD = new MySQLiteOpenHelper(context, nomBase, versionBase);
    }

    /**
     * Ajoute une formation à la base de données.
     *
     * @param formation Formation à ajouter.
     */
    public void ajout(Formation formation) {
        bd = accesBD.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idformation", formation.getId());
        bd.insert(TABLEFAVORIS, null, values);
        bd.close();
    }

    /**
     * Supprime une formation de la base de données.
     *
     * @param formation Formation à supprimer.
     */
    public void suppr(Formation formation) {
        suppr(formation.getId());
    }

    /**
     * Supprime une formation de la base de données.
     * @param idFormation Id de la formation à supprimer.
     */
    public void suppr(Integer idFormation) {
        bd = accesBD.getWritableDatabase();
        bd.delete(TABLEFAVORIS, "idformation" + "=?", new String[]{String.valueOf(idFormation)});
        bd.close();
    }

    /**
     * Vérifie si une formation existe dans la base de données.
     *
     * @param formation Formation dont on vérifie l'existance.
     * @return true si elle existe dans la bdd, false sinon.
     */
    public boolean existe(Formation formation) {
        bd = accesBD.getReadableDatabase();
        Cursor curseur = bd.query(TABLEFAVORIS,
                null,
                "idformation =?",
                new String[]{String.valueOf(formation.getId())},
                null, null, null);
        boolean existe = !curseur.isAfterLast();
        curseur.close();
        return existe;
    }

    /**
     * Récupère les identifiants dans la bdd, qui correspondent aux id des formations favorites.
     *
     * @return Liste d'integer représentant les identifiants.
     */
    public List<Integer> getIdsFavoris() {
        ArrayList<Integer> lesFavoris = new ArrayList<>();
        bd = accesBD.getReadableDatabase();
        String req = "SELECT * FROM favoris";
        Cursor curseur = bd.rawQuery(req, null);
        if (!curseur.moveToFirst()) return lesFavoris;
        while (!curseur.isAfterLast()) {
            lesFavoris.add(curseur.getInt(0));
            curseur.moveToNext();
        }
        curseur.close();
        return lesFavoris;
    }


}
