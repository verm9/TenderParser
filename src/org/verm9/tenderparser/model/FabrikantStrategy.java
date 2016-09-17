package org.verm9.tenderparser.model;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.verm9.tenderparser.vo.Item;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by nonu on 9/16/2016.
 */
public class FabrikantStrategy implements Strategy {
    // Only GET data is sent.
    private static final String URL_FORMAT =
            "https://www.fabrikant.ru/trades/procedure/search/?query=&procedure_stage=0&price_from=500000&price_to=12500000&currency=1&date_type=date_publication&date_from=&date_to=&ensure=all&region%%5B0%%5D=r138&count_on_page=40&order_by=default&order_direction=1&page=%d";

    @Override
    public List<Item> getItems(String[] keyWords) {
        List<Item> result = new LinkedList<>();
        Document resultPage = null;
        int page = 1;
        while (true) {
            try {
                resultPage = getDocument(page++);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements items = resultPage.getElementsByClass("Search-list").first().getElementsByClass("Search-result-item");
            if (items.size() <= 0)
                break;
            for (Element e : items) {
                Elements itemsEntry = e.getElementsByClass("Search-item-option");
                String description = itemsEntry.get(0).getElementsByTag("a").text();
                if (containsAnySearchWord(description, keyWords)) {
                    System.out.println(description);
                    Item i = new Item();
                    i.setDescription(description);
                    // Remove a span from price div
                    itemsEntry.get(1).getElementsByTag("span").remove().first();
                    i.setPrice( itemsEntry.get(1).text() );
                    i.setCompanyName( itemsEntry.get(4).getElementsByTag("a").text() );
                    // Remove a span from price div
                    itemsEntry.get(6).getElementsByTag("span").remove().first();
                    i.setPublishDate( itemsEntry.get(6).text() );
                    // Remove a span from price div
                    itemsEntry.get(7).getElementsByTag("span").remove().first();
                    i.setEndDate(itemsEntry.get(7).text() );
                    i.setSiteName("Fabrikant");
                    i.setUrl( itemsEntry.get(0).getElementsByTag("a").first().attr("href") );
                    result.add(i);
                }
            }

        }

        return result;
    }

    private boolean containsAnySearchWord(String description, String[] keyWords) {
        for (String keyWord : keyWords) {
            if (description.contains(keyWord)) {
                return true;
            }
        }
        return false;
    }

    private Document getDocument(int page) throws IOException {
        return Jsoup.connect(String.format(URL_FORMAT, page))
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36 OPR/39.0.2256.48")
                .timeout(5000)
                .get();
    }

}
