package skilvit.fr.data_manager;

public class MiniEntree {
    String date;
    String heure;
    String sens_emot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public MiniEntree(String date, String heure, String sens_emot, int id)
    {
        this.date = date;
        this.heure = heure;
        this.sens_emot = sens_emot;
        this.id = id;
    }



}