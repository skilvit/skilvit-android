package skilvit.fr.data_manager;

import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import skilvit.fr.BuildConfig;

/**
 * Created by Clément on 11/02/2018.
 */
public class Connexion {
    private static final String TAG = Connexion.class.getSimpleName();
//    String lien_pdf;
    public static String exportation_entrees_pdf(String url, JSONArray entrees)
            throws IOException /* MalformedURLException */ {
        StringBuilder sb = new StringBuilder();
        if(BuildConfig.DEBUG)
            Log.d(TAG, url);
        URL _url = new URL(url);
        if(BuildConfig.DEBUG)
            Log.d(TAG, "On ouvre la connexion");
        HttpURLConnection httpURLConnection = (HttpURLConnection) _url
                .openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type",
                "application/json");

        httpURLConnection.setRequestProperty("Content-Length", "" +
                Integer.toString(entrees.toString().getBytes().length));
        httpURLConnection.setRequestProperty("Content-Language", "fr-FR");

        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        if(BuildConfig.DEBUG)
            Log.d(TAG, "On va envoyer les données");

        //Send request
        DataOutputStream wr = new DataOutputStream(
                httpURLConnection.getOutputStream ());
        wr.write(entrees.toString().getBytes());
        wr.flush();
        wr.close();
        if(BuildConfig.DEBUG)
            Log.d(TAG, "on a tout envoyé !!!");

        //Get Response
//        InputStream is = httpURLConnection.getInputStream();
//        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//        String line;
//        StringBuffer response = new StringBuffer();
//        while((line = rd.readLine()) != null) {
//            response.append(line);
//            response.append('\r');
//        }
//        rd.close();
//        response.toString();

        //Log.d(TAG, httpURLConnection.getErrorStream().toString());
        if(BuildConfig.DEBUG)
            Log.d(TAG, "on va voir la connexion");
        final int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            if(BuildConfig.DEBUG)
                Log.d(TAG, "La connexion est bonne");
            InputStreamReader inputStreamReader = new InputStreamReader(
                    httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if(sb.length() > 0)
                {
                    sb.append("\n");
                }
                sb.append(line);
            }
            if(BuildConfig.DEBUG)
                Log.e(TAG, sb.toString());
            try {
                bufferedReader.close();
            } catch (IOException e) {
                // ein Fehler beim Schließen wird bewusst ignoriert
            }
        }
        httpURLConnection.disconnect();
        return sb.toString();
    }
}
