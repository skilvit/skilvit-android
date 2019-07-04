package skilvit.fr.activites;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import skilvit.fr.BuildConfig;
import skilvit.fr.R;
import skilvit.fr.data_manager.DBManager;
import skilvit.fr.data_manager.Date;
import skilvit.fr.data_manager.Hour;
import skilvit.fr.data_manager.ObservationDate;
import skilvit.fr.data_manager.Situation;

public class ConsultationEntreeActivity extends AppCompatActivity {
    private static final  String TAG = ConsultationEntreeActivity.class.getSimpleName();

    EditText texte_date;
    EditText texte_heure;

    Button bouton_activer_desactiver_modification;
    Button bouton_enregistrer_modification;
    Button bouton_suprimmer_entree;
    Button bouton_entree_vers_liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final int entree_id = getIntent().getIntExtra("entree_id", 0);

        if(BuildConfig.DEBUG)
            Log.d(TAG, "entree_id " + entree_id);

        final DBManager db_manager = new DBManager(ConsultationEntreeActivity.this);

        EditText texte_intensite;
        EditText texte_situation;
        EditText texte_emotions_sensations;
        EditText texte_pensees;
        EditText texte_comportement;


        final boolean[] entrees_editables = new boolean[1];

        setContentView(R.layout.activity_consultation_entree);

        entrees_editables[0] = false;
        texte_date = (EditText) findViewById(R.id.texte_date);
        texte_date.setTag(texte_date.getKeyListener());
        texte_date.setKeyListener(null);

        texte_heure = (EditText) findViewById(R.id.texte_heure);
        texte_heure.setTag(texte_heure.getKeyListener());
        texte_heure.setKeyListener(null);

        texte_intensite = (EditText) findViewById(R.id.texte_intensite);
        texte_intensite.setTag(texte_intensite.getKeyListener());
        texte_intensite.setKeyListener(null);

        texte_situation = (EditText) findViewById(R.id.texte_situation);
        texte_situation.setTag(texte_situation.getKeyListener());
        texte_situation.setKeyListener(null);

        texte_emotions_sensations = (EditText) findViewById(R.id.texte_emotions_sensations);
        texte_emotions_sensations.setTag(texte_emotions_sensations.getKeyListener());
        texte_emotions_sensations.setKeyListener(null);

        texte_pensees = (EditText) findViewById(R.id.texte_pensees);
        texte_pensees.setTag(texte_pensees.getKeyListener());
        texte_pensees.setKeyListener(null);

        texte_comportement = (EditText) findViewById(R.id.texte_comportement);
        texte_comportement.setTag(texte_comportement.getKeyListener());
        texte_comportement.setKeyListener(null);

        Situation e = db_manager.retrieve_one_situation(entree_id);
        if (e != null) {
            if(BuildConfig.DEBUG)
                Log.d(TAG, e.afficher_previsualisation());
            final ObservationDate obd = e.getObd();
            texte_date.setText(obd.getDate());
            texte_heure.setText(obd.getHeure());
            texte_intensite.setText(e.getIntensite());
            texte_situation.setText(e.getSituation());
            texte_emotions_sensations.setText(e.getEmotions_sensation());
            texte_pensees.setText(e.getPensees());
            texte_comportement.setText(e.getComportement());


            texte_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ConsultationEntreeActivity.this,
                            (view, year, month, dayOfMonth) -> texte_date.setText(String.format(Locale.FRANCE, "%d/%d/%d", dayOfMonth, month+1, year)), obd.annee, obd.mois-1, obd.jour);
                    datePickerDialog.show();
                }
            });



            texte_heure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(ConsultationEntreeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            texte_heure.setText(String.format(Locale.FRANCE, "%d:%d", hourOfDay, minute));

                        }
                    }, obd.heure, obd.minute, true);
                    timePickerDialog.show();
                }
            });

            texte_date.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    if (!Date.isValidDate(texte_date.getText().toString())) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ConsultationEntreeActivity.this);
                        String message = getString(R.string.a_corriger);
                        builder.setTitle(getString(R.string.date_incorrecte));
                        builder.setMessage(message);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
            texte_heure.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    if (!Hour.isValidHour(texte_heure.getText().toString())) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ConsultationEntreeActivity.this);
                        String message = getString(R.string.heure_incorrecte);
                        builder.setTitle(getString(R.string.heure_incorrecte));
                        builder.setMessage(message);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });


            bouton_activer_desactiver_modification = (Button) findViewById(R.id.bouton_activation_modification);
            bouton_activer_desactiver_modification.setOnClickListener(v -> {
                if (entrees_editables[0]) {
                    bouton_activer_desactiver_modification.setText(getResources().getString(R.string.activer_modification_situation));
                    entrees_editables[0] = false;


                    texte_date.setTag(texte_date.getKeyListener());
                    texte_date.setKeyListener(null);
                    texte_heure.setTag(texte_heure.getKeyListener());
                    texte_heure.setKeyListener(null);
                    texte_intensite.setTag(texte_intensite.getKeyListener());
                    texte_intensite.setKeyListener(null);
                    texte_situation.setTag(texte_situation.getKeyListener());
                    texte_situation.setKeyListener(null);

                    texte_emotions_sensations.setTag(texte_emotions_sensations.getKeyListener());
                    texte_emotions_sensations.setKeyListener(null);
                    texte_pensees.setTag(texte_pensees.getKeyListener());
                    texte_pensees.setKeyListener(null);
                    texte_comportement.setTag(texte_comportement.getKeyListener());
                    texte_comportement.setKeyListener(null);

                } else {
                    bouton_activer_desactiver_modification.setText(getResources().getString(R.string.desactiver_modification_situation));
                    entrees_editables[0] = true;
                    texte_date.setKeyListener((KeyListener) texte_date.getTag());
                    texte_heure.setKeyListener((KeyListener) texte_heure.getTag());
                    texte_intensite.setKeyListener((KeyListener) texte_intensite.getTag());
                    texte_situation.setKeyListener((KeyListener) texte_situation.getTag());
                    texte_emotions_sensations.setKeyListener((KeyListener) texte_emotions_sensations.getTag());
                    texte_pensees.setKeyListener((KeyListener) texte_pensees.getTag());
                    texte_comportement.setKeyListener((KeyListener) texte_comportement.getTag());

                }
                Log.d(TAG, String.valueOf(entrees_editables[0]));
            });

            bouton_enregistrer_modification = (Button) findViewById(R.id.bouton_enregistrer_modification);
            bouton_suprimmer_entree = (Button) findViewById(R.id.bouton_suprimmer_entree);


            bouton_enregistrer_modification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Date.isValidDate(texte_date.getText().toString()) &&
                            Hour.isValidHour(texte_heure.getText().toString())) {
                        Situation nouvelle_situation;
                        nouvelle_situation = new Situation(texte_date.getText().toString(),
                                texte_heure.getText().toString(),
                                texte_intensite.getText().toString(),
                                texte_situation.getText().toString(),
                                texte_emotions_sensations.getText().toString(),
                                texte_pensees.getText().toString(),
                                texte_comportement.getText().toString());
                        db_manager.update_situation(entree_id, nouvelle_situation);
                        Intent i = new Intent(ConsultationEntreeActivity.this, ConsultationListeActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ConsultationEntreeActivity.this);
                        String message = "Mettez des valeurs correctes";
                        builder.setTitle("Mettez des valeurs correctes");
                        builder.setMessage(message);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
            bouton_suprimmer_entree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(ConsultationEntreeActivity.this);
                    String message = getString(R.string.suppression_fiche_demande_confirmation);
                    builder.setTitle(getString(R.string.suppression_interrogation));
                    builder.setMessage(message);
                    builder.setPositiveButton(getString(R.string.suppression_fiche_confirmation), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db_manager.delete_situation(entree_id);
                            Intent i = new Intent(ConsultationEntreeActivity.this, ConsultationListeActivity.class);
                            startActivity(i);
                        }
                    });
                    builder.setNegativeButton(getString(R.string.suppression_fiche_annulation), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

        bouton_entree_vers_liste = (Button) findViewById(R.id.bouton_entree_vers_liste);
        bouton_entree_vers_liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(ConsultationEntreeActivity.this, ConsultationListeActivity.class);
//                startActivity(i);
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

    }
}
