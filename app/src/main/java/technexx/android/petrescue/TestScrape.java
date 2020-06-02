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

        List<String> image;
        List<String> id;
        List<String> description;
        List<String> breed;
        List<String> weight;
        List<String> age;
        List<String> location;

        //getElementsByClass - TableContent1 and TableContent2 are parent nodes of each dog's traits.
        //Todo: Create seven separate lists (ID, Description, etc.).
        //Todo: Scrape from Element Classes "TableContent1" and "TableContent2" for data. Use getElementsContainingText to send the data into the 7 lists.

        try {
            String url = "https://petharbor.com/results.asp?searchtype=ADOPT&stylesheet=https://www.laanimalservices.com/wp-content/themes/laas/laasph.css&friends=1&samaritans=1&nosuccess=1&orderby=located%20at&rows=10&imght=120&imgres=detail&tWidth=200&view=sysadm.v_lact&nomax=1&fontface=arial&fontsize=10&miles=20&shelterlist='LACT','LACT1','LACT4','LACT3','LACT2','LACT5','LACT6'&atype=&where=type_dog&PAGE=2";

            Document doc = Jsoup.connect(url).get();

            //Just need the correct fetching style here.
            Elements content = doc.getElementsByClass("TableContent1");
            Elements contentTwo = doc.getElementsByClass("TableContent2");
            testList = new ArrayList<>();
            testListTwo = new ArrayList<>();

            for (Element postContent : content) {
                String outputOne = postContent.text();
                testList.add(outputOne);
            }
            for (Element postContentTwo : contentTwo) {
                String outputTwo = postContentTwo.text();
                testListTwo.add(outputTwo);
            }
            Log.i("testLog", content.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getList() {

    }
}