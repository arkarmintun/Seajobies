package com.arkarmintun.seajobies.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.activity.AgentDetailActivity;
import com.arkarmintun.seajobies.model.Agent;

import java.util.List;

/**
 * Created by arkar on 5/4/16.
 */
public class AgentAdapter extends RecyclerView.Adapter<AgentAdapter.ViewHolder> {

    private List<Agent> agents;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.single_agent);
            Typeface robotoRegular = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/Roboto-Regular.ttf");
            this.name.setTypeface(robotoRegular);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AgentDetailActivity.class);
                    intent.putExtra("companyName", name.getText());
                    v.getContext().startActivity(intent);
                    Log.d("RecyclerView", "onClick： " + getLayoutPosition() + name.getText());
                }
            });
        }
    }

    public AgentAdapter(List<Agent> agents) {
        this.agents = agents;
    }

    @Override
    public AgentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_agent, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(agents.get(position).name);
    }

    @Override
    public int getItemCount() {
        return agents.size();
    }
}
