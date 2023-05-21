package com.example.deckadence.deck;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deckadence.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;;

public class DeckAdapter extends FirestoreRecyclerAdapter<Deck,DeckAdapter.DeckHolder> {
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    public DeckAdapter(@NonNull FirestoreRecyclerOptions<Deck> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DeckHolder holder, int position, @NonNull Deck model) {
        holder.textViewTitle.setText(model.getTitle());
        Log.d("deck", "Model " + model.toString());
        holder.textViewLastStudiedDate.setText(model.getDate());
    }

    @NonNull
    @Override
    public DeckHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_card, parent, false);
        return new DeckHolder(v);
    }

    //must be public!!
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
    class DeckHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        TextView textViewLastStudied;
        TextView textViewLastStudiedDate;

        public DeckHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.rec_deck_title);
            textViewLastStudied = itemView.findViewById(R.id.rec_last_studied);
            textViewLastStudiedDate = itemView.findViewById(R.id.rec_last_studied_date);
            Log.d("deck","new holder");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && clickListener != null)
                    {
                        clickListener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && clickListener != null)
                    {
                        longClickListener.onItemLongClick(getSnapshots().getSnapshot(position), position);
                    }
                    return false;
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.clickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.longClickListener = listener;
    }
}
