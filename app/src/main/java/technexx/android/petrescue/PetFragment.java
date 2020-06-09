package technexx.android.petrescue;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
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

    private String holder;
    private String holderTwo;
    private int totalSize;

    private String dogs;
    private String cats;
    private String other;

    private String urlPre;
    private String url;

    private onMainMenuCallback mOnMainMenuCallback;
    private listCallback mListCallback;

    public interface onMainMenuCallback {
        void onMainMenu();
    }

    public interface listCallback {
        void onDisplayList(ArrayList<String> image, ArrayList<String> id, ArrayList<String> name, ArrayList<String> breed, ArrayList<String> weight, ArrayList<String> age, ArrayList<String> location, ArrayList<String> rescue);
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
        View root = inflater.inflate(R.layout.pet_fragment, container, false);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mOnMainMenuCallback.onMainMenu();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

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
                aSyncUriFetch();
                aSyncRetrieveAll();
            }
        });

        westValley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        eastValley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSyncUriFetch();
                aSyncRetrieveShelter("East Valley");
            }
        });

        westLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSyncUriFetch();
                aSyncRetrieveShelter("West LA");
            }
        });

        southLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSyncUriFetch();
                aSyncRetrieveShelter("South LA");
            }
        });

        northCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        harbor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSyncUriFetch();
                aSyncRetrieveShelter("Harbor");
            }
        });
        return root;
    }

    //Depending on if Dog/Cat/Other is selected, setting uri and scraping data for list.
    private void aSyncUriFetch() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (dogs != null) {
                    url = urlPre + "dog";
                }
                if (cats != null) {
                    url = urlPre + "cat";
                }
                if (other != null) {
                    url = urlPre + "other";
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
                    Elements testImage = content.select("img");

                    imageList = new ArrayList<>();
                    idList = new ArrayList<>();
                    nameList = new ArrayList<>();
                    breedList = new ArrayList<>();
                    weightList = new ArrayList<>();
                    ageList = new ArrayList<>();
                    locationList = new ArrayList<>();
                    rescueList = new ArrayList<>();

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
                        }
                        if (holder.contains("My name is")) {
                            String[] split = holder.split("and");
                            String shorten = split[0];
                            String cull = shorten.substring(11);
                            nameList.add(cull);
                            if (holder.contains("rescue")) {
                                rescueList.add(getString(R.string.rescue));
                            } else if (holder.contains("minor")) {
                                rescueList.add(getString(R.string.minor_care));
                            } else if (holder.contains("major")) {
                                rescueList.add(getString(R.string.major_care));
                            } else {
                                rescueList.add("");
                            }
                        }
                        if (holder.contains("Lbs")) {
                            String shorten = holder.substring(3);
                            weightList.add(shorten);
                        }
                        if (holder.contains("yr") || holder.contains("Age Unknown") || holder.contains("mos") || holder.contains("wks")) {
                            String shorten = holder.substring(5);
                            ageList.add(shorten);
                        }

                        if (holder.contains("Los Angeles Animal")) {
                            String[] split = holder.split("Shelter");
                            String shorten = split[0];
                            String cull = shorten.substring(30);
                            locationList.add(cull);
                        }
                        if (!holder.contains("A1") && !holder.contains("A0") && !holder.contains("My name is") && !holder.contains("Lbs") && !holder.contains("yr") && !holder.contains("Los Angeles")) {
                            breedList.add(holder);
                        }
                    }

                    for (int i=0; i < testListTwo.size(); i++) {
                        holderTwo = testListTwo.get(i);

                        if (holderTwo.contains("A1") || holderTwo.contains("A0")) {
                            idList.add(holderTwo);
                            String imgPost = null;
                            if (testList.get(i + 5).contains("Harbor")) {
                                imgPost = holderTwo + "&LOCATION=LACT1";
                            } else if (testList.get(i + 5).contains("West Los Angeles")) {
                                imgPost = holderTwo + "&LOCATION=LACT2";
                            } else if (testList.get(i + 5).contains("South Los Angeles")) {
                                imgPost = holderTwo + "&LOCATION=LACT3";
                            } else {
                                imgPost = holderTwo + "&LOCATION=LACT";
                            }
                            String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                            String fullImg = imgPre + imgPost;
                            imageList.add(fullImg);
                        }
                        if (holderTwo.contains("My name is")) {
                            String[] split = holderTwo.split("and");
                            String shorten = split[0];
                            String cull = shorten.substring(11);
                            nameList.add(cull);
                            if (holderTwo.contains("rescue")) {
                                rescueList.add(getString(R.string.rescue));
                            } else if (holderTwo.contains("minor")) {
                                rescueList.add(getString(R.string.minor_care));
                            } else if (holderTwo.contains("major")) {
                                rescueList.add(getString(R.string.major_care));
                            } else {
                                rescueList.add("");
                            }
                        }
                        if (holderTwo.contains("Lbs")) {
                            String shorten = holderTwo.substring(3);
                            weightList.add(shorten);
                        }
                        if (holderTwo.contains("yr") || holderTwo.contains("Age Unknown") || holderTwo.contains("mos") || holderTwo.contains("wks")) {
                            String shorten = holderTwo.substring(5);
                            ageList.add(shorten);
                        }
                        if (holderTwo.contains("Los Angeles Animal")) {
                            String[] split = holderTwo.split("Shelter");
                            String shorten = split[0];
                            String cull = shorten.substring(30);
                            locationList.add(cull);
                        }
                        if (!holderTwo.contains("A1") && !holderTwo.contains("A0") && !holderTwo.contains("My name is") && !holderTwo.contains("Lbs") && !holderTwo.contains("yr") && !holderTwo.contains("Los Angeles")) {
                            breedList.add(holderTwo);
                        }
                    }

                    mListCallback.onDisplayList(imageList, idList, nameList, breedList, weightList, ageList, locationList, rescueList);

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

                                String shortenAge = testList.get(i-1).substring(5);
                                ageList.add(shortenAge);

                                String shortenWeight = testList.get(i-2).substring(3);
                                weightList.add(shortenWeight);

                                breedList.add(testList.get(i-3));

                                String[] split = testList.get(i-4).split("and");
                                String shortenName = split[0];
                                String cull = shortenName.substring(11);
                                nameList.add(cull);

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
                            }
                        }

                        //LACT1 = Harbor, LACT2 = West LA, LACT3 = South LA

                        if (loc.equals("West LA")) {
                            if (holder.contains("West Los Angeles")) {
                                splitLoc = holder.split("Shelter");
                                shortenLoc = splitLoc[0];
                                cullLoc = shortenLoc.substring(30);
                                locationList.add(cullLoc);

                                String shortenAge = testList.get(i-1).substring(5);
                                ageList.add(shortenAge);

                                String shortenWeight = testList.get(i-2).substring(3);
                                weightList.add(shortenWeight);

                                breedList.add(testList.get(i-3));

                                String[] split = testList.get(i-4).split("and");
                                String shortenName = split[0];
                                String cull = shortenName.substring(11);
                                nameList.add(cull);

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
                            }
                        }

                        if (loc.equals("South LA")) {
                            if (holder.contains("South Los Angeles")) {
                                splitLoc = holder.split("Shelter");
                                shortenLoc = splitLoc[0];
                                cullLoc = shortenLoc.substring(30);
                                locationList.add(cullLoc);

                                String shortenAge = testList.get(i-1).substring(5);
                                ageList.add(shortenAge);

                                String shortenWeight = testList.get(i-2).substring(3);
                                weightList.add(shortenWeight);

                                breedList.add(testList.get(i-3));

                                String[] split = testList.get(i-4).split("and");
                                String shortenName = split[0];
                                String cull = shortenName.substring(11);
                                nameList.add(cull);

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
                            }
                        }

                        if (loc.equals("Harbor")) {
                            if (holder.contains("Harbor")) {
                                splitLoc = holder.split("Shelter");
                                shortenLoc = splitLoc[0];
                                cullLoc = shortenLoc.substring(30);
                                locationList.add(cullLoc);

                                String shortenAge = testList.get(i-1).substring(5);
                                ageList.add(shortenAge);

                                String shortenWeight = testList.get(i-2).substring(3);
                                weightList.add(shortenWeight);

                                breedList.add(testList.get(i-3));

                                String[] split = testList.get(i-4).split("and");
                                String shortenName = split[0];
                                String cull = shortenName.substring(11);
                                nameList.add(cull);

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
                            }
                        }
                    }

                    for (int i = 0; i < testListTwo.size(); i++) {
                        holderTwo = testListTwo.get(i);

                        String[] splitLoc = null;
                        String shortenLoc = null;
                        String cullLoc = null;

                        if (loc.equals("East Valley")) {
                            if (holderTwo.contains("East Valley")) {
                                splitLoc = holderTwo.split("Shelter");
                                shortenLoc = splitLoc[0];
                                cullLoc = shortenLoc.substring(30);
                                locationList.add(cullLoc);

                                String shortenAge = testListTwo.get(i-1).substring(5);
                                ageList.add(shortenAge);

                                String shortenWeight = testListTwo.get(i-2).substring(3);
                                weightList.add(shortenWeight);

                                breedList.add(testListTwo.get(i-3));

                                String[] split = testListTwo.get(i-4).split("and");
                                String shortenName = split[0];
                                String cull = shortenName.substring(11);
                                nameList.add(cull);

                                if (testListTwo.get(i-4).contains("minor")) {
                                    rescueList.add(getString(R.string.minor_care));
                                } else if (testListTwo.get(i-4).contains("major")) {
                                    rescueList.add(getString(R.string.major_care));
                                } else {
                                    rescueList.add("");
                                }

                                idList.add(testListTwo.get(i-5));
                                String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                String imgPost = testListTwo.get(i-5) + "&LOCATION=LACT";
                                String fullImg = imgPre + imgPost;
                                imageList.add(fullImg);
                            }
                        }

                        if (loc.equals("West Los Angeles")) {
                            if (holderTwo.contains("West Los Angeles")) {
                                splitLoc = holderTwo.split("Shelter");
                                shortenLoc = splitLoc[0];
                                cullLoc = shortenLoc.substring(30);
                                locationList.add(cullLoc);

                                String shortenAge = testListTwo.get(i-1).substring(5);
                                ageList.add(shortenAge);

                                String shortenWeight = testListTwo.get(i-2).substring(3);
                                weightList.add(shortenWeight);

                                breedList.add(testListTwo.get(i-3));

                                String[] split = testListTwo.get(i-4).split("and");
                                String shortenName = split[0];
                                String cull = shortenName.substring(11);
                                nameList.add(cull);

                                if (testListTwo.get(i-4).contains("minor")) {
                                    rescueList.add(getString(R.string.minor_care));
                                } else if (testListTwo.get(i-4).contains("major")) {
                                    rescueList.add(getString(R.string.major_care));
                                } else {
                                    rescueList.add("");
                                }

                                idList.add(testListTwo.get(i-5));
                                String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                String imgPost = testListTwo.get(i-5) + "&LOCATION=LACT2";
                                String fullImg = imgPre + imgPost;
                                imageList.add(fullImg);
                            }
                        }

                        if (loc.equals("South Los Angeles")) {
                            if (holderTwo.contains("South Los Angeles")) {
                                splitLoc = holderTwo.split("Shelter");
                                shortenLoc = splitLoc[0];
                                cullLoc = shortenLoc.substring(30);
                                locationList.add(cullLoc);

                                String shortenAge = testListTwo.get(i-1).substring(5);
                                ageList.add(shortenAge);

                                String shortenWeight = testListTwo.get(i-2).substring(3);
                                weightList.add(shortenWeight);

                                breedList.add(testListTwo.get(i-3));

                                String[] split = testListTwo.get(i-4).split("and");
                                String shortenName = split[0];
                                String cull = shortenName.substring(11);
                                nameList.add(cull);

                                if (testListTwo.get(i-4).contains("minor")) {
                                    rescueList.add(getString(R.string.minor_care));
                                } else if (testListTwo.get(i-4).contains("major")) {
                                    rescueList.add(getString(R.string.major_care));
                                } else {
                                    rescueList.add("");
                                }

                                idList.add(testListTwo.get(i-5));
                                String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                String imgPost = testListTwo.get(i-5) + "&LOCATION=LACT3";
                                String fullImg = imgPre + imgPost;
                                imageList.add(fullImg);
                            }
                        }

                        if (loc.equals("Harbor")) {
                            if (holderTwo.contains("Harbor")) {
                                splitLoc = holderTwo.split("Shelter");
                                shortenLoc = splitLoc[0];
                                cullLoc = shortenLoc.substring(30);
                                locationList.add(cullLoc);

                                String shortenAge = testListTwo.get(i-1).substring(5);
                                ageList.add(shortenAge);

                                String shortenWeight = testListTwo.get(i-2).substring(3);
                                weightList.add(shortenWeight);

                                breedList.add(testListTwo.get(i-3));

                                String[] split = testListTwo.get(i-4).split("and");
                                String shortenName = split[0];
                                String cull = shortenName.substring(11);
                                nameList.add(cull);

                                if (testListTwo.get(i-4).contains("minor")) {
                                    rescueList.add(getString(R.string.minor_care));
                                } else if (testListTwo.get(i-4).contains("major")) {
                                    rescueList.add(getString(R.string.major_care));
                                } else {
                                    rescueList.add("");
                                }

                                idList.add(testListTwo.get(i-5));
                                String imgPre = "https://petharbor.com/get_image.asp?RES=Detail&ID=";
                                String imgPost = testListTwo.get(i-5) + "&LOCATION=LACT1";
                                String fullImg = imgPre + imgPost;
                                imageList.add(fullImg);
                            }
                        }
                    }

                    mListCallback.onDisplayList(imageList, idList, nameList, breedList, weightList, ageList, locationList, rescueList);

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

//                    mListCallback.onDisplayList(imageList, idList, nameList, breedList, weightList, ageList, locationList);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
