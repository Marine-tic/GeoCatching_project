package iut.unice.fr.geocatching.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import iut.unice.fr.geocatching.R;

public class ListePartieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_partie);

        Button btn_join = (Button)findViewById(R.id.JoinButton);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListePartieActivity.this, MapsActivity.class));
            }
        });
    }
}
