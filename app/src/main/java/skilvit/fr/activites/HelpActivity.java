package skilvit.fr.activites;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import skilvit.fr.R;

public class HelpActivity extends AppCompatActivity {

    private TextView helpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        helpText = findViewById(R.id.help_text);

        String helpPage = savedInstanceState.getString("help");

        switch (helpPage)
        {
            case "activite_physique":
                helpText.setText(getResources().getText(R.string.activite_physique_aide));
                break;
            case "alimentation":
                helpText.setText(getResources().getText(R.string.nourriture_aide));
                break;
            case "situation":
                helpText.setText(getResources().getText(R.string.situation_aide));
                break;
            case "poids":
                helpText.setText(getResources().getText(R.string.poids_aide));
                break;
            case "sommeil":
                helpText.setText(getResources().getText(R.string.sommeil_aide));
                break;
            case "prise_medicament":
                helpText.setText(getResources().getText(R.string.prise_medicament_aide));
                break;
            case "glycemie":
                helpText.setText(getResources().getText(R.string.glycemie_aide));
                break;
        }

    }

}
