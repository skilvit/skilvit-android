package skilvit.fr.data_manager;

/**
 * Created by Clément on 04/03/2018.
 */
public class Sommeil extends Mesure{
    private int id;
    private ObservationDate obd;
    private ObservationDate evenement_obd;
    private String evenement;
    private String commentaire;


    public Sommeil(String s_date, String s_heure, String evenement,
                   String s_date_evenement, String s_heure_evenement, String commentaire)
    {
        super(s_date, s_heure);
        this.obd = super.obd;
        this.evenement = evenement;
        this.evenement_obd = new ObservationDate();
        this.evenement_obd.modify_hour(s_heure_evenement);
        this.evenement_obd.modifyDate(s_date_evenement);
        this.commentaire = commentaire;
    }
//    public Sommeil(int jour, int mois, int annee, int heure, int minute, String poids)
//    {
//        super(jour, mois, annee, heure, minute);
//        this.obd = super.obd;
//        this.poids = poids;
//    }

    public Sommeil(int jour, int mois, int annee, int heure, int minute,
                   String evenement,
                   int jour_evenement, int mois_evenement, int annee_evenement, int heure_evenement, int minute_evenement,
                   String commentaire)
    {
        super(jour, mois, annee, heure, minute);
        this.obd = super.obd;
        this.evenement_obd = new ObservationDate(jour_evenement, mois_evenement, annee_evenement, heure_evenement, minute_evenement);
        this.evenement = evenement;
        this.commentaire = commentaire;
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
        return this.obd.toString()+" "+this.evenement.toString()+" "+this.evenement_obd+" "+this.commentaire;
    }

    public String toString()
    {
        return this.obd.toString()+"\n"+this.evenement.toString()+"\n"+this.evenement_obd+"\n"+this.commentaire;
    }

    public ObservationDate getObd() {
        return obd;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public ObservationDate getEvenement_obd() {
        return evenement_obd;
    }

    public void setEvenement_obd(ObservationDate evenement_obd) {
        this.evenement_obd = evenement_obd;
    }


    public String getEvenement() {
        return evenement;
    }

    public void setEvenement(String evenement) {
        this.evenement = evenement;
    }

    public String toCSVLine()
    {
        return super.toCSVLine()+ Mesure.CSV_SEPARATOR + evenement + CSV_SEPARATOR + evenement_obd.toString() + CSV_SEPARATOR + commentaire;
    }
}
