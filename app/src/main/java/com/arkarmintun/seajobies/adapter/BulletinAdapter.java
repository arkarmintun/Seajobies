package com.arkarmintun.seajobies.adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.model.Bulletin;

import java.util.List;

/**
 * Created by arkar on 5/11/16.
 */
public class BulletinAdapter extends RecyclerView.Adapter<BulletinAdapter.ViewHolder> {

    private List<Bulletin> feeds;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtContent;
        ImageView imgFeed;

        public ViewHolder(View v) {
            super(v);
            txtContent = (TextView) v.findViewById(R.id.text_content);
            imgFeed = (ImageView) v.findViewById(R.id.img_feed);
//            content = (TextView) v.findViewById(R.id.single_agent);
//            Typeface robotoRegular = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/Roboto-Regular.ttf");
//            this.content.setTypeface(robotoRegular);

//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(v.getContext(), AgentDetailActivity.class);
//                    intent.putExtra("companyName", name.getText());
//                    v.getContext().startActivity(intent);
//                    Log.d("RecyclerView", "onClick： " + getLayoutPosition() + name.getText());
//                }
//            });
        }
    }

    public BulletinAdapter(List<Bulletin> feeds) {
        this.feeds = feeds;
    }

    @Override
    public BulletinAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_feed, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txtContent.setText(feeds.get(position).content);
        holder.imgFeed.setImageBitmap(feeds.get(position).photo);
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }
}
