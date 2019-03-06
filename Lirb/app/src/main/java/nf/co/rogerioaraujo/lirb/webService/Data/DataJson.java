package nf.co.rogerioaraujo.lirb.webService.Data;

public class DataJson {
    private int bookId;
    private String title;
    private String pages;
    private String edition;
    private String pubDate;
    private String author;
    private String thumbnail;
    private String sinopse;
    private String userId;
    private String language;
    private String isbn;
    private String pubCompany;

    public DataJson() {
    }

    public DataJson(int bookId, String title, String pages, String edition, String pubDate,
                     String author, String thumbnail, String sinopse, String userId, String language,
                     String isbn, String pubCompany) {
        this.bookId = bookId;
        this.title = title;
        this.pages = pages;
        this.edition = edition;
        this.pubDate = pubDate;
        this.author = author;
        this.thumbnail = thumbnail;
        this.sinopse = sinopse;
        this.userId = userId;
        this.language = language;
        this.isbn = isbn;
        this.pubCompany = pubCompany;
    }

    public DataJson(int bookId, String title, String author, String thumbnail, String sinopse) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.thumbnail = thumbnail;
        this.sinopse = sinopse;
    }


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPubCompany() {
        return pubCompany;
    }

    public void setPubCompany(String pubCompany) {
        this.pubCompany = pubCompany;
    }

    @Override
    public String toString() {
        return "DataJson{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", pages='" + pages + '\'' +
                ", edition='" + edition + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", author='" + author + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", userId='" + userId + '\'' +
                ", language='" + language + '\'' +
                ", isbn='" + isbn + '\'' +
                ", pubCompany='" + pubCompany + '\'' +
                '}';
    }
}
