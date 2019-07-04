package skilvit.fr.data_manager;

/**
 * Created by Clément on 04/08/2017.
 */
public class PriseMedicament extends Mesure {
    private int id;

    public int getId() {
        return id;
    }

    private ObservationDate obd;

    public ObservationDate getObd() {
        return obd;
    }

    public void setObd(ObservationDate obd) {
        this.obd = obd;
    }

    private String medicament;

    public String getMedicament() {
        return medicament;
    }

    public void setMedicament(String medicament) {
        this.medicament = medicament;
    }

    private String dosage;

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public PriseMedicament(String s_date, String s_heure, String medicament, String dosage)
    {
        super(s_date, s_heure);
        this.obd = super.obd;
        this.medicament = medicament;
        this.dosage = dosage;
    }

    public PriseMedicament(int jour, int mois, int annee, int heure, int minute, String medicament, String dosage)
    {
        super(jour, mois, annee, heure, minute);
        this.obd = super.obd;
        this.medicament = medicament;
        this.dosage = dosage;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public int getId(int id)
    {
        return this.id;
    }

    public String afficher_previsualisation()
    {
        return this.obd.toString()+" "+this.medicament+ " "+this.dosage;
    }

    public String toString()
    {
        return this.obd.toString()+"\n"+this.medicament+"\n"+this.dosage;
    }

    public String toCSVLine()
    {
        return super.toCSVLine()+ Mesure.CSV_SEPARATOR + medicament
                + CSV_SEPARATOR + dosage;
    }


}
