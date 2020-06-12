package technexx.android.petrescue;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactFragment extends Fragment {

    Uri address;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.contact_fragment, container, false);

        TextView contact_one = root.findViewById(R.id.contact_one);
        TextView contact_two = root.findViewById(R.id.contact_two);
        TextView contact_three = root.findViewById(R.id.contact_three);
        TextView contact_four = root.findViewById(R.id.contact_four);
        ImageView contact_image = root.findViewById(R.id.contact_image);
        TextView contact_description = root.findViewById(R.id.contact_description);

        //Setting underline on address.
        contact_two.setPaintFlags(contact_two.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Bundle args = getArguments();
        String shelter = null;
        String description = null;
        String image = null;

        if (args != null) {
            shelter = args.getString("shelter");
            description = args.getString("description");
            image = args.getString("image", image);
        }

        if (shelter.equals("ev")) {
            contact_one.setText(getString(R.string.loc_eastValley));
            contact_two.setText(getString(R.string.eastValley_address));
            address = Uri.parse("https://www.google.com/maps/search/?api=1&query=34.194641, -118.446258");
        }
        if (shelter.equals("wLA")) {
            contact_one.setText(getString(R.string.loc_westLA));
            contact_two.setText(getString(R.string.westLA_address));
            address = Uri.parse("https://www.google.com/maps/search/?api=1&query=34.034891, -118.440626");
        }
        if (shelter.equals("sLA")) {
            contact_one.setText(getString(R.string.loc_southLA));
            contact_two.setText(getString(R.string.southLA_address));
            address = Uri.parse("https://www.google.com/maps/search/?api=1&query= 33.985115, -118.310999");
        }
        if (shelter.equals("har")) {
            contact_one.setText(getString(R.string.loc_harbor));
            contact_two.setText(getString(R.string.harbor_address));
            address = Uri.parse("https://www.google.com/maps/search/?api=1&query=33.752227, -118.294216");
        }

        contact_three.setText(getString(R.string.shelter_hours));
        contact_four.setText(getString(R.string.shelter_phone));
        contact_description.setText(description);

        Picasso.get().load(image).resize(800, 800).centerCrop().into(contact_image);

        //Linkify makes the Uri coordinates unnecessary, but we're keeping them as a backup.
        Linkify.addLinks(contact_two, Linkify.ALL);
        Linkify.addLinks(contact_four, Linkify.ALL);
        contact_two.setLinkTextColor(getResources().getColor(R.color.darkGreen));
        contact_four.setLinkTextColor(getResources().getColor(R.color.darkGreen));

        return root;
    }
}