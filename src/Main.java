import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.LinkedList;

import java.net.URL;
import java.util.Objects;

/*
KEY
Categories:
1: Grill
2: Knife and Fork
3: Sunrise grill - breakfast only
4:

Times:
#breakfast
#brunch - weekends only
#lunch
#dinner



 */
public class Main {
    public static String URL_Dinner = "https://lewisandclark.cafebonappetit.com/#dinner";
    public static String URL_Dinner_Legacy = "https://legacy.cafebonappetit.com/print-menu/cafe/150/menu/452158/days/today/pgbrks/0/";


    public static String grill = "div.station-title-inline-block:nth-of-type(2)";
    public static String Knife_and_Fork = "div.station-title-inline-block:nth-of-type(3)";
    public static String Sunrise_Grill = "div.station-title-inline-block:nth-of-type(4)";
    public static String Bakery1 = "div.station-title-inline-block:nth-of-type(8)";
    public static String Bakery2 = "div.site-panel__daypart-item:nth-of-type(9)";

    public static String Bakery3 = "div.site-panel__daypart-item:nth-of-type(10)" ;
    public static String Bakery4 = "div.site-panel__daypart-item:nth-of-type(11)" ;
    public static String LinkedList_To_String(LinkedList list)
    {
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            temp.append(list.get(i)).append(",");
        }
        return temp.toString();

    }

    public static LinkedList Scrape(String TIMEX, int CATEGORYX, String Block_Val) {
        LinkedList<String> items = new LinkedList<String>();
            try {

                final Document Dinner_doc = Jsoup.connect(URL_Dinner).get(); //Get Document

                Elements day = Dinner_doc.select(TIMEX);
                Elements category = day.select(Block_Val);
                //Grill: div.station-title-inline-block:nth-of-type(2)
                //##div.site-panel__daypart-item:nth-of-type(10)

                Elements category_item = category.select("button.h4.site-panel__daypart-item-title");


                category_item.forEach(el -> System.out.println(items.add(el.text())));
                return items;

            } catch (Exception ex) {
                ex.printStackTrace();
                return items;
            }
        }


    public static String Get_Items(String TIME, int CATEGORY) {

        switch (CATEGORY) {

            case 1: { //Grill or Bakery
                LinkedList<String> item_list = new LinkedList<String>();
                item_list.addAll(0,Scrape(TIME,CATEGORY,grill));
               return LinkedList_To_String(item_list);

            }

            case 2: //Knife and Fork
            {
                LinkedList<String> item_list = new LinkedList<String>();
                item_list.addAll(0,Scrape(TIME,CATEGORY,Knife_and_Fork));
               return LinkedList_To_String(item_list);
            }

            case 3:  //Sunrise grill - breakfast only
            {
                LinkedList<String> item_list = new LinkedList<String>();
                item_list.addAll(0,Scrape(TIME,CATEGORY,Sunrise_Grill));
                return LinkedList_To_String(item_list);
            }


            case 4:  //Bakery
            {
                LinkedList<String> item_list = new LinkedList<String>();

                //Bakery section is split into 3 sections each with two items
                item_list.addAll(item_list.size(),Scrape(TIME,CATEGORY,Bakery1));
                item_list.addAll(item_list.size(),Scrape(TIME,CATEGORY,Bakery2));
                item_list.addAll(item_list.size(),Scrape(TIME,CATEGORY,Bakery3));
                item_list.addAll(item_list.size(),Scrape(TIME,CATEGORY,Bakery4));
                for (int i = 0; i < item_list.size();i++)
                {
                //For Some reason Dressings and Desserts are grouped together
                    if (item_list.get(i).contains("Oil") || item_list.get(i).contains("Vinegar") || item_list.get(i).contains("Dressing")) {
                //This is a quick and dirty way to remove dressings but should find a more longterm solution in the future
                        System.out.println("Removing: " + item_list.get(i));
                        item_list.remove(i);
                    }


                    else {
                        continue;
                    }

                }
                System.out.println("Total Items in List: " + item_list.size());
                return LinkedList_To_String(item_list);

            }



        }
        return "ERROR";

    }
    public static void main(String[] args) {
        System.out.println(Get_Items("#lunch",4));

    }
}

//Dressing + Knife and Fork: div.even.row:nth-of-type(2)
//Grill: div.even.row:nth-of-type(4)
//div.station-title-inline-block:nth-of-type(2)
//Deli Bar: div.odd.row:nth-of-type(3)
//Salad Bar + Kettle: div.odd.row:nth-of-type(5)
