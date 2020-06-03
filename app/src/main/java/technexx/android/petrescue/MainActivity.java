package technexx.android.petrescue;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    List<String> testList = null;
    List<String> testListTwo = null;

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

        aSyncRetrieval();
    }


    private void aSyncRetrieval() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                //Todo: Send data over to Main.
                //Todo: Use split() on substrings calling the first "." index char to scrape the Breed.

                try {
                    String url = "https://petharbor.com/results.asp?searchtype=ADOPT&stylesheet=https://www.laanimalservices.com/wp-content/themes/laas/laasph.css&friends=1&samaritans=1&nosuccess=1&orderby=located%20at&rows=10&imght=120&imgres=detail&tWidth=200&view=sysadm.v_lact&nomax=1&fontface=arial&fontsize=10&miles=20&shelterlist='LACT','LACT1','LACT4','LACT3','LACT2','LACT5','LACT6'&atype=&where=type_dog&PAGE=2";

                    Document doc = Jsoup.connect(url).get();

                    //Just need the correct fetching style here.
                    Elements content = doc.getElementsByClass("TableContent1");
                    Elements contentTwo = doc.getElementsByClass("TableContent2");

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
                        }
                        if (holderTwo.contains("A1")) {
                            idList.add(holderTwo);
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

                    for (Element postContent : content) {
                        String outputOne = postContent.text();
                        testList.add(outputOne);
                    }

//            Log.i("testLog", testList.toString());
                    Log.i("id", idList.toString());
                    Log.i("name", nameList.toString());
                    Log.i("breed", breedList.toString());
                    Log.i("weight", weightList.toString());
                    Log.i("age", ageList.toString());
                    Log.i("location", locationList.toString());

                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("test", idList);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
