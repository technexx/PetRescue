package technexx.android.petrescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MenuFragment.dogCallback, MenuFragment.catCallback, MenuFragment.othersCallback, DogFragment.listCallback {

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
        DogFragment dogFragment = new DogFragment();

        Bundle b = new Bundle();
        b.putString("dogs", "dogs");
        dogFragment.setArguments(b);

        fm.beginTransaction()
                .replace(R.id.animals, dogFragment)
                .commit();
    }

    @Override
    public void onCatCalled() {
        FragmentManager fm = getSupportFragmentManager();
        DogFragment dogFragment = new DogFragment();

        Bundle b = new Bundle();
        b.putString("cats", "cats");
        dogFragment.setArguments(b);

        fm.beginTransaction()
                .replace(R.id.animals, dogFragment)
                .commit();
    }

    @Override
    public void onOthersCalled() {
        FragmentManager fm = getSupportFragmentManager();
        DogFragment dogFragment = new DogFragment();

        Bundle b = new Bundle();
        b.putString("other", "other");
        dogFragment.setArguments(b);

        fm.beginTransaction()
                .replace(R.id.animals, dogFragment)
                .commit();
    }

    @Override
    public void onDisplayList(ArrayList<String> image, ArrayList<String> id, ArrayList<String> name, ArrayList<String> breed, ArrayList<String> weight, ArrayList<String> age, ArrayList<String> location) {
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

        displayFragment.setArguments(b);

        fm.beginTransaction()
                .replace(R.id.animals, displayFragment)
                .commit();


    }

}
