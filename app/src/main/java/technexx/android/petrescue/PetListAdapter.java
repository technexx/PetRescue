package technexx.android.petrescue;

import android.content.Context;
import android.graphics.Typeface;
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

public class PetListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> imageList;
    private ArrayList<String> idList;
    private ArrayList<String> nameList;
    private ArrayList<String> breedList;
    private ArrayList<String> weightList;
    private ArrayList<String> ageList;
    private ArrayList<String> locationList;
    private ArrayList<String> rescuelist;
    private Context context;
    private clickListener mOnClickListener;

    public interface clickListener {
        void onClick();
    }

    public void setClick(clickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    PetListAdapter(ArrayList<String> image, ArrayList<String> id, ArrayList<String> name, ArrayList<String> breed, ArrayList<String> weight, ArrayList<String> age, ArrayList<String> location, ArrayList<String> rescue, Context context) {
        this.imageList = image; this.idList = id; this.nameList = name; this.breedList = breed; this.weightList = weight; this.ageList = age; this.locationList = location; this.rescuelist = rescue;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pet_info, parent, false);

        ViewHolder holder = new ViewHolder(view);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onClick();
            }
        });

        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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
        }
    }
}
