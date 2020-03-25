package com.example.databasemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasemanagement.R;
import com.example.databasemanagement.models.League;

import java.util.List;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder> {
    private List<League> allLeagues;
    private LayoutInflater mLayoutInflater;

    public LeagueAdapter(Context context, List<League> allLeagues) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.allLeagues = allLeagues;
    }

    @NonNull
    @Override
    public LeagueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.league_entry_item, parent, false);
        return new LeagueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueViewHolder holder, int position) {
        League currentLeague = allLeagues.get(position);
        holder.entryTextView.setText(currentLeague.name);
    }

    @Override
    public int getItemCount() {
        if (allLeagues == null) {
            return 0;
        }
        return allLeagues.size();
    }

    public class LeagueViewHolder extends RecyclerView.ViewHolder {
        TextView entryTextView;

        public LeagueViewHolder(@NonNull View itemView) {
            super(itemView);
            entryTextView = itemView.findViewById(R.id.league_entry_item);
        }
    }

    public void setLeagueList(List<League> allLeagues) {
        this.allLeagues = allLeagues;
        notifyDataSetChanged();
    }

    public List<League> getAllLeagues() {
        return allLeagues;
    }

}
