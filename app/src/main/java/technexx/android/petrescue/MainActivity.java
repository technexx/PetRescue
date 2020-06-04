package technexx.android.petrescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MenuFragment.dogCallback, MenuFragment.catCallback, MenuFragment.othersCallback {

    List<String> testList = null;
    List<String> testListTwo = null;
    List<String> testListThree = null;

    ArrayList<String> imageList;
    ArrayList<String> idList;
    ArrayList<String> nameList;
    ArrayList<String> breedList;
    ArrayList<String> weightList;
    ArrayList<String> ageList;
    ArrayList<String> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        MenuFragment menuFragment = new MenuFragment();

        fm.beginTransaction()
                .add(R.id.animals, menuFragment)
                .commit();

        aSyncRetrieval();
    }

    @Override
    public void onDogCalled() {
        FragmentManager fm = getSupportFragmentManager();
        DogFragment dogFragment = new DogFragment();

        fm.beginTransaction()
                .replace(R.id.animals, dogFragment)
                .commit();
    }

    @Override
    public void onCatCalled() {
        FragmentManager fm = getSupportFragmentManager();
        CatFragment catFragment = new CatFragment();

        fm.beginTransaction()
                .replace(R.id.animals, catFragment)
                .commit();
    }

    @Override
    public void onOthersCalled() {
        FragmentManager fm = getSupportFragmentManager();
        OthersFragment othersFragment = new OthersFragment();

        fm.beginTransaction()
                .replace(R.id.animals, othersFragment)
                .commit();
    }

    private void aSyncRetrieval() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "https://petharbor.com/results.asp?searchtype=ADOPT&stylesheet=https://www.laanimalservices.com/wp-content/themes/laas/laasph.css&friends=1&samaritans=1&nosuccess=1&orderby=located%20at&rows=10&imght=120&imgres=detail&tWidth=200&view=sysadm.v_lact&nomax=1&fontface=arial&fontsize=10&miles=20&shelterlist='LACT','LACT1','LACT4','LACT3','LACT2','LACT5','LACT6'&atype=&where=type_dog&PAGE=2";

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

                    testList = content.eachText();
                    testListTwo = contentTwo.eachText();

                    for (int i=0; i<testList.size(); i++) {
                        String holder = testList.get(i);
                        String holderTwo = testListTwo.get(i);

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
                        if (holder.contains("yr")) {
                            String shorten = holder.substring(5);
                            ageList.add(shorten);
                        }
                        if (holderTwo.contains("yr")) {
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

                    Log.i("id", idList.toString());
                    Log.i("name", nameList.toString());
                    Log.i("breed", breedList.toString());
                    Log.i("weight", weightList.toString());
                    Log.i("age", ageList.toString());
                    Log.i("location", locationList.toString());
                    Log.i("image", imageList.toString());

                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("test", idList);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
