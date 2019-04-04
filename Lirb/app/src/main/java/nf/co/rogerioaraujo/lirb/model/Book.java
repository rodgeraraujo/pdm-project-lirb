package nf.co.rogerioaraujo.lirb.model;

import java.sql.Date;

public class Book {
    //`book_id`, `book_title`, `book_edition`, `book_pubDate`, `book_year`, `book_author`,
    // `book_cover`, `book_sinopse`, `book_language`, `book_isbn`, `book_publisher`, `user_id_fk`
    private int id;
    private String title;
    private String edition;
    private Date pubDate;
    private int year;
    private String author;
    private String cover;
    private String sinopse;
    private String language;
    private String isbn;
    private String publisher;
    private int user_id;

    public Book() {
    }

    public Book(int id, String title, String edition, Date pubDate, int year, String author,
                String cover, String sinopse, String language, String isbn, String publisher, int user_id) {
        this.id = id;
        this.title = title;
        this.edition = edition;
        this.pubDate = pubDate;
        this.year = year;
        this.author = author;
        this.cover = cover;
        this.sinopse = sinopse;
        this.language = language;
        this.isbn = isbn;
        this.publisher = publisher;
        this.user_id = user_id; this.title = title;
        this.edition = edition;
        this.pubDate = pubDate;
        this.year = year;
        this.author = author;
        this.cover = cover;
        this.sinopse = sinopse;
        this.language = language;
        this.isbn = isbn;
        this.publisher = publisher;
        this.user_id = user_id;
    }

    public Book(String titulo, String edicao, Date pubDate, int ano, String autor, String cover,
                String sinopse, String idioma, String isbn, int user_id) {
        this.title = titulo;
        this.edition = edicao;
        this.pubDate = pubDate;
        this.year = ano;
        this.author = autor;
        this.cover = cover;
        this.sinopse = sinopse;
        this.language = idioma;
        this.isbn = isbn;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", edition='" + edition + '\'' +
                ", pubDate=" + pubDate +
                ", year=" + year +
                ", author='" + author + '\'' +
                ", cover='" + cover + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", language='" + language + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
