package technexx.android.petrescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MenuFragment.dogCallback, MenuFragment.catCallback, MenuFragment.othersCallback, PetFragment.listCallback, DisplayFragment.onShelterMenuCallback, PetFragment.onMainMenuCallback {

    //Todo: Disable fetch buttons after first press, otherwise fragments get detached.
    //Todo: Lots of "bs" weights in Other, also errors w/ unknown ages.
    //Todo: Add contact info of shelter/warning that all info is pulled from website.
    //Todo: Longer breeds, e.g. "American Staffordshire" run off screen.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        MenuFragment menuFragment = new MenuFragment();

        fm.beginTransaction()
                .add(R.id.animals, menuFragment)
                .commit();
    }

    @Override
    public void onDogCalled() {
        FragmentManager fm = getSupportFragmentManager();
        PetFragment petFragment = new PetFragment();

        Bundle b = new Bundle();
        b.putString("dogs", "dogs");
        petFragment.setArguments(b);

        fm.beginTransaction()
                .replace(R.id.animals, petFragment)
                .commit();
    }

    @Override
    public void onCatCalled() {
        FragmentManager fm = getSupportFragmentManager();
        PetFragment petFragment = new PetFragment();

        Bundle b = new Bundle();
        b.putString("cats", "cats");
        petFragment.setArguments(b);

        fm.beginTransaction()
                .replace(R.id.animals, petFragment)
                .commit();
    }

    @Override
    public void onOthersCalled() {
        FragmentManager fm = getSupportFragmentManager();
        PetFragment petFragment = new PetFragment();

        Bundle b = new Bundle();
        b.putString("other", "other");
        petFragment.setArguments(b);

        fm.beginTransaction()
                .replace(R.id.animals, petFragment)
                .commit();
    }

    @Override
    public void onDisplayList(ArrayList<String> image, ArrayList<String> id, ArrayList<String> name, ArrayList<String> breed, ArrayList<String> weight, ArrayList<String> age, ArrayList<String> location, ArrayList<String> rescue) {
        FragmentManager fm = getSupportFragmentManager();
        DisplayFragment displayFragment = new DisplayFragment();

        Bundle b = new Bundle();
        b.putStringArrayList("imageList", image);
        b.putStringArrayList("idList", id);
        b.putStringArrayList("nameList", name);
        b.putStringArrayList("breedList", breed);
        b.putStringArrayList("weightList", weight);
        b.putStringArrayList("ageList", age);
        b.putStringArrayList("locationList", location);
        b.putStringArrayList("rescue", rescue);

        displayFragment.setArguments(b);

        fm.beginTransaction()
                .replace(R.id.animals, displayFragment)
                .commit();
    }

    @Override
    public void onShelterMenu() {
        FragmentManager fm = getSupportFragmentManager();
        MenuFragment menuFragment = new MenuFragment();

        fm.beginTransaction()
                .replace(R.id.animals, menuFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMainMenu() {
        FragmentManager fm = getSupportFragmentManager();
        MenuFragment menuFragment = new MenuFragment();

        fm.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.animals, menuFragment)
                .commit();
    }
}
