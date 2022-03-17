package com.example.mediatek86formations.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mediatek86formations.outils.MySQLiteOpenHelper;

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
        Cursor curseur = bd.query("favoris", null, "idformation =?", new String[]{String.valueOf(formation.getId())}, null, null, null);
        System.out.print(curseur.getCount());
        boolean existe = !curseur.isAfterLast();
        curseur.close();
        return existe;
    }


}
