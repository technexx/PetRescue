package technexx.android.petrescue;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MenuFragment extends Fragment {

    private dogCallback mDogCallback;
    private catCallback mCatCallback;
    private othersCallback mOthersCallback;

    public interface dogCallback {
        void onDogCalled();
    }

    public interface catCallback {
        void onCatCalled();
    }

    public interface othersCallback {
        void onOthersCalled();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mDogCallback = (dogCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement dogCallback");
        }
        try {
            mCatCallback = (catCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement catCallback");
        }
        try {
            mOthersCallback = (othersCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement othersCallback");
        }
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.menu_fragment, container, false);

        Button dogs = root.findViewById(R.id.dogs);
        Button cats = root.findViewById(R.id.cats);
        Button others = root.findViewById(R.id.others);

        dogs.setBackgroundResource(R.drawable.dogmod2);
        cats.setBackgroundResource(R.drawable.catmod2);
        others.setBackgroundResource(R.drawable.hamster2);

        dogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDogCallback.onDogCalled();
            }
        });

        cats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCatCallback.onCatCalled();
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOthersCallback.onOthersCalled();
            }
        });

        return  root;
    }
}
