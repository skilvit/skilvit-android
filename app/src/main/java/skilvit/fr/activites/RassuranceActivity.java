package skilvit.fr.activites;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import skilvit.fr.R;

public class RassuranceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rassurance);

        //L'activité permettra de communiquer à d'autres personnes que l'on ne va pas bien,
        // ça envoie un sms pré-rempli, on pourra aussi donner les numéros
    }

}
