package technexx.android.petrescue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DisplayFragment extends Fragment implements PetListAdapter.clickListener, PetListAdapter.spinnerClickListener {

    private ArrayList<String> imageList;
    private ArrayList<String> idList;
    private ArrayList<String> nameList;
    private ArrayList<String> breedList;
    private ArrayList<String> weightList;
    private ArrayList<String> ageList;
    private ArrayList<String> locationList;
    private ArrayList<String> rescueList;
    private ArrayList<String> descriptionList;
    private String filter;

    private ArrayList<String> ageEditMos;
    private ArrayList<String> ageEditYrs;
    private ArrayList<String> ageEditBoth;
    private ArrayList<String> weightEdit;

    private onShelterMenuCallback mOnShelterMenuCallback;
    private onContactCallback mOnContactCallback;
    private onFilterCallback mOnFilterCallback;

    public interface onShelterMenuCallback {
        void onShelterMenu(boolean enabled);
    }

    public interface onContactCallback {
        void onContact(String shelter, String description, String image, String id);
    }

    public interface onFilterCallback {
        void onFilter(String filter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mOnShelterMenuCallback = (onShelterMenuCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement shelterMenuCallback");
        }
        try {
            mOnContactCallback = (onContactCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement onContactCallback");
        }
        try {
            mOnFilterCallback = (onFilterCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement onFilterCallback");
        }
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.display_fragment, container, false);

        //Disabling back button until data is displayed (in onStart()).
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mOnShelterMenuCallback.onShelterMenu(false);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        SharedPreferences pref = getContext().getSharedPreferences("SharedPref", 0);
        SharedPreferences.Editor editor = pref.edit();

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
            descriptionList = args.getStringArrayList("description");
            filter = args.getString("filter");
        }

        RecyclerView animalRecycler = root.findViewById(R.id.pet_recycler);
        PetListAdapter petListAdapter = new PetListAdapter(imageList, idList, nameList, breedList, weightList, ageList, locationList, rescueList, descriptionList, getContext());
        petListAdapter.setClick(this);
        petListAdapter.setSpinnerClick(this);

        animalRecycler.setAdapter(petListAdapter);
        animalRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.horizontal_divider));

        animalRecycler.addItemDecoration(decoration);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Enabling back button once all data is loaded from previous Fragment.
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mOnShelterMenuCallback.onShelterMenu(true);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    //Calling the onClick interface in adapter.
    @Override
    public void onViewClick(String description, String image, String id) {
        mOnContactCallback.onContact(locationList.toString(), description, image, id);
    }

    @Override
    public void onSpinnerClick(String filter) {
//        mOnFilterCallback.onFilter(filter);
    }
}

