package technexx.android.petrescue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.contact_fragment, container, false);

        TextView contact = root.findViewById(R.id.contact);
        contact.setText(R.string.dogs);
        return root;
    }
}