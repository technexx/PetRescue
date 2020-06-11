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

        Bundle args = getArguments();
        String shelter = null;

        if (args != null) {
            shelter = args.getString("shelter");
        }

        if (shelter.equals("ev")) {
            contact_one.setText(getString(R.string.eastValley_contact));
        }
        if (shelter.equals("wLA")) {
            contact_one.setText(getString(R.string.westLA_contact));
        }
        if (shelter.equals("sLA")) {
            contact_one.setText(getString(R.string.southLA_contact));
        }
        if (shelter.equals("har")) {
            contact_one.setText(getString(R.string.harbor_contact));
        }

        return root;
    }
}