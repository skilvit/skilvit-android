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
import skilvit.fr.R;
import skilvit.fr.data_manager.DBManager;
import skilvit.fr.data_manager.Date;
import skilvit.fr.data_manager.Hour;
import skilvit.fr.data_manager.Poids;

public class PoidsActivity extends AppCompatActivity {
    private static final String TAG = PoidsActivity.class.getSimpleName();

    DBManager db;
    EditText date_entree;
    EditText heure_entree;
    EditText entree_poids;

    Button bouton_enregistrer;
    Button bouton_afficher_liste;
    Button bouton_retour;

    TextView texte_derniere_p;

    Poids poids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poids);

        date_entree = (EditText) findViewById(R.id.date_entree);
        heure_entree = (EditText) findViewById(R.id.heure_entree);
        entree_poids = (EditText) findViewById(R.id.entree_poids);
        bouton_enregistrer = (Button) findViewById(R.id.bouton_enregistrer);
        bouton_afficher_liste = findViewById(R.id.bouton_liste);
        bouton_retour = (Button) findViewById(R.id.bouton_retour);
        texte_derniere_p = (TextView) findViewById(R.id.texte_dernier_poids);

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

        db = new DBManager(PoidsActivity.this);

        ArrayList<Poids> dernier_p =  db.retrieve_last_weight(5);
        int taille_p = dernier_p.size();
        String texte_p = "";
        if(taille_p> 0)
        {
            for(Poids i_p : dernier_p)
            {

                texte_p = texte_p.concat("\n"+i_p.toString());
//                        = texte_pm.append(;
            }
            texte_derniere_p.setText(texte_p);
        }


        // C'est pour ajouter une nouvelle prise de médicament
        bouton_enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Date.isValidDate(date_entree.getText().toString()) &&
                        Hour.isValidHour(heure_entree.getText().toString()))
                {
                    poids = new Poids(date_entree.getText().toString(),
                            heure_entree.getText().toString(),
                            entree_poids.getText().toString());
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, poids.afficher_previsualisation());
                    db.insert(poids);
                    Intent i = new Intent(PoidsActivity.this, MainActivity.class);
                    startActivity(i);

                }
                else
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(PoidsActivity.this);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(PoidsActivity.this,
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(PoidsActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                        final AlertDialog.Builder builder = new AlertDialog.Builder(PoidsActivity.this);
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
                        final AlertDialog.Builder builder = new AlertDialog.Builder(PoidsActivity.this);
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
                Intent i = new Intent(PoidsActivity.this, ConsultationListeActivity.class);
                i.putExtra("entree", "poids");
                startActivity(i);
            }
        });

        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(PoidsActivity.this, MainActivity.class);
//                startActivity(i);
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_poids, menu);
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
            Intent i = new Intent(PoidsActivity.this, MainActivity.class);
            i.putExtra("help", "poids");
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
