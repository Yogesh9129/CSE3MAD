package com.example.deckadence.deck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deckadence.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DeckAdapter extends FirestoreRecyclerAdapter<Deck,DeckAdapter.DeckHolder> {
    public DeckAdapter(@NonNull FirestoreRecyclerOptions<Deck> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DeckHolder holder, int position, @NonNull Deck model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewLastStudiedDate.setText(model.getLastStudiedDate().toString().substring(0,10));
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // open deck
                }
            });
            // long click for rename deck popup maybe
        }
    }
}
