package com.example.organaizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder> {
    private List<Item> items;
    private int layout;

    private OnDeleteClickListener onDeleteClickListener;
    private OnEditClickListener onEditClickListener;

    public NewAdapter(int layout, List<Item> items, OnDeleteClickListener onDeleteClickListener, OnEditClickListener onEditClickListener) {
        this.layout = layout;
        this.items = items;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onEditClickListener = onEditClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(layout, parent, false);
        return new ViewHolder(contactView, onDeleteClickListener, onEditClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.textViewTitle.setText(item.getTitle());
        holder.textViewDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        ImageView imageViewDelete;
        ImageView imageViewEdit;
        OnDeleteClickListener onDeleteClickListener;
        OnEditClickListener onEditClickListener;

        public ViewHolder(View itemView,  OnDeleteClickListener onDeleteClickListener, OnEditClickListener onEditClickListener) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitleOrTime);
            textViewDescription = itemView.findViewById(R.id.textViewTask);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
            imageViewEdit = itemView.findViewById(R.id.imageViewEdit);
            this.onDeleteClickListener = onDeleteClickListener;
            this.onEditClickListener = onEditClickListener;
            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClickListener.onDeleteClick(getAdapterPosition());
                }
            });
            imageViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClickListener.onEditClick(getAdapterPosition());
                }
            });
        }

    }



    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public interface OnEditClickListener {
        void onEditClick(int position);
    }
}