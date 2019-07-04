package skilvit.fr.activites;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import skilvit.fr.BuildConfig;
import skilvit.fr.data_manager.DBManager;
import skilvit.fr.data_manager.Date;
import skilvit.fr.data_manager.Hour;
import skilvit.fr.R;
import skilvit.fr.data_manager.PriseMedicament;

public class PriseMedicamentActivity extends AppCompatActivity {
    private static final String TAG = PriseMedicamentActivity.class.getSimpleName();

    DBManager db;
    EditText date_entree;
    EditText heure_entree;
    EditText entree_medicament_pris;
    EditText entree_dosage_pris;
    Button bouton_enregistrer;
    Button bouton_afficher_liste;
    Button bouton_retour;
    TextView texte_dernière_prise_medicament;
    PriseMedicament pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prise_medicament);

        date_entree = (EditText) findViewById(R.id.date_entree);
        heure_entree = (EditText) findViewById(R.id.heure_entree);
        entree_medicament_pris = (EditText) findViewById(R.id.entree_glycemie);
        entree_dosage_pris = (EditText) findViewById(R.id.entree_nourriture);
        bouton_enregistrer = (Button) findViewById(R.id.bouton_enregistrer);
        bouton_afficher_liste = findViewById(R.id.bouton_liste);
        bouton_retour = (Button) findViewById(R.id.bouton_retour);
        texte_dernière_prise_medicament =
                (TextView) findViewById(R.id.texte_dernière_prise_medicament);

        Calendar rightNow = Calendar.getInstance();
        final int jour = rightNow.get(Calendar.DAY_OF_MONTH);
        final int mois= rightNow.get(Calendar.MONTH)+1;
        final int annee= rightNow.get(Calendar.YEAR);
        if(BuildConfig.DEBUG)
            Log.d(TAG, rightNow.getTime().toString());

        final int heure = rightNow.get(Calendar.HOUR_OF_DAY);
        final int minute = rightNow.get(Calendar.MINUTE);
        date_entree.setText(jour+"/"+mois+"/"+annee);
        heure_entree.setText(heure+":"+minute);


        // On retourne la dernière prise de médicament, pour suivre un peu
        db = new DBManager(PriseMedicamentActivity.this);
        ArrayList<PriseMedicament> dernier_pm =  db.retrieve_last_medicament(5);
        int taille_dpm = dernier_pm.size();
        String texte_pm = "";
        if(taille_dpm > 0)
        {
            for(PriseMedicament i_pm : dernier_pm)
            {

                texte_pm = texte_pm.concat("\n"+i_pm.toString());
//                        = texte_pm.append(;
            }
            texte_dernière_prise_medicament.setText(texte_pm);
        }

        // C'est pour ajouter une nouvelle prise de médicament
        bouton_enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Date.isValidDate(date_entree.getText().toString()) &&
                        Hour.isValidHour(heure_entree.getText().toString()))
                {
                    pm = new PriseMedicament(date_entree.getText().toString(),
                            heure_entree.getText().toString(),
                            entree_medicament_pris.getText().toString(),
                            entree_dosage_pris.getText().toString());
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, pm.afficher_previsualisation());
                    db.insert(pm);
                    Intent i = new Intent(PriseMedicamentActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(PriseMedicamentActivity.this);
                    String message = getString(R.string.demander_bonnes_dates_heures);
                    builder.setTitle(getString(R.string.demander_bonnes_valeurs));
                    builder.setMessage(message);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        date_entree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PriseMedicamentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date_entree.setText(String.format(Locale.FRANCE, "%d/%d/%d", dayOfMonth, month+1, year));
                            }
                        }, annee, mois-1, jour);
                datePickerDialog.show();
            }
        });



        heure_entree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(PriseMedicamentActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        heure_entree.setText(String.format(Locale.FRANCE, "%d:%d", hourOfDay, minute));

                    }
                }, heure, minute, true);
                timePickerDialog.show();
            }
        });

        date_entree.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    if(!Date.isValidDate(date_entree.getText().toString()))
                    {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(PriseMedicamentActivity.this);
                        String message = "Format de la date incorrect";
                        builder.setTitle(getString(R.string.date_incorrecte));
                        builder.setMessage(message);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });
        heure_entree.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Hour.isValidHour(heure_entree.getText().toString())) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(PriseMedicamentActivity.this);
                        String message = getString(R.string.heure_incorrecte);
                        builder.setTitle(getString(R.string.heure_incorrecte));
                        builder.setMessage(message);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }
            }
        });

        bouton_afficher_liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PriseMedicamentActivity.this, ConsultationListeActivity.class);
                i.putExtra("entree", "prise_medicament");
                startActivity(i);
            }
        });
        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(PriseMedicamentActivity.this, MainActivity.class);
//                startActivity(i);
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prise_medicament, menu);
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
            Intent i = new Intent(PriseMedicamentActivity.this, MainActivity.class);
            i.putExtra("help", "prise_medicament");
            startActivity(i);
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
