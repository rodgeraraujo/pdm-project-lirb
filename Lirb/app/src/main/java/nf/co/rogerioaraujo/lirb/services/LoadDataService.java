package nf.co.rogerioaraujo.lirb.services;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import nf.co.rogerioaraujo.lirb.webService.Adapter.CustomAdapter;
import nf.co.rogerioaraujo.lirb.webService.Data.DataJson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadDataService extends AsyncTask<Integer,Void,Void>{

    private List<DataJson> data_list;
    private CustomAdapter adapter;

    @Override
    protected Void doInBackground(Integer... integers) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
//                .url("https://lirb.000webhostapp.com/api/book/listBooks.php?id=" + integers[0])
                .url("http://192.168.1.140/rodger/api/book/listBooks.php?id=" + integers[0])
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body());
            JSONArray array = new JSONArray(response.body().string());

            for (int i=0; i<array.length(); i++){

                JSONObject object = array.getJSONObject(i);

                DataJson data = new DataJson(
                        object.getInt("book_id"),
                        object.getString("book_title"),
                        object.getString("book_author"),
                        object.getString("book_cover"),
                        object.getString("book_sinopse")
                );

                data_list.add(data);
            }

            //adapter = new CustomAdapter(data_list);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
//            Context c = getApplicationContext();
//            Toast.makeText(c, "No more itens to show", Toast.LENGTH_LONG).show();
            System.out.println("End of content");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        adapter.notifyDataSetChanged();
    }
}
