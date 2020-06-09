package technexx.android.petrescue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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
    private ArrayList<String> rescueList;

    private onShelterMenuCallback mOnShelterMenuCallback;

    public interface onShelterMenuCallback {
            void onShelterMenu();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mOnShelterMenuCallback = (onShelterMenuCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement shelterMenuCallback");
        }
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.display_fragment, container, false);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mOnShelterMenuCallback.onShelterMenu();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        Bundle args = getArguments();
        if (args != null) {
            imageList = args.getStringArrayList("imageList");
            idList = args.getStringArrayList("idList");
            nameList = args.getStringArrayList("nameList");
            breedList = args.getStringArrayList("breedList");
            weightList = args.getStringArrayList("weightList");
            ageList = args.getStringArrayList("ageList");
            locationList = args.getStringArrayList("locationList");
            rescueList = args.getStringArrayList("rescue");
        }

        RecyclerView animalRecycler = root.findViewById(R.id.pet_recycler);
        PetListAdapter petListAdapter = new PetListAdapter(imageList, idList, nameList, breedList, weightList, ageList, locationList, rescueList, getContext());

        animalRecycler.setAdapter(petListAdapter);
        animalRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.horizontal_divider));

        animalRecycler.addItemDecoration(decoration);

        return root;
    }
}
