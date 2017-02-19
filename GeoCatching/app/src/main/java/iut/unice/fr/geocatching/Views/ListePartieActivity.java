package iut.unice.fr.geocatching.Views;

import android.content.Intent;
import android.database.MatrixCursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

import iut.unice.fr.geocatching.Models.Partie;
import iut.unice.fr.geocatching.R;
import iut.unice.fr.geocatching.ViewsModels.VMListePartieActivity;

public class ListePartieActivity extends FragmentActivity {
    ListView mListView;
    String[] listeStringPartie = new String[]{"_id", "col1",};
    VMListePartieActivity Ctrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_partie);

        Ctrl = new VMListePartieActivity();
        mListView = (ListView) findViewById(R.id.listView);
        final ArrayList<Partie> malistepartie =  Ctrl.getPartieListe();
        int i = 0;

        MatrixCursor matrixCursor= new MatrixCursor(listeStringPartie);
        startManagingCursor(matrixCursor);
        for(Partie maPartie : malistepartie){
            matrixCursor.addRow(new Object[] {i,maPartie.getNom()});
            i++;
        }
        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"
        String[] from = new String[] {"col1"};

        int[] to = new int[] { R.id.textViewCol1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row_item, matrixCursor, from,to, 0);
        mListView.setAdapter(adapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                Intent intentListe = new Intent(ListePartieActivity.this,  JoinMapsActivity.class);
                intentListe.putExtra("name", name);
                intentListe.putExtra("namePartie",malistepartie.get(position).getNom());
                startActivity(intentListe);
            }
        };

        mListView.setOnItemClickListener(itemClickListener);
    }
}
