package skilvit.fr.activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.appcompat.app.AppCompatActivity;
import skilvit.fr.BuildConfig;
import skilvit.fr.R;

public class EntreesPDFActivity extends AppCompatActivity {
    private static final  String TAG = EntreesPDFActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_pdf_entrees);
        final String filename = getIntent().getStringExtra("path_html_file");
        Log.d(TAG, "path_html_file " + filename);
        final WebView wv;

        File dirPublicDocuments =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(dirPublicDocuments.toString(), filename); //.split(":")[1].split("/")[1]
        Log.d(TAG, file.toString());
//        String path = file.getAbsolutePath();
//        String displayName = null;
        final StringBuilder sb = new StringBuilder();
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

        wv =  findViewById(R.id.vue_web_pdf);
        WebSettings webSettings = wv.getSettings();
        webSettings.setAllowFileAccessFromFileURLs(true);
//        webSettings.set
//        webSettings.setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);
//        wv.getSettings().setPluginState(PluginState.ON);
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setAllowContentAccess(true);
        wv.getSettings().setAllowFileAccessFromFileURLs(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        wv.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EntreesPDFActivity.this);
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += " Do you want to continue anyway?";

                builder.setTitle("SSL Certificate Error");
                builder.setMessage(message);
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }

        });
//        wv.loadData(sb.toString(), "text/html", "utf-8");
        wv.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html", "utf-8", "");
        if(BuildConfig.DEBUG)
            Log.d(TAG, "Données html : " + sb.toString());






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vue_pdf_entrees, menu);
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
