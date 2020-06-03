package technexx.android.petrescue;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestScrape extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {

        List<String> testList = null;
        List<String> testListTwo = null;

        List<String> imageList;
        List<String> idList;
        List<String> descriptionList;
        List<String> breedList;
        List<String> weightList;
        List<String> ageList;
        List<String> locationList;

        //Todo: Send data over to Main.
        //Todo: Extract either (a)Just first sentence of Description scrape or (b)Sub-stringed Name and Breed Strings.
        //Todo: Extract sub-string of Weight & Age (returns extra numbers).

        try {
            String url = "https://petharbor.com/results.asp?searchtype=ADOPT&stylesheet=https://www.laanimalservices.com/wp-content/themes/laas/laasph.css&friends=1&samaritans=1&nosuccess=1&orderby=located%20at&rows=10&imght=120&imgres=detail&tWidth=200&view=sysadm.v_lact&nomax=1&fontface=arial&fontsize=10&miles=20&shelterlist='LACT','LACT1','LACT4','LACT3','LACT2','LACT5','LACT6'&atype=&where=type_dog&PAGE=2";

            Document doc = Jsoup.connect(url).get();

            //Just need the correct fetching style here.
            Elements content = doc.getElementsByClass("TableContent1");
            Elements contentTwo = doc.getElementsByClass("TableContent2");

            imageList = new ArrayList<>();
            idList = new ArrayList<>();
            descriptionList = new ArrayList<>();
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
                    descriptionList.add(holder);
                }
                if (holderTwo.contains("My name is")) {
                    descriptionList.add(holderTwo);
                }
                if (holder.contains("Lbs")) {
                    weightList.add(holder);
                }
                if (holderTwo.contains("Lbs")) {
                    weightList.add(holderTwo);
                }
                if (holder.contains("yrs")) {
                    ageList.add(holder);
                }
                if (holderTwo.contains("yrs")) {
                    ageList.add(holderTwo);
                }
                if (holder.contains("Los Angeles")) {
                    locationList.add(holder);
                }
                if (holderTwo.contains("Los Angeles")) {
                    locationList.add(holderTwo);
                }
            }

            for (Element postContent : content) {
                String outputOne = postContent.text();
                testList.add(outputOne);
            }

//            Log.i("testLog", testList.toString());
            Log.i("ids", idList.toString());
            Log.i("descriptions", descriptionList.toString());
            Log.i("weight", weightList.toString());
            Log.i("age", ageList.toString());
            Log.i("location", locationList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getList() {

    }
}