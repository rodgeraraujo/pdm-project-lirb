package nf.co.rogerioaraujo.lirb.services;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.webkit.MimeTypeMap;

import com.mysql.jdbc.Statement;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import nf.co.rogerioaraujo.lirb.database.MySQL;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadFiletService extends AsyncTask<String, String, String> {

    private ProgressDialog pDialog;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    private String msg;
    private String url;
    private String file;

    public UploadFiletService(Context context, String url, String file) {
        this.context = context;
        this.url = url;
        this.file = file;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pDialog = new ProgressDialog(context);
        pDialog.setTitle("Publicando...");
        pDialog.setMessage("Fazendo upload dos dados...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.setInverseBackgroundForced(true);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        Thread t = new Thread(() -> {

            File f  = new File(file);
            String content_type  = getMimeType(f.getPath());

            String file_path = f.getAbsolutePath();
            OkHttpClient client = new OkHttpClient();
            RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

            RequestBody request_body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("type",content_type)
                    .addFormDataPart("uploaded_file",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(request_body)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if(!response.isSuccessful()){
                    throw new IOException("Error : "+response);
                }

                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        t.start();
        System.out.println("Success upload");
        return "";
    }

    private String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}