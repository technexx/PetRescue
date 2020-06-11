package technexx.android.petrescue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.contact_fragment, container, false);

        TextView contact_one = root.findViewById(R.id.contact_one);
        TextView contact_two = root.findViewById(R.id.contact_two);
        TextView contact_three = root.findViewById(R.id.contact_three);
        TextView contact_four = root.findViewById(R.id.contact_four);


        Bundle args = getArguments();
        String shelter = null;

        if (args != null) {
            shelter = args.getString("shelter");
        }

        if (shelter.equals("ev")) {
            contact_one.setText(getString(R.string.loc_eastValley));
            contact_two.setText(getString(R.string.eastValley_address));
        }
        if (shelter.equals("wLA")) {
            contact_one.setText(getString(R.string.loc_westLA));
            contact_two.setText(getString(R.string.westLA_address));
        }
        if (shelter.equals("sLA")) {
            contact_one.setText(getString(R.string.loc_southLA));
            contact_two.setText(getString(R.string.southLA_address));
        }
        if (shelter.equals("har")) {
            contact_one.setText(getString(R.string.loc_harbor));
            contact_two.setText(getString(R.string.harbor_address));
        }

        contact_three.setText(getString(R.string.shelter_hours));
        contact_four.setText(getString(R.string.shelter_phone));

        return root;
    }
}