package com.example.diplomat.dijoov2;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

/**
 * Created by Diplomat on 3/13/2016.
 */
public class DijooViewHolder extends RecyclerView.ViewHolder {

    TextView dijooTitle;
    TextView dijooCategory;
    TextView dijooDailyTotal;
    TextView dijooListItemLetter;
    TextView dijooUnits;

    public DijooViewHolder(android.view.View itemView){
        super(itemView);

        dijooTitle = (TextView) itemView.findViewById(R.id.dijooItemTitle);
        dijooCategory = (TextView) itemView.findViewById(R.id.dijooItemCategory);
        dijooDailyTotal = (TextView) itemView.findViewById(R.id.total_count);
        dijooListItemLetter = (TextView) itemView.findViewById(R.id.dijoo_title_letter);
        dijooUnits = (TextView) itemView.findViewById(R.id.units_label);
    }






}
