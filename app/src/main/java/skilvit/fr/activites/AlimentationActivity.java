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
import skilvit.fr.data_manager.DBManager;
import skilvit.fr.data_manager.Date;
import skilvit.fr.data_manager.Hour;
import skilvit.fr.R;
import skilvit.fr.data_manager.Alimentation;

public class AlimentationActivity extends AppCompatActivity {
    private static final String TAG = Alimentation.class.getSimpleName();

    DBManager db;
    EditText date_entree;
    EditText heure_entree;

    RadioGroup rg;
    EditText entree_nourriture;

    Button bouton_enregistrer;
    Button bouton_afficher_liste;
    Button bouton_retour;
    TextView texte_dernière_al;
    Alimentation alimentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nourriture);

        date_entree = (EditText) findViewById(R.id.date_entree);
        heure_entree = (EditText) findViewById(R.id.heure_entree);

        entree_nourriture = (EditText) findViewById(R.id.entree_nourriture);
        rg = (RadioGroup) findViewById(R.id.rg_nourriture);

        bouton_enregistrer = (Button) findViewById(R.id.bouton_enregistrer);
        bouton_afficher_liste = findViewById(R.id.bouton_liste);
        bouton_retour = (Button) findViewById(R.id.bouton_retour);
        texte_dernière_al = (TextView) findViewById(R.id.texte_derniere_al);


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


        date_entree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AlimentationActivity.this,
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(AlimentationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        heure_entree.setText(String.format(Locale.FRANCE, "%d:%d", hourOfDay, minute));

                    }
                }, heure, minute, true);
                timePickerDialog.show();
            }
        });

        db = new DBManager(AlimentationActivity.this);



        ArrayList<Alimentation> dernier_al =  db.retrieve_last_al(5);
        int taille_al = dernier_al.size();
        String texte_al = "";
        if(taille_al > 0)
        {
            for(Alimentation i_ap : dernier_al)
            {

                texte_al = texte_al.concat("\n"+i_ap.toString());
//                        = texte_pm.append(;
            }
            texte_dernière_al.setText(texte_al);
        }



        // C'est pour ajouter une nouvelle prise de médicament
        bouton_enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Date.isValidDate(date_entree.getText().toString()) &&
                        Hour.isValidHour(heure_entree.getText().toString()))
                {
                    RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

                    alimentation = new Alimentation(date_entree.getText().toString(),
                            heure_entree.getText().toString(), rb.getText().toString(),
                            entree_nourriture.getText().toString());
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, alimentation.afficher_previsualisation());
                    db.insert(alimentation);
                    Intent i = new Intent(AlimentationActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(AlimentationActivity.this);
                    String message = getString(R.string.demander_bonnes_dates_heures);
                    builder.setTitle(getString(R.string.demander_bonnes_valeurs));
                    builder.setMessage(message);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        date_entree.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    if(!Date.isValidDate(date_entree.getText().toString()))
                    {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(AlimentationActivity.this);
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
                        final AlertDialog.Builder builder = new AlertDialog.Builder(AlimentationActivity.this);
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
                Intent i = new Intent(AlimentationActivity.this, ConsultationListeActivity.class);
                i.putExtra("entree", "alimentation");
                startActivity(i);
            }
        });

        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(AlimentationActivity.this, MainActivity.class);
//                startActivity(i);
                onBackPressed();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nourriture, menu);
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
            Intent i = new Intent(AlimentationActivity.this, MainActivity.class);
            i.putExtra("help", "alimentation");
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
