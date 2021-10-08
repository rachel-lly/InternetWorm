package com.example.internetworm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.internetworm.databinding.GoodsItemBinding;

import java.util.ArrayList;

class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

    private ArrayList<Article> articles;
    private Context context;


    public GoodsAdapter(ArrayList<Article> articles,Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public GoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(GoodsItemBinding.inflate(LayoutInflater.from(context)).getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsAdapter.ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.author.setText(article.getAuthor());
        Glide.with(context).load(article.getImgUrl()).into(holder.img);
        holder.title.setText(article.getTitle());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView title;
        private ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
        }
    }
}
