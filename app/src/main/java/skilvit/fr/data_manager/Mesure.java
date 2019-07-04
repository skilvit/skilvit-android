package skilvit.fr.data_manager;

/**
 * Created by Clément on 05/08/2017.
 */
public class Mesure {
    int id;
    ObservationDate obd;
    public static String CSV_SEPARATOR = "\t";
    Mesure(ObservationDate obd)
    {
        this.obd = obd;
    }
    Mesure(String s_date, String s_heure)
    {
        this.obd = new ObservationDate();
        this.obd.modify_hour(s_heure);
        this.obd.modifyDate(s_date);
    }
    Mesure(int jour, int mois, int annee, int heure, int minute)
    {
        this.obd = new ObservationDate(jour, mois, annee, heure, minute);
    }

    public ObservationDate getObd() {
        return obd;
    }
    public String afficher_previsualisation()
    {
        return "";
    }

    public String toCSVLine(){
        return id + CSV_SEPARATOR + obd.toString();
    }

}
