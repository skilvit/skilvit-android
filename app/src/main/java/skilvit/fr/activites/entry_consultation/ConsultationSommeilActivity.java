package skilvit.fr.activites.entry_consultation;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import skilvit.fr.R;
import skilvit.fr.activites.ConsultationListeActivity;
import skilvit.fr.data_manager.DBManager;
import skilvit.fr.data_manager.Date;
import skilvit.fr.data_manager.Hour;
import skilvit.fr.data_manager.ObservationDate;
import skilvit.fr.data_manager.Sommeil;

public class ConsultationSommeilActivity extends AppCompatActivity {
    private static final  String TAG = ConsultationSommeilActivity.class.getSimpleName();

    EditText texte_date;
    EditText texte_heure;

    EditText entree_date_evenement;
    EditText entree_heure_evenement;

    RadioGroup evenement_sommeil;
    EditText entree_commentaire;

    Button bouton_activer_desactiver_modification;
    Button bouton_enregistrer_modification;
    Button bouton_suprimmer_entree;
    Button bouton_entree_vers_liste;

    boolean entrees_editables;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_sommeil);

        final int entree_id = getIntent().getIntExtra("entree_id", 0);

        texte_date = (EditText) findViewById(R.id.date_entree);
        texte_date.setTag(texte_date.getKeyListener());
        texte_date.setKeyListener(null);

        texte_heure = (EditText) findViewById(R.id.heure_entree);
        texte_heure.setTag(texte_heure.getKeyListener());
        texte_heure.setKeyListener(null);

        final DBManager db_manager = new DBManager(ConsultationSommeilActivity.this);

        Sommeil e = db_manager.retrieve_one_sommeil(entree_id);

        if (e != null) {
            final ObservationDate obd = e.getObd();


            texte_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ConsultationSommeilActivity.this,
                            (view, year, month, dayOfMonth) -> texte_date.setText(String.format(Locale.FRANCE, "%d/%d/%d", dayOfMonth, month + 1, year)), obd.annee, obd.mois - 1, obd.jour);
                    datePickerDialog.show();
                }
            });


            texte_heure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(ConsultationSommeilActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ConsultationSommeilActivity.this);
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
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ConsultationSommeilActivity.this);
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
                if (entrees_editables) {
                    bouton_activer_desactiver_modification.setText(getResources().getString(R.string.activer_modification_situation));
                    entrees_editables = false;


                    texte_date.setTag(texte_date.getKeyListener());
                    texte_date.setKeyListener(null);
                    texte_heure.setTag(texte_heure.getKeyListener());
                    texte_heure.setKeyListener(null);
                    entree_date_evenement.setTag(entree_date_evenement.getKeyListener());
                    entree_date_evenement.setKeyListener(null);
                    entree_heure_evenement.setTag(entree_heure_evenement.getKeyListener());
                    entree_heure_evenement.setKeyListener(null);

                    evenement_sommeil.setClickable(true);

                    entree_commentaire.setTag(entree_commentaire.getKeyListener());
                    entree_commentaire.setKeyListener(null);

                } else {
                    bouton_activer_desactiver_modification.setText(getResources().getString(R.string.desactiver_modification_situation));
                    entrees_editables = true;
                    texte_date.setKeyListener((KeyListener) texte_date.getTag());
                    texte_heure.setKeyListener((KeyListener) texte_heure.getTag());
                    entree_date_evenement.setKeyListener((KeyListener) entree_date_evenement.getTag());
                    entree_heure_evenement.setKeyListener((KeyListener) entree_heure_evenement.getTag());
                    entree_commentaire.setKeyListener((KeyListener) entree_commentaire.getTag());
                    evenement_sommeil.setClickable(false);
                }
                Log.d(TAG, String.valueOf(entrees_editables));
            });

            bouton_enregistrer_modification = (Button) findViewById(R.id.bouton_enregistrer_modification);
            bouton_suprimmer_entree = (Button) findViewById(R.id.bouton_suprimmer_entree);


            bouton_enregistrer_modification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Date.isValidDate(texte_date.getText().toString()) &&
                            Hour.isValidHour(texte_heure.getText().toString())) {
                        Sommeil nouveau_sommeil;
                        RadioButton rb = (RadioButton) findViewById(evenement_sommeil.getCheckedRadioButtonId());

                        nouveau_sommeil = new Sommeil(texte_date.getText().toString(),
                                texte_heure.getText().toString(),
                                rb.getText().toString(),
                                entree_date_evenement.getText().toString(),
                                entree_heure_evenement.getText().toString(),
                                entree_commentaire.getText().toString());
                        db_manager.update_sommeil(entree_id, nouveau_sommeil);
                        Intent i = new Intent(ConsultationSommeilActivity.this, ConsultationListeActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ConsultationSommeilActivity.this);
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

                    final AlertDialog.Builder builder = new AlertDialog.Builder(ConsultationSommeilActivity.this);
                    String message = getString(R.string.suppression_fiche_demande_confirmation);
                    builder.setTitle(getString(R.string.suppression_interrogation));
                    builder.setMessage(message);
                    builder.setPositiveButton(getString(R.string.suppression_fiche_confirmation), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db_manager.delete_sommeil(entree_id);
                            Intent i = new Intent(ConsultationSommeilActivity.this, ConsultationListeActivity.class);
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
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

    }
}
