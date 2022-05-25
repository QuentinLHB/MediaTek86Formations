package com.example.mediatek86formations.vue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediatek86formations.*;
import com.example.mediatek86formations.controleur.Controle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * initialisations
     */
    private void init(){
        Controle.getInstance(MainActivity.this);
        creerMenu();
    }

    /**
     * appelle les procédures événementielles pour gérer le menu
     */
    private void creerMenu(){
        ecouteMenu(findViewById(R.id.btnFormations), false);
        ecouteMenu(findViewById(R.id.btnFavoris), true);
    }

    /**
     * procédure événementielle sur le clic d'une image du menu
     * @param btn Button sur lequel ajouter un évènement.
     * @param iNavigFavoris True si l'utilisateur va parcourir les favoris.
     */
    private void ecouteMenu(ImageButton btn, boolean iNavigFavoris){
        btn.setOnClickListener(v -> {
            Activity activity = MainActivity.this;
            Intent intent = new Intent(activity, FormationsActivity.class);
            intent.putExtra("navig", iNavigFavoris);
            activity.startActivity(intent);
        });
    }

}