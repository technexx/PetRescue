package technexx.android.petrescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements MenuFragment.dogCallback, MenuFragment.catCallback, MenuFragment.othersCallback {

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

}
