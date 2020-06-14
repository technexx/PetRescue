package technexx.android.petrescue;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.zip.Inflater;

public class MenuFragment extends Fragment {

    private dogCallback mDogCallback;
    private catCallback mCatCallback;
    private othersCallback mOthersCallback;

    boolean disclaimerShown;

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

        SharedPreferences pref = getContext().getSharedPreferences("SharedPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("animal", "");
        editor.apply();

        final ImageButton dogs = root.findViewById(R.id.dogs);
        final ImageButton cats = root.findViewById(R.id.cats);
        final ImageButton others = root.findViewById(R.id.others);

        dogs.setBackgroundResource(R.drawable.dogmod2);
        dogs.setBackgroundResource(R.drawable.dogmod2);
        cats.setBackgroundResource(R.drawable.catmod2);
        others.setBackgroundResource(R.drawable.hamster2);

        Bundle args = getArguments();

        if (args != null) {
            disclaimerShown = args.getBoolean("disclaimer");
        }

        final ConstraintLayout menuFragment = root.findViewById(R.id.menu_constrained);
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View myView = layoutInflater.inflate(R.layout.disclaimer, null);

        final PopupWindow popupWindow = new PopupWindow(myView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //This runnable is necessary to prevent the popup from inflating too early and crashing the app.

        if (!disclaimerShown) {
            menuFragment.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        popupWindow.setWidth(800);
                        popupWindow.setHeight(1000);
                        popupWindow.setElevation(5);
                        popupWindow.showAtLocation(menuFragment, Gravity.CENTER, 0, 0);

                        Button okay = myView.findViewById(R.id.exit_disclaimer);

                        dogs.setEnabled(false);
                        cats.setEnabled(false);
                        others.setEnabled(false);

                        okay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                                dogs.setEnabled(true);
                                cats.setEnabled(true);
                                others.setEnabled(true);
                            }
                        });


                    } catch (Exception e) {
                        Log.e("Error", "Popup trying to attach too early");
                    }
                }
            });
        }


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
