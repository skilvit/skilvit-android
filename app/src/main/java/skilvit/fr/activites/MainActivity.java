package skilvit.fr.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import skilvit.fr.data_manager.DBManager;
import skilvit.fr.R;
import skilvit.fr.in_out.ExportationEntreesActivity;
import skilvit.fr.in_out.ImportationEntreeActivity;

public class MainActivity extends AppCompatActivity {

    private static final  String TAG = DBManager.class.getSimpleName();


    Button bouton_consultation;
    Button bouton_situation;
    Button bouton_exportation;
    Button bouton_importation;
    Button bouton_dosage_medicament;
    Button bouton_options;
    Button bouton_notice;

    Button bouton_activite_physique;
    Button bouton_alimentation;
    Button bouton_glycemie;
    Button bouton_poids;
    Button bouton_sommeil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bouton_consultation = (Button) findViewById(R.id.bouton_consultation);
        bouton_situation = (Button) findViewById(R.id.bouton_nouvelle_entree);
        bouton_dosage_medicament = (Button) findViewById(R.id.bouton_dosage_medicament);
        bouton_exportation = (Button) findViewById(R.id.bouton_exporter_entrees);
        bouton_importation = (Button) findViewById(R.id.bouton_importer_entrees);
        bouton_options = (Button) findViewById(R.id.bouton_options);
        bouton_notice = (Button) findViewById(R.id.bouton_notice);
        bouton_activite_physique = (Button) findViewById(R.id.bouton_actvite_physique);
        bouton_alimentation = (Button) findViewById(R.id.bouton_alimentation);
        bouton_glycemie = (Button) findViewById(R.id.bouton_glycemie);
        bouton_poids = (Button) findViewById(R.id.bouton_poids);
        bouton_sommeil = (Button) findViewById(R.id.bouton_sommeil);

        create_option();
//        JSONObject options = retrieve_option();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        sp.getBoolean("fiche_activite_physique", true);




        bouton_consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ConsultationListeActivity.class);
                i.putExtra("entree", "situation");
                startActivity(i);
            }
        });
        bouton_situation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreationSituationActivity.class);
                startActivity(i);
            }
        });

        bouton_dosage_medicament.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PriseMedicamentActivity.class);
                startActivity(i);
            }
        });

        bouton_exportation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ExportationEntreesActivity.class);
                startActivity(i);
            }
        });

        bouton_importation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ImportationEntreeActivity.class);
                startActivity(i);
            }
        });
        bouton_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Notice.class);
                startActivity(i);
            }
        });
        bouton_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(i);
            }
        });

        if(sp.getBoolean("fiche_activite_physique", true))
        {
            bouton_activite_physique.setVisibility(View.VISIBLE);
            bouton_activite_physique.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, ActivitePhysiqueActivity.class);
                    startActivity(i);
                }
            });
        }
        else
        {
            bouton_activite_physique.setVisibility(View.GONE);
        }


        ;
//        sp.getBoolean("fiche_prise_medicament", true);
//        sp.getBoolean("fiche_situation", true);

        if(sp.getBoolean("fiche_alimentation", true)) {

            bouton_alimentation.setVisibility(View.VISIBLE);
            bouton_alimentation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, AlimentationActivity.class);
                    startActivity(i);
                }
            });
        }
        else
        {
            bouton_alimentation.setVisibility(View.GONE);

        }
        if(sp.getBoolean("fiche_glycemie", true)) {
            bouton_glycemie.setVisibility(View.VISIBLE);
            bouton_glycemie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, GlycemieActivity.class);
                    startActivity(i);
                }
            });
        }
        else
        {
            bouton_glycemie.setVisibility(View.GONE);
        }

        if(sp.getBoolean("fiche_poids", true)) {
            bouton_poids.setVisibility(View.VISIBLE);
            bouton_poids.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, PoidsActivity.class);
                    startActivity(i);
                }
            });
        }
        else
        {
            bouton_poids.setVisibility(View.GONE);
        }

        if(sp.getBoolean("fiche_sommeil", true)) {
            bouton_sommeil.setVisibility(View.VISIBLE);
            bouton_sommeil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, SommeilActivity.class);
                    startActivity(i);
                }
            });
        }
        else
        {
            bouton_sommeil.setVisibility(View.GONE);
        }
    }



//    private JSONObject retrieve_option()
//    {
//        String JSON_EXTENSION = ".json";
//        String OPTION_FILENAME = "options";
//        FileOutputStream fos = null;
//        OutputStreamWriter osw = null;
//        JSONObject object;
//
//        String assert_directory = "file:///android_asset/";
//        File json_file = new File(assert_directory, OPTION_FILENAME + JSON_EXTENSION);
//
//
//        StringBuilder sb = new StringBuilder();
//        FileInputStream fis = null;
//        InputStreamReader isr = null;
//        BufferedReader br = null;
//        try
//        {
//            fis = new FileInputStream(json_file);
//            isr = new InputStreamReader(fis);
//            br = new BufferedReader(isr);
//            String resultat;
//            while ((resultat = br.readLine()) != null)
//            {
//                if(sb.length() > 0)
//                {
//                    sb.append("\n");
//                }
//                sb.append(resultat);
//            }
//        }
//        catch(IOException e)
//        {
//            if(BuildConfig.DEBUG)
//                Log.e(TAG, e.toString());
//        }
//        finally {
//            if(br != null)
//            {
//                try
//                {
//                    br.close();
//                }
//                catch(IOException e)
//                {
//                    if(BuildConfig.DEBUG)
//                        Log.e(TAG, e.toString());
//                }
//            }
//            if(isr != null)
//            {
//                try
//                {
//                    isr.close();
//                }
//                catch(IOException e)
//                {
//                    if(BuildConfig.DEBUG)
//                        Log.e(TAG, e.toString());
//                }
//            }
//            if(fis != null)
//            {
//                try
//                {
//                    fis.close();
//                }
//                catch(IOException e)
//                {
//                    if(BuildConfig.DEBUG)
//                        Log.e(TAG, e.toString());
//                }
//            }
//        }
//        try {
//            object = new JSONObject(sb.toString());
//            return object;
//        }
//        catch (JSONException e) {
//            Log.d(TAG, "mauvais options");
//        }
//        return new JSONObject();
//    }

    private void create_option()
    {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putBoolean("fiche_activite_physique", true);
//        editor.putBoolean("fiche_alimentation", true);
//        editor.putBoolean("fiche_glycemie", true);
//        editor.putBoolean("fiche_poids", true);
//        editor.putBoolean("fiche_prise_medicament", true);
//        editor.putBoolean("fiche_situation", true);
//        editor.putBoolean("fiche_sommeil", true);
//        editor.apply();
//        String JSON_EXTENSION = ".json";
//        String OPTION_FILENAME = "options";
//        FileOutputStream fos = null;
//        OutputStreamWriter osw = null;
////        JSONArray json_array;
//        JSONObject object;
////        json_array = new JSONArray();
//        String assert_directory = "file:///android_asset/";
//        if(BuildConfig.DEBUG)
//            Log.d(TAG, assert_directory);
//
//        File json_file = new File(assert_directory, OPTION_FILENAME + JSON_EXTENSION);
//        if(json_file.exists())
//        {
//            // Rien
//            if(BuildConfig.DEBUG) {
//                Log.d(TAG, "Le fichier option existe déjà !");
//            }
//        }
//        else
//        {
//            try {
//                if(BuildConfig.DEBUG) {
//                    Log.d(TAG, "On crée le fichier de config !!!");
//                }
//                fos = new FileOutputStream(json_file);
//                osw = new OutputStreamWriter(fos);
//                object = new JSONObject();
//                object.put("fiche_prise_medicament", true);
//                object.put("fiche_activite_physique", true);
//                object.put("fiche_alimentation", true);
//                object.put("fiche_glycemie", true);
//                object.put("fiche_poids", true);
//                object.put("fiche_prise_medicament", true);
//                object.put("fiche_situation", true);
//                object.put("fiche_sommeil", true);
//
//                if(BuildConfig.DEBUG) {
//                    Log.d(TAG, object.toString());
//                    Log.d(TAG, osw.toString());
//                }
//                osw.write(object.toString());
//            } catch (FileNotFoundException e) {
//                if(BuildConfig.DEBUG)
//                    Log.e(TAG, "new FileOutputStream()", e);
//            } catch (IOException e) {
//                if(BuildConfig.DEBUG)
//                    Log.e(TAG, "problème io");
//            } catch (JSONException e) {
//                if(BuildConfig.DEBUG)
//                    Log.e(TAG, "Problème avec les données JSON");
//            }finally {
//                if (osw != null) {
//                    try {
//                        osw.close();
//                    } catch (IOException e) {
//                        if(BuildConfig.DEBUG)
//                            Log.e(TAG, "osw.close()", e);
//                    }
//                }
//                if (fos != null) {
//                    try {
//                        fos.close();
//                    } catch (IOException e) {
//                        if(BuildConfig.DEBUG)
//                            Log.e(TAG, "fos.close()", e);
//                    }
//                }
//            }
//        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
