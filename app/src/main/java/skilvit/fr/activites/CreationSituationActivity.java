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
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import skilvit.fr.BuildConfig;
import skilvit.fr.R;
import skilvit.fr.data_manager.DBManager;
import skilvit.fr.data_manager.Date;
import skilvit.fr.data_manager.Hour;
import skilvit.fr.data_manager.ObservationDate;
import skilvit.fr.data_manager.Situation;

public class CreationSituationActivity extends AppCompatActivity {
    private static final String TAG = CreationSituationActivity.class.getSimpleName();

    EditText date_entree;
    EditText heure_entree;
    EditText entree_intensite;
    EditText entree_situation;
    EditText entree_emotions_sensations;
    EditText entree_pensees;
    EditText entree_taux_croyance;
    EditText entree_pensee_alternative;
    EditText entree_taux_croyance_alternative;
    EditText entree_comportement;
    Button bouton_enregistrement;
    Button bouton_afficher_liste;
    Button bouton_creation_entree_vers_accueil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_situation);
        date_entree = (EditText) findViewById(R.id.date_entree);
        heure_entree = (EditText) findViewById(R.id.heure_entree);
        entree_intensite = (EditText) findViewById(R.id.intensite_text);
        entree_situation  = (EditText) findViewById(R.id.entree_situation);
        entree_emotions_sensations = (EditText) findViewById(R.id.entree_emotions_sensations);
        entree_pensees = (EditText) findViewById(R.id.entree_pensees);
        entree_comportement = (EditText) findViewById(R.id.entree_comportement);

//        entree_taux_croyance = (EditText) findViewById(R.id.entree_taux_croyance);
//        entree_pensee_alternative = (EditText) findViewById(R.id.entree_pensee_alternative);
//        entree_taux_croyance_alternative = (EditText) findViewById(R.id.entree_taux_croyance_alternative);

        Calendar rightNow = Calendar.getInstance();
        final int jour = rightNow.get(Calendar.DAY_OF_MONTH);
        final int mois= rightNow.get(Calendar.MONTH)+1;
        final int annee= rightNow.get(Calendar.YEAR);
        Log.d(TAG, rightNow.getTime().toString());

        final int heure = rightNow.get(Calendar.HOUR_OF_DAY);
        final int minute = rightNow.get(Calendar.MINUTE);
        date_entree.setText(jour + "/" + mois + "/" + annee);
        heure_entree.setText(heure + ":" + minute);

        date_entree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreationSituationActivity.this,
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreationSituationActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                        final AlertDialog.Builder builder = new AlertDialog.Builder(CreationSituationActivity.this);
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
                if(!hasFocus)
                {
                    if(!Hour.isValidHour(heure_entree.getText().toString()))
                    {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(CreationSituationActivity.this);
                        String message = getString(R.string.heure_incorrecte);
                        builder.setTitle(getString(R.string.heure_incorrecte));
                        builder.setMessage(message);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });


        bouton_enregistrement=(Button)findViewById(R.id.bouton_enregistrement);

        bouton_enregistrement.setOnClickListener(new View.OnClickListener()
        {
             @Override
             public void onClick(View v)
             {
                 if (Date.isValidDate(date_entree.getText().toString()) &&
                         Hour.isValidHour(heure_entree.getText().toString()))
                 {
                     //on vérifie que les données sont correctement remplies
                     //On capte la position du portable si la donnée entrée est maintenant
                     //on enregistre toutes les données dans la base de données du portable
                     ObservationDate obd = new ObservationDate();
                     Situation situation = new Situation(date_entree.getText().toString(),
                             heure_entree.getText().toString(), entree_intensite.getText().toString(),
                             entree_situation.getText().toString(),
                             entree_emotions_sensations.getText().toString(),
                             entree_pensees.getText().toString(),
                             entree_comportement.getText().toString());
//                     situation.setCroyances(entree_taux_croyance.getText().toString(),
//                             entree_pensee_alternative.getText().toString(),
//                             entree_taux_croyance_alternative.getText().toString());
                     DBManager db = new DBManager(CreationSituationActivity.this);
                     db.insert(situation);
                     if (BuildConfig.DEBUG)
                         Log.d(TAG, "Les données viennent d'être entrées.");
                     Intent i = new Intent(CreationSituationActivity.this, ConsultationListeActivity.class);
                     startActivity(i);
                 }
                 else
                 {
                     final AlertDialog.Builder builder = new AlertDialog.Builder(CreationSituationActivity.this);
                     String message = getString(R.string.demander_bonnes_dates_heures);
                     builder.setTitle(getString(R.string.demander_bonnes_valeurs));
                     builder.setMessage(message);
                     final AlertDialog dialog = builder.create();
                     dialog.show();
                 }
             }
         });

        bouton_afficher_liste = findViewById(R.id.bouton_liste);
        bouton_afficher_liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreationSituationActivity.this, ConsultationListeActivity.class);
                i.putExtra("entree", "situation");
                startActivity(i);
            }
        });


        bouton_creation_entree_vers_accueil=(Button)

        findViewById(R.id.bouton_creation_situation_vers_accueil);
        bouton_creation_entree_vers_accueil.setOnClickListener(new View.OnClickListener()
        {
           @Override
           public void onClick(View v) {
//                   Intent i = new Intent(CreationSituationActivity.this, MainActivity.class);
//                   startActivity(i);
               onBackPressed();
           }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_creation_situation, menu);
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
