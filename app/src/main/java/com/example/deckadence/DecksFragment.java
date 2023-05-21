package com.example.deckadence;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.deckadence.deck.Deck;
import com.example.deckadence.deck.DeckAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DecksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DecksFragment extends Fragment {
    private static final String TAG = "deck";
    private FirebaseAnalytics mFirebaseAnalytics;
    private DeckAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference deckRef = db.collection("Decks");
    private GoogleSignInAccount account;
    private View view;
    private DecksFragment.GoogleSignInInterface googleSignInInterface;

    public DecksFragment() {
        // Required empty public constructor
    }
    public static DecksFragment newInstance() {
        return new DecksFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            googleSignInInterface = (GoogleSignInInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement GoogleSignInInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_decks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        setUpRecyclerView();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
    }

    private void setUpRecyclerView() {
        account = googleSignInInterface.getGoogleSignIn();
        String token = "";
        try {
            token = account.getId();
        } catch (NullPointerException e) {
            // suppress
        }
        Log.d(TAG,"token: "+ token);
        Query query = db.collection("Deck").whereEqualTo("token",token).orderBy("title", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Deck> options = new FirestoreRecyclerOptions.Builder<Deck>()
                .setQuery(query, Deck.class)
                .build();
        adapter = new DeckAdapter(options);
        Log.d(TAG,"new adapter");
        RecyclerView recyclerView = view.findViewById(R.id.rec_view);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(null);
        Log.d(TAG,"recycler working");
        //note as we are not using up and down gestures the first argument is 0
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getBindingAdapterPosition());
                Toast.makeText(getContext(), "Deck deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new DeckAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"0");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "deck_card");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "deck");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle);
                Intent intent = new Intent(getContext(), StudyActivity.class);
                intent.putExtra("deckID", documentSnapshot.getId());
                getContext().startActivity(intent);
            }
        });
        adapter.setOnItemLongClickListener(new DeckAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(DocumentSnapshot documentSnapshot, int position) {
                PopupMenu popupMenu = new PopupMenu(getContext(),recyclerView);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Bundle bundle = new Bundle();
                        String deckID = documentSnapshot.getId();
                        bundle.putString("DECK_ID", deckID);

                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        AddCardFragment fragment = new AddCardFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.hide(DecksFragment.this);
                        fragmentTransaction.add(android.R.id.content, fragment);
                        fragmentTransaction.commit();
                        return false;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });
        adapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart(); // call superclass method before anything else
        adapter.startListening();
        Log.d(TAG,"listening for changes");
    }

    @Override
    public void onStop() {
        super.onStop(); // call superclass method before anything else
        adapter.stopListening();
    }

    public interface GoogleSignInInterface {
        GoogleSignInAccount getGoogleSignIn();
    }
}