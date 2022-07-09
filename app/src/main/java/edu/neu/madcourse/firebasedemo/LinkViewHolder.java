package edu.neu.madcourse.firebasedemo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.neu.madcourse.firebasedemo.realtimedatabase.models.User;
public class LinkViewHolder extends RecyclerView.ViewHolder
{
    public TextView nameTV;
    public TextView urlTV;
    public ImageView myImg;

    public LinkViewHolder(@NonNull View itemView){
        super((itemView));
        this.nameTV = itemView.findViewById(R.id.name);
        this.urlTV = itemView.findViewById(R.id.url);
        this.myImg = itemView.findViewById(R.id.imageView);
        /*itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linkviewinterface != null){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        linkviewinterface.onItemClick(pos);
                    }
                }
            }
        });*/
    }

    public void  bindThisData(User theItemToBind){
        nameTV.setText(theItemToBind.username);
        urlTV.setText(theItemToBind.score);

        int index = Integer.parseInt(theItemToBind.score);
        if(index%2 == 0) {
            myImg.setImageResource(R.drawable.thinking_face);
        }else{
            myImg.setImageResource(R.drawable.foo);
        }
    }
}
