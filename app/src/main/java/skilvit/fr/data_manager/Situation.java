package skilvit.fr.data_manager;

/**
 * Created by Clément on 21/07/2017.
 */
public class Situation extends Mesure {
    int id;

    public int getId() {
        return id;
    }

    ObservationDate obd;

    public ObservationDate getObd() {
        return obd;
    }

    public void setObd(ObservationDate obd) {
        this.obd = obd;
    }

    private String intensite;
    public String getIntensite() {
        return intensite;
    }

    public void setIntensite(String intensite) {
        this.intensite = intensite;
    }

    private String situation;

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    private String emotions_sensation;

    public String getEmotions_sensation() {
        return emotions_sensation;
    }

    public void setEmotions_sensation(String emotions_sensation) {
        this.emotions_sensation = emotions_sensation;
    }

    private String pensees;

    public String getPensees() {
        return pensees;
    }

    public void setPensees(String pensees) {
        this.pensees = pensees;
    }

    private String taux_croyance = "";

    public String getTaux_croyance() {
        return taux_croyance;
    }

    public void setTaux_croyance(String taux_croyance) {
        this.taux_croyance = taux_croyance;
    }

    private String pensee_alternative = "";

    public String getPensee_alternative() {
        return pensee_alternative;
    }

    public void setPensee_alternative(String pensee_alternative) {
        this.pensee_alternative = pensee_alternative;
    }

    private String taux_croyance_actualise = "";

    public String getTaux_croyance_actualise() {
        return taux_croyance_actualise;
    }

    public void setTaux_croyance_actualise(String taux_croyance_actualise) {
        this.taux_croyance_actualise = taux_croyance_actualise;
    }

    private String comportement;

    public String getComportement() {
        return comportement;
    }

    public void setComportement(String comportement) {
        this.comportement = comportement;
    }

    public Situation(String s_date, String s_heure, String intensite, String situation, String emotions_sensation, String pensees, String comportement) {
        super(s_date, s_heure);
        this.obd = super.obd;
        this.intensite = intensite;
        this.situation = situation;
        this.emotions_sensation = emotions_sensation;
        this.pensees = pensees;
        this.comportement = comportement;
    }
    public Situation(int jour, int mois, int annee, int heure, int minute, String intensite, String situation, String emotions_sensation, String pensees, String comportement) {
        super(jour, mois, annee, heure, minute);
        this.obd = super.obd;
        this.situation = situation;
        this.intensite = intensite;
        this.emotions_sensation = emotions_sensation;
        this.pensees = pensees;
        this.comportement = comportement;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setCroyances(String taux_croyance, String pensee_alternative,
                             String taux_croyance_actualise)
    {
        this.taux_croyance = taux_croyance;
        this.pensee_alternative = pensee_alternative;
        this.taux_croyance_actualise = taux_croyance_actualise;
    }
    public String afficher_previsualisation()
    {
        return this.obd.toString()+" "+this.emotions_sensation;
    }

    public String toString()
    {
        return this.obd.toString()+"\n"+this.situation+"\n"+this.intensite+"\n"
                +this.emotions_sensation+"\n"+this.pensees+"\n"+this.comportement;
    }

    public String toCSVLine()
    {
        return super.toCSVLine()+ Mesure.CSV_SEPARATOR + situation
                + CSV_SEPARATOR + intensite+ CSV_SEPARATOR +
                emotions_sensation+ Mesure.CSV_SEPARATOR + pensees
                + CSV_SEPARATOR + comportement + CSV_SEPARATOR + taux_croyance+ Mesure.CSV_SEPARATOR +
                pensee_alternative+ CSV_SEPARATOR + taux_croyance_actualise;
    }
}
