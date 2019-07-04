package skilvit.fr.in_out;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.appcompat.app.AppCompatActivity;
import skilvit.fr.BuildConfig;
import skilvit.fr.R;
import skilvit.fr.data_manager.ActivitePhysique;
import skilvit.fr.data_manager.Alimentation;
import skilvit.fr.data_manager.DBManager;
import skilvit.fr.data_manager.Glycemie;
import skilvit.fr.data_manager.Poids;
import skilvit.fr.data_manager.PriseMedicament;
import skilvit.fr.data_manager.Situation;
import skilvit.fr.data_manager.Sommeil;

public class ImportationEntreeActivity extends AppCompatActivity {
    private static final  String TAG = ImportationEntreeActivity.class.getSimpleName();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    int code = 154;
    Button bouton_recuperation;
    TextView texte_etat_importation;
    Button bouton_retour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importation_entree);

        verifyStoragePermissions(ImportationEntreeActivity.this);

        bouton_recuperation = (Button) findViewById(R.id.bouton_recuperation);
        texte_etat_importation = (TextView) findViewById(R.id.texte_etat_importation);

        bouton_recuperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        bouton_retour = (Button) findViewById(R.id.bouton_importation_retour);
        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(ImportationEntreeActivity.this, MainActivity.class);
//                startActivity(i);
                onBackPressed();
            }
        });


    }

    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");      //all files
//        intent.setType("text/json");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), code);
        } catch (android.content.ActivityNotFoundException ex) {
            if(BuildConfig.DEBUG)
                Log.e(TAG, ex.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == code && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            String uriString = null;
            if (uri != null) {
                uriString = uri.toString();
            }
            File dirPublicDocuments =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            if(BuildConfig.DEBUG) {
                Log.d(TAG, dirPublicDocuments.toString());
                Log.d(TAG, "uri " + uriString);
                if (uri != null) {
                    Log.d(TAG, uri.getPath());
                }
            }

            File file = new File(dirPublicDocuments.toString(), uri.getPath().split(":")[1]); //.split(":")[1].split("/")[1]
//                    File file = new File(uri.getPath()); //.split(":")[1].split("/")[1]
//                    Log.d(TAG, uriString.split(":")[0]);
//                    Log.d(TAG, uriString.split(":")[1]);
//                    myFile.getPath();
//                    myFile.getAbsolutePath();
//                    myFile.get
//                    myFile.getPath()
            String path = file.getAbsolutePath();


            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
//                        String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        if(BuildConfig.DEBUG)
                            Log.d(TAG, path);
                        StringBuilder sb = new StringBuilder();
                        FileInputStream fis = null;
                        InputStreamReader isr = null;
                        BufferedReader br = null;
                        try
                        {
                            fis = new FileInputStream(file);
                            isr = new InputStreamReader(fis);
                            br = new BufferedReader(isr);
                            String resultat;
                            while ((resultat = br.readLine()) != null)
                            {
                                if(sb.length() > 0)
                                {
                                    sb.append("\n");
                                }
                                sb.append(resultat);
                            }
                        }
                        catch(IOException e)
                        {
                            if(BuildConfig.DEBUG)
                                Log.e(TAG, e.toString());
                        }
                        finally {
                            if(br != null)
                            {
                                try
                                {
                                    br.close();
                                }
                                catch(IOException e)
                                {
                                    if(BuildConfig.DEBUG)
                                        Log.e(TAG, e.toString());
                                }
                            }
                            if(isr != null)
                            {
                                try
                                {
                                    isr.close();
                                }
                                catch(IOException e)
                                {
                                    if(BuildConfig.DEBUG)
                                        Log.e(TAG, e.toString());
                                }
                            }
                            if(fis != null)
                            {
                                try
                                {
                                    fis.close();
                                }
                                catch(IOException e)
                                {
                                    if(BuildConfig.DEBUG)
                                        Log.e(TAG, e.toString());
                                }
                            }
                        }
                        retrieve_json_file(sb.toString());
                    }
                } finally {
                    if(cursor != null) {
                        cursor.close();
                    }
                }
            } else if (uriString.startsWith("file://")) {
                String displayName = file.getName();
            }
        }
    }


    public void retrieve_json_file(String content)
    {
        DBManager db = new DBManager(ImportationEntreeActivity.this);
        try
        {
            JSONArray objet = new JSONArray(content);
            for(int i = 0; i < objet.length(); i ++) {
                JSONObject jso = (JSONObject) objet.get(i);
                if(BuildConfig.DEBUG)
                    Log.d(TAG, jso.toString());
                if (!jso.has("type")) {
                    Situation e = new Situation(jso.getInt("jour"),
                            jso.getInt("mois"),
                            jso.getInt("annee"),
                            jso.getInt("heure"),
                            jso.getInt("minute"),
                            jso.getString("intensite"),
                            jso.getString("situation"),
                            jso.getString("emotions_sensations"),
                            jso.getString("pensees"),
                            jso.getString("comportement"));
//                    situations.add(e);
                    db.insert(e);
                } else {
                    String type = jso.getString("type");

                    switch (type) {
                        case "situation":
                            Situation e = new Situation(jso.getInt("jour"),
                                    jso.getInt("mois"),
                                    jso.getInt("annee"),
                                    jso.getInt("heure"),
                                    jso.getInt("minute"),
                                    jso.getString("intensite"),
                                    jso.getString("situation"),
                                    jso.getString("emotions_sensations"),
                                    jso.getString("pensees"),
                                    jso.getString("comportement"));
                            String taux_croyance = "";
                            String pensee_alternative = "";
                            String taux_croyance_actualise = "";
                            if (jso.has("taux_croyance")) {
                                taux_croyance = jso.getString("taux_croyance");
                            }
                            if (jso.has("pensee_alternative")) {
                                pensee_alternative = jso.getString("pensee_alternative");
                            }
                            if (jso.has("taux_croyance_actualise")) {
                                taux_croyance_actualise = jso.getString("taux_croyance_actualise");
                            }
                            e.setCroyances(taux_croyance, pensee_alternative, taux_croyance_actualise);
//                    situations.add(e);
                            db.insert(e);
                            break;
                        case "prise_medicament":
                            PriseMedicament pm = new PriseMedicament(
                                    jso.getInt("jour"),
                                    jso.getInt("mois"),
                                    jso.getInt("annee"),
                                    jso.getInt("heure"),
                                    jso.getInt("minute"),
                                    jso.getString("medicament"),
                                    jso.getString("dosage"));
//                    pms.add(pm);
                            db.insert(pm);
                            break;
                        case "activite_physique":
                            ActivitePhysique ap = new ActivitePhysique(
                                    jso.getInt("jour"),
                                    jso.getInt("mois"),
                                    jso.getInt("annee"),
                                    jso.getInt("heure"),
                                    jso.getInt("minute"),
                                    jso.getString("sport"),
                                    jso.getString("duree"),
                                    jso.getString("difficulte_ressentie"));
                            db.insert(ap);
                            break;
                        case "alimentation":
                            Alimentation ali = new Alimentation(
                                    jso.getInt("jour"),
                                    jso.getInt("mois"),
                                    jso.getInt("annee"),
                                    jso.getInt("heure"),
                                    jso.getInt("minute"),
                                    jso.getString("repas"),
                                    jso.getString("nourriture")
                            );
                            db.insert(ali);
                            break;
                        case "glycemie":
                            Glycemie g = new Glycemie(
                                    jso.getInt("jour"),
                                    jso.getInt("mois"),
                                    jso.getInt("annee"),
                                    jso.getInt("heure"),
                                    jso.getInt("minute"),
                                    jso.getString("glycemie")
                            );
//                            db.insert(g);
                            break;

                        case "poids":
                            Poids poids = new Poids(
                                    jso.getInt("jour"),
                                    jso.getInt("mois"),
                                    jso.getInt("annee"),
                                    jso.getInt("heure"),
                                    jso.getInt("minute"),
                                    jso.getString("poids")
                            );
                            db.insert(poids);
                            break;
                        case "sommeil":
                            Sommeil sommeil = new Sommeil(
                                    jso.getInt("jour"),
                                    jso.getInt("mois"),
                                    jso.getInt("annee"),
                                    jso.getInt("heure"),
                                    jso.getInt("minute"),
                                    jso.getString("evenement"),

                                    jso.getInt("jour_evenement"),
                                    jso.getInt("mois_evenement"),
                                    jso.getInt("annee_evenement"),
                                    jso.getInt("heure_evenement"),
                                    jso.getInt("minute_evenement"),
                                    jso.getString("commentaire")
                            );
//                            db.insert(sommeil);
                            break;
                    }
                }
            }
            texte_etat_importation.setText(R.string.correct_importation);
        }
        catch (JSONException e)
        {
            texte_etat_importation.setText(R.string.furnished_data_incorrect);
            if(BuildConfig.DEBUG)
                Log.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_importation_entree, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void verifyStoragePermissions(Context context) {
        // Check if we have write permission
        int permission = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            requestPermissions(
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
