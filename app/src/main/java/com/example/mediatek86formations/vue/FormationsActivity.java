package com.example.mediatek86formations.vue;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediatek86formations.*;
import com.example.mediatek86formations.controleur.Controle;
import com.example.mediatek86formations.modele.Formation;

import java.util.Collections;
import java.util.List;

public class FormationsActivity extends AppCompatActivity {

    private Controle controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controle = Controle.getInstance(FormationsActivity.this);
        setContentView(R.layout.activity_formations);
        init();
    }

    /**
     * initialisations
     */
    private void init(){
        List<Formation> lesFormations;

        if(controle.isNavigFavoris()){
            lesFormations = controle.getFavoris();
        }else{
            lesFormations = controle.getLesFormations();
        }

        controle = Controle.getInstance(FormationsActivity.this);
        Button btnFiltrer = findViewById(R.id.btnFiltrer);
        EditText txtFiltre = findViewById(R.id.txtFiltre);
        creerListe(lesFormations);

        btnFiltrer.setOnClickListener(view -> {
            // Pas de filtre : Toutes les entrées (toutes le formations ou tous les favs
            if(txtFiltre.getText().toString().isEmpty()){
                creerListe(lesFormations);
            }
            // Filtre
            else{
                creerListe(controle.filtreFormations(lesFormations, txtFiltre.getText().toString()));
            }
            View vue = this.getCurrentFocus();
            if (vue != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        btnFiltrer.requestFocus();
    }

    /**
     * création de la liste adapter
     */
    private void creerListe(List<Formation> lesFormations){
        if(lesFormations != null){
            Collections.sort(lesFormations, Collections.reverseOrder());
            ListView listView = findViewById(R.id.lstFormations);
            FormationListAdapter adapter = new FormationListAdapter(lesFormations,FormationsActivity.this);
            listView.setAdapter(adapter);
        }
    }

}