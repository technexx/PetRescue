package technexx.android.petrescue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.menu_fragment, container, false);

        Button dogs = root.findViewById(R.id.dogs);
        Button cats = root.findViewById(R.id.cats);
        Button others = root.findViewById(R.id.others);

        return  root;
    }
}
