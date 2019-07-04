package skilvit.fr.data_manager;

/**
 * Created by Clément on 04/03/2018.
 */
public class Alimentation extends Mesure{

    private int id;
    private ObservationDate obd;
    private String repas;
    private String nourriture;

    public Alimentation(String s_date, String s_heure, String repas, String nourriture) {
        super(s_date, s_heure);
        this.obd = super.obd;
        this.repas = repas;
        this.nourriture = nourriture;
    }
    public Alimentation(int jour, int mois, int annee, int heure, int minute, String repas, String nourriture)
    {
        super(jour, mois, annee, heure, minute);
        this.obd = super.obd;
        this.repas = repas;
        this.nourriture = nourriture;
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
        return this.obd.toString()+" "+this.repas+ " "+this.nourriture;
    }

    public String toString()
    {
        return this.obd.toString()+"\n"+this.repas+"\n"+this.nourriture;
    }

    public ObservationDate getObd() {
        return obd;
    }

    public String getRepas() {
        return repas;
    }

    public String getNourriture() {
        return nourriture;
    }


    public String toCSVLine()
    {
        return super.toCSVLine()+ Mesure.CSV_SEPARATOR + repas +
                Mesure.CSV_SEPARATOR+ nourriture ;
    }
}
