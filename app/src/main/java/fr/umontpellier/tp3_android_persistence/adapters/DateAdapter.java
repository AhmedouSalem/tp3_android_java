package fr.umontpellier.tp3_android_persistence.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.tp3_android_persistence.R;
import fr.umontpellier.tp3_android_persistence.models.DateItem;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    public interface OnDateSelectedListener {
        void onDateSelected(String date);
    }

    private final List<DateItem> dateList;
    private final Context context;
    private final OnDateSelectedListener listener;
    private int selectedPosition = -1;

    public DateAdapter(Context context, List<DateItem> dateList, OnDateSelectedListener listener) {
        this.context = context;
        this.dateList = new ArrayList<>(dateList);
        this.listener = listener;

        // 🔍 Détection initiale du jour sélectionné
        for (int i = 0; i < dateList.size(); i++) {
            if (dateList.get(i).isSelected()) {
                selectedPosition = i;
                break;
            }
        }
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        DateItem item = dateList.get(position);
        holder.tvDateLabel.setText(item.getLabel());

        // 🎨 Appliquer le style selon sélection
        if (item.isSelected()) {
            holder.tvDateLabel.setBackgroundResource(R.drawable.bg_date_selected);
            holder.tvDateLabel.setTextColor(context.getResources().getColor(android.R.color.white));
        } else {
            holder.tvDateLabel.setBackgroundResource(R.drawable.bg_date_unselected);
            holder.tvDateLabel.setTextColor(context.getResources().getColor(R.color.primaryBackground));
        }

        // 🖱️ Gérer la sélection de date
        holder.itemView.setOnClickListener(v -> {
            if (selectedPosition != position) {
                if (selectedPosition >= 0 && selectedPosition < dateList.size()) {
                    dateList.get(selectedPosition).setSelected(false);
                }

                item.setSelected(true);
                selectedPosition = position;
                notifyDataSetChanged();
                listener.onDateSelected(item.getFullDate());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    // ✅ Appelé quand tu veux afficher un nouveau mois ou une nouvelle année
    public void updateDates(List<DateItem> newDates) {
        this.dateList.clear();
        this.dateList.addAll(newDates);

        // Trouver le nouvel index sélectionné
        selectedPosition = -1;
        for (int i = 0; i < newDates.size(); i++) {
            if (newDates.get(i).isSelected()) {
                selectedPosition = i;
                break;
            }
        }

        notifyDataSetChanged();
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView tvDateLabel;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateLabel = itemView.findViewById(R.id.tvDateLabel);
        }
    }
}
