import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.LinkedList;

import java.net.URL;


public class Main {
    public static String URL_Dinner = "https://lewisandclark.cafebonappetit.com/#dinner";
    public static String URL_Dinner_Legacy = "https://legacy.cafebonappetit.com/print-menu/cafe/150/menu/452158/days/today/pgbrks/0/";
    public static String Get_Items(String TIME, int CATEGORY) {


        try {
            LinkedList<String> items = new LinkedList<String>();
            final Document Dinner_doc = Jsoup.connect(URL_Dinner).get(); //Get Document

            Elements day = Dinner_doc.select(TIME);
            Elements category = day.select("div.station-title-inline-block:nth-of-type(2)");
            //Grill: div.station-title-inline-block:nth-of-type(2)

            Elements category_item = category.select("button.h4.site-panel__daypart-item-title");


                category_item.forEach(el -> System.out.println(items.add(el.text())));
                StringBuilder temp = new StringBuilder();

                for (int i = 0; i < items.size();i++)
                {
                    temp.append(items.get(i)).append(",");
                }

                return temp.toString();


        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR";
        }



    }

    public static void main(String[] args) {
        System.out.println(Get_Items("#lunch",1));

    }
}

//Dressing + Knife and Fork: div.even.row:nth-of-type(2)
//Grill: div.even.row:nth-of-type(4)

//div.station-title-inline-block:nth-of-type(2)
//Deli Bar: div.odd.row:nth-of-type(3)
//Salad Bar + Kettle: div.odd.row:nth-of-type(5)
