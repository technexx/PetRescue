package technexx.android.petrescue;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Handler;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PetListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> imageList;
    private ArrayList<String> idList;
    private ArrayList<String> nameList;
    private ArrayList<String> breedList;
    private ArrayList<String> weightList;
    private ArrayList<String> ageList;
    private ArrayList<String> locationList;
    private ArrayList<String> rescuelist;
    private ArrayList<String> descriptionList;
    private Context context;
    private clickListener mOnClickListener;
    private spinnerClickListener mOnSpinnerClick;
    //Creates a global instance of the View class which is inflated in onCreateViewHolder. We can then also reference it in the ViewHolder class that is called by onBindViewHolder, and thus pass in the appropriate positions to its onClick method.
    private View newView;

    private static final int TYPE_FILTER = 0;
    private static final int TYPE_PET = 1;

    //This is so the interface can be implemented in the appropriate Fragment.
    public interface clickListener {
        void onViewClick(String description, String image, String id);
    }

    //This is so a method tied to its (above) interface can be called and set on the adapter within the Fragment.
    public void setClick(clickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface spinnerClickListener {
        void onSpinnerClick (String filter);
    }

    public void setSpinnerClick(spinnerClickListener mOnSpinnerClick) {
        this.mOnSpinnerClick = mOnSpinnerClick;
    }

    PetListAdapter(ArrayList<String> image, ArrayList<String> id, ArrayList<String> name, ArrayList<String> breed, ArrayList<String> weight, ArrayList<String> age, ArrayList<String> location, ArrayList<String> rescue, ArrayList<String> description, Context context) {
        this.imageList = image; this.idList = id; this.nameList = name; this.breedList = breed; this.weightList = weight; this.ageList = age; this.locationList = location; this.rescuelist = rescue; this.descriptionList = description;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_PET) {
            newView = LayoutInflater.from(context).inflate(R.layout.pet_info, parent, false);
            return new PetHolder((newView));
        } else {
            newView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, parent, false);
            return new FilterHolder(newView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof PetHolder) {
            PetHolder petHolder = (PetHolder)holder;

            Picasso.get().load(imageList.get(position -1)).resize(450, 400).into(petHolder.image);
            petHolder.animalID.setText(idList.get(position -1));
            petHolder.name.setText(nameList.get(position -1));
            petHolder.breed.setText(breedList.get(position -1));
            petHolder.age.setText(ageList.get(position -1));
            petHolder.weight.setText(weightList.get(position -1));
            petHolder.location.setText(locationList.get(position -1));

            petHolder.rescue.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            petHolder.rescue.setText(rescuelist.get(position -1));

            if (rescuelist.get(position -1).equals("")){
                petHolder.rescue.setVisibility(View.GONE);
            } else {
                petHolder.rescue.setVisibility(View.VISIBLE);
            }

            petHolder.myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onViewClick(descriptionList.get(position -1), imageList.get(position -1), idList.get(position -1));
                }
            });
        } else {
            if (holder instanceof FilterHolder) {
                final List<String> filters = new ArrayList<String>();
                filters.add("Sort by");
                filters.add("Breed");
                filters.add("Age");
                filters.add("Weight");

                ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(context, R.layout.default_spinner, filters);
                filterAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                FilterHolder filterHolder = (FilterHolder) holder;
                filterHolder.filter.setAdapter(filterAdapter);

                filterHolder.filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(context, filters.get(position), Toast.LENGTH_SHORT).show();
                        mOnSpinnerClick.onSpinnerClick(filters.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        }
    }


    @Override
    public int getItemCount() {
        return idList.size() +1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_FILTER;
        } else {
            return TYPE_PET;
        }
    }

    public class PetHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView animalID;
        TextView name;
        TextView breed;
        TextView age;
        TextView weight;
        TextView location;
        TextView rescue;
        View myView;

        public PetHolder(@NonNull View itemView) {
            super((itemView));
            image = itemView.findViewById(R.id.pet_picture);
            animalID = itemView.findViewById(R.id.pet_id);
            name = itemView.findViewById(R.id.pet_name);
            breed = itemView.findViewById(R.id.pet_breed);
            age = itemView.findViewById(R.id.pet_age);
            weight = itemView.findViewById(R.id.pet_weight);
            location = itemView.findViewById(R.id.pet_location);
            rescue = itemView.findViewById(R.id.pet_rescue);

            myView = newView;
        }
    }

    public class FilterHolder extends RecyclerView.ViewHolder {
        Spinner filter;


        public FilterHolder(@NonNull View itemView) {
            super(itemView);
            filter = itemView.findViewById(R.id.filter_spinner);
        }
    }
}
