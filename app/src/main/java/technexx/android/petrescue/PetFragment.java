package technexx.android.petrescue;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PetFragment extends Fragment {

    private List<String> testList = null;
    private List<String> testListTwo = null;

    private ArrayList<String> imageList;
    private ArrayList<String> idList;
    private ArrayList<String> nameList;
    private ArrayList<String> breedList;
    private ArrayList<String> weightList;
    private ArrayList<String> ageList;
    private ArrayList<String> locationList;
    private ArrayList<String> rescueList;
    private ArrayList<String> descriptionList;

    private String holder;
    private String holderTwo;

    private String dogs;
    private String cats;
    private String other;

    private String urlPre;
    private String url;
    private String urlFilter;
    boolean filterBreed;
    boolean filterAge;
    boolean filterWeight;

    private onMainMenuCallback mOnMainMenuCallback;
    private listCallback mListCallback;

    private Button westValley;
    private Button eastValley ;
    private Button westLA;
    private Button southLA;
    private Button northCentral;
    private Button harbor;
    private Button allAnimals;

    private ProgressBar progressBar;
    private String animal;

    public interface onMainMenuCallback {
        void onMainMenu();
    }

    public interface listCallback {
        void onDisplayList(ArrayList<String> image, ArrayList<String> id, ArrayList<String> name, ArrayList<String> breed, ArrayList<String> weight, ArrayList<String> age, ArrayList<String> location, ArrayList<String> rescue, ArrayList<String> description);
    }

    public void disableButtons() {
        allAnimals.setEnabled(false);
        westValley.setEnabled(false);
        eastValley.setEnabled(false);
        westLA.setEnabled(false);
        southLA.setEnabled(false);
        northCentral.setEnabled(false);
        harbor.setEnabled(false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListCallback = (listCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement listCallback");
        }
        try {
            mOnMainMenuCallback = (onMainMenuCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement onMainMenuCallback");
        }
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.pet_fragment, container, false);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mOnMainMenuCallback.onMainMenu();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        SharedPreferences pref = getContext().getSharedPreferences("SharedPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        animal = pref.getString("animal", "");

        westValley = root.findViewById(R.id.westValley);
        eastValley = root.findViewById(R.id.eastValley);
        westLA = root.findViewById(R.id.westLA);
        southLA = root.findViewById(R.id.southLA);
        northCentral = root.findViewById(R.id.northCentral);
        harbor = root.findViewById(R.id.harbor);
        allAnimals = root.findViewById(R.id.all_animals);

        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setScaleX(1.5F);
        progressBar.setScaleY(1.5F);

        Bundle args = getArguments();
        if (args != null) {
            dogs = args.getString("dogs");
            cats = args.getString("cats");
            other = args.getString("other");
        }

        if (dogs != null || animal.equals("dog")) {
            allAnimals.setText(R.string.all_dogs);
            editor.putString("animal", "dog");
        }
        if (cats != null || animal.equals("cat")) {
            allAnimals.setText(R.string.all_cats);
            editor.putString("animal", "cat");
        }
        if (other != null || animal.equals("other")) {
            allAnimals.setText(R.string.all_other);
            editor.putString("animal", "other");
        }
        editor.apply();

        urlPre = "https://petharbor.com/results.asp?searchtype=ADOPT&stylesheet=https://www.laanimalservices.com/wp-content/themes/laas/laasph.css&friends=1&samaritans=1&nosuccess=1&orderby=located%20at&rows=500&imght=120&imgres=detail&tWidth=200&view=sysadm.v_lact&nomax=1&fontface=arial&fontsize=10&miles=100&shelterlist=%27LACT%27,%27LACT1%27,%27LACT4%27,%27LACT3%27,%27LACT2%27,%27LACT5%27,%27LACT6%27&atype=&where=type_";

        allAnimals.setEnabled(true);
        westValley.setEnabled(true);
        eastValley.setEnabled(true);
        westLA.setEnabled(true);
        southLA.setEnabled(true);
        northCentral.setEnabled(true);
        harbor.setEnabled(true);

        progressBar.setVisibility(View.GONE);

        allAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSyncUriFetch();
                aSyncRetrieveAll();
                disableButtons();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        westValley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), R.string.closed, Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(getResources().getColor(R.color.off_white));
                toast.show();
            }
        });

        eastValley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSyncUriFetch();
                aSyncRetrieveShelter("East Valley");
                disableButtons();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        westLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSyncUriFetch();
                aSyncRetrieveShelter("West LA");
                disableButtons();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        southLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSyncUriFetch();
                aSyncRetrieveShelter("South LA");
                disableButtons();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        northCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), R.string.closed, Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(getResources().getColor(R.color.off_white));
                toast.show();
            }
        });

        harbor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSyncUriFetch();
                aSyncRetrieveShelter("Harbor");
                disableButtons();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        return root;
    }

    //Depending on if Dog/Cat/Other is selected, setting uri and scraping data for list.
    private void aSyncUriFetch() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                if (dogs != null || animal.equals("dog")) {
                    url = urlPre + "dog";
                }
                if (cats != null || animal.equals("cat")) {
                    url = urlPre + "cat";
                }
                if (other != null || animal.equals("other")) {
                    url = urlPre + "other";
                }
                if (filterBreed) {
                    url = url + "&NewOrderBy=Breed&PAGE=1";
                }
                if (filterAge) {
                    url = url + "&NewOrderBy=Age&PAGE=1";
                }
                if (filterWeight) {
                    url = url + "&NewOrderBy=Weight&PAGE=1";
                }
            }
        });
    }

    private void aSyncRetrieveAll() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(url).get();

                    Elements content = doc.getElementsByClass("TableContent1");
                    Elements contentTwo = doc.getElementsByClass("TableContent2");

                    imageList = new ArrayList<>();
                    idList = new ArrayList<>();
                    nameList = new ArrayList<>();
                    breedList = new ArrayList<>();
                    weightList = new ArrayList<>();
                    ageList = new ArrayList<>();
                    locationList = new ArrayList<>();
                    rescueList = new ArrayList<>();
                    descriptionList = new ArrayList<>();

                    testList = content.eachText();
                    testListTwo = contentTwo.eachText();

                    for (int i=0; i < testList.size(); i++) {

                        holder = testList.get(i);

                        //LACT1 = Harbor, LACT2 = West LA, LACT3 = South LA
                        if (holder.contains("A1") || holder.contains("A0")) {
                            idList.add(holder);
                            String imgPost = null;
                            if (testList.get(i + 5).contains("Harbor")) {
                                imgPost = holder + "&LOCATION=LACT1";
                            } else if (testList.get(i + 5).contains("West Los Angeles")) {
                                imgPost = holder + "&LOCATION=LACT2";
                            } else if (testList.get(i + 5).contains("South Los Angeles")) {
                                imgPost = holder + "&LOCATION=LACT3";
                            } else {
                                imgPost = holder + "&LOCATION=LACT";
                            }
                            String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                            String fullImg = imgPre + imgPost;
                            imageList.add(fullImg);

                            descriptionList.add(testList.get(i+1));
                            if (!testList.get(i+1).contains("My name is")) {
                                nameList.add("N/A");
                            } else {
                                String[] split = testList.get(i+1).split("and");
                                String shorten = split[0];
                                String cull = shorten.substring(11);
                                nameList.add(cull);
                            }
                            if (testList.get(i+1).contains("rescue")) {
                                rescueList.add(getString(R.string.rescue));
                            } else if (testList.get(i+1).contains("minor")) {
                                rescueList.add(getString(R.string.minor_care));
                            } else if (testList.get(i+1).contains("major")) {
                                rescueList.add(getString(R.string.major_care));
                            } else {
                                rescueList.add("");
                            }

                            breedList.add(testList.get(i+2));

                            if (testList.get(i+3).equals("0 Lbs")) {
                                weightList.add("N/A");
                            } else {
                                String shorten = testList.get(i+3).substring(3);
                                weightList.add(shorten);
                            }

                            if  (testList.get(i+4).contains("Age Unknown")) {
                                ageList.add("Unknown");
                            } else {
                                String shorten = testList.get(i+4).substring(5);
                                ageList.add(shorten);
                            }

                            String[] splitTwo = testList.get(i+5).split("Shelter");
                            String shortenTwo = splitTwo[0];
                            String cullTwo = shortenTwo.substring(30);
                            locationList.add(cullTwo);
                        }

                        if (i<testListTwo.size()) {
                            holderTwo = testListTwo.get(i);

                            if (holderTwo.contains("A1") || holderTwo.contains("A0")) {
                                idList.add(holderTwo);
                                String imgPost = null;
                                if (testListTwo.get(i + 5).contains("Harbor")) {
                                    imgPost = holderTwo + "&LOCATION=LACT1";
                                } else if (testListTwo.get(i + 5).contains("West Los Angeles")) {
                                    imgPost = holderTwo + "&LOCATION=LACT2";
                                } else if (testListTwo.get(i + 5).contains("South Los Angeles")) {
                                    imgPost = holderTwo + "&LOCATION=LACT3";
                                } else {
                                    imgPost = holderTwo + "&LOCATION=LACT";
                                }
                                String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                String fullImg = imgPre + imgPost;
                                imageList.add(fullImg);

                                descriptionList.add(testListTwo.get(i+1));
                                if (!testListTwo.get(i+1).contains("My name is")) {
                                    nameList.add("N/A");
                                } else {
                                    String[] split = testListTwo.get(i+1).split("and");
                                    String shorten = split[0];
                                    String cull = shorten.substring(11);
                                    nameList.add(cull);
                                }
                                if (testListTwo.get(i+1).contains("rescue")) {
                                    rescueList.add(getString(R.string.rescue));
                                } else if (testListTwo.get(i+1).contains("minor")) {
                                    rescueList.add(getString(R.string.minor_care));
                                } else if (testListTwo.get(i+1).contains("major")) {
                                    rescueList.add(getString(R.string.major_care));
                                } else {
                                    rescueList.add("");
                                }

                                breedList.add(testListTwo.get(i+2));

                                if (testListTwo.get(i+3).equals("0 Lbs")) {
                                    weightList.add("N/A");
                                } else {
                                    String shorten = testListTwo.get(i+3).substring(3);
                                    weightList.add(shorten);
                                }

                                if  (testListTwo.get(i+4).contains("Age Unknown")) {
                                    ageList.add("Unknown");
                                } else {
                                    String shorten = testListTwo.get(i+4).substring(5);
                                    ageList.add(shorten);
                                }

                                String[] split = testListTwo.get(i+5).split("Shelter");
                                String shorten = split[0];
                                String cull = shorten.substring(30);
                                locationList.add(cull);
                            }
                        }
                    }

                    mListCallback.onDisplayList(imageList, idList, nameList, breedList, weightList, ageList, locationList, rescueList, descriptionList);

                    Log.i("ageSort", ageList.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void aSyncRetrieveShelter(final String loc) {
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
                    rescueList = new ArrayList<>();
                    descriptionList = new ArrayList<>();

                    testList = content.eachText();
                    testListTwo = contentTwo.eachText();

                    for (int i = 0; i < testList.size(); i++) {
                        holder = testList.get(i);

                        String[] splitLoc = null;
                        String shortenLoc = null;
                        String cullLoc = null;

                        if (loc.equals("East Valley")) {
                            if (holder.contains("East Valley")) {
                                splitLoc = holder.split("Shelter");
                                shortenLoc = splitLoc[0];
                                cullLoc = shortenLoc.substring(30);
                                locationList.add(cullLoc);

                                if (testList.get(i-1).contains("Unknown")) {
                                    ageList.add("Unknown");
                                } else {
                                    String shortenAge = testList.get(i-1).substring(5);
                                    ageList.add(shortenAge);
                                }

                                if (testList.get(i-2).equals("0 Lbs")) {
                                    weightList.add("N/A");
                                } else {
                                    String shortenWeight = testList.get(i-2).substring(3);
                                    weightList.add(shortenWeight);
                                }

                                breedList.add(testList.get(i-3));

                                if (!testList.get(i-4).contains("My name is")) {
                                    descriptionList.add(testList.get(i-4));
                                    nameList.add("N/A");
                                } else {
                                    descriptionList.add(testList.get(i-4));
                                    String[] split = testList.get(i-4).split("and");
                                    String shortenName = split[0];
                                    String cull = shortenName.substring(11);
                                    nameList.add(cull);
                                }

                                if (testList.get(i-4).contains("minor")) {
                                    rescueList.add(getString(R.string.minor_care));
                                } else if (testList.get(i-4).contains("major")) {
                                    rescueList.add(getString(R.string.major_care));
                                } else {
                                    rescueList.add("");
                                }

                                idList.add(testList.get(i-5));
                                String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                String imgPost = testList.get(i-5) + "&LOCATION=LACT";
                                String fullImg = imgPre + imgPost;
                                imageList.add(fullImg);

                                if (i<testListTwo.size()) {
                                    holderTwo = testListTwo.get(i);
                                    splitLoc = holderTwo.split("Shelter");
                                    shortenLoc = splitLoc[0];
                                    cullLoc = shortenLoc.substring(30);
                                    locationList.add(cullLoc);

                                    if (testListTwo.get(i - 1).contains("Unknown")) {
                                        ageList.add("Unknown");
                                    } else {
                                        String shortenAge = testListTwo.get(i-1).substring(5);
                                        ageList.add(shortenAge);
                                    }

                                    if (testListTwo.get(i-2).equals("0 Lbs")) {
                                        weightList.add("N/A");
                                    } else {
                                        String shortenWeight = testListTwo.get(i-2).substring(3);
                                        weightList.add(shortenWeight);
                                    }

                                    breedList.add(testListTwo.get(i-3));

                                    if (!testListTwo.get(i-4).contains("My name is")) {
                                        nameList.add("N/A");
                                        descriptionList.add(testListTwo.get(i-4));
                                    } else {
                                        descriptionList.add(testListTwo.get(i-4));
                                        String[] split = testListTwo.get(i-4).split("and");
                                        String shortenName = split[0];
                                        String cull = shortenName.substring(11);
                                        nameList.add(cull);
                                    }

                                    if (testListTwo.get(i-4).contains("minor")) {
                                        rescueList.add(getString(R.string.minor_care));
                                    } else if (testListTwo.get(i-4).contains("major")) {
                                        rescueList.add(getString(R.string.major_care));
                                    } else {
                                        rescueList.add("");
                                    }

                                    idList.add(testListTwo.get(i-5));
                                    imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                    imgPost = testListTwo.get(i-5) + "&LOCATION=LACT";
                                    fullImg = imgPre + imgPost;
                                    imageList.add(fullImg);
                                }
                            }
                        }

                        //LACT1 = Harbor, LACT2 = West LA, LACT3 = South LA

                        if (loc.equals("West LA")) {
                            if (holder.contains("West Los Angeles")) {
                                splitLoc = holder.split("Shelter");
                                shortenLoc = splitLoc[0];
                                cullLoc = shortenLoc.substring(30);
                                locationList.add(cullLoc);

                                if (testList.get(i-1).contains("Unknown")) {
                                    ageList.add("Age Unknown");
                                } else {
                                    String shortenAge = testList.get(i-1).substring(5);
                                    ageList.add(shortenAge);
                                }

                                if (testList.get(i-2).equals("0 Lbs")) {
                                    weightList.add("N/A");
                                } else {
                                    String shortenWeight = testList.get(i-2).substring(3);
                                    weightList.add(shortenWeight);
                                }

                                breedList.add(testList.get(i-3));

                                if (!testList.get(i-4).contains("My name is")) {
                                    nameList.add("N/A");
                                    descriptionList.add(testList.get(i-4));
                                } else {
                                    descriptionList.add(testList.get(i-4));
                                    String[] split = testList.get(i-4).split("and");
                                    String shortenName = split[0];
                                    String cull = shortenName.substring(11);
                                    nameList.add(cull);
                                }

                                if (testList.get(i-4).contains("minor")) {
                                    rescueList.add(getString(R.string.minor_care));
                                } else if (testList.get(i-4).contains("major")) {
                                    rescueList.add(getString(R.string.major_care));
                                } else {
                                    rescueList.add("");
                                }

                                idList.add(testList.get(i-5));
                                String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                String imgPost = testList.get(i-5) + "&LOCATION=LACT2";
                                String fullImg = imgPre + imgPost;
                                imageList.add(fullImg);

                                if (i<testListTwo.size()) {
                                    holderTwo = testListTwo.get(i);
                                    splitLoc = holderTwo.split("Shelter");
                                    shortenLoc = splitLoc[0];
                                    cullLoc = shortenLoc.substring(30);
                                    locationList.add(cullLoc);

                                    if (testListTwo.get(i - 1).contains("Unknown")) {
                                        ageList.add("Unknown");
                                    } else {
                                        String shortenAge = testListTwo.get(i-1).substring(5);
                                        ageList.add(shortenAge);
                                    }

                                    if (testListTwo.get(i-2).equals("0 Lbs")) {
                                        weightList.add("N/A");
                                    } else {
                                        String shortenWeight = testListTwo.get(i-2).substring(3);
                                        weightList.add(shortenWeight);
                                    }

                                    breedList.add(testListTwo.get(i-3));

                                    if (!testListTwo.get(i-4).contains("My name is")) {
                                        nameList.add("N/A");
                                        descriptionList.add(testListTwo.get(i-4));
                                    } else {
                                        descriptionList.add(testListTwo.get(i-4));
                                        String[] split = testListTwo.get(i-4).split("and");
                                        String shortenName = split[0];
                                        String cull = shortenName.substring(11);
                                        nameList.add(cull);
                                    }

                                    if (testListTwo.get(i-4).contains("minor")) {
                                        rescueList.add(getString(R.string.minor_care));
                                    } else if (testListTwo.get(i-4).contains("major")) {
                                        rescueList.add(getString(R.string.major_care));
                                    } else {
                                        rescueList.add("");
                                    }

                                    idList.add(testListTwo.get(i-5));
                                    imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                    imgPost = testListTwo.get(i-5) + "&LOCATION=LACT2";
                                    fullImg = imgPre + imgPost;
                                    imageList.add(fullImg);
                                }
                            }
                        }

                        if (loc.equals("South LA")) {
                            if (holder.contains("South Los Angeles")) {
                                splitLoc = holder.split("Shelter");
                                shortenLoc = splitLoc[0];
                                cullLoc = shortenLoc.substring(30);
                                locationList.add(cullLoc);

                                if (testList.get(i-1).contains("Unknown")) {
                                    ageList.add("Age Unknown");
                                } else {
                                    String shortenAge = testList.get(i-1).substring(5);
                                    ageList.add(shortenAge);
                                }

                                if (testList.get(i-2).equals("0 Lbs")) {
                                    weightList.add("N/A");
                                } else {
                                    String shortenWeight = testList.get(i-2).substring(3);
                                    weightList.add(shortenWeight);
                                }

                                breedList.add(testList.get(i-3));

                                if (!testList.get(i-4).contains("My name is")) {
                                    descriptionList.add(testList.get(i-4));
                                    nameList.add("N/A");
                                } else {
                                    descriptionList.add(testList.get(i-4));
                                    String[] split = testList.get(i-4).split("and");
                                    String shortenName = split[0];
                                    String cull = shortenName.substring(11);
                                    nameList.add(cull);
                                }

                                if (testList.get(i-4).contains("minor")) {
                                    rescueList.add(getString(R.string.minor_care));
                                } else if (testList.get(i-4).contains("major")) {
                                    rescueList.add(getString(R.string.major_care));
                                } else {
                                    rescueList.add("");
                                }

                                idList.add(testList.get(i-5));
                                String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                String imgPost = testList.get(i-5) + "&LOCATION=LACT3";
                                String fullImg = imgPre + imgPost;
                                imageList.add(fullImg);

                                if (i<testListTwo.size()) {
                                    holderTwo = testListTwo.get(i);
                                    splitLoc = holderTwo.split("Shelter");
                                    shortenLoc = splitLoc[0];
                                    cullLoc = shortenLoc.substring(30);
                                    locationList.add(cullLoc);

                                    if (testListTwo.get(i - 1).contains("Unknown")) {
                                        ageList.add("Unknown");
                                    } else {
                                        String shortenAge = testListTwo.get(i-1).substring(5);
                                        ageList.add(shortenAge);
                                    }

                                    if (testListTwo.get(i-2).equals("0 Lbs")) {
                                        weightList.add("N/A");
                                    } else {
                                        String shortenWeight = testListTwo.get(i-2).substring(3);
                                        weightList.add(shortenWeight);
                                    }

                                    breedList.add(testListTwo.get(i-3));

                                    if (!testListTwo.get(i-4).contains("My name is")) {
                                        nameList.add("N/A");
                                        descriptionList.add(testListTwo.get(i-4));
                                    } else {
                                        descriptionList.add(testListTwo.get(i-4));
                                        String[] split = testListTwo.get(i-4).split("and");
                                        String shortenName = split[0];
                                        String cull = shortenName.substring(11);
                                        nameList.add(cull);
                                    }

                                    if (testListTwo.get(i-4).contains("minor")) {
                                        rescueList.add(getString(R.string.minor_care));
                                    } else if (testListTwo.get(i-4).contains("major")) {
                                        rescueList.add(getString(R.string.major_care));
                                    } else {
                                        rescueList.add("");
                                    }

                                    idList.add(testListTwo.get(i-5));
                                    imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                    imgPost = testListTwo.get(i-5) + "&LOCATION=LACT3";
                                    fullImg = imgPre + imgPost;
                                    imageList.add(fullImg);
                                }
                            }
                        }

                        if (loc.equals("Harbor")) {
                            if (holder.contains("Harbor")) {
                                splitLoc = holder.split("Shelter");
                                shortenLoc = splitLoc[0];
                                cullLoc = shortenLoc.substring(30);
                                locationList.add(cullLoc);

                                if (testList.get(i-1).contains("Unknown")) {
                                    ageList.add("Age Unknown");
                                } else {
                                    String shortenAge = testList.get(i-1).substring(5);
                                    ageList.add(shortenAge);
                                }

                                if (testList.get(i-2).equals("0 Lbs")) {
                                    weightList.add("N/A");
                                } else {
                                    String shortenWeight = testList.get(i-2).substring(3);
                                    weightList.add(shortenWeight);
                                }

                                breedList.add(testList.get(i-3));

                                if (!testList.get(i-4).contains("My name is")) {
                                    descriptionList.add(testList.get(i-4));
                                    nameList.add("N/A");
                                } else {
                                    descriptionList.add(testList.get(i-4));
                                    String[] split = testList.get(i-4).split("and");
                                    String shortenName = split[0];
                                    String cull = shortenName.substring(11);
                                    nameList.add(cull);
                                }

                                if (testList.get(i-4).contains("minor")) {
                                    rescueList.add(getString(R.string.minor_care));
                                } else if (testList.get(i-4).contains("major")) {
                                    rescueList.add(getString(R.string.major_care));
                                } else {
                                    rescueList.add("");
                                }

                                idList.add(testList.get(i-5));
                                String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                String imgPost = testList.get(i-5) + "&LOCATION=LACT1";
                                String fullImg = imgPre + imgPost;
                                imageList.add(fullImg);

                                if (i<testListTwo.size()) {
                                    holderTwo = testListTwo.get(i);

                                    splitLoc = holderTwo.split("Shelter");
                                    shortenLoc = splitLoc[0];
                                    cullLoc = shortenLoc.substring(30);
                                    locationList.add(cullLoc);

                                    if (testListTwo.get(i - 1).contains("Unknown")) {
                                        ageList.add("Unknown");
                                    } else {
                                        String shortenAge = testListTwo.get(i-1).substring(5);
                                        ageList.add(shortenAge);
                                    }

                                    if (testListTwo.get(i-2).equals("0 Lbs")) {
                                        weightList.add("N/A");
                                    } else {
                                        String shortenWeight = testListTwo.get(i-2).substring(3);
                                        weightList.add(shortenWeight);
                                    }

                                    breedList.add(testListTwo.get(i-3));

                                    if (!testListTwo.get(i-4).contains("My name is")) {
                                        nameList.add("N/A");
                                        descriptionList.add(testListTwo.get(i-4));
                                    } else {
                                        descriptionList.add(testListTwo.get(i-4));
                                        String[] split = testListTwo.get(i-4).split("and");
                                        String shortenName = split[0];
                                        String cull = shortenName.substring(11);
                                        nameList.add(cull);
                                    }

                                    if (testListTwo.get(i-4).contains("minor")) {
                                        rescueList.add(getString(R.string.minor_care));
                                    } else if (testListTwo.get(i-4).contains("major")) {
                                        rescueList.add(getString(R.string.major_care));
                                    } else {
                                        rescueList.add("");
                                    }

                                    idList.add(testListTwo.get(i-5));
                                    imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                    imgPost = testListTwo.get(i-5) + "&LOCATION=LACT1";
                                    fullImg = imgPre + imgPost;
                                    imageList.add(fullImg);
                                }
                            }
                        }
                    }

                    mListCallback.onDisplayList(imageList, idList, nameList, breedList, weightList, ageList, locationList, rescueList, descriptionList);

                    Log.i("id", idList.toString());
                    Log.i("name", nameList.toString());
                    Log.i("breed", breedList.toString());
                    Log.i("weight", weightList.toString());
                    Log.i("age", ageList.toString());
                    Log.i("location", locationList.toString());
                    Log.i("image", imageList.toString());
                    Log.i("rescue", rescueList.toString());
                    Log.i("countRescue", String.valueOf(rescueList.size()));
                    Log.i("countID", String.valueOf(idList.size()));
                    Log.i("countName", String.valueOf(nameList.size()));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
