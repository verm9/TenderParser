package org.verm9.tenderparser.view;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.verm9.tenderparser.Controller;
import org.verm9.tenderparser.vo.Item;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by nonu on 9/9/2016.
 */
public class HtmlView implements View
{
    private Controller controller;
    private final String filePath;
    private final String[] keyWords =  {"электромонтаж", "отоплен", "вентиляц", "строительн", "монтажн",
                "кондиционирован", "сжатый воздух", "компьютерн", "сети"};

    public HtmlView()
    {
        this.filePath = "./items.html";
    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    @Override
    public void update(List<Item> vacancies)
    {
        try
        {
            String body = getUpdatedFileContent(vacancies);
            updateFile(body);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    protected Document getDocument() throws IOException {
        Document result = Jsoup.parse(new File(filePath), "UTF-8");
        return result;
    }

    private String getUpdatedFileContent(List<Item> itemList) {
        Document document;
        try
        {
            document = getDocument();

            Element template = document.select("[class*=\"template\"]").first();

            Elements previousVacancies = document.getElementsByAttributeValue("class", "item");
            for (Element e : previousVacancies) {
                if (!e.classNames().contains("template")) {
                    e.remove();
                }
            }

            for (Item i : itemList) {
                Element toAdd = template.clone();
                toAdd.removeAttr("style");
                toAdd.removeClass("template");

                toAdd.select("[class=\"description\"]").first().getElementsByTag("a").first().text(i.getDescription());
                toAdd.select("[class=\"price\"]").first().text(i.getPrice());
                toAdd.select("[class=\"published\"]").first().text(i.getPublishDate());
                toAdd.select("[class=\"endDate\"]").first().text(i.getEndDate());
                toAdd.select("[class=\"companyName\"]").first().text(i.getCompanyName());
                toAdd.select("[class=\"siteName\"]").first().text(i.getSiteName());
                toAdd.getElementsByTag("a").first().text(i.getDescription()).attr("href", i.getUrl());
                template.before(toAdd.outerHtml());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "Some exception occurred";
        }

        return document.html();
    }

    private void updateFile(String body) throws Exception{
        PrintWriter writer = new PrintWriter(filePath, "UTF-8");
        writer.print(body);
        writer.close();
    }

    public void userKeyWordsSelectEmulationMethod() {
        controller.onKeyWordSelect(keyWords);
    }
}
