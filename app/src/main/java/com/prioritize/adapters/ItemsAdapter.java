package com.prioritize.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.prioritize.R;
import com.prioritize.models.Task;

import java.util.List;
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{


    public static final String TAG = "ItemsAdapter";

    private List<Task> items;
    private OnLongClickListener longClickListener;
    private OnClickListener clickListener;
    Context context;
    //Ugly, but time is an issue
    private boolean redToasted = true;
    private boolean yellowToasted = true;

    public interface OnClickListener {
        void onItemClicked(int position);
    }

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    public ItemsAdapter(List<Task> items) {
        this.items = items;
    }

    public ItemsAdapter(List<Task> items, OnLongClickListener longClickListener, OnClickListener clickListener, Context context) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflater to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // wrap it inside a view holder and return it
        return new ViewHolder(todoView);
    }

    // responsible for binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Grab the item at the position
        Task item = items.get(position);
        // bind the item into the specified view holder
        holder.bind(item);
    }

    private void displayMessage(String message) { //convenience method for toasts
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // Tells the RV how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Container to provide easy access to views that represent each row of the list
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        public void bind(Task item) {
            tvItem.setText(item.getTitle());
            Log.d(TAG, "Days to due: " + item.daysToDue());
            if (item.daysToDue() < 0) {
                if (redToasted) {
                    displayMessage("Red items are overdue!");
                    redToasted = false;
                }

                tvItem.setTextColor(Color.WHITE);
                tvItem.setBackgroundColor(Color.RED);
            } else if (item.daysToDue() < 2) {
                if (yellowToasted) {
                    displayMessage("Yellow items are upcoming soon");
                    yellowToasted = false;
                }
                tvItem.setTextColor(Color.BLACK);
                tvItem.setBackgroundColor(Color.YELLOW);
            } else {
                tvItem.setTextColor(Color.BLACK);
                tvItem.setBackgroundColor(ContextCompat.getColor(context, R.color.winBackground));
            }
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Notify the listener which position was long press
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
