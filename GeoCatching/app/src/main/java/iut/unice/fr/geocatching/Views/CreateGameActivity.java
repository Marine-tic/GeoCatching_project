package iut.unice.fr.geocatching.Views;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import iut.unice.fr.geocatching.R;

public class CreateGameActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        Button btn_validateGame = (Button)findViewById(R.id.ValidateCreateGameButton);
        Button btn_createTerrainZone = (Button)findViewById(R.id.CreateTerrainZoneButton);

        btn_validateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateGameActivity.this, ListePartieActivity.class));
            }
        });

        btn_createTerrainZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateGameActivity.this, CreateMapsActivity.class));
            }
        });


    }
}
