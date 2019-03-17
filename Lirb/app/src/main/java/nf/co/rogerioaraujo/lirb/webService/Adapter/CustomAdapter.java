package nf.co.rogerioaraujo.lirb.webService.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import nf.co.rogerioaraujo.lirb.R;
import nf.co.rogerioaraujo.lirb.activity.BookActivity;
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
//        holder.txtAuthor.setText(dataJson.get(position).getAuthor());
//        holder.txtSinopse.setText(dataJson.get(position).getSinopse());
        Glide.with(context).load(dataJson.get(position).getThumbnail()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookActivity.class);

                // parsing data to the book activity
                intent.putExtra("Title", dataJson.get(position).getTitle());
                intent.putExtra("Author", dataJson.get(position).getAuthor());
                intent.putExtra("Sinopse", dataJson.get(position).getSinopse());
                intent.putExtra("Thumbnail", dataJson.get(position).getThumbnail());

                // start the activity
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataJson.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle, txtAuthor, txtSinopse;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
//            txtAuthor = itemView.findViewById(R.id.txtAuthor);
//            txtSinopse = itemView.findViewById(R.id.txtSinopse);
            imageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
