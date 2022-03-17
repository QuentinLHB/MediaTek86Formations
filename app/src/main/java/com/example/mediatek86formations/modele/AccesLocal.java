package com.example.mediatek86formations.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mediatek86formations.outils.MySQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Classe permettant d'effectuer des actions sur la base de donnée locale SQLite de l'application.
 */
public class AccesLocal {
    private String nomBase = "bdMediatek.sqlite";
    private Integer versionBase = 1;
    private MySQLiteOpenHelper accesBD;
    private SQLiteDatabase bd;

    /**
     * constructeur : valorise l'accès à la BDD
     * @param context
     */
    public AccesLocal(Context context){
        accesBD = new MySQLiteOpenHelper(context, nomBase, versionBase);
    }

    /**
     * Ajoute une formation à la base de données.
     * @param formation Formation à ajouter.
     */
    public void ajout(Formation formation){
        bd = accesBD.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idformation", formation.getId());
        bd.insert("favoris", null, values);
        bd.close();
    }

    /**
     * Supprime une formation de la base de données.
     * @param formation Formation à supprimer.
     */
    public void suppr(Formation formation){
        bd = accesBD.getWritableDatabase();
        bd.delete("favoris", "idformation" + "=?" , new String[]{String.valueOf(formation.getId())});
        bd.close();
    }

    /**
     * Vérifie si une formation existe dans la base de données.
     * @param formation Formation dont on vérifie l'existance.
     * @return true si elle existe dans la bdd, false sinon.
     */
    public boolean existe(Formation formation){
        bd = accesBD.getReadableDatabase();
        Cursor curseur = bd.query("favoris",
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
     * @return Liste d'integer représentant les identifiants.
     */
    public ArrayList<Integer> getIdsFavoris(){
        ArrayList<Integer> lesFavoris = new ArrayList<>();
        bd = accesBD.getReadableDatabase();
        String req = "SELECT * FROM favoris";
        Cursor curseur = bd.rawQuery(req, null);
        if(!curseur.moveToFirst()) return lesFavoris;
        while (!curseur.isAfterLast()){
            lesFavoris.add(curseur.getInt(0));
            curseur.moveToNext();
        }
        curseur.close();
        return lesFavoris;
    }


}
