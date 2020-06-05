package technexx.android.petrescue;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DisplayFragment extends Fragment {

    private ArrayList<String> imageList;
    private ArrayList<String> idList;
    private ArrayList<String> nameList;
    private ArrayList<String> breedList;
    private ArrayList<String> weightList;
    private ArrayList<String> ageList;
    private ArrayList<String> locationList;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.display_fragment, container, false);

        Bundle args = getArguments();

        if (args != null) {
            imageList = args.getStringArrayList("imageList");
            idList = args.getStringArrayList("idList");
            nameList = args.getStringArrayList("nameList");
            breedList = args.getStringArrayList("breedList");
            weightList = args.getStringArrayList("weightList");
            ageList = args.getStringArrayList("ageList");
            locationList = args.getStringArrayList("locationList");
        }

        RecyclerView animalRecycler = root.findViewById(R.id.pet_recycler);
        PetListAdapter petListAdapter = new PetListAdapter(imageList, idList, nameList, breedList, weightList, ageList, locationList, getContext());

        animalRecycler.setAdapter(petListAdapter);
        animalRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
