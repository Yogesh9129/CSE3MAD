package com.example.deckadence.deck;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deckadence.R;
import com.example.deckadence.StudyActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.analytics.FirebaseAnalytics;

public class DeckAdapter extends FirestoreRecyclerAdapter<Deck,DeckAdapter.DeckHolder> {
    private Context context;
    public DeckAdapter(@NonNull FirestoreRecyclerOptions<Deck> options, Context context) {
        super(options);
        this.context = context;
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
        return new DeckHolder(v, context);
    }

    //must be public!!
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
    class DeckHolder extends RecyclerView.ViewHolder{

        private final FirebaseAnalytics mFirebaseAnalytics;
        TextView textViewTitle;
        TextView textViewLastStudied;
        TextView textViewLastStudiedDate;

        public DeckHolder(@NonNull View itemView, Context context) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.rec_deck_title);
            textViewLastStudied = itemView.findViewById(R.id.rec_last_studied);
            textViewLastStudiedDate = itemView.findViewById(R.id.rec_last_studied_date);
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"0");
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "deck_card");
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "deck");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle);
                    // open deck
					Intent intent = new Intent(context, StudyActivity.class);
                    intent.putExtra("deckID","1");
                    context.startActivity(intent);
                    // should show up when study is finished
                    Toast.makeText(context, "Study finished!", Toast.LENGTH_SHORT).show();
                }
            });
            // long click for deck menu popup (rename, add new card)
        }
    }
}
