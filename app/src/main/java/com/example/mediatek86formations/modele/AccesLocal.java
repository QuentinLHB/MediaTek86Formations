package com.example.mediatek86formations.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mediatek86formations.outils.MySQLiteOpenHelper;

import java.util.ArrayList;

public class AccesLocal {
    private String nomBase = "bdCoach.sqlite";
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

    public void ajout(Formation formation){
        bd = accesBD.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idformation", formation.getId());
        bd.insert("favoris", null, values);
        bd.close();
    }

    public void suppr(Formation formation){
        bd = accesBD.getWritableDatabase();
        bd.delete("favoris", "idformation" + "=?" , new String[]{String.valueOf(formation.getId())});
        bd.close();
    }

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

    public ArrayList<Integer> getIdsFavoris(){
        ArrayList<Integer> lesFavoris = new ArrayList<>();
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
