package com.example.mediatek86formations.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mediatek86formations.R;
import com.example.mediatek86formations.controleur.Controle;
import com.example.mediatek86formations.modele.Formation;

import java.util.ArrayList;
import java.util.Collections;

public class FormationsActivity extends AppCompatActivity {

    private Controle controle;
    private Button btnFiltrer;
    private EditText txtFiltre;
    private ArrayList<Formation> lesFormations;

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
        if(controle.isNavigFavoris()){
            lesFormations = controle.getFavoris();
        }else{
            lesFormations = controle.getLesFormations();
        }

        controle = Controle.getInstance(FormationsActivity.this);
        btnFiltrer = (Button) findViewById(R.id.btnFiltrer);
        txtFiltre = (EditText) findViewById(R.id.txtFiltre);
        creerListe(lesFormations);

        btnFiltrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pas de filtre : Toutes les entrées (toutes le formations ou tous les favs
                if(txtFiltre.getText().toString().isEmpty()){
                    creerListe(lesFormations);
                }
                // Filtre
                else{
                    creerListe(controle.filtreFormations(lesFormations, txtFiltre.getText().toString()));
                }
                btnFiltrer.requestFocus();
            }
        });
        btnFiltrer.requestFocus();
    }

    /**
     * création de la liste adapter
     */
    private void creerListe(ArrayList<Formation> lesFormations){
        if(lesFormations != null){
            Collections.sort(lesFormations, Collections.<Formation>reverseOrder());
            ListView listView = (ListView)findViewById(R.id.lstFormations);
            FormationListAdapter adapter = new FormationListAdapter(lesFormations,FormationsActivity.this);
            listView.setAdapter(adapter);
        }
    }

}