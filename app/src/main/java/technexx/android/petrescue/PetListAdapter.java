package technexx.android.petrescue;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Handler;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
    //Creates a global instance of the View class which is inflated in onCreateViewHolder. We can then also reference it in the ViewHolder class that is called by onBindViewHolder, and thus pass in the appropriate positions to its onClick method.
    private View newView;

    public interface clickListener {
        void onClick(String description, String image, String id);
    }

    public void setClick(clickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    PetListAdapter(ArrayList<String> image, ArrayList<String> id, ArrayList<String> name, ArrayList<String> breed, ArrayList<String> weight, ArrayList<String> age, ArrayList<String> location, ArrayList<String> rescue, ArrayList<String> description, Context context) {
        this.imageList = image; this.idList = id; this.nameList = name; this.breedList = breed; this.weightList = weight; this.ageList = age; this.locationList = location; this.rescuelist = rescue; this.descriptionList = description;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        newView = LayoutInflater.from(context).inflate(R.layout.pet_info, parent, false);

        //Setting click on entirety of inflated view.
        ViewHolder viewHolder = new ViewHolder(newView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ViewHolder viewHolder = (ViewHolder) holder;

        Picasso.get().load(imageList.get(position)).resize(450, 400).into(viewHolder.image);
        viewHolder.animalID.setText(idList.get(position));
        viewHolder.name.setText(nameList.get(position));
        viewHolder.breed.setText(breedList.get(position));
        viewHolder.age.setText(ageList.get(position));
        viewHolder.weight.setText(weightList.get(position));
        viewHolder.location.setText(locationList.get(position));
        viewHolder.rescue.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        viewHolder.rescue.setText(rescuelist.get(position));

        viewHolder.myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onClick(descriptionList.get(position), imageList.get(position), idList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return idList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView animalID;
        TextView name;
        TextView breed;
        TextView age;
        TextView weight;
        TextView location;
        TextView rescue;
        View myView;

        public ViewHolder(@NonNull View itemView) {
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
}
