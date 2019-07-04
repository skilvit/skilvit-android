package skilvit.fr.data_manager;

import java.io.Serializable;

/**
 * Created by Clément on 21/07/2017.
 */


public class ObservationDate implements Serializable {
    public int jour;
    public int mois;
    public int annee;
    public int heure;
    public int minute;
    public String s_jour;
    public String s_mois;
    public String s_annee;
    public String s_heure;
    public String s_minute;

    public ObservationDate()
    {
        this.jour = 1;
        this.mois = 1;
        this.annee = 2000;
        this.heure = 1;
        this.minute = 1;
        update_string_values();
    }
    public ObservationDate(int jour, int mois, int annee, int heure, int minute) {
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
        this.heure = heure;
        this.minute = minute;
        update_string_values();
    }

    public ObservationDate getPreviousDay() {
        ObservationDate previous_day;
        if (jour == 1 && mois == 1) {
            previous_day = new ObservationDate(31, 12, annee - 1, heure, minute);
        } else if (jour == 1 && (mois == 5 || mois == 7 || mois == 8 || mois == 10 || mois == 12)) {
            previous_day = new ObservationDate(30, mois - 1, annee, heure, minute);
        } else if (jour == 1 && mois == 3 && annee % 4 == 0) {
            previous_day = new ObservationDate(29, mois - 1, annee, heure, minute);
        } else if (jour == 1 && mois == 3 && annee % 4 != 0) {
            previous_day = new ObservationDate(28, mois - 1, annee, heure, minute);
        } else if (jour == 1 && (mois == 4 || mois == 6 || mois == 9 || mois == 11)) {
            previous_day = new ObservationDate(31, mois - 1, annee, heure, minute);
        } else {
            previous_day = new ObservationDate(jour - 1, mois, annee, heure, minute);
        }
        return previous_day;
    }

    public ObservationDate getNextDay() {
        ObservationDate next_day;
        if (jour == 31 && mois == 12) {
            next_day = new ObservationDate(1, 1, annee + 1, heure, minute);
        } else if (jour == 31 && (mois == 1 || mois == 3 || mois == 5 || mois == 7 || mois == 8 || mois == 10)) {
            next_day = new ObservationDate(1, mois + 1, annee, heure, minute);
        } else if (jour == 28 && mois == 2 && annee % 4 == 0) {
            next_day = new ObservationDate(29, mois, annee, heure, minute);
        } else if (jour == 28 && mois == 2 && annee % 4 != 0) {
            next_day = new ObservationDate(1, mois + 1, annee, heure, minute);
        } else if (jour == 29 && mois == 2 && annee % 4 == 0) {
            next_day = new ObservationDate(1, mois + 1, annee, heure, minute);
        } else if (jour == 30 && (mois == 4 || mois == 6 || mois == 9 || mois == 11)) {
            next_day = new ObservationDate(1, mois + 1, annee, heure, minute);
        } else {
            next_day = new ObservationDate(jour + 1, mois, annee, heure, minute);
        }
        return next_day;
    }

    private void update_string_values() {
        this.s_jour = Integer.toString(jour);
        this.s_mois = Integer.toString(mois);
        this.s_annee = Integer.toString(annee);
        this.s_heure = Integer.toString(heure);
        this.s_minute = Integer.toString(minute);
    }

    public String getDate()
    {
        return s_jour + "/" + s_mois + "/" + s_annee;
    }

    public String getHeure()
    {
        return s_heure+":"+s_minute;
    }

    public String toString() {
        return s_heure+":"+s_minute+" "+s_jour + "/" + s_mois + "/" + s_annee;
    }

    public void modifyDate(String string) {
        String[] res = string.split("/");
        this.jour = Integer.parseInt(res[0]);
        this.mois = Integer.parseInt(res[1]);
        this.annee = Integer.parseInt(res[2]);
        update_string_values();
    }

    public void modify_hour(String string)
    {
        String[] res = string.split(":");
        this.heure = Integer.parseInt(res[0]);
        this.minute = Integer.parseInt(res[1]);
        update_string_values();
    }

    public int compareTo(ObservationDate autre)
    {
        if(est_anterieur_a(autre))
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public boolean est_anterieur_a(ObservationDate autre)
    {
        if(this.annee < autre.annee)
        {
            return true;
        }
        else if(this.annee > autre.annee)
        {
            return false;
        }
        else
        {
            if(this.mois < autre.mois)
            {
                return true;
            }
            else if(this.mois > autre.mois)
            {
                return false;
            }
            else
            {
                if(this.jour < autre.jour)
                {
                    return true;
                }
                else if(this.jour > autre.jour)
                {
                    return false;
                }
                else
                {
                    if(this.heure < autre.heure)
                    {
                        return true;
                    }
                    else if (this.heure > autre.heure)
                    {
                        return false;
                    }
                    else
                    {
                        return this.minute < autre.minute;
                    }
                }
            }
        }
    }
}