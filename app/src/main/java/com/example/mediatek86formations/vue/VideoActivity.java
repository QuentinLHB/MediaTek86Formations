package com.example.mediatek86formations.vue;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediatek86formations.*;
import com.example.mediatek86formations.controleur.Controle;
import com.example.mediatek86formations.modele.Formation;

public class VideoActivity extends AppCompatActivity {

    /**
     * objet d'affichage de la vidéo
     */
    WebView wbvYoutube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        init();
    }

    /**
     * Affichage  de la vidéo
     */
    private void init(){
        Formation formation = (Formation) getIntent().getSerializableExtra("formation");
        if(formation!=null) {
            wbvYoutube = findViewById(R.id.wbvYoutube);
            wbvYoutube.getSettings().setJavaScriptEnabled(true);
            wbvYoutube.setWebViewClient(new WebViewClient());
            wbvYoutube.loadUrl("https://www.youtube.com/embed/" + formation.getVideoId());
        }
    }

}