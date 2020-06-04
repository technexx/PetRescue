package technexx.android.petrescue;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {

    List<AnimalList> animalList;
    Context context;

    public ListAdapter(List<AnimalList> animalList, Context context) {
        this.animalList = animalList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class AnimalList extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView animalID;
        public TextView age;
        public TextView breed;
        public TextView weight;

        public AnimalList(@NonNull View itemView) {
            super((itemView));
            image = itemView.findViewById(R.id.pet_picture);
            animalID = itemView.findViewById(R.id.pet_id);
            age = itemView.findViewById(R.id.pet_age);
            breed = itemView.findViewById(R.id.pet_breed);
            weight = itemView.findViewById(R.id.pet_weight);
        }
    }
}
