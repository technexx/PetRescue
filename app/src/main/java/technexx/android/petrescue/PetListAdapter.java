package technexx.android.petrescue;

import android.content.Context;
import android.media.Image;
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

public class PetListAdapter extends RecyclerView.Adapter<PetListAdapter.ViewHolder> {

    private ArrayList<String> imageList;
    private ArrayList<String> idList;
    private ArrayList<String> nameList;
    private ArrayList<String> breedList;
    private ArrayList<String> weightList;
    private ArrayList<String> ageList;
    private ArrayList<String> locationList;
    private Context context;

    public PetListAdapter(ArrayList<String> image, ArrayList<String> id, ArrayList<String> name, ArrayList<String> breed, ArrayList<String> weight, ArrayList<String> age, ArrayList<String> location, Context context) {
        this.imageList = image; this.idList = id; this.nameList = name; this.breedList = breed; this.weightList = weight; this.ageList = age; this.locationList = location;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pet_info, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.get().load(imageList.get(position)).resize(400, 400).into(holder.image);
        holder.animalID.setText(idList.get(position));
        holder.name.setText(nameList.get(position));
        holder.breed.setText(breedList.get(position));
        holder.age.setText(ageList.get(position));
        holder.weight.setText(weightList.get(position));
        holder.location.setText(locationList.get(position));
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

        public ViewHolder(@NonNull View itemView) {
            super((itemView));
            image = itemView.findViewById(R.id.pet_picture);
            animalID = itemView.findViewById(R.id.pet_id);
            name = itemView.findViewById(R.id.pet_name);
            breed = itemView.findViewById(R.id.pet_breed);
            age = itemView.findViewById(R.id.pet_age);
            weight = itemView.findViewById(R.id.pet_weight);
            location = itemView.findViewById(R.id.pet_location);
        }
    }
}
