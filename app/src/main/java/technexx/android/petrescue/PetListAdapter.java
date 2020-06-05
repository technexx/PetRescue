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

    private ArrayList<String> animalList;
    private Context context;

    private static final int type_image = 0;
    private static final int type_id = 1;
    private static final int type_breed = 2;
    private static final int type_weight = 3;
    private static final int type_age = 4;
    private static final int type_location = 5;


    public PetListAdapter(ArrayList<String> animalList, Context context) {
        this.animalList = animalList;
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
        switch (position) {

        }
        Picasso.get().load(animalList.get(position)).into(holder.image);
        holder.animalID.setText(animalList.get(position +1));
        holder.breed.setText(animalList.get(position +2));
        holder.weight.setText(animalList.get(position +3));
        holder.age.setText(animalList.get(position +4));
        holder.location.setText(animalList.get(position +5));
    }


    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView animalID;
        TextView breed;
        TextView age;
        TextView weight;
        TextView location;

        public ViewHolder(@NonNull View itemView) {
            super((itemView));
            image = itemView.findViewById(R.id.pet_picture);
            animalID = itemView.findViewById(R.id.pet_id);
            breed = itemView.findViewById(R.id.pet_breed);
            age = itemView.findViewById(R.id.pet_age);
            weight = itemView.findViewById(R.id.pet_weight);
            location = itemView.findViewById(R.id.pet_location);
        }
    }
}
