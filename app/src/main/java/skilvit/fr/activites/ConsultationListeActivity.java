package skilvit.fr.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;
import skilvit.fr.BuildConfig;
import skilvit.fr.R;
import skilvit.fr.activites.EntryConsultation.ConsultationActivitePhysiqueActivity;
import skilvit.fr.activites.EntryConsultation.ConsultationAlimentationActivity;
import skilvit.fr.activites.EntryConsultation.ConsultationGlycemieActivity;
import skilvit.fr.activites.EntryConsultation.ConsultationPoidsActivity;
import skilvit.fr.activites.EntryConsultation.ConsultationPriseMedicamentActivity;
import skilvit.fr.activites.EntryConsultation.ConsultationSommeilActivity;
import skilvit.fr.data_manager.ActivitePhysique;
import skilvit.fr.data_manager.Alimentation;
import skilvit.fr.data_manager.DBManager;
import skilvit.fr.data_manager.Glycemie;
import skilvit.fr.data_manager.MiniEntree;
import skilvit.fr.data_manager.MiniEntreeAdapter;
import skilvit.fr.data_manager.ObservationDate;
import skilvit.fr.data_manager.Poids;
import skilvit.fr.data_manager.PriseMedicament;
import skilvit.fr.data_manager.Situation;
import skilvit.fr.data_manager.Sommeil;

public class ConsultationListeActivity extends AppCompatActivity {
    private static final String TAG = DBManager.class.getSimpleName();

    ListView liste_entrees_vue;
    Button bouton_liste_vers_accueil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_liste);

        liste_entrees_vue = (ListView) findViewById(R.id.liste_entrees_vue);
        DBManager db = new DBManager(ConsultationListeActivity.this);

        Intent intent = getIntent();
        String type_liste = intent.getStringExtra("entree");
        // TODO créer un String spécifique pour chaque famille de Mesure
        ArrayList<MiniEntree> mini_entrees = new ArrayList<>();
        switch (type_liste)
        {
            case "situation":
                ArrayList<Situation> situations = db.retrieve_last_situations(Long.MAX_VALUE);
                if (!situations.isEmpty())
                {
                    Collections.sort(situations, (o1, o2) -> o1.getObd().compareTo(o2.getObd()));
//                    trier(situations);
                    for (Situation situation : situations) {
                        ObservationDate obd = situation.getObd();
                        mini_entrees.add(new MiniEntree(obd.getDate(), obd.getHeure(),
                                situation.getEmotions_sensation(), situation.getId()));
                        if(BuildConfig.DEBUG)
                            Log.d(TAG, situation.afficher_previsualisation());
                    }
                }

                break;

            case "prise_medicament":
                ArrayList<PriseMedicament> pms = db.retrieve_last_medicament(Long.MAX_VALUE);
                if (!pms.isEmpty())
                {
//                    pms = trier(pms);
                    Collections.sort(pms, (o1, o2) -> o1.getObd().compareTo(o2.getObd()));
                    for (PriseMedicament pm: pms) {
                        ObservationDate obd = pm.getObd();
                        mini_entrees.add(new MiniEntree(obd.getDate(), obd.getHeure(),
                                pm.getMedicament() + ":" + pm.getDosage(), pm.getId()));
                        if(BuildConfig.DEBUG)
                            Log.d(TAG, pm.afficher_previsualisation());
                    }
                }
                break;

            case "alimentation":
                ArrayList<Alimentation> ams = db.retrieve_last_al(Long.MAX_VALUE);
                if (!ams.isEmpty())
                {
//                    ams = trier(ams);
                    Collections.sort(ams, (o1, o2) -> o1.getObd().compareTo(o2.getObd()));
                    for (Alimentation am : ams) {
                        ObservationDate obd = am.getObd();
                        mini_entrees.add(new MiniEntree(obd.getDate(), obd.getHeure(),
                                am.getRepas(), am.getId()));
                        if(BuildConfig.DEBUG)
                            Log.d(TAG, am.afficher_previsualisation());
                    }
                }
                break;
            case "activite_physique":
                ArrayList<ActivitePhysique> aps = db.retrieve_last_ap(Long.MAX_VALUE);
                if (!aps.isEmpty())
                {
                    Collections.sort(aps, (o1, o2) -> o1.getObd().compareTo(o2.getObd()));
//                    aps = trier(aps);
                    for (ActivitePhysique ap : aps) {
                        ObservationDate obd = ap.getObd();
                        mini_entrees.add(new MiniEntree(obd.getDate(), obd.getHeure(),
                                ap.getSport(), ap.getId()));
                        if(BuildConfig.DEBUG)
                            Log.d(TAG, ap.afficher_previsualisation());
                    }
                }
                break;

            case "glycemie":
                ArrayList<Glycemie> gls = db.retrieve_last_glycemy(Long.MAX_VALUE);
                if (!gls.isEmpty())
                {
                    Collections.sort(gls, (o1, o2) -> o1.getObd().compareTo(o2.getObd()));
//                    gls = trier(gls);
                    for (Glycemie glycemie : gls) {
                        ObservationDate obd = glycemie.getObd();
                        mini_entrees.add(new MiniEntree(obd.getDate(), obd.getHeure(),
                                glycemie.getGlycemie(), glycemie.getId()));
                        if(BuildConfig.DEBUG)
                            Log.d(TAG, glycemie.afficher_previsualisation());
                    }
                }
                break;

            case "poids":
                ArrayList<Poids> poids = db.retrieve_last_weight(Long.MAX_VALUE);
                if (!poids.isEmpty())
                {
                    Collections.sort(poids, (o1, o2) -> o1.getObd().compareTo(o2.getObd()));
//                    poids = trier(poids);
                    for (Poids p: poids) {
                        ObservationDate obd = p.getObd();
                        mini_entrees.add(new MiniEntree(obd.getDate(), obd.getHeure(),
                                p.getPoids(), p.getId()));
                        if(BuildConfig.DEBUG)
                            Log.d(TAG, p.afficher_previsualisation());
                    }
                }
                break;

            case "sommeil":
                ArrayList<Sommeil> sommeils = db.retrieve_last_sommeil(Long.MAX_VALUE);
                if (!sommeils.isEmpty())
                {
                    Collections.sort(sommeils, (o1, o2) -> o1.getObd().compareTo(o2.getObd()));
//                    sommeils = trier(sommeils);
                    for (Sommeil sommeil: sommeils) {
                        ObservationDate obd = sommeil.getEvenement_obd();
                        mini_entrees.add(new MiniEntree(obd.getDate(), obd.getHeure(),
                                sommeil.getEvenement(), sommeil.getId()));
                        if(BuildConfig.DEBUG)
                            Log.d(TAG, sommeil.afficher_previsualisation());
                    }
                }
                break;
        }

//        ArrayList<ActivitePhysique> aps = db.retrieve_last_ap(Long.MAX_VALUE);
        final ArrayAdapter<MiniEntree> adapter = new MiniEntreeAdapter(ConsultationListeActivity.this,
                mini_entrees);
        liste_entrees_vue.setAdapter(adapter);
        liste_entrees_vue.setClickable(true);
        liste_entrees_vue.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if(BuildConfig.DEBUG)
                    Log.d(TAG, "On a cliqué sur l'item " + position);

                MiniEntree m = (MiniEntree) liste_entrees_vue.getItemAtPosition(position);
                Intent i;
                switch(type_liste)
                {
                    case "situation":

                        i = new Intent(ConsultationListeActivity.this, ConsultationEntreeActivity.class);
                        i.putExtra("entree_id", m.getId());
                        startActivity(i);
                        break;
                    case "prise_medicament":
                        i = new Intent(ConsultationListeActivity.this, ConsultationPriseMedicamentActivity.class);
                        i.putExtra("entree_id", m.getId());
                        startActivity(i);
                        break;

                    case "ailmentation":

                        i = new Intent(ConsultationListeActivity.this, ConsultationAlimentationActivity.class);
                        i.putExtra("entree_id", m.getId());
                        startActivity(i);
                        break;
                    case "activite_physique":

                        i = new Intent(ConsultationListeActivity.this, ConsultationActivitePhysiqueActivity.class);
                        i.putExtra("entree_id", m.getId());
                        startActivity(i);
                        break;
                    case "glycemie":

                        i = new Intent(ConsultationListeActivity.this, ConsultationGlycemieActivity.class);
                        i.putExtra("entree_id", m.getId());
                        startActivity(i);
                        break;
                    case "poids":

                        i = new Intent(ConsultationListeActivity.this, ConsultationPoidsActivity.class);
                        i.putExtra("entree_id", m.getId());
                        startActivity(i);
                        break;
                    case "sommeil":

                        i = new Intent(ConsultationListeActivity.this, ConsultationSommeilActivity.class);
                        i.putExtra("entree_id", m.getId());
                        startActivity(i);
                        break;
                }

            }
        });



        bouton_liste_vers_accueil = (Button) findViewById(R.id.bouton_liste_vers_accueil);
        bouton_liste_vers_accueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(ConsultationListeActivity.this, MainActivity.class);
                startActivity(intention);
//                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

    }
}




