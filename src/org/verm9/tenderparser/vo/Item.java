package org.verm9.tenderparser.vo;

/**
 * Created by nonu on 9/5/2016.
 */
public class Item
{
    private String description;
    private String price;
    private String companyName;
    private String siteName;
    private String url;
    private String publishDate;
    private String endDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (price != null ? !price.equals(item.price) : item.price != null) return false;
        if (companyName != null ? !companyName.equals(item.companyName) : item.companyName != null) return false;
        if (siteName != null ? !siteName.equals(item.siteName) : item.siteName != null) return false;
        if (url != null ? !url.equals(item.url) : item.url != null) return false;
        if (publishDate != null ? !publishDate.equals(item.publishDate) : item.publishDate != null) return false;
        return endDate != null ? endDate.equals(item.endDate) : item.endDate == null;

    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (siteName != null ? siteName.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
