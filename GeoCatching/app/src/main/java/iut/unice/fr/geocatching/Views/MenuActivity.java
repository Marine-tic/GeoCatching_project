package iut.unice.fr.geocatching.Views;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
                startActivity(new Intent(MenuActivity.this, ListePartieActivity.class));
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, CreateGameActivity.class));
            }
        });

        btn_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, FreeMapsActivity.class));
            }
        });
    }
}
