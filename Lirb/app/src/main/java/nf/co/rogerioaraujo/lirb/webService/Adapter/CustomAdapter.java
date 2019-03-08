package nf.co.rogerioaraujo.lirb.webService.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import nf.co.rogerioaraujo.lirb.R;
import nf.co.rogerioaraujo.lirb.activity.HomeActivity;
import nf.co.rogerioaraujo.lirb.activity.fragments.TabOneFragment;
import nf.co.rogerioaraujo.lirb.webService.Data.DataJson;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Context context;
    private List<DataJson> dataJson;

    public CustomAdapter(Context context, List<DataJson> dataJson) {
        this.context = context;
        this.dataJson = dataJson;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtTitle.setText(dataJson.get(position).getTitle());
        holder.txtAuthor.setText(dataJson.get(position).getAuthor());
        holder.txtSinopse.setText(dataJson.get(position).getSinopse());
        Glide.with(context).load(dataJson.get(position).getThumbnail()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataJson.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtTitle, txtAuthor, txtSinopse;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtSinopse = itemView.findViewById(R.id.txtSinopse);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
