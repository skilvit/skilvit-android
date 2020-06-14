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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import skilvit.fr.BuildConfig;
import skilvit.fr.R;
import skilvit.fr.data_manager.DBManager;
import skilvit.fr.data_manager.Date;
import skilvit.fr.data_manager.Hour;
import skilvit.fr.data_manager.Sommeil;

public class SommeilActivity extends AppCompatActivity {

    private static final String TAG = SommeilActivity.class.getSimpleName();

    DBManager db;
    EditText date_entree;
    EditText heure_entree;

    EditText entree_date_evenement;
    EditText entree_heure_evenement;

    RadioGroup evenement_sommeil;
    EditText entree_commentaire;

    Button bouton_enregistrer;
    Button bouton_afficher_liste;
    Button bouton_retour;

    TextView texte_dernier_sommeil;

    Sommeil sommeil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sommeil);

        date_entree = (EditText) findViewById(R.id.date_entree);
        heure_entree = (EditText) findViewById(R.id.heure_entree);

        entree_date_evenement = (EditText) findViewById(R.id.entree_date_evenement);
        entree_heure_evenement = (EditText) findViewById(R.id.entree_heure_evenement);


//        entree_date_coucher = (EditText) findViewById(R.id.entree_s_date_coucher);
//        entree_heure_coucher = (EditText) findViewById(R.id.entree_s_heure_coucher);
//        entree_date_lever = (EditText) findViewById(R.id.entree_s_date_lever);
//        entree_heure_lever = (EditText) findViewById(R.id.entree_s_heure_lever);
        evenement_sommeil = findViewById(R.id.evenementRadioButton);

        entree_commentaire = (EditText) findViewById(R.id.entree_s_commentaire);
        texte_dernier_sommeil = (TextView) findViewById(R.id.texte_dernier_sommeil);


        bouton_enregistrer = (Button) findViewById(R.id.bouton_enregistrer_sommeil);
        bouton_afficher_liste = findViewById(R.id.bouton_liste);
        bouton_retour = (Button) findViewById(R.id.bouton_retour_sommeil);

        Calendar rightNow = Calendar.getInstance();
        final int jour = rightNow.get(Calendar.DAY_OF_MONTH);
        final int mois= rightNow.get(Calendar.MONTH)+1;
        final int annee= rightNow.get(Calendar.YEAR);
        if(BuildConfig.DEBUG)
            Log.d(TAG, rightNow.getTime().toString());

        final int heure = rightNow.get(Calendar.HOUR_OF_DAY);
        final int minute = rightNow.get(Calendar.MINUTE);
        date_entree.setText(new StringBuilder().append(jour).append("/").append(mois).append("/").append(annee).toString());
        heure_entree.setText(new StringBuilder().append(heure).append(":").append(minute).toString());

        db = new DBManager(SommeilActivity.this);
//        db.onUpgrade(db.getWritableDatabase(), 3, 4);

        ArrayList<Sommeil> dernier_sommeil =  db.retrieve_last_sommeil(5);
        int taille_sommeil = dernier_sommeil.size();
        String texte_sommeil = "";
        if(taille_sommeil> 0)
        {
            for(Sommeil i_sommeil : dernier_sommeil)
            {

                texte_sommeil = texte_sommeil.concat("\n"+i_sommeil.toString());
            }
            texte_dernier_sommeil.setText(texte_sommeil);
        }
        // C'est pour ajouter une nouvelle prise de médicament
        bouton_enregistrer.setOnClickListener(new View.OnClickListener() {
            String selected_evement_sommeil = "";

            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton) findViewById(evenement_sommeil.getCheckedRadioButtonId());
                switch(rb.getId())
                {
                    case R.id.radioButtonCoucher:
                        selected_evement_sommeil = getResources().getString(R.string.texte_sommeil_lever);
                        break;
                    case R.id.radioButtonLever:
                        selected_evement_sommeil = getResources().getString(R.string.texte_sommeil_coucher);
                        break;
                    case R.id.radioButtonReveilDansNuit:
                        selected_evement_sommeil = getResources().getString(R.string.texte_sommeil_reveil_dans_nuit);
                        break;
                }

                if (Date.isValidDate(date_entree.getText().toString()) &&
                    Hour.isValidHour(heure_entree.getText().toString()) &&

                    Date.isValidDate(entree_date_evenement.getText().toString()) &&
                    Hour.isValidHour(entree_heure_evenement.getText().toString()))
                {

                    sommeil = new Sommeil(date_entree.getText().toString(),
                            heure_entree.getText().toString(),
                            selected_evement_sommeil,
                            entree_date_evenement.getText().toString(),
                            entree_heure_evenement.getText().toString(),
                            entree_commentaire.getText().toString());
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, sommeil.afficher_previsualisation());
                    db.insert(sommeil);
                    Intent i = new Intent(SommeilActivity.this, MainActivity.class);
                    startActivity(i);


                }
                else
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SommeilActivity.this);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(SommeilActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date_entree.setText(String.format(Locale.FRANCE, "%d/%d/%d", dayOfMonth, month+1, year));
                            }
                        }, annee, mois-1, jour);
                datePickerDialog.show();
            }
        });
        date_entree.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Date.isValidDate(date_entree.getText().toString())) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SommeilActivity.this);
                        String message = "Format de la date incorrect";
                        builder.setTitle(getString(R.string.date_incorrecte));
                        builder.setMessage(message);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });



        heure_entree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(SommeilActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        heure_entree.setText(String.format(Locale.FRANCE, "%d:%d", hourOfDay, minute));

                    }
                }, heure, minute, true);
                timePickerDialog.show();
            }
        });
        heure_entree.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Hour.isValidHour(heure_entree.getText().toString())) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SommeilActivity.this);
                        String message = getString(R.string.heure_incorrecte);
                        builder.setTitle(getString(R.string.heure_incorrecte));
                        builder.setMessage(message);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });

        entree_date_evenement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SommeilActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                entree_date_evenement.setText(String.format(Locale.FRANCE, "%d/%d/%d", dayOfMonth, month+1, year));
                            }
                        }, annee, mois-1, jour);
                datePickerDialog.show();
            }
        });
        entree_date_evenement.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Date.isValidDate(entree_date_evenement.getText().toString())) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SommeilActivity.this);
                        String message = "Format de la date incorrect";
                        builder.setTitle(getString(R.string.date_incorrecte));
                        builder.setMessage(message);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });

        entree_heure_evenement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(SommeilActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        entree_heure_evenement.setText(String.format(Locale.FRANCE, "%d:%d", hourOfDay, minute));

                    }
                }, heure, minute, true);
                timePickerDialog.show();
            }
        });
        entree_heure_evenement.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Hour.isValidHour(entree_heure_evenement.getText().toString())) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SommeilActivity.this);
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
                Intent i = new Intent(SommeilActivity.this, ConsultationListeActivity.class);
                i.putExtra("entree", "sommeil");
                startActivity(i);
            }
        });



        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(SommeilActivity.this, MainActivity.class);
//                startActivity(i);
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sommeil, menu);
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
            Intent i = new Intent(SommeilActivity.this, MainActivity.class);
            i.putExtra("help", "sommeil");
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
