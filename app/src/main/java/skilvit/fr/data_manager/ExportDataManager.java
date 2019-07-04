package skilvit.fr.data_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import skilvit.fr.BuildConfig;

public class ExportDataManager {

    private static final String TAG = ExportDataManager.class.getSimpleName();

    private static String FILENAME = "suivi";
    private static String JSON_EXTENSION = ".json";
    private static String TXT_EXTENSION= ".txt";
    private static String CSV_EXTENSION= ".csv";
//    private String HTML_EXTENSION = ".html";
//    private String PDF_EXTENSION = ".pdf";
    private static String SERVER_NAME = "https://www.skilvit.fr";
    private static String exportation_pdf = "/tcc/ajax/exportation_donnees_appli";


    private static ArrayList<Situation> situations;
    private static ArrayList<PriseMedicament> pms;
    private static ArrayList<ActivitePhysique> aps;
    private static ArrayList<Alimentation> alis;
    private static ArrayList<Glycemie> gs;
    private static ArrayList<Poids> ps;
    private static ArrayList<Sommeil> sommeils;


    public static void retrieveData(Context context) {
        DBManager db = new DBManager(context);
        situations = db.retrieve_last_situations(Long.MAX_VALUE);
        pms = db.retrieve_last_medicament(Long.MAX_VALUE);
        aps = db.retrieve_last_ap(Long.MAX_VALUE);
        alis = db.retrieve_last_al(Long.MAX_VALUE);
        gs = db.retrieve_last_glycemy(Long.MAX_VALUE);
        ps = db.retrieve_last_weight(Long.MAX_VALUE);
        sommeils = db.retrieve_last_sommeil(Long.MAX_VALUE);
    }


    public static File exportJSONFile(Context context) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        JSONArray json_array = new JSONArray();
        JSONObject object;
        int i = 0;
        int j;

        retrieveData(context);

        if (!situations.isEmpty()) {
            for (Situation situation : situations) {
                try {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, situation.afficher_previsualisation());
                    object = new JSONObject();
                    object.put("type", "situation");
                    ObservationDate obd = situation.getObd();
                    object.put("jour", obd.s_jour);
                    object.put("mois", obd.s_mois);
                    object.put("annee", obd.s_annee);
                    object.put("heure", obd.s_heure);
                    object.put("minute", obd.s_minute);
                    object.put("intensite", situation.getIntensite());
                    object.put("situation", situation.getSituation());
                    object.put("emotions_sensations", situation.getEmotions_sensation());
                    object.put("pensees", situation.getPensees());
                    object.put("taux_croyance", situation.getTaux_croyance());
                    object.put("pensee_alternative", situation.getPensee_alternative());
                    object.put("taux_croyance_actualise", situation.getTaux_croyance_actualise());
                    object.put("comportement", situation.getComportement());
                    json_array.put(i, object);
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "i : " + i);
                    i++;
                } catch (JSONException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Problème avec les données JSON");
                }
            }
        }
        if (!pms.isEmpty()) {
            j = i;
            for (PriseMedicament pm : pms) {
                try {
                    object = new JSONObject();
                    object.put("type", "prise_medicament");
                    ObservationDate obd = pm.getObd();
                    object.put("jour", obd.s_jour);
                    object.put("mois", obd.s_mois);
                    object.put("annee", obd.s_annee);
                    object.put("heure", obd.s_heure);
                    object.put("minute", obd.s_minute);
                    object.put("medicament", pm.getMedicament());
                    object.put("dosage", pm.getDosage());
                    json_array.put(j, object);
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "j : " + j);
                    j++;
                } catch (JSONException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Problème avec les données PriseMedicament JSON");
                }
            }
        }
        if (!aps.isEmpty()) {
            j = i;
            for (ActivitePhysique ap : aps) {
                try {
                    object = new JSONObject();
                    object.put("type", "activite_physique");
                    ObservationDate obd = ap.getObd();
                    object.put("jour", obd.s_jour);
                    object.put("mois", obd.s_mois);
                    object.put("annee", obd.s_annee);
                    object.put("heure", obd.s_heure);
                    object.put("minute", obd.s_minute);
                    object.put("sport", ap.getSport());
                    object.put("duree", ap.getDuree());
                    object.put("difficulte_ressentie", ap.getDifficulte_ressentie());
                    json_array.put(j, object);
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "j : " + j);
                    j++;
                } catch (JSONException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Problème avec les données ActivitePhysique JSON");
                }
            }
        }
        if (!alis.isEmpty()) {
            j = i;
            for (Alimentation ali : alis) {
                try {
                    object = new JSONObject();
                    object.put("type", "alimentation");
                    ObservationDate obd = ali.getObd();
                    object.put("jour", obd.s_jour);
                    object.put("mois", obd.s_mois);
                    object.put("annee", obd.s_annee);
                    object.put("heure", obd.s_heure);
                    object.put("minute", obd.s_minute);
                    object.put("repas", ali.getRepas());
                    object.put("nourriture", ali.getNourriture());
                    json_array.put(j, object);
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "j : " + j);
                    j++;
                } catch (JSONException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Problème avec les données Alimentation JSON");
                }
            }
        }
        if (!aps.isEmpty()) {
            j = i;
            for (Glycemie g : gs) {
                try {
                    object = new JSONObject();
                    object.put("type", "glycemie");
                    ObservationDate obd = g.getObd();
                    object.put("jour", obd.s_jour);
                    object.put("mois", obd.s_mois);
                    object.put("annee", obd.s_annee);
                    object.put("heure", obd.s_heure);
                    object.put("minute", obd.s_minute);
                    object.put("glycemie", g.getGlycemie());
                    json_array.put(j, object);
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "j : " + j);
                    j++;
                } catch (JSONException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Problème avec les données Glycemie JSON");
                }
            }
        }
        if (!ps.isEmpty()) {
            j = i;
            for (Poids p : ps) {
                try {
                    object = new JSONObject();
                    object.put("type", "poids");
                    ObservationDate obd = p.getObd();
                    object.put("jour", obd.s_jour);
                    object.put("mois", obd.s_mois);
                    object.put("annee", obd.s_annee);
                    object.put("heure", obd.s_heure);
                    object.put("minute", obd.s_minute);
                    object.put("poids", p.getPoids());
                    json_array.put(j, object);
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "j : " + j);
                    j++;
                } catch (JSONException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Problème avec les données Poids JSON");
                }
            }
        }
        if (!aps.isEmpty()) {
            j = i;
            for (Sommeil sommeil : sommeils) {
                try {
                    object = new JSONObject();
                    object.put("type", "sommeil");

                    ObservationDate obd = sommeil.getObd();
                    object.put("jour", obd.s_jour);
                    object.put("mois", obd.s_mois);
                    object.put("annee", obd.s_annee);
                    object.put("heure", obd.s_heure);
                    object.put("minute", obd.s_minute);

                    object.put("evenement", sommeil.getEvenement());

                    ObservationDate obd_evenement = sommeil.getEvenement_obd();
                    object.put("jour_evenement", obd_evenement.s_jour);
                    object.put("mois_evenement", obd_evenement.s_mois);
                    object.put("annee_evenement", obd_evenement.s_annee);
                    object.put("heure_evenement", obd_evenement.s_heure);
                    object.put("minute_evenement", obd_evenement.s_minute);

                    object.put("commentaire", sommeil.getCommentaire());

                    json_array.put(j, object);
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "j : " + j);
                    j++;
                } catch (JSONException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Problème avec les données Sommeil JSON");
                }
            }
        }


        try {
            File dirPublicDocuments =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            if (!dirPublicDocuments.mkdirs()) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "tous les sous-dossiers" + dirPublicDocuments.getAbsolutePath() +
                            " existent déjà");
                }
            }
            if (BuildConfig.DEBUG)
                Log.d(TAG, dirPublicDocuments.toString());

            File json_file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILENAME + JSON_EXTENSION);
            fos = new FileOutputStream(json_file);
            osw = new OutputStreamWriter(fos);


            if (BuildConfig.DEBUG) {
                Log.d(TAG, json_array.toString());
                Log.d(TAG, osw.toString());
            }
            osw.write(json_array.toString());

            Toast.makeText(context, "Le fichier JSON a correctement été exporté.", Toast.LENGTH_SHORT).show();
            return json_file;

        } catch (FileNotFoundException e) {
            if (BuildConfig.DEBUG)
                Log.e(TAG, "new FileOutputStream()", e);
            return null;
        } catch (IOException e) {
            if (BuildConfig.DEBUG)
                Log.e(TAG, "problème io");
            return null;
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "osw.close()", e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "fos.close()", e);
                }
            }
        }
    }

    public static void exportPDFFile(final Context context) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        final JSONArray json_array = new JSONArray();
        JSONObject object;

        retrieveData(context);

        int i = 0;
        int j;
        if (!situations.isEmpty()) {

            for (Situation situation : situations) {
                try {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, situation.afficher_previsualisation());
                    object = new JSONObject();
                    object.put("type", "situation");
                    ObservationDate obd = situation.getObd();
                    object.put("jour", obd.s_jour);
                    object.put("mois", obd.s_mois);
                    object.put("annee", obd.s_annee);
                    object.put("heure", obd.s_heure);
                    object.put("minute", obd.s_minute);
                    object.put("intensite", situation.getIntensite());
                    object.put("situation", situation.getSituation());
                    object.put("emotions_sensations", situation.getEmotions_sensation());
                    object.put("pensees", situation.getPensees());
                    object.put("taux_croyance", situation.getTaux_croyance());
                    object.put("pensee_alternative", situation.getPensee_alternative());
                    object.put("taux_croyance_actualise", situation.getTaux_croyance_actualise());
                    object.put("comportement", situation.getComportement());
                    json_array.put(i, object);
//                    if (BuildConfig.DEBUG)
//                        Log.d(TAG, "i : " + i);
                    i++;
                } catch (JSONException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Problème avec les données JSON");
                }
            }
        }
        if (!pms.isEmpty()) {
            j = i;
            for (PriseMedicament pm : pms) {
                try {
                    object = new JSONObject();
                    object.put("type", "prise_medicament");
                    ObservationDate obd = pm.getObd();
                    object.put("jour", obd.s_jour);
                    object.put("mois", obd.s_mois);
                    object.put("annee", obd.s_annee);
                    object.put("heure", obd.s_heure);
                    object.put("minute", obd.s_minute);
                    object.put("medicament", pm.getMedicament());
                    object.put("dosage", pm.getDosage());
                    json_array.put(j, object);
//                    if (BuildConfig.DEBUG)
//                        Log.d(TAG, "j : " + j);
                    j++;
                } catch (JSONException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Problème avec les données PriseMedicament JSON");
                }
            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String lien_pdf = Connexion.exportation_entrees_pdf(SERVER_NAME + exportation_pdf, json_array);
                    Intent intent = new Intent("pdf_received");
                    intent.putExtra("lien_pdf", lien_pdf);
                    Log.d(TAG, "lien pdf "+lien_pdf);
                    context.sendBroadcast(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static String exportSituationFields(ArrayList<Situation> mesures) {
        StringBuilder lines = new StringBuilder();
        for (Situation situation : situations) {
            lines.append(situation.toCSVLine()).append("\n");
        }
        return lines.toString();
    }


    public static File exportCSVFile(Context context) {
        File csv_file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILENAME + CSV_EXTENSION);
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        retrieveData(context);


        try {
            File dirPublicDocuments =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            if (!dirPublicDocuments.mkdirs()) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "tous les sous-dossiers" + dirPublicDocuments.getAbsolutePath() +
                            " existent déjà");
                }
            }
            if (BuildConfig.DEBUG)
                Log.d(TAG, dirPublicDocuments.toString());


            fos = new FileOutputStream(csv_file);
            osw = new OutputStreamWriter(fos);

            StringBuilder lines = new StringBuilder();
            for (Situation situation : situations) {
                lines.append(situation.toCSVLine()).append("\n");
            }

            for (PriseMedicament pm : pms) {
                lines.append(pm.toCSVLine()).append("\n");
            }

            for (ActivitePhysique ap : aps) {
                lines.append(ap.toCSVLine()).append("\n");
            }

            for (Alimentation ali : alis) {
                lines.append(ali.toCSVLine()).append("\n");
            }

            for (Poids poids : ps) {
                lines.append(poids.toCSVLine()).append("\n");
            }

            for (Sommeil sommeil : sommeils) {
                lines.append(sommeil.toCSVLine()).append("\n");
            }

            osw.write(lines.toString());
            Log.d(TAG, lines.toString());


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "osw.close()", e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "fos.close()", e);
                }
            }
        }
        return csv_file;

    }


    public static File exportTXTFile(Context context) {
        File txt_file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILENAME + TXT_EXTENSION);
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        retrieveData(context);

        try {
            File dirPublicDocuments =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            if (!dirPublicDocuments.mkdirs()) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "tous les sous-dossiers" + dirPublicDocuments.getAbsolutePath() +
                            " existent déjà");
                }
            }
            if (BuildConfig.DEBUG)
                Log.d(TAG, dirPublicDocuments.toString());


            fos = new FileOutputStream(txt_file);
            osw = new OutputStreamWriter(fos);

            StringBuilder lines = new StringBuilder();
            for (Situation situation : situations) {
                lines.append(situation.toCSVLine()).append("\n");
            }

            for (PriseMedicament pm : pms) {
                lines.append(pm.toCSVLine()).append("\n");
            }

            for (ActivitePhysique ap : aps) {
                lines.append(ap.toCSVLine()).append("\n");
            }

            for (Alimentation ali : alis) {
                lines.append(ali.toCSVLine()).append("\n");
            }

            for (Poids poids : ps) {
                lines.append(poids.toCSVLine()).append("\n");
            }

            for (Sommeil sommeil : sommeils) {
                lines.append(sommeil.toCSVLine()).append("\n");
            }

            osw.write(lines.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "osw.close()", e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "fos.close()", e);
                }
            }
        }
        return txt_file;
    }

}
