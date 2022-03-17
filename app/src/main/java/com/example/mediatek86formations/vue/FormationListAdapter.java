package com.example.mediatek86formations.vue;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mediatek86formations.R;
import com.example.mediatek86formations.controleur.Controle;
import com.example.mediatek86formations.modele.Formation;

import java.util.ArrayList;

public class FormationListAdapter extends BaseAdapter {

    private ArrayList<Formation> lesFormations;
    private LayoutInflater inflater;
    private Controle controle;
    private Context context;

    /**
     * @param lesFormations
     * @param context
     */
    public FormationListAdapter(ArrayList<Formation> lesFormations, Context context) {
        this.lesFormations = lesFormations;
        this.controle = Controle.getInstance(context);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * @return nombre de formations
     */
    @Override
    public int getCount() {
        return lesFormations.size();
    }

    /**
     * @param i position de l'item
     * @return valeur à cette position
     */
    @Override
    public Object getItem(int i) {
        return lesFormations.get(i);
    }

    /**
     * @param i position de l'item
     * @return id à cette position
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Cobstruction de la ligne
     *
     * @param position
     * @param view
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Formation laFormation = lesFormations.get(position);
        ViewProperties viewProperties;
        if (view == null) {
            viewProperties = new ViewProperties();
            view = inflater.inflate(R.layout.layout_liste_formations, null);
            viewProperties.txtListeTitle = (TextView) view.findViewById(R.id.txtListTitle);
            viewProperties.txtListPublishedAt = (TextView) view.findViewById(R.id.txtListPublishedAt);
            viewProperties.btnListFavori = (ImageButton) view.findViewById(R.id.btnListFavori);
            int img;
            if (controle.estFavori(laFormation)) {
                img = R.drawable.coeur_rouge;
            } else {
                img = R.drawable.coeur_gris;
            }
            viewProperties.btnListFavori.setImageResource(img);
            viewProperties.btnListFavori.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int img;
                    if (controle.estFavori(laFormation)) {
                        controle.enleverDesFavoris(laFormation);
                        img = R.drawable.coeur_gris;

                    } else {
                        controle.mettreEnFavori(laFormation);
                        img = R.drawable.coeur_rouge;
                    }
                    viewProperties.btnListFavori.setImageResource(img);
                    notifyDataSetChanged();
                }
            });
            view.setTag(viewProperties);
        } else {
            viewProperties = (ViewProperties) view.getTag();
        }
        viewProperties.txtListeTitle.setText(lesFormations.get(position).getTitle());
        viewProperties.txtListPublishedAt.setText(lesFormations.get(position).getPublishedAtToString());
        viewProperties.txtListeTitle.setTag(position);
        viewProperties.txtListeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirUneFormationActivity(v);
            }
        });
        viewProperties.txtListPublishedAt.setTag(position);
        viewProperties.txtListPublishedAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirUneFormationActivity(v);
            }
        });
        return view;
    }

    /**
     * Ouvre la page du détail de la formation
     *
     * @param v
     */
    private void ouvrirUneFormationActivity(View v) {
        int indice = (int) v.getTag();
        controle.setFormation(lesFormations.get(indice));
        Intent intent = new Intent(context, UneFormationActivity.class);
        context.startActivity(intent);
    }

    /**
     * Propriétés de la ligne
     */
    private class ViewProperties {
        ImageButton btnListFavori;
        TextView txtListPublishedAt;
        TextView txtListeTitle;
    }

}
