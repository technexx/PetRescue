package technexx.android.petrescue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DisplayFragment extends Fragment {

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.display_fragment, container, false);

        RecyclerView animalRecycler = root.findViewById(R.id.pet_recycler);

//        PetListAdapter petListAdapter = new PetListAdapter()

        return root;
    }
}
