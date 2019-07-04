package skilvit.fr.activites;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import skilvit.fr.R;

public class OptionsActivity extends AppCompatActivity {

    CheckBox situation_checkbox;
    CheckBox pm_checkbox;
    CheckBox ap_checkbox;
    CheckBox alimentation_checkbox;
    CheckBox sommeil_checkbox;
    CheckBox poids_checkbox;
    CheckBox glycemie_checkbox;
    EditText adresse_email_entry;
    Button bouton_enregistrer;
    Button bouton_retour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        situation_checkbox = (CheckBox) findViewById(R.id.situation_checkbox);
        pm_checkbox = (CheckBox) findViewById(R.id.pm_checkbox);
        ap_checkbox = (CheckBox) findViewById(R.id.ap_checkbox);
        sommeil_checkbox = (CheckBox) findViewById(R.id.sommeil_checkbox);
        alimentation_checkbox = (CheckBox) findViewById(R.id.repas_checkbox);
        poids_checkbox = (CheckBox) findViewById(R.id.poids_checkbox);
        glycemie_checkbox = (CheckBox) findViewById(R.id.glycemie_checkbox);
        adresse_email_entry = (EditText) findViewById(R.id.adresse_email_praticien_entree);

        bouton_enregistrer = (Button) findViewById(R.id.bouton_enregistrer_options);
        bouton_retour = (Button) findViewById(R.id.boutonr_retour_options);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(OptionsActivity.this);
        situation_checkbox.setChecked(sp.getBoolean("fiche_situation", true));
        pm_checkbox.setChecked(sp.getBoolean("fiche_prise_medicament", true));
        ap_checkbox.setChecked(sp.getBoolean("fiche_activite_physique", true));
        alimentation_checkbox.setChecked(sp.getBoolean("fiche_alimentation", true));
        glycemie_checkbox.setChecked(sp.getBoolean("fiche_glycemie", true));
        poids_checkbox.setChecked(sp.getBoolean("fiche_poids", true));
        sommeil_checkbox.setChecked(sp.getBoolean("fiche_sommeil", true));
        adresse_email_entry.setText(sp.getString("adresse_email_praticien", ""));
//        SharedPreferences.Editor editor = sp.edit();


        bouton_enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(OptionsActivity.this);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("fiche_situation", situation_checkbox.isChecked());
                editor.putBoolean("fiche_prise_medicament", pm_checkbox.isChecked());
                editor.putBoolean("fiche_activite_physique", ap_checkbox.isChecked());
                editor.putBoolean("fiche_alimentation", alimentation_checkbox.isChecked());
                editor.putBoolean("fiche_glycemie", glycemie_checkbox.isChecked());
                editor.putBoolean("fiche_poids", poids_checkbox.isChecked());
                editor.putBoolean("fiche_sommeil", sommeil_checkbox.isChecked());
                editor.putString("adresse_email_praticien", adresse_email_entry.getText().toString());
                editor.apply();
                onBackPressed();
            }
        });
        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(OptionsActivity.this, MainActivity.class);
//                startActivity(i);
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

    }
}
