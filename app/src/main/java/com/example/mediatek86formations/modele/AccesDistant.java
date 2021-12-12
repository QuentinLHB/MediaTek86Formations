package com.example.mediatek86formations.modele;

import android.util.Log;

import com.example.mediatek86formations.controleur.Controle;
import com.example.mediatek86formations.outils.AccesREST;
import com.example.mediatek86formations.outils.AsyncResponse;
import com.example.mediatek86formations.outils.MesOutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class AccesDistant implements AsyncResponse {

    private static final String SERVERADDR = "http://192.168.0.10/rest_mediatek86formations/";
    private Controle controle;

    /**
     * constructeur
     */
    public AccesDistant(){
        controle = Controle.getInstance();
    }

    /**
     * retour du serveur distant
     * @param output
     */
    @Override
    public void processFinish(String output) {
        Log.d("serveur", "************" + output);
        try {
            JSONObject retour = new JSONObject(output);
            String message = retour.getString("message");
            if (!message.equals("OK")) {
                Log.d("erreur", "********* problème retour api rest :" + message);
            } else {
                JSONArray infos = retour.getJSONArray("result");
                ArrayList<Formation> lesFormations = new ArrayList<>();
                for (int k = 0; k < infos.length(); k++) {
                    JSONObject info = new JSONObject(infos.get(k).toString());
                    int id = Integer.parseInt(info.getString("id"));
                    Date publishedAt = MesOutils.convertStringToDate(info.getString("published_at"),
                            "yyyy-MM-dd hh:mm:ss");
                    String title = info.getString("title");
                    String description = info.getString("description");
                    String miniature = info.getString("miniature");
                    String picture = info.getString("picture");
                    String videoId = info.getString("video_id");
                    Formation formation = new Formation(id, publishedAt, title, description, miniature, picture, videoId);
                    lesFormations.add(formation);
                }
                controle.setLesFormations(lesFormations);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * envoi de données vers le serveur distant
     * @param operation
     * @param lesDonneesJSON
     */
    public void envoi(String operation, JSONObject lesDonneesJSON){
        AccesREST accesDonnees = new AccesREST();
        accesDonnees.delegate = this;
        String requesMethod = null;
        switch (operation){
            case "tous" : requesMethod="GET"; break;
        }
        if (requesMethod != null) {
            accesDonnees.setRequestMethod(requesMethod);
            accesDonnees.addParam("formation");
            if (lesDonneesJSON != null) {
                accesDonnees.addParam(lesDonneesJSON.toString());
            }
            accesDonnees.execute(SERVERADDR);
        }
    }

}
