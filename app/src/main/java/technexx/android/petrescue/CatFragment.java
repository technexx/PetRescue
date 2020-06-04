package technexx.android.petrescue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class CatFragment extends Fragment {

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.cat_fragment, container, false);

        Button allCats = root.findViewById(R.id.all_cats);
        Button westValley = root.findViewById(R.id.westValley);
        Button eastValley = root.findViewById(R.id.eastValley);
        Button westLA = root.findViewById(R.id.westLA);
        Button southLA = root.findViewById(R.id.southLA);
        Button northCentral = root.findViewById(R.id.northCentral);
        Button harbor = root.findViewById(R.id.harbor);


        return  root;
    }
}
