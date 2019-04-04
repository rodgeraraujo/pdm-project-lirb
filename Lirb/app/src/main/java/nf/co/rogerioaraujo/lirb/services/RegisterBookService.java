package nf.co.rogerioaraujo.lirb.services;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nf.co.rogerioaraujo.lirb.database.MySQL;
import nf.co.rogerioaraujo.lirb.model.Book;
import nf.co.rogerioaraujo.lirb.model.User;

public class RegisterBookService extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ProgressDialog mProgressDialog;

    private Book book;
    private String msg;

    //MySQL instace
    MySQL mySQL = new MySQL();

    //query SQL
    public static final String INSERT =
            "INSERT INTO sql10286563.book_data(`book_title`, `book_edition`, `book_pubDate`, " +
                    "`book_year`, `book_author`, `book_cover`, `book_sinopse`, `book_language`, `book_isbn`, `user_id_fk`)" +
                    " VALUES(?,?,?,?,?,?,?,?,?,?)";

    public RegisterBookService(Context context, Book book) {
        this.context = context;
        this.book = book;
    }

//    @Override
//    protected void onPreExecute() {
//        mProgressDialog = ProgressDialog.show(
//                context,
//                "",
//                "Please wait, getting database...");
//    }

    @Override
    protected String doInBackground(String... strings) {
        try {

            Connection connection = mySQL.newConnection();

            if (connection == null) {
                msg = "Problema com a conexão :(";
            } else {
                PreparedStatement pstmt = connection.prepareStatement(INSERT);

                pstmt.setString(1, book.getTitle());
                pstmt.setString(2, book.getEdition());
                pstmt.setDate(3, book.getPubDate());
                pstmt.setInt(4, book.getYear());
                pstmt.setString(5, book.getAuthor());
                pstmt.setString(6, book.getCover());
                pstmt.setString(7, book.getSinopse());
                pstmt.setString(8, book.getLanguage());
                pstmt.setString(9, book.getIsbn());
                pstmt.setInt(10, book.getUser_id());

                pstmt.executeUpdate();
//                pstmt.close();

//                msg = getBookId(connection);
                msg = "Livro cadastrado com sucesso!";

                pstmt.close();
                connection.close();
                return msg;
            }
        } catch (SQLException e) {
            msg = "Houve algum problema com os dados informados";
            e.printStackTrace();
        }
        return msg;
    }

    private String getBookId(Connection conn) throws SQLException {
        String query = "SELECT * FROM book_data WHERE book_isbn = '" + book.getIsbn() +"'";

        Statement st = (Statement) conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        int id = rs.getInt("book_id");

        return String.valueOf(id);
    }
}
