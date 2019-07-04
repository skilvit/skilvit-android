package skilvit.fr.data_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import skilvit.fr.BuildConfig;

/**
 * Created by Clément on 21/07/2017.
 */

public class DBManager extends SQLiteOpenHelper {

    private static final  String TAG = DBManager.class.getSimpleName();


    private static final String DATABASE_NAME = "db_skilvit.db";
    private static final int DATABASE_VERSION = 3;
    public static final String _ID = "_id";

    /*
    Création des noms de table
     */
    private static final String TABLE_ENREGISTREMENT_NAME = "enregistrement";
    private static final String TABLE_MEDICAMENT_NAME = "prise_medicament";
    private static final String TABLE_ACTIVITE_PHYSIQUE_NAME = "activite_physique";
    private static final String TABLE_SOMMEIL_NAME = "sommeil";
    private static final String TABLE_GLYCEMIE_NAME= "glycemie";
    private static final String TABLE_ALIMENTATION_NAME= "alimentation";
    private static final String TABLE_POIDS_NAME= "poids";

/*
Création de tables
 */
    private static final String TABLE_TCC_CREATE = "CREATE TABLE IF NOT EXISTS "+ TABLE_ENREGISTREMENT_NAME
            +" ("+_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "jour INTEGER NOT NULL, "
            + "mois INTEGER NOT NULL, "
            + "annee INTEGER NOT NULL, "
            + "heure INTEGER NOT NULL, "
            + "minute INTEGER NOT NULL, "
            + "intensite INTEGER NOT NULL, "
            + "situation TEXT NOT NULL, "
            + "emotions_sensations TEXT NOT NULL, "
            + "pensees TEXT NOT NULL, " // pensée automatique
            + "taux_croyance TEXT, "
            + "pensee_alternative TEXT, "
            + "taux_croyance_actualise TEXT, "
            + "comportement TEXT NOT NULL);";

    private static final String TABLE_MEDICAMENT_CREATE = "CREATE TABLE IF NOT EXISTS "+ TABLE_MEDICAMENT_NAME
            +" ("+_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "jour INTEGER NOT NULL, "
            + "mois INTEGER NOT NULL, "
            + "annee INTEGER NOT NULL, "
            + "heure INTEGER NOT NULL, "
            + "minute INTEGER NOT NULL, "
            + "medicament INTEGER NOT NULL, "
            + "dosage TEXT NOT NULL);";

    private static final String TABLE_ACTIVITE_PHYSIQUE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_ACTIVITE_PHYSIQUE_NAME
            + " ("+_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "jour INTEGER NOT NULL, "
            + "mois INTEGER NOT NULL, "
            + "annee INTEGER NOT NULL, "
            + "heure INTEGER NOT NULL, "
            + "minute INTEGER NOT NULL, "
            + "sport TEXT NOT NULL, "
            + "duree TEXT NOT NULL, "
            + "difficulte_ressentie TEXT NOT NULL);";

    private static final String TABLE_ALIMENTATION_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_ALIMENTATION_NAME
            + " ("+_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "jour INTEGER NOT NULL, "
            + "mois INTEGER NOT NULL, "
            + "annee INTEGER NOT NULL, "
            + "heure INTEGER NOT NULL, "
            + "minute INTEGER NOT NULL, "
            + "repas TEXT NOT NULL, "
            + "nourriture TEXT NOT NULL);";

    private static final String TABLE_GLYCEMIE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_GLYCEMIE_NAME
            + " ("+_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "jour INTEGER NOT NULL, "
            + "mois INTEGER NOT NULL, "
            + "annee INTEGER NOT NULL, "
            + "heure INTEGER NOT NULL, "
            + "minute INTEGER NOT NULL, "
            + "glycemie TEXT NOT NULL);";

    private static final String TABLE_POIDS_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_POIDS_NAME
            + " ("+_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "jour INTEGER NOT NULL, "
            + "mois INTEGER NOT NULL, "
            + "annee INTEGER NOT NULL, "
            + "heure INTEGER NOT NULL, "
            + "minute INTEGER NOT NULL, "
            + "poids TEXT NOT NULL);";
    private static final String TABLE_SOMMEIL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_SOMMEIL_NAME
            + " ("+_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "jour INTEGER NOT NULL, "
            + "mois INTEGER NOT NULL, "
            + "annee INTEGER NOT NULL, "
            + "heure INTEGER NOT NULL, "
            + "minute INTEGER NOT NULL, "
            + "evenement TEXT NOT NULL,"
            + "jour_evenement INTEGER NOT NULL, "
            + "mois_evenement INTEGER NOT NULL, "
            + "annee_evenement INTEGER NOT NULL, "
            + "heure_evenement INTEGER NOT NULL, "
            + "minute_evenement INTEGER NOT NULL, "
            + "commentaire TEXT);";

    /*
    Les colonnes
     */
    private static final String[] COLONNES = {_ID, "jour", "mois", "annee", "heure", "minute",
            "intensite", "situation", "emotions_sensations", "pensees","taux_croyance",
    "pensee_alternative", "taux_croyance_actualise", "comportement"}; //
    private static final String[] COLONNES_MEDICAMENT = {_ID, "jour", "mois", "annee", "heure", "minute",
            "medicament", "dosage"};
    private static final String[] COLONNES_ACTIVITE_PHYSIQUE = {_ID, "jour", "mois", "annee", "heure",
            "minute", "sport", "duree", "difficulte_ressentie"};
    private static final String[] COLONNES_ALIMENTATION = {_ID, "jour", "mois", "annee", "heure",
                "minute", "repas", "nourriture"};
    private static final String[] COLONNES_POIDS = {_ID, "jour", "mois", "annee", "heure",
            "minute", "poids"};
    private static final String[] COLONNES_GLYCEMIE = {_ID, "jour", "mois", "annee", "heure",
            "minute", "glycemie"};
    private static final String[] COLONNES_SOMMEIL= {_ID, "jour", "mois", "annee", "heure",
            "minute", "evenement", "jour_evenement", "mois_evenement", "annee_evenement", "heure_evenement",
            "minute_evenement", "commentaire"};


    /*
    Supprimer les tables
     */
    private static final String TABLE_DROP = "DROP TABLE IF EXISTS "
            + TABLE_ENREGISTREMENT_NAME;
    private static final String TABLE_MEDICAMENT_DROP = "DROP TABLE IF EXISTS "
            + TABLE_MEDICAMENT_NAME;

    private static final String TABLE_ACTIVITE_PHYSIQUE_DROP = "DROP TABLE IF EXISTS "
            +TABLE_ACTIVITE_PHYSIQUE_NAME;
    private static final String TABLE_ALIMENTATION_DROP = "DROP TABLE IF EXISTS "
            +TABLE_ALIMENTATION_NAME;
    private static final String TABLE_SOMMEIL_DROP = "DROP TABLE IF EXISTS "
            +TABLE_SOMMEIL_NAME;
    private static final String TABLE_POIDS_DROP = "DROP TABLE IF EXISTS "
            +TABLE_POIDS_NAME;
    private static final String TABLE_GLYCEMIE_DROP = "DROP TABLE IF EXISTS "
            +TABLE_GLYCEMIE_NAME;

    public DBManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_TCC_CREATE);
        db.execSQL(TABLE_MEDICAMENT_CREATE);
        db.execSQL(TABLE_ACTIVITE_PHYSIQUE_CREATE);
        db.execSQL(TABLE_ALIMENTATION_CREATE);
        db.execSQL(TABLE_GLYCEMIE_CREATE);
        db.execSQL(TABLE_POIDS_CREATE);
        db.execSQL(TABLE_SOMMEIL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion)
    {
        db.execSQL(TABLE_DROP);
        db.execSQL(TABLE_MEDICAMENT_DROP);
        db.execSQL(TABLE_ACTIVITE_PHYSIQUE_DROP);
        db.execSQL(TABLE_ALIMENTATION_DROP);
        db.execSQL(TABLE_GLYCEMIE_DROP);
        db.execSQL(TABLE_POIDS_DROP);
        db.execSQL(TABLE_SOMMEIL_DROP);
        onCreate(db);
    }

    /*
    Les insertions
     */

    public void insert(Situation situation)
    {
        long rowId = -1;
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            ObservationDate obd = situation.getObd();
            values.put("jour", obd.s_jour);
            values.put("mois", obd.s_mois);
            values.put("annee", obd.s_annee);
            values.put("heure", obd.s_heure);
            values.put("minute", obd.s_minute);
            values.put("intensite", situation.getIntensite());
            values.put("situation", situation.getSituation());
            values.put("emotions_sensations", situation.getEmotions_sensation());
            values.put("pensees", situation.getPensees());
            values.put("taux_croyance", situation.getTaux_croyance());
            values.put("pensee_alternative", situation.getPensee_alternative());
            values.put("taux_croyance_actualise", situation.getTaux_croyance_actualise());
            values.put("comportement", situation.getComportement());

            rowId = db.insert(TABLE_ENREGISTREMENT_NAME, null, values);
            db.close();
        }
        catch(SQLiteException e)
        {
            if(BuildConfig.DEBUG)
                Log.e(TAG, "insert()", e);
        }
        finally
        {
            if(BuildConfig.DEBUG)
               Log.d(TAG, "insert(): rowId="+rowId);
        }
    }
    public void insert(PriseMedicament pm)
    {
        long rowId = -1;
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            ObservationDate obd = pm.getObd();
            values.put("jour", obd.s_jour);
            values.put("mois", obd.s_mois);
            values.put("annee", obd.s_annee);
            values.put("heure", obd.s_heure);
            values.put("minute", obd.s_minute);
            values.put("medicament", pm.getMedicament());
            values.put("dosage", pm.getDosage());
            rowId = db.insert(TABLE_MEDICAMENT_NAME, null, values);
            db.close();
        }
        catch(SQLiteException e)
        {
            if(BuildConfig.DEBUG)
                Log.e(TAG, "insert()", e);
        }
        finally
        {
            if(BuildConfig.DEBUG)
                Log.d(TAG, "insert(): rowId="+rowId);
        }
    }
    public void insert(Alimentation alimentation)
    {
        long rowId = -1;
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            ObservationDate obd = alimentation.getObd();
            values.put("jour", obd.s_jour);
            values.put("mois", obd.s_mois);
            values.put("annee", obd.s_annee);
            values.put("heure", obd.s_heure);
            values.put("minute", obd.s_minute);
            values.put("repas", alimentation.getRepas());
            values.put("nourriture", alimentation.getNourriture());
            rowId = db.insert(TABLE_ALIMENTATION_NAME, null, values);
            db.close();
        }
        catch (SQLiteException e)
        {
            if(BuildConfig.DEBUG)
                Log.e(TAG, "insert()", e);
        }
        finally
        {
            if(BuildConfig.DEBUG)
                Log.d(TAG, "insert(): rowId="+rowId);
        }

    }
    public void insert(Glycemie glycemie)
    {
        long rowId = -1;
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            ObservationDate obd = glycemie.getObd();
            values.put("jour", obd.s_jour);
            values.put("mois", obd.s_mois);
            values.put("annee", obd.s_annee);
            values.put("heure", obd.s_heure);
            values.put("minute", obd.s_minute);
            values.put("glycemie", glycemie.getGlycemie());
            rowId = db.insert(TABLE_GLYCEMIE_NAME, null, values);
            db.close();
        }
        catch (SQLiteException e)
        {
            if(BuildConfig.DEBUG)
                Log.e(TAG, "insert()", e);
        }
        finally
        {
            if(BuildConfig.DEBUG)
                Log.d(TAG, "insert(): rowId="+rowId);
        }

    }
    public void insert(Poids poids)
    {
        long rowId = -1;
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            ObservationDate obd = poids.getObd();
            values.put("jour", obd.s_jour);
            values.put("mois", obd.s_mois);
            values.put("annee", obd.s_annee);
            values.put("heure", obd.s_heure);
            values.put("minute", obd.s_minute);
            values.put("poids", poids.getPoids());
            rowId = db.insert(TABLE_POIDS_NAME, null, values);
            db.close();
        }
        catch (SQLiteException e)
        {
            if(BuildConfig.DEBUG)
                Log.e(TAG, "insert()", e);
        }
        finally
        {
            if(BuildConfig.DEBUG)
                Log.d(TAG, "insert(): rowId="+rowId);
        }

    }
    public void insert(Sommeil sommeil)
    {
        long rowId = -1;
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();

            ObservationDate obd = sommeil.getObd();
            values.put("jour", obd.s_jour);
            values.put("mois", obd.s_mois);
            values.put("annee", obd.s_annee);
            values.put("heure", obd.s_heure);
            values.put("minute", obd.s_minute);

            ObservationDate evenement_obs = sommeil.getEvenement_obd();
            values.put("jour_coucher", evenement_obs.s_jour);
            values.put("mois_coucher", evenement_obs.s_mois);
            values.put("annee_coucher", evenement_obs.s_annee);
            values.put("heure_coucher", evenement_obs.s_heure);
            values.put("minute_coucher", evenement_obs.s_minute);

            values.put("evenement", sommeil.getEvenement());

            values.put("commentaire", sommeil.getCommentaire());
            rowId = db.insert(TABLE_SOMMEIL_NAME, null, values);
            db.close();
        }
        catch (SQLiteException e)
        {
            if(BuildConfig.DEBUG)
                Log.e(TAG, "insert()", e);
        }
        finally
        {
            if(BuildConfig.DEBUG)
                Log.d(TAG, "insert(): rowId="+rowId);
        }

    }
    public void insert(ActivitePhysique ap)
    {
        long rowId = -1;

        try
        {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            ObservationDate obd = ap.getObd();
            values.put("jour", obd.s_jour);
            values.put("mois", obd.s_mois);
            values.put("annee", obd.s_annee);
            values.put("heure", obd.s_heure);
            values.put("minute", obd.s_minute);
            values.put("sport", ap.getSport());
            values.put("duree", ap.getDuree());
            values.put("difficulte_ressentie", ap.getDifficulte_ressentie());
            rowId = db.insert(TABLE_ACTIVITE_PHYSIQUE_NAME, null, values);
            db.close();
        }
        catch (SQLiteException e)
        {
            if(BuildConfig.DEBUG)
                Log.e(TAG, "insert()", e);
        }
        finally
        {
            if(BuildConfig.DEBUG)
                Log.d(TAG, "insert(): rowId="+rowId);

        }
    }

    public ArrayList<Sommeil> retrieve_last_sommeil(long nombre_sommeil)
    {
        ArrayList<Sommeil> sommeils = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cr = db.query(true, TABLE_SOMMEIL_NAME, COLONNES_SOMMEIL, "", null, "", "", _ID, "");
        cr.getCount();
        int i = 0;
        if(cr.moveToLast()) {
            do {
                int jour = cr.getInt(1);
                int mois = cr.getInt(2);
                int annee = cr.getInt(3);
                int heure = cr.getInt(4);
                int minute = cr.getInt(5);
                String evenement = cr.getString(6);
                int jour_evenement = cr.getInt(7);
                int mois_evenement = cr.getInt(8);
                int annee_evenement = cr.getInt(9);
                int heure_evenement = cr.getInt(10);
                int minute_evenement = cr.getInt(11);
                String commentaire = cr.getString(12);

                Sommeil sommeil = new Sommeil(jour, mois, annee, heure, minute, evenement,
                        jour_evenement, mois_evenement, annee_evenement, heure_evenement, minute_evenement,
                        commentaire);

                sommeil.setId(cr.getInt(0));
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, sommeil.afficher_previsualisation());
                    Log.d(TAG, "id : " + sommeil.getId());
                }
                sommeils.add(sommeil);
                i++;
            } while (cr.moveToPrevious() && i < nombre_sommeil);
        }
        cr.close();
        db.close();
        return sommeils;
    }



    public ArrayList<Glycemie> retrieve_last_glycemy(long nombre_glycemy)
    {
        ArrayList<Glycemie> gs = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cr = db.query(true, TABLE_GLYCEMIE_NAME, COLONNES_GLYCEMIE, "", null, "", "", _ID, "");
        cr.getCount();
        int i = 0;
        if(cr.moveToLast()) {
            do {
                int jour = cr.getInt(1);
                int mois = cr.getInt(2);
                int annee = cr.getInt(3);
                int heure = cr.getInt(4);
                int minute = cr.getInt(5);
                String glycemie = cr.getString(6);
                Glycemie g = new Glycemie(jour, mois, annee, heure, minute, glycemie);
                g.setId(cr.getInt(0));
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, g.afficher_previsualisation());
                    Log.d(TAG, "id : " + g.getId());
                }
                gs.add(g);
                i++;
            } while (cr.moveToPrevious() && i < nombre_glycemy);
        }
        cr.close();
        db.close();
        return gs;
    }


    public ArrayList<Poids> retrieve_last_weight(long nombre_weight)
    {
        ArrayList<Poids> ps = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cr = db.query(true, TABLE_POIDS_NAME, COLONNES_POIDS, "", null, "", "", _ID, "");
        cr.getCount();
        int i = 0;
        if(cr.moveToLast()) {
            do {
                int jour = cr.getInt(1);
                int mois = cr.getInt(2);
                int annee = cr.getInt(3);
                int heure = cr.getInt(4);
                int minute = cr.getInt(5);
                String poids = cr.getString(6);
                Poids p = new Poids(jour, mois, annee, heure, minute, poids);
                p.setId(cr.getInt(0));
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, p.afficher_previsualisation());
                    Log.d(TAG, "id : " + p.getId());
                }
                ps.add(p);
                i++;
            } while (cr.moveToPrevious() && i < nombre_weight);
        }
        cr.close();
        db.close();
        return ps;
    }

    public ArrayList<Alimentation> retrieve_last_al(long nombre_al)
    {
        ArrayList<Alimentation> als = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cr = db.query(true, TABLE_ALIMENTATION_NAME, COLONNES_ALIMENTATION, "", null, "", "", _ID, "");
        cr.getCount();
        int i = 0;
        if(cr.moveToLast()) {
            do {
                int jour = cr.getInt(1);
                int mois = cr.getInt(2);
                int annee = cr.getInt(3);
                int heure = cr.getInt(4);
                int minute = cr.getInt(5);
                String repas = cr.getString(6);
                String nourriture = cr.getString(7);
                Alimentation ap = new Alimentation(jour, mois, annee, heure, minute, repas, nourriture);
                ap.setId(cr.getInt(0));
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, ap.afficher_previsualisation());
                    Log.d(TAG, "id : " + ap.getId());
                }
                als.add(ap);
                i++;
            } while (cr.moveToPrevious() && i < nombre_al);
        }
        cr.close();
        db.close();
        return als;
    }

    public ArrayList<ActivitePhysique> retrieve_last_ap(long nombre_ap)
    {
        ArrayList<ActivitePhysique> aps = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cr = db.query(true, TABLE_ACTIVITE_PHYSIQUE_NAME, COLONNES_ACTIVITE_PHYSIQUE, "", null, "", "", _ID, "");
        cr.getCount();
        int i = 0;
        if(cr.moveToLast()) {
            do {
                int jour = cr.getInt(1);
                int mois = cr.getInt(2);
                int annee = cr.getInt(3);
                int heure = cr.getInt(4);
                int minute = cr.getInt(5);
                String sport = cr.getString(6);
                String duree = cr.getString(7);
                String difficulte_ressentie = cr.getString(8);
                ActivitePhysique ap = new ActivitePhysique(jour, mois, annee, heure, minute, sport, duree, difficulte_ressentie);
                ap.setId(cr.getInt(0));
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, ap.afficher_previsualisation());
                    Log.d(TAG, "id : " + ap.getId());
                }
                aps.add(ap);
                i++;
            } while (cr.moveToPrevious() && i < nombre_ap);
        }
        cr.close();
        db.close();
        return aps;
    }


    public ArrayList<PriseMedicament> retrieve_last_medicament(long nombre_medicament)
    {
        ArrayList<PriseMedicament> pms = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cr = db.query(true, TABLE_MEDICAMENT_NAME, COLONNES_MEDICAMENT, "", null, "", "", _ID, "");
        cr.getCount();
        int i = 0;
        if(cr.moveToLast()) {
            do {
                int jour = cr.getInt(1);
                int mois = cr.getInt(2);
                int annee = cr.getInt(3);
                int heure = cr.getInt(4);
                int minute = cr.getInt(5);
                String medicament = cr.getString(6);
                String dosage = cr.getString(7);
                PriseMedicament pm = new PriseMedicament(jour, mois, annee, heure, minute, medicament, dosage);
                pm.setId(cr.getInt(0));
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, pm.afficher_previsualisation());
                    Log.d(TAG, "id : " + pm.getId());
                }
                pms.add(pm);
                i++;
            } while (cr.moveToPrevious() && i < nombre_medicament);
        }
        cr.close();
        db.close();
        return pms;
    }

    public ArrayList<Situation> retrieve_last_situations(long nombre_entree)
    {
        ArrayList<Situation> situations = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cr = db.query(true, TABLE_ENREGISTREMENT_NAME, COLONNES, "", null, "", "", _ID, "");
        cr.getCount();
        int i = 0;
        if(cr.moveToLast()) {
            do {
                int jour = cr.getInt(1);
                int mois = cr.getInt(2);
                int annee = cr.getInt(3);
                int heure = cr.getInt(4);
                int minute = cr.getInt(5);
                String intensite = cr.getString(6);
                String situation = cr.getString(7);
                String emotions_sensation = cr.getString(8);
                String pensees = cr.getString(9);
                String taux_croyance= cr.getString(10);
                String pensee_alternative = cr.getString(11);
                String taux_croyance_actualise = cr.getString(12);
                String comportement = cr.getString(13);
                Situation e = new Situation(jour, mois, annee, heure, minute, intensite, situation,
                        emotions_sensation, pensees, comportement);
                e.setCroyances(taux_croyance, pensee_alternative, taux_croyance_actualise);
                e.setId(cr.getInt(0));
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, e.afficher_previsualisation());
                    Log.d(TAG, "id : " + e.getId());
                }
                situations.add(e);
                i++;
            } while (cr.moveToPrevious() && i < nombre_entree);
        }
        cr.close();
        db.close();
        return situations;


    }
    public Situation retrieve_one_situation(long id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Situation e = null;
        Cursor cr = db.query(true, TABLE_ENREGISTREMENT_NAME, COLONNES, _ID+" = "+id, null, "", "", "", "");
        if(cr.moveToFirst()) {
            int jour = cr.getInt(1);
            int mois = cr.getInt(2);
            int annee = cr.getInt(3);
            int heure = cr.getInt(4);
            int minute = cr.getInt(5);
            String intensite = cr.getString(6);
            String situation = cr.getString(7);
            String emotions_sensation = cr.getString(8);
            String pensees = cr.getString(9);
            String taux_croyance= cr.getString(10);
            String pensee_alternative = cr.getString(11);
            String taux_croyance_actualise = cr.getString(12);
            String comportement = cr.getString(13);

            e = new Situation(jour, mois, annee, heure, minute, intensite, situation,
                    emotions_sensation, pensees, comportement);
            e.setId(cr.getInt(0));
            e.setCroyances(taux_croyance, pensee_alternative, taux_croyance_actualise);
            if(BuildConfig.DEBUG) {
                Log.d(TAG, e.afficher_previsualisation());
                Log.d(TAG, "id : " + e.getId());
            }
        }
        cr.close();
        db.close();
        return e;
    }


    public PriseMedicament retrieve_one_prise_medicament(long id)
    {
        SQLiteDatabase db = getReadableDatabase();
        PriseMedicament pm = null;
        Cursor cr = db.query(true, TABLE_MEDICAMENT_NAME, COLONNES_MEDICAMENT, _ID + " = " + id, null, "", "", "", "");
        if(cr.moveToFirst()) {
            int jour = cr.getInt(1);
            int mois = cr.getInt(2);
            int annee = cr.getInt(3);
            int heure = cr.getInt(4);
            int minute = cr.getInt(5);
            String medicament = cr.getString(6);
            String dosage = cr.getString(7);

            pm = new PriseMedicament(jour, mois, annee, heure, minute, medicament, dosage);

            pm.setId(cr.getInt(0));
            if(BuildConfig.DEBUG)
            {
            Log.d(TAG, pm.afficher_previsualisation());
            Log.d(TAG, "id : " + pm.getId());
            }
        }
        cr.close();
        db.close();
        return pm;
    }

    public ActivitePhysique retrieve_one_activite_physique(long id)
    {
        SQLiteDatabase db = getReadableDatabase();
        ActivitePhysique ap = null;
        Cursor cr = db.query(true, TABLE_ACTIVITE_PHYSIQUE_NAME, COLONNES_ACTIVITE_PHYSIQUE, _ID + " = " + id, null, "", "", "", "");
        if(cr.moveToFirst()) {
            int jour = cr.getInt(1);
            int mois = cr.getInt(2);
            int annee = cr.getInt(3);
            int heure = cr.getInt(4);
            int minute = cr.getInt(5);
            String sport = cr.getString(6);
            String duree = cr.getString(7);
            String difficulte_ressentie = cr.getString(8);

            ap = new ActivitePhysique(jour, mois, annee, heure, minute, sport, duree, difficulte_ressentie);

            ap.setId(cr.getInt(0));
            if(BuildConfig.DEBUG)
            {
                Log.d(TAG, ap.afficher_previsualisation());
                Log.d(TAG, "id : " + ap.getId());
            }
        }
        cr.close();
        db.close();
        return ap;
    }

    public Alimentation retrieve_one_alimentation(long id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Alimentation al = null;
        Cursor cr = db.query(true, TABLE_ALIMENTATION_NAME, COLONNES_ALIMENTATION, _ID + " = " + id, null, "", "", "", "");
        if(cr.moveToFirst()) {
            int jour = cr.getInt(1);
            int mois = cr.getInt(2);
            int annee = cr.getInt(3);
            int heure = cr.getInt(4);
            int minute = cr.getInt(5);
            String repas = cr.getString(6);
            String nourriture = cr.getString(7);

            al = new Alimentation(jour, mois, annee, heure, minute, repas, nourriture);

            al.setId(cr.getInt(0));
            if(BuildConfig.DEBUG)
            {
                Log.d(TAG, al.afficher_previsualisation());
                Log.d(TAG, "id : " + al.getId());
            }
        }
        cr.close();
        db.close();
        return al;
    }

    public Glycemie retrieve_one_glycemie(long id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Glycemie gl = null;
        Cursor cr = db.query(true, TABLE_GLYCEMIE_NAME, COLONNES_GLYCEMIE, _ID + " = " + id, null, "", "", "", "");
        if(cr.moveToFirst()) {
            int jour = cr.getInt(1);
            int mois = cr.getInt(2);
            int annee = cr.getInt(3);
            int heure = cr.getInt(4);
            int minute = cr.getInt(5);
            String glycemie_valeur = cr.getString(6);

            gl = new Glycemie(jour, mois, annee, heure, minute, glycemie_valeur);

            gl.setId(cr.getInt(0));
            if(BuildConfig.DEBUG)
            {
                Log.d(TAG, gl.afficher_previsualisation());
                Log.d(TAG, "id : " + gl.getId());
            }
        }
        cr.close();
        db.close();
        return gl;
    }

    public Poids retrieve_one_poids(long id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Poids poids = null;
        Cursor cr = db.query(true, TABLE_POIDS_NAME, COLONNES_POIDS, _ID + " = " + id, null, "", "", "", "");
        if(cr.moveToFirst()) {
            int jour = cr.getInt(1);
            int mois = cr.getInt(2);
            int annee = cr.getInt(3);
            int heure = cr.getInt(4);
            int minute = cr.getInt(5);
            String poids_valeur = cr.getString(6);

            poids = new Poids(jour, mois, annee, heure, minute, poids_valeur);

            poids.setId(cr.getInt(0));
            if(BuildConfig.DEBUG)
            {
                Log.d(TAG, poids.afficher_previsualisation());
                Log.d(TAG, "id : " + poids.getId());
            }
        }
        cr.close();
        db.close();
        return poids;
    }


    public Sommeil retrieve_one_sommeil(long id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Sommeil sommeil = null;
        Cursor cr = db.query(true, TABLE_SOMMEIL_NAME, COLONNES_SOMMEIL, _ID + " = " + id, null, "", "", "", "");
        if(cr.moveToFirst()) {
            int jour = cr.getInt(1);
            int mois = cr.getInt(2);
            int annee = cr.getInt(3);
            int heure = cr.getInt(4);
            int minute = cr.getInt(5);
            String evenement = cr.getString(6);
            int jour_evenement = cr.getInt(7);
            int mois_evenement = cr.getInt(8);
            int annee_evenement = cr.getInt(9);
            int heure_evenement = cr.getInt(10);
            int minute_evenement = cr.getInt(11);
            String commentaire = cr.getString(12);

            sommeil = new Sommeil(jour, mois, annee, heure, minute, evenement, jour_evenement, mois_evenement, annee_evenement, heure_evenement, minute_evenement,
            commentaire);

            sommeil.setId(cr.getInt(0));
            if(BuildConfig.DEBUG)
            {
                Log.d(TAG, sommeil.afficher_previsualisation());
                Log.d(TAG, "id : " + sommeil.getId());
            }
        }
        cr.close();
        db.close();
        return sommeil;
    }


    public void delete_situation(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        int n_deleted = db.delete(TABLE_ENREGISTREMENT_NAME, _ID + " = ?", new String[]{Long.toString(id)});
        if(BuildConfig.DEBUG)
            Log.d(TAG, "delete_situation(): id=" + id + "-> " + n_deleted);
    }

    public void delete_pm(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        int n_deleted = db.delete(TABLE_MEDICAMENT_NAME, _ID + " = ?", new String[] {Long.toString(id)});
        if(BuildConfig.DEBUG)
            Log.d(TAG, "delete_pm(): id="+id+"-> "+n_deleted);
    }
    public void delete_ap(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        int n_deleted = db.delete(TABLE_ACTIVITE_PHYSIQUE_NAME, _ID + " = ?", new String[] {Long.toString(id)});
        if(BuildConfig.DEBUG)
            Log.d(TAG, "delete_ap(): id="+id+"-> "+n_deleted);
    }
    public void delete_alimentation(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        int n_deleted = db.delete(TABLE_ALIMENTATION_NAME, _ID + " = ?", new String[] {Long.toString(id)});
        if(BuildConfig.DEBUG)
            Log.d(TAG, "delete_alimentation(): id="+id+"-> "+n_deleted);
    }
    public void delete_poids(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        int n_deleted = db.delete(TABLE_POIDS_NAME, _ID + " = ?", new String[] {Long.toString(id)});
        if(BuildConfig.DEBUG)
            Log.d(TAG, "delete_poids(): id="+id+"-> "+n_deleted);
    }
    public void delete_glycemie(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        int n_deleted = db.delete(TABLE_GLYCEMIE_NAME, _ID + " = ?", new String[] {Long.toString(id)});
        if(BuildConfig.DEBUG)
            Log.d(TAG, "delete_glycemie(): id="+id+"-> "+n_deleted);
    }
    public void delete_sommeil(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        int n_deleted = db.delete(TABLE_SOMMEIL_NAME, _ID + " = ?", new String[] {Long.toString(id)});
        if(BuildConfig.DEBUG)
            Log.d(TAG, "delete_sommeil(): id="+id+"-> "+n_deleted);
    }

    /* Mises-à-jour

     */
    public void update_situation(long id, Situation situation)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        ObservationDate obd = situation.getObd();
        values.put("jour", obd.s_jour);
        values.put("mois", obd.s_mois);
        values.put("annee", obd.s_annee);
        values.put("heure", obd.s_heure);
        values.put("minute", obd.s_minute);
        values.put("intensite", situation.getIntensite());
        values.put("situation", situation.getSituation());
        values.put("emotions_sensations", situation.getEmotions_sensation());
        values.put("pensees", situation.getPensees());
        values.put("taux_croyance", situation.getTaux_croyance());
        values.put("pensee_alternative", situation.getPensee_alternative());
        values.put("taux_croyance_actualise", situation.getTaux_croyance_actualise());
        values.put("comportement", situation.getComportement());

        db.update(TABLE_ENREGISTREMENT_NAME, values, _ID + " = ?", new String[]{Long.toString(id)});
        db.close();
    }

    public void update_pm(long id, PriseMedicament pm)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        ObservationDate obd = pm.getObd();
        values.put("jour", obd.s_jour);
        values.put("mois", obd.s_mois);
        values.put("annee", obd.s_annee);
        values.put("heure", obd.s_heure);
        values.put("minute", obd.s_minute);
        values.put("medicament", pm.getMedicament());
        values.put("dosage", pm.getDosage());

        db.update(TABLE_MEDICAMENT_NAME, values, _ID + " = ?", new String[] {Long.toString(id)});
        db.close();
    }

    public void update_ap(long id, ActivitePhysique activitePhysique)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        ObservationDate obd = activitePhysique.getObd();
        values.put("jour", obd.s_jour);
        values.put("mois", obd.s_mois);
        values.put("annee", obd.s_annee);
        values.put("heure", obd.s_heure);
        values.put("minute", obd.s_minute);
        values.put("sport", activitePhysique.getSport());
        values.put("duree", activitePhysique.getDuree());
        values.put("difficulte_ressentie", activitePhysique.getDifficulte_ressentie());

        db.update(TABLE_ACTIVITE_PHYSIQUE_NAME, values, _ID + " = ?", new String[] {Long.toString(id)});
        db.close();
    }

    public void update_alimentation(long id, Alimentation alimentation)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        ObservationDate obd = alimentation.getObd();
        values.put("jour", obd.s_jour);
        values.put("mois", obd.s_mois);
        values.put("annee", obd.s_annee);
        values.put("heure", obd.s_heure);
        values.put("minute", obd.s_minute);
        values.put("repas", alimentation.getRepas());
        values.put("nourriture", alimentation.getNourriture());

        db.update(TABLE_ALIMENTATION_NAME, values, _ID + " = ?", new String[] {Long.toString(id)});
        db.close();
    }
    public void update_glycemie(long id, Glycemie glycemie)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        ObservationDate obd = glycemie.getObd();
        values.put("jour", obd.s_jour);
        values.put("mois", obd.s_mois);
        values.put("annee", obd.s_annee);
        values.put("heure", obd.s_heure);
        values.put("minute", obd.s_minute);
        values.put("glycemie", glycemie.getGlycemie());

        db.update(TABLE_GLYCEMIE_NAME, values, _ID + " = ?", new String[] {Long.toString(id)});
        db.close();
    }
    public void update_poids(long id, Poids poids)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        ObservationDate obd = poids.getObd();
        values.put("jour", obd.s_jour);
        values.put("mois", obd.s_mois);
        values.put("annee", obd.s_annee);
        values.put("heure", obd.s_heure);
        values.put("minute", obd.s_minute);
        values.put("poids", poids.getPoids());

        db.update(TABLE_POIDS_NAME, values, _ID + " = ?", new String[] {Long.toString(id)});
        db.close();
    }
    public void update_sommeil(long id, Sommeil sommeil)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        ObservationDate obd = sommeil.getObd();
        values.put("jour", obd.s_jour);
        values.put("mois", obd.s_mois);
        values.put("annee", obd.s_annee);
        values.put("heure", obd.s_heure);
        values.put("minute", obd.s_minute);

        ObservationDate evenement_obs = sommeil.getEvenement_obd();
        values.put("jour_coucher", evenement_obs.s_jour);
        values.put("mois_coucher", evenement_obs.s_mois);
        values.put("annee_coucher", evenement_obs.s_annee);
        values.put("heure_coucher", evenement_obs.s_heure);
        values.put("minute_coucher", evenement_obs.s_minute);

        values.put("evenement", sommeil.getEvenement());

        values.put("commentaire", sommeil.getCommentaire());

        db.update(TABLE_SOMMEIL_NAME, values, _ID + " = ?", new String[] {Long.toString(id)});
        db.close();
    }
}
