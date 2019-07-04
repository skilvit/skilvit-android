package skilvit.fr.data_manager;

/**
 * Created by Clément on 04/03/2018.
 */
public class Poids extends Mesure{
    private int id;
    private ObservationDate obd;
    private String poids;

    public Poids(String s_date, String s_heure, String poids)
    {
        super(s_date, s_heure);
        this.obd = super.obd;
        this.poids = poids;
    }
    public Poids(int jour, int mois, int annee, int heure, int minute, String poids)
    {
        super(jour, mois, annee, heure, minute);
        this.obd = super.obd;
        this.poids = poids;
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
        return this.obd.toString()+" "+this.poids;
    }

    public String toString()
    {
        return this.obd.toString()+"\n"+this.poids;
    }

    public ObservationDate getObd() {
        return obd;
    }

    public String getPoids() {
        return poids;
    }

    public String toCSVLine()
    {
        return super.toCSVLine()+ Mesure.CSV_SEPARATOR + poids;
    }
}
