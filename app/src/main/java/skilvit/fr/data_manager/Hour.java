package skilvit.fr.data_manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Clément on 20/02/2018.
 */
public class Hour {
    static public boolean isValidHour(String s_hour)
    {
        Pattern p = Pattern.compile("^[0-2]?[0-9]:[0-5]?[0-9]$");
        Matcher m = p.matcher(s_hour);
        return m.find();
    }
}
