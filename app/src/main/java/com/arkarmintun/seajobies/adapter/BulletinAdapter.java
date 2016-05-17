package com.arkarmintun.seajobies.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.activity.AgentDetailActivity;
import com.arkarmintun.seajobies.model.Bulletin;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.mmaug.mmfont.utils.FontUtils;
import org.mmaug.mmfont.utils.Fonts;

import java.util.List;

/**
 * Created by arkar on 5/11/16.
 */
public class BulletinAdapter extends RecyclerView.Adapter<BulletinAdapter.ViewHolder> {

    private List<Bulletin> feeds;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFeed;
        TextView txtContent, txtName, txtTimestamp, txtUrl;

        public ViewHolder(View v) {
            super(v);
            txtContent = (TextView) v.findViewById(R.id.text_content);
            imgFeed = (ImageView) v.findViewById(R.id.img_feed);
            txtName = (TextView) v.findViewById(R.id.text_name);
            txtTimestamp = (TextView) v.findViewById(R.id.text_timestamp);
            txtUrl = (TextView) v.findViewById(R.id.text_url);
            FontUtils fontUtils = new FontUtils(v.getContext());
            fontUtils.setTypeFace(Fonts.NOTO_REGULAR, txtContent, txtUrl);

            txtUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(txtUrl.getText().toString()));
                    v.getContext().startActivity(browserIntent);
                }
            });

            Typeface robotoRegular = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/Roboto-Regular.ttf");
            Typeface robotoLight = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/Roboto-Light.ttf");
            this.txtName.setTypeface(robotoRegular);
            this.txtTimestamp.setTypeface(robotoLight);

            txtName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AgentDetailActivity.class);
                    intent.putExtra("companyName", txtName.getText());
                    v.getContext().startActivity(intent);
                }
            });
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

        if (!feeds.get(position).url.equals("")) {
            holder.txtUrl.setText(feeds.get(position).url);
            holder.txtUrl.setVisibility(View.VISIBLE);
        }
        holder.txtName.setText(feeds.get(position).name);
        holder.txtContent.setText(feeds.get(position).content);
        holder.txtTimestamp.setText(feeds.get(position).timestamp);
        Uri uri = Uri.parse(feeds.get(position).photoUrl);
        Context context = holder.imgFeed.getContext();
        Glide.with(context)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgFeed);

    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }
}
