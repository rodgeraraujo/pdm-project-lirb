package nf.co.rogerioaraujo.lirb.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import nf.co.rogerioaraujo.lirb.R;

public class ReaderActivity extends Activity {

    private WebView web;
    private ProgressDialog dialog;
    private String book_url;

    @SuppressLint("SetJavaScriptEnabled")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

//        getSupportActionBar().setTitle("Nome da Obra");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get title name from user input
        Intent intent = getIntent();
        String book_name = intent.getExtras().getString("URI");

        book_url = "http://192.168.1.140/rodger/api/reader/bib/i/?book=" + book_name;

        //Hide title bar - this should be done before SetContentView
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_reader);

        // web view settings
        web = findViewById(R.id.webview);
        WebSettings webSettings = web.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        web.setWebViewClient(new WebViewClient() {
            // This method will be triggered when the Page Started Loading
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                dialog = ProgressDialog.show(ReaderActivity.this, null,
                        "Aguarde... Estamos carregando seu livro...");
                dialog.setCancelable(true);
                super.onPageStarted(view, url, favicon);
            }

            // This method will be triggered when the Page loading is completed

            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
                super.onPageFinished(view, url);
            }

            // This method will be triggered when error page appear
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                dialog.dismiss();
                // You can redirect to your own page instead getting the default
                // error page
                Toast.makeText(ReaderActivity.this,
                        "The Requested Page Does Not Exist", Toast.LENGTH_LONG).show();
                web.loadUrl("http://arunimmanuel.blogspot.in");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        web.loadUrl(book_url);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}