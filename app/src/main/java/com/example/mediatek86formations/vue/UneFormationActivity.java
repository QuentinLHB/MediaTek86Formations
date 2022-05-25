package com.example.mediatek86formations.vue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediatek86formations.*;
import com.example.mediatek86formations.controleur.Controle;
import com.example.mediatek86formations.modele.Formation;
import com.example.mediatek86formations.outils.MesOutils;

public class UneFormationActivity extends AppCompatActivity {

    private ImageButton btnPicture;
    private Formation formation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_une_formation);
        init();
    }

    /**
     * Remplissage des objets graphiques
     */
    private void init() {
        TextView txtPublishedAt = findViewById(R.id.txtPublishedAt);
        TextView txtTitle = findViewById(R.id.txtTitle);
        TextView txtDescription = findViewById(R.id.txtDescription);
        btnPicture = findViewById(R.id.btnPicture);
        Intent intent = getIntent();
        formation = (Formation) intent.getSerializableExtra("formation");
        if (formation != null) {
            txtPublishedAt.setText(formation.getPublishedAtToString());
            txtTitle.setText(formation.getTitle());
            txtDescription.setText(formation.getDescription());
            MesOutils.loadMapPreview(btnPicture, formation.getPicture());
        }
        ecouteBtnPicture();
    }

    /**
     * Procédure événementielle sur le clic du bouton btnPicture
     */
    private void ecouteBtnPicture() {
        btnPicture.setOnClickListener(v -> {
            Activity activity = UneFormationActivity.this;
            Intent intent = new Intent(activity, VideoActivity.class);
            intent.putExtra("formation", formation);
            activity.startActivity(intent);
        });
    }

}