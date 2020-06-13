package technexx.android.petrescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MenuFragment.dogCallback, MenuFragment.catCallback, MenuFragment.othersCallback, PetFragment.listCallback, DisplayFragment.onShelterMenuCallback, PetFragment.onMainMenuCallback, DisplayFragment.onContactCallback {

    //Todo: South LA breed is overrunning.

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
    public void onDisplayList(ArrayList<String> image, ArrayList<String> id, ArrayList<String> name, ArrayList<String> breed, ArrayList<String> weight, ArrayList<String> age, ArrayList<String> location, ArrayList<String> rescue, ArrayList<String> description) {
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
        b.putStringArrayList("description", description);

        displayFragment.setArguments(b);

        fm.beginTransaction()
                .replace(R.id.animals, displayFragment)
                .commit();
    }

    @Override
    public void onShelterMenu(boolean enable) {
        FragmentManager fm = getSupportFragmentManager();
        PetFragment petFragment = new PetFragment();

        //Receiving boolean to determine whether data is loaded or not in DisplayFragment, in order to toggle enabling of Back button.
        if (enable) {
            fm.beginTransaction()
                    .replace(R.id.animals, petFragment)
                    .commit();
        }
    }

    @Override
    public void onMainMenu() {
        FragmentManager fm = getSupportFragmentManager();
        MenuFragment menuFragment = new MenuFragment();

        fm.beginTransaction()
                .replace(R.id.animals, menuFragment)
                .commit();
    }

    @Override
    public void onContact(String shelter, String description, String image, String id) {
        FragmentManager fm = getSupportFragmentManager();
        ContactFragment contactFragment = new ContactFragment();

        Bundle b = new Bundle();
        if (shelter.contains("East Valley")) {
            b.putString("shelter", "ev");
        }
        if (shelter.contains("West Los Angeles ")) {
            b.putString("shelter", "wLA");
        }
        if (shelter.contains("South Los Angeles")) {
            b.putString("shelter", "sLA");
        }
        if (shelter.contains("Harbor")) {
            b.putString("shelter", "har");
        }

        b.putString("description", description);
        b.putString("image", image);
        b.putString("id", "ID:" + " "  + id);

        contactFragment.setArguments(b);

        fm.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.animals, contactFragment)
                .commit();
    }
}
