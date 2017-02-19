package iut.unice.fr.geocatching.Views;

import android.content.Intent;
import android.database.MatrixCursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import iut.unice.fr.geocatching.R;
import iut.unice.fr.geocatching.ViewsModels.VMListePartieActivity;

public class ListeEquipeActivity extends FragmentActivity {
    String[] listeStringEquipe = new String[]{"_id", "col1",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_equipe);

        final VMListePartieActivity vmListePartieActivity = new VMListePartieActivity();
        ListView mListView = (ListView) findViewById(R.id.listViewEquipe);
        Intent intent = getIntent();
        String namePartie = intent.getStringExtra("namePartie");
        final ArrayList<String> malisteequipe =  vmListePartieActivity.getEquipeListe(namePartie);
        int i = 0;

        MatrixCursor matrixCursor= new MatrixCursor(listeStringEquipe);
        startManagingCursor(matrixCursor);
        for(String monEquipe : malisteequipe){
            matrixCursor.addRow(new Object[] {i,monEquipe});
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
                String namePartie = intent.getStringExtra("namePartie");
                Intent intentListe = new Intent(ListeEquipeActivity.this, JoinMapsActivity.class);
                intentListe.putExtra("name", name);
                intentListe.putExtra("namePartie", namePartie);
                intentListe.putExtra("nameEquipe",malisteequipe.get(position));
                vmListePartieActivity.rejoindreEquipe(name, namePartie, malisteequipe.get(position));
                startActivity(intentListe);
                finish();
            }
        };

        mListView.setOnItemClickListener(itemClickListener);
    }
}
