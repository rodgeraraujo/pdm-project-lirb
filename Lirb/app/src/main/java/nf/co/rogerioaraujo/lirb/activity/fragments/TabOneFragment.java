package nf.co.rogerioaraujo.lirb.activity.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nf.co.rogerioaraujo.lirb.R;
import nf.co.rogerioaraujo.lirb.webService.Adapter.CustomAdapter;
import nf.co.rogerioaraujo.lirb.webService.Data.DataJson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TabOneFragment extends Fragment {

    private int id;

    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<DataJson> data_list;

    private Context mContext;

    public TabOneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_one, container, false);

        // Load data from db and display in a recycler view
        RecyclerView recyclerView = new RecyclerView(mContext);
        recyclerView.findViewById(R.id.recycler_view);

        data_list  = new ArrayList<>();
        System.out.println("data then load " + data_list);
        loadDataFromServer(0); // id 0, to get the first iten from db

        System.out.println("data after load " + data_list);
        gridLayoutManager = new GridLayoutManager(mContext,2);
        
        recyclerView.hasFixedSize(); // good practice to make our layout more faster
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter(mContext, data_list);
        recyclerView.setAdapter(adapter);

        // Scroll and load data for more 4 itens
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                id = data_list.get(data_list.size()-1).getBookId();

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size()-1){
                    loadDataFromServer(id);
                }

            }
        });

        return inflater.inflate(R.layout.fragment_tab_one, container, false);
    }

    private void loadDataFromServer(final int id) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://lirb.000webhostapp.com/scriptJson.php?id=" + id)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        DataJson data = new DataJson(
                                object.getInt("bookId"),
                                object.getString("title"),
                                object.getString("author"),
                                object.getString("thumbnail"),
                                object.getString("sinopse")
                        );

                        data_list.add(data);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    //Context c = getApplicationContext();
                    //Toast.makeText(c, "No more itens to show", Toast.LENGTH_LONG).show();
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        task.execute();
    }

}