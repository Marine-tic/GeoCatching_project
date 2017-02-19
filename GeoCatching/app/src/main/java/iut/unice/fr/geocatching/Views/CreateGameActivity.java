package iut.unice.fr.geocatching.Views;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import iut.unice.fr.geocatching.R;

public class CreateGameActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        Button btn_createTerrainZone = (Button)findViewById(R.id.CreateTerrainZoneButton);
        final EditText editText_namePartie = (EditText) findViewById(R.id.partieName);
        final DatePicker datePicker_dateFin = (DatePicker) findViewById(R.id.datePickerFin);

        Intent intent = getIntent();
        String namePartie = intent.getStringExtra("namePartie");
        String jourFin = intent.getStringExtra("jourFin");
        String moisFin = intent.getStringExtra("moisFin");
        String anneeFin = intent.getStringExtra("anneeFin");

        if(namePartie != null) {
            editText_namePartie.setText(namePartie);
        }

        if(jourFin != null && moisFin != null && anneeFin != null) {
            datePicker_dateFin.updateDate(Integer.parseInt(anneeFin), Integer.parseInt(moisFin), Integer.parseInt(jourFin));
        }

        btn_createTerrainZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_namePartie.getText().toString().trim().equals("")) {
                    editText_namePartie.setError( "Un nom de partie est requis" );
                }else{
                    Intent intentCreate = new Intent(CreateGameActivity.this,  CreateMapsActivity.class);
                    intentCreate.putExtra("namePartie", editText_namePartie.getText().toString());
                    intentCreate.putExtra("jourFin", datePicker_dateFin.getDayOfMonth()+"");
                    intentCreate.putExtra("moisFin", datePicker_dateFin.getMonth()+"");
                    intentCreate.putExtra("anneeFin", datePicker_dateFin.getYear()+"");
                    startActivity(intentCreate);
                    finish();
                }
            }
        });
    }
}
