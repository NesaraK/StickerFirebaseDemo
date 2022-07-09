package edu.neu.madcourse.firebasedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import edu.neu.madcourse.firebasedemo.realtimedatabase.models.User;
public class LinkAdapter extends RecyclerView.Adapter<LinkViewHolder> {
    private final List<User> link;
    private final Context context;
    //private final LinkViewInterface linkviewinterface;



    public LinkAdapter(List<User> records, Context context){
        this.link = records;
        this.context = context;
        //this.linkviewinterface = linkviewinterface;
    }

    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new LinkViewHolder(LayoutInflater.from(context).inflate(R.layout.item_link, null));
    }

    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position){
        holder.bindThisData(link.get(position));
    }

    public int getItemCount(){
        return link.size();
    }
}

