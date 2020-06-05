package technexx.android.petrescue;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DogFragment extends Fragment {

    listCallback mListCallback;

    private List<String> testList = null;
    private List<String> testListTwo = null;

    private ArrayList<String> imageList;
    private ArrayList<String> idList;
    private ArrayList<String> nameList;
    private ArrayList<String> breedList;
    private ArrayList<String> weightList;
    private ArrayList<String> ageList;
    private ArrayList<String> locationList;

    private ArrayList<String> animalList;

    private String holder;
    private String holderTwo;
    private int totalSize;

    private String dogs;
    private String cats;
    private String other;

    private String urlPre;
    private String url;

    public interface listCallback {
        void onDisplayList(ArrayList<String> petList);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListCallback = (listCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement listCallback");
        }
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dog_fragment, container, false);

        Button westValley = root.findViewById(R.id.westValley);
        Button eastValley = root.findViewById(R.id.eastValley);
        Button westLA = root.findViewById(R.id.westLA);
        Button southLA = root.findViewById(R.id.southLA);
        Button northCentral = root.findViewById(R.id.northCentral);
        Button harbor = root.findViewById(R.id.harbor);
        Button allAnimals = root.findViewById(R.id.all_animals);

        Bundle args = getArguments();
        if (args != null) {
            dogs = args.getString("dogs");
            cats = args.getString("cats");
            other = args.getString("other");
        }

        if (dogs != null) {
            allAnimals.setText(R.string.all_dogs);
        }
        if (cats != null) {
            allAnimals.setText(R.string.all_cats);
        }
        if (other != null) {
            allAnimals.setText(R.string.all_other);
        }

        urlPre = "https://petharbor.com/results.asp?searchtype=ADOPT&stylesheet=https://www.laanimalservices.com/wp-content/themes/laas/laasph.css&friends=1&samaritans=1&nosuccess=1&orderby=located%20at&rows=500&imght=120&imgres=detail&tWidth=200&view=sysadm.v_lact&nomax=1&fontface=arial&fontsize=10&miles=100&shelterlist=%27LACT%27,%27LACT1%27,%27LACT4%27,%27LACT3%27,%27LACT2%27,%27LACT5%27,%27LACT6%27&atype=&where=type_";

        allAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Depending on if Dog/Cat/Other is selected, setting uri and scraping data for list.
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (dogs != null) {
                            url = urlPre+"dog";
                        }
                        if (cats != null) {
                            url = urlPre + "cat";
                        }
                        if (other != null) {
                            url = urlPre + "other";
                        }
                        //Calling the rest of the scrape on a subsequent aSync method.
                        aSyncRetrieval();
                    }
                });
            }
        });

        return  root;
    }

    private void aSyncRetrieval() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(url).get();

                    Elements content = doc.getElementsByClass("TableContent1");
                    Elements contentTwo = doc.getElementsByClass("TableContent2");
                    Elements testImage = content.select("img");

                    imageList = new ArrayList<>();
                    idList = new ArrayList<>();
                    nameList = new ArrayList<>();
                    breedList = new ArrayList<>();
                    weightList = new ArrayList<>();
                    ageList = new ArrayList<>();
                    locationList = new ArrayList<>();
                    animalList = new ArrayList<>();

                    testList = content.eachText();
                    testListTwo = contentTwo.eachText();

                    for (int i=0; i<testList.size(); i++) {
                        holder = testList.get(i);
                        //Setting a check on the second TableContents fetch. If an odd number of pets come back, it will throw an OOB exception because its index fetch is based on TableContents1's index.
                        if (i < testListTwo.size()) {
                            holderTwo = testListTwo.get(i);
                        }

                        if (holder.contains("A1")) {
                            idList.add(holder);
                            String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                            String imgPost = holder+"&LOCATION=LACT";
                            String fullImg = imgPre + imgPost;
                            imageList.add(fullImg);
                        }
                        if (holderTwo.contains("A1")) {
                            idList.add(holderTwo);
                            String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                            String imgPost = holderTwo+"&LOCATION=LACT";
                            String fullImg = imgPre + imgPost;
                            imageList.add(fullImg);
                        }

                        if (holder.contains("My name is")) {
                            String[] split = holder.split(" ");
                            String shorten = split[3];
                            nameList.add(shorten);
                        }
                        if (holderTwo.contains("My name is")) {
                            String[] split = holderTwo.split(" ");
                            String shorten = split[3];
                            nameList.add(shorten);
                        }
                        if (holder.contains("Lbs")) {
                            String shorten = holder.substring(3);
                            weightList.add(shorten);
                        }
                        if (holderTwo.contains("Lbs")) {
                            String shorten = holderTwo.substring(3);
                            weightList.add(shorten);
                        }
                        if (holder.contains("yr") || holder.contains("Age Unknown") || holder.contains("mos") || holder.contains("wks")) {
                            String shorten = holder.substring(5);
                            ageList.add(shorten);
                        }
                        if (holderTwo.contains("yr") || holderTwo.contains("Age Unknown") || holderTwo.contains("mos") || holderTwo.contains("wks")) {
                            String shorten = holderTwo.substring(5);
                            ageList.add(shorten);
                        }
                        if (holder.contains("Los Angeles")) {
                            locationList.add(holder);
                        }
                        if (holderTwo.contains("Los Angeles")) {
                            locationList.add(holderTwo);
                        }
                        if (!holder.contains("A1") && !holder.contains("My name is") && !holder.contains("Lbs") && !holder.contains("yr") && !holder.contains("Los Angeles")) {
                            breedList.add(holder);
                        }
                        if (!holderTwo.contains("A1") && !holderTwo.contains("My name is") && !holderTwo.contains("Lbs") && !holderTwo.contains("yr") && !holderTwo.contains("Los Angeles")) {
                            breedList.add(holderTwo);
                        }
                    }

                    //Any individual list works for this loop's size check, since they are all the same size.
                    for (int x=0; x<idList.size(); x++) {
                        //Combining animal info into one list, to be passed into adapter and set to populate RecyclerView.
                        animalList.add(imageList.get(x));
                        animalList.add(idList.get(x));
                        animalList.add(breedList.get(x));
                        animalList.add(weightList.get(x));
                        animalList.add(ageList.get(x));
                        animalList.add(locationList.get(x));
                    }

//                    Log.i("id", idList.toString());
//                    Log.i("name", nameList.toString());
//                    Log.i("breed", breedList.toString());
//                    Log.i("weight", weightList.toString());
//                    Log.i("age", ageList.toString());
//                    Log.i("location", locationList.toString());
//                    Log.i("image", imageList.toString());
//                    Log.i("animal", animalList.toString());


                    mListCallback.onDisplayList(animalList);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
