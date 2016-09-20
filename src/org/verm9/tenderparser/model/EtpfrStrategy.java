package org.verm9.tenderparser.model;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.verm9.tenderparser.vo.Item;

import java.io.IOException;
import java.util.*;

/**
 * Created by nonu on 9/16/2016.
 */
public class EtpfrStrategy implements Strategy {
    // No data sent with.
    private static final String URL_FORMAT_START_PAGE = "http://web.etprf.ru/BRNotification/Index?_ga=1.131529551.1136421104.1473501001";

    // Send with a big post bunch. Choose page inside the bunch.
    private static final String URL_FORMAT_FILTERED_PAGE = "http://web.etprf.ru/BRNotification?_ga=1.131529551.1136421104.1473501001&IsPartialView=1&IsTableContentOnlyRequest=1";

    // For relative urls in items.
    private static final String URL_ITEM_TEMPLATE = "http://web.etprf.ru";

    @Override
    public List<Item> getItems(String[] keyWords) {
        List<Item> result = new LinkedList<>();
        Document startPage = null;
        try {
            startPage = Jsoup.connect(String.format(URL_FORMAT_START_PAGE))
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36 OPR/39.0.2256.48")
                    .referrer("")
                    .timeout(5000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse post bunch for the filter request.
        String pageAttr = "";
        String pageAttr2 = "";
        Map<String, String> bunch = new HashMap<>();
        Element filterForm = startPage.getElementsByClass("view-form").first();
        filterForm.getElementsByAttribute("disabled").remove(); // do not process disabled inputs
        Elements inputFields = filterForm.getElementsByAttribute("name");
        for (Element e : inputFields) {
            bunch.put(e.attr("name"), e.attr("value"));
            if (e.attr("name").contains("PageNumberView")) {
                pageAttr = e.attr("name");
            }
            if (e.attr("name").contains("PageNumber") && !e.attr("name").equals(pageAttr)) {
                pageAttr2 = e.attr("name");
            }
        }
        bunch.remove("Filter.CustomerGroups");
        bunch.remove("Filter.NotificationTypeV");
        bunch.put("Filter.StartPriceFrom", "500000");
        bunch.put("Filter.StartPriceTo", "12500000");
        bunch.put("Filter.RegionRF", "Санкт-Петербург");

        // Get pages with filtered tenders while it contains these.
        Document filteredPage = null;
        int page = 1;
        while (true) {
            bunch.put(pageAttr, String.valueOf(page));
            bunch.put(pageAttr2, String.valueOf(page++));
            try {
                filteredPage = getDocument(bunch, page);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements items = filteredPage.getElementsByTag("tr");
            if (items.size() <= 2) // 2 because of one tr for metadata and one tr for footer
                break;
            for (Element e : items) {
                String description = "";
                try {
                    description = e.getElementsByTag("td").get(3).text();
                } catch (IndexOutOfBoundsException e1) {
                    // It is a header or something same.
                }
                if ( containsAnySearchWord(description, keyWords) ) {
                    System.out.println(description);
                    Item i = new Item();
                    i.setDescription(description);
                    i.setPrice( e.getElementsByTag("td").get(4).text() );
                    i.setCompanyName( e.getElementsByTag("td").get(5).text() );

                    String publishDate = e.getElementsByTag("td").get(7).text();
                    publishDate = publishDate.substring(0, 16);
                    i.setPublishDate(publishDate);

                    String endDate = e.getElementsByTag("td").get(8).text();
                    endDate = endDate.substring(0, 16);
                    i.setEndDate(endDate);

                    i.setSiteName("etpfr");
                    i.setUrl( URL_ITEM_TEMPLATE + e.getElementsByClass("RowAction").first().getElementsByTag("a").first().attr("href") );
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

    private Document getDocument(Map<String, String> bunch, int page) throws IOException {
        return Jsoup.connect(String.format(URL_FORMAT_FILTERED_PAGE))
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36 OPR/39.0.2256.48")
                .referrer("http://web.etprf.ru/BRNotification")
                .header("X-Requested-With", "XMLHttpRequest")
                .data(bunch)
                .timeout(5000)
                .method(Connection.Method.POST)
                .execute()
                .parse();
    }

}
