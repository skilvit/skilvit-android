package skilvit.fr.in_out;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import skilvit.fr.BuildConfig;
import skilvit.fr.R;
import skilvit.fr.data_manager.ExportDataManager;


public class ExportationEntreesActivity extends AppCompatActivity {
    private static final String TAG = ExportationEntreesActivity.class.getSimpleName();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    Button bouton_exporter_JSON;
    Button bouton_exporter_PDF;
    Button bouton_exporter_TXT;
    Button bouton_exporter_CSV;
    Button bouton_envoyer_email;
    Button bouton_exportation_vers_accueil;

    BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportation_entrees);

        verifyStoragePermissions(ExportationEntreesActivity.this);

        // On propose plusieurs boutons correspondants à plusieurs formats de fichiers où on exporte les données
        bouton_exporter_JSON = findViewById(R.id.bouton_exporter_entree_JSON);
        bouton_exporter_PDF = findViewById(R.id.bouton_exporter_entree_PDF);
        bouton_exporter_TXT = findViewById(R.id.bouton_exporter_format_texte);
        bouton_exporter_CSV = findViewById(R.id.bouton_exporter_format_csv);
        bouton_envoyer_email = findViewById(R.id.bouton_envoyer_mail);
        bouton_exportation_vers_accueil = findViewById(R.id.bouton_exportation_vers_accueil);
        verifyStoragePermissions(ExportationEntreesActivity.this);
//
//        grantPermission(WRITE_EXTERNAL_STORAGE);
//        grantPermission(READ_EXTERNAL_STORAGE);

        // Soit on le stocke sur le portable, soit on l'envoie à quelqu'un avec l'adresse email.

//        File dirBase = Environment.getExternalStorageDirectory();
//        File dirAppBase = new File(dirBase.getAbsolutePath()+File.separator+
//        "Android"+File.separator+"data"+File.separator+getClass().getPackage().getName()+
//        File.separator+"files");
//        if(!dirAppBase.mkdirs()){
//            Log.d(TAG, "on crée tous les sous-dossiers");
//        }
//        Log.d(TAG, dirAppBase.toString());
            //enregistrement du fichier !
        bouton_exporter_JSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = ExportDataManager.exportJSONFile(ExportationEntreesActivity.this);
                if(file != null)
                {
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    if (downloadManager != null) {
                        downloadManager.addCompletedDownload(file.getName(), file.getName(), true, "text/plain", file.getAbsolutePath(), file.length(), true);
                    }
                    Toast.makeText(ExportationEntreesActivity.this, R.string.fichier_json_exporte, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ExportationEntreesActivity.this, R.string.fichier_json_non_exporte, Toast.LENGTH_SHORT).show();
                }
            }
        });

        bouton_exporter_PDF.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                ExportDataManager.exportPDFFile(ExportationEntreesActivity.this);
            }
        });

        bouton_exporter_TXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = ExportDataManager.exportTXTFile(ExportationEntreesActivity.this);
                if(file != null)
                {
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    if (downloadManager != null) {
                        downloadManager.addCompletedDownload(file.getName(), file.getName(), true, "text/plain", file.getAbsolutePath(), file.length(), true);
                    }
                    Toast.makeText(ExportationEntreesActivity.this, R.string.fichier_txt_exporte, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ExportationEntreesActivity.this, R.string.fichier_txt_non_exporte, Toast.LENGTH_SHORT).show();
                }

            }
        });

        bouton_exporter_CSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = ExportDataManager.exportCSVFile(ExportationEntreesActivity.this);
                if(file != null)
                {
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    if (downloadManager != null) {
                        downloadManager.addCompletedDownload(file.getName(), file.getName(), true, "text/plain", file.getAbsolutePath(), file.length(), true);
                    }
                    Toast.makeText(ExportationEntreesActivity.this, R.string.fichier_csv_exporte, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ExportationEntreesActivity.this, R.string.fichier_csv_non_exporte, Toast.LENGTH_SHORT).show();
                }

            }
        });

        bouton_envoyer_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = ExportDataManager.exportCSVFile(ExportationEntreesActivity.this);
                if(file != null)
                {
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ExportationEntreesActivity.this);
                    String email_address = sp.getString("adresse_email_praticien", "");
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email_address});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getText(R.string.compte_rendu));
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getText(R.string.texte_compte_rendu));

                    ArrayList<Parcelable> uris = new ArrayList<Parcelable>();
                    uris.add(Uri.fromFile(file));
                    emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                    emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

                    Toast.makeText(ExportationEntreesActivity.this, R.string.please_choose_email_app, Toast.LENGTH_SHORT).show();
                    startActivity(Intent.createChooser(emailIntent, getString(R.string.share)));
                }
                else
                {
                    Toast.makeText(ExportationEntreesActivity.this, R.string.generation_fichier_a_envoyer_impossible, Toast.LENGTH_SHORT).show();
                }
            }
        });


    bouton_exportation_vers_accueil.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
//            Intent i = new Intent(ExportationEntreesActivity
//                    .this, MainActivity.class);
//            startActivity(i);
        }
    });

    }




//    private void grantPermission(String permission) {
//        Context context = ExportationEntreesActivity.this;
//        long checkPermission = ContextCompat.checkSelfPermission(context, permission);
//        if (checkPermission != PERMISSION_GRANTED) {
//            throw new RuntimeException("Failed to grant " + permission);
//        }
//    }
    @Override
    public void onStart()
    {
        super.onStart();
        registerReceivers();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        unregisterReceivers();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

    }

    public void registerReceivers()
    {
        IntentFilter intentFilter = new IntentFilter("pdf_received");
        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String lien_pdf = intent.getStringExtra("lien_pdf");
                Toast.makeText(ExportationEntreesActivity.this, R.string.fichier_pdf_exporte, Toast.LENGTH_SHORT).show();
                Intent target = new Intent(Intent.ACTION_VIEW);

                target.setDataAndType(Uri.parse(lien_pdf), "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent_chooser = Intent.createChooser(target, getString(R.string.ouvrir_fichier));
                if(BuildConfig.DEBUG){Log.d(TAG, "bro reçu lien pdf "+lien_pdf);}

                try {
                    startActivity(intent_chooser);

                } catch (ActivityNotFoundException e) {
                    Log.d(TAG, getString(R.string.no_app_found_to_open_this));
                }
            }

        };
        registerReceiver(br, intentFilter);
    }

    public void unregisterReceivers()
    {
        if(br != null)
        {try{unregisterReceiver(br);}
        catch (IllegalArgumentException e){
            if(BuildConfig.DEBUG){Log.d(TAG, "br was not registered");}}}
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
