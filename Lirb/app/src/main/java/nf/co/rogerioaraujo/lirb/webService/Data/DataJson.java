package nf.co.rogerioaraujo.lirb.webService.Data;

public class DataJson {
    private String title;
    private String pubDate;
    private String pages;
    private String bookId;
    private String edition;
    private String author;
    private String thumbnail;
    private String sinopse;
    private String user;

    public DataJson(String title, String pubDate, String pages, String bookId,
                    String edition, String author, String thumbnail, String sinopse, String user) {
        this.title = title;
        this.pubDate = pubDate;
        this.pages = pages;
        this.bookId = bookId;
        this.edition = edition;
        this.author = author;
        this.thumbnail = thumbnail;
        this.sinopse = sinopse;
        this.user = user;
    }

    public DataJson(String title, String author, String thumbnail, String sinopse) {
        this.title = title;
        this.author = author;
        this.thumbnail = thumbnail;
        this.sinopse = sinopse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
