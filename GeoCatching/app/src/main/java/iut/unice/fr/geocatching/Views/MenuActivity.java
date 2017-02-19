package iut.unice.fr.geocatching.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import iut.unice.fr.geocatching.R;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button btn_joinGame = (Button)findViewById(R.id.JoinGameButton);
        Button btn_create = (Button)findViewById(R.id.CreateGameButton);
        Button btn_free = (Button)findViewById(R.id.FreeButton);

        btn_joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                Intent intentListe = new Intent(MenuActivity.this, ListePartieActivity.class);
                intentListe.putExtra("name", name);
                startActivity(intentListe);
                finish();
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                Intent intentCreate = new Intent(MenuActivity.this, CreateGameActivity.class);
                intentCreate.putExtra("name", name);
                startActivity(intentCreate);
                startActivity(new Intent(MenuActivity.this, CreateGameActivity.class));
            }
        });

        btn_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                Intent intentFree = new Intent(MenuActivity.this, FreeMapsActivity.class);
                intentFree.putExtra("name", name);
                startActivity(intentFree);
                finish();
            }
        });
    }
}
