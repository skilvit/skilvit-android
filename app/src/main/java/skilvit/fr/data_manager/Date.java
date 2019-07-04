package skilvit.fr.data_manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Clément on 20/02/2018.
 */
public class Date {
    static public boolean isValidDate(String s_date)
    {
        Pattern p = Pattern.compile("^[0-3]?[0-9]/[0-1]?[1-9]/20[0-9][0-9]$");
        Matcher m = p.matcher(s_date);
        return m.find();
    }
}
