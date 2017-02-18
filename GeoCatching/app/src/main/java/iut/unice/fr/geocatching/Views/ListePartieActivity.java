package iut.unice.fr.geocatching.Views;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import iut.unice.fr.geocatching.Models.Partie;
import iut.unice.fr.geocatching.R;
import iut.unice.fr.geocatching.ViewsModels.VMListePartieActivity;

public class ListePartieActivity extends FragmentActivity {
    ListView mListView;
    String[] partie = new String[]{};
    VMListePartieActivity Ctrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ctrl = new VMListePartieActivity();
        setContentView(R.layout.activity_liste_partie);

        mListView = (ListView) findViewById(R.id.listView);
        ArrayList<Partie> malistepartie = new ArrayList<>();
        Partie p1 = new Partie("Partie1",new Date());
        Partie p2 = new Partie("Partie2",new Date());
        Partie p3 = new Partie("Partie3",new Date());
        Partie p4 = new Partie("Partie4",new Date());
        malistepartie.add(p1);
        malistepartie.add(p2);
        malistepartie.add(p3);
        malistepartie.add(p4);

        //Ctrl.getPartieListe();

        for(Partie maPartie : malistepartie){
            partie[partie.length] = maPartie.getNom();
        }
        System.out.println(partie);
        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListePartieActivity.this, android.R.layout.simple_list_item_1, partie);
        mListView.setAdapter(adapter);
        Button btn_join = (Button)findViewById(R.id.JoinButton);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListePartieActivity.this, JoinMapsActivity.class));
            }
        });
    }
}
