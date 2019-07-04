package skilvit.fr.data_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import skilvit.fr.R;

/**
 * Created by Clément on 23/07/2017.
 */
public class MiniEntreeAdapter extends ArrayAdapter<MiniEntree> {
    private static final  String TAG = MiniEntreeAdapter.class.getSimpleName();

    public MiniEntreeAdapter(Context context, ArrayList<MiniEntree> entrees) {
        super(context, 0, entrees);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_mini_entree, parent, false);
        }//convertView.setClickable(true);
        MiniEntreeVueTeneur vue_teneur = (MiniEntreeVueTeneur) convertView.getTag();
        if (vue_teneur == null) {
            vue_teneur = new MiniEntreeVueTeneur();

            vue_teneur.mini_date = (TextView) convertView.findViewById(R.id.mini_date_vue);
            vue_teneur.mini_heure = (TextView) convertView.findViewById(R.id.mini_heure_vue);
            vue_teneur.mini_sens_emot = (TextView) convertView.findViewById(R.id.mini_sens_emot_vue);
            convertView.setTag(vue_teneur);
        }

        MiniEntree mini_entree = getItem(position);

        vue_teneur.mini_date.setText(mini_entree.date);
        vue_teneur.mini_heure.setText(mini_entree.heure);
        vue_teneur.mini_sens_emot.setText(mini_entree.sens_emot);

        return convertView;
    }

    private class MiniEntreeVueTeneur{
        public TextView mini_date;
        public TextView mini_heure;
        public TextView mini_sens_emot;
    }
}