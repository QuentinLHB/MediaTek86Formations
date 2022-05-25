package com.example.mediatek86formations.vue;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mediatek86formations.*;
import com.example.mediatek86formations.controleur.Controle;
import com.example.mediatek86formations.modele.Formation;

import java.util.List;

public class FormationListAdapter extends BaseAdapter {

    private final List<Formation> lesFormations;
    private final LayoutInflater inflater;
    private final Controle controle;
    private final Context context;
    private final boolean isNavigFavoris;

    /**
     * @param lesFormations Liste de formations à afficher.
     * @param context Contexte
     */
    public FormationListAdapter(List<Formation> lesFormations, boolean isNavigFavoris, Context context) {
        this.lesFormations = lesFormations;
        this.controle = Controle.getInstance(context);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.isNavigFavoris = isNavigFavoris;

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
     * Construction de la ligne
     *
     * @param position Position dans la liste.
     * @param view Vue
     * @param parent Parent
     * @return Vue
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewProperties viewProperties;
        if (view == null) {
            viewProperties = new ViewProperties();
            view = inflater.inflate(R.layout.layout_liste_formations, null);
            viewProperties.txtListeTitle = view.findViewById(R.id.txtListTitle);
            viewProperties.txtListPublishedAt = view.findViewById(R.id.txtListPublishedAt);
            viewProperties.btnListFavori = view.findViewById(R.id.btnListFavori);


            view.setTag(viewProperties);
        } else {
            viewProperties = (ViewProperties) view.getTag();
        }
        setBtnFavoriOnClickListener(viewProperties.btnListFavori, lesFormations.get(position));
        viewProperties.txtListeTitle.setText(lesFormations.get(position).getTitle());
        viewProperties.txtListPublishedAt.setText(lesFormations.get(position).getPublishedAtToString());
        viewProperties.txtListeTitle.setTag(position);
        viewProperties.txtListeTitle.setOnClickListener(this::ouvrirUneFormationActivity);
        viewProperties.txtListPublishedAt.setTag(position);
        viewProperties.txtListPublishedAt.setOnClickListener(this::ouvrirUneFormationActivity);
        int img = controle.estFavori(lesFormations.get(position)) ? R.drawable.coeur_rouge : R.drawable.coeur_gris;
        viewProperties.btnListFavori.setImageResource(img);
        return view;
    }

    /**
     * Applique la procédure évenementielle selon le contexte : Ajout ou suppression de favoris.
     * @param btnFavori Bouton favori de la ligne.
     * @param formation Formation de la ligne.
     */
    private void setBtnFavoriOnClickListener(ImageButton btnFavori, Formation formation){
        if(isNavigFavoris){
            btnFavori.setOnClickListener(view -> {
                controle.supprimeDesFavoris(formation);
                lesFormations.remove(formation);
                notifyDataSetChanged();
            });
        }
        else{
            btnFavori.setOnClickListener(view -> {
                int img;
                if (controle.estFavori(formation)) {
                    controle.supprimeDesFavoris(formation);
                    img = R.drawable.coeur_gris;

                } else {
                    controle.metEnFavori(formation);
                    img = R.drawable.coeur_rouge;
                }
                btnFavori.setImageResource(img);
                notifyDataSetChanged();
            });
        }
    }

    /**
     * Ouvre la page du détail de la formation
     *
     * @param v Vue
     */
    private void ouvrirUneFormationActivity(View v) {
        int indice = (int) v.getTag();
        Intent intent = new Intent(context, UneFormationActivity.class);
        intent.putExtra("formation", lesFormations.get(indice));
        context.startActivity(intent);
    }

    /**
     * Propriétés de la ligne
     */
    private static class ViewProperties {
        ImageButton btnListFavori;
        TextView txtListPublishedAt;
        TextView txtListeTitle;
    }

}
