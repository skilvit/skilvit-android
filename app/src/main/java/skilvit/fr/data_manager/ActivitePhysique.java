package skilvit.fr.data_manager;

/**
 * Created by Clément on 04/03/2018.
 */
public class ActivitePhysique extends Mesure{
    private int id;
    private ObservationDate obd;
    private String sport;
    private String duree; // en minutes ça serait le mieux
    private String difficulte_ressentie;
    public ActivitePhysique(String s_date, String s_heure, String sport, String duree, String difficulte_ressentie)
    {
        super(s_date, s_heure);
        this.obd = super.obd;
        this.sport = sport;
        this.duree = duree;
        this.difficulte_ressentie = difficulte_ressentie;
    }
    public ActivitePhysique(int jour, int mois, int annee, int heure, int minute, String sport, String duree, String difficulte_ressentie)
    {
        super(jour, mois, annee, heure, minute);
        this.obd = super.obd;
        this.sport = sport;
        this.duree = duree;
        this.difficulte_ressentie = difficulte_ressentie;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return this.id;
    }
    public String afficher_previsualisation()
    {
        return this.obd.toString()+" "+this.sport+ " "+this.duree+" "+this.difficulte_ressentie;
    }
    public String toString()
    {
        return this.obd.toString()+"\n"+this.sport+"\n"+this.duree+"\n"+this.difficulte_ressentie;
    }

    public ObservationDate getObd() {
        return obd;
    }

    public String getSport() {
        return sport;
    }

    public String getDuree() {
        return duree;
    }

    public String getDifficulte_ressentie() {
        return difficulte_ressentie;
    }

    public String toCSVLine()
    {
        return super.toCSVLine()+ Mesure.CSV_SEPARATOR + sport + CSV_SEPARATOR + duree + CSV_SEPARATOR + difficulte_ressentie;
    }
}
