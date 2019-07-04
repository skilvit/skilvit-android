package skilvit.fr.data_manager;

/**
 * Created by Clément on 04/03/2018.
 */
public class Glycemie extends Mesure{
    private int id;
    private ObservationDate obd;
    private String glycemie;

    public Glycemie(String s_date, String s_heure, String glycemie)
    {
        super(s_date, s_heure);
        this.obd = super.obd;
        this.glycemie = glycemie;

    }
    public Glycemie(int jour, int mois, int annee, int heure, int minute, String glycemie)
    {
        super(jour, mois, annee, heure, minute);
        this.obd = super.obd;
        this.glycemie = glycemie;

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
        return this.obd.toString()+" "+this.glycemie;
    }

    public String toString()
    {
        return this.obd.toString()+"\n"+this.glycemie;
    }

    public ObservationDate getObd() {
        return obd;
    }

    public String getGlycemie() {
        return glycemie;
    }

    public String toCSVField()
    {
        return super.toCSVLine()+ Mesure.CSV_SEPARATOR + glycemie;
    }
}
