package iut.unice.fr.geocatching;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button) findViewById(R.id.ConnectButton);
        View.OnClickListener Connect = new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_maps);
            }
        };
        b1.setOnClickListener(Connect);
    }

}
