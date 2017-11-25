package com.a58070096.patcharaponjoksamut.steamstalker.ViewModel;

import android.util.Log;

import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.NewsQueryModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by patcharaponjoksamut on 25/11/2017 AD.
 */

public class ProfileGameViewModel implements ValueEventListener {


    private static ProfileGameViewModel instance = new ProfileGameViewModel();

    public static ProfileGameViewModel getInstance() {
        return instance;
    }

    ArrayList<NewsQueryModel> allFollowedGame = new ArrayList<>();

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot != null) {
            Log.v("Debug", "Recived Snapshot" + dataSnapshot.toString());
            allFollowedGame = new ArrayList<>();
            for(DataSnapshot game: dataSnapshot.getChildren()) {
                NewsQueryModel newsQueryModel = new NewsQueryModel();
                newsQueryModel.setAppName((String)game.getValue());
                newsQueryModel.setAppId(game.getKey());
                allFollowedGame.add(newsQueryModel);
            }
            Log.v("Debug", "Result" + allFollowedGame.size());
            getFollowedGame();
        } else {
            allFollowedGame = new ArrayList<>();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public interface ProfileGameListener {
        void onFollowedGameResponse(ArrayList<NewsQueryModel> allFollowedGame);
    }

    private FirebaseDatabase database;

    public ProfileGameViewModel() {
        database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef = database.getReference("users/" + currentUser.getUid() +"/followedGame");
        myRef.addValueEventListener(this);
    }

    private ArrayList<ProfileGameListener> listeners = new ArrayList<>();

    public void getFollowedGame() {

        for(ProfileGameListener listener: listeners) {
            listener.onFollowedGameResponse(allFollowedGame);
        }

    }

    public void setListener(ProfileGameListener listener) {
        listeners.add(listener);
    }

    public ArrayList<NewsQueryModel> getFollowedGameRaw() {
        return allFollowedGame;
    }

    public void followGame(GameModel game) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = database.getReference("users/" + currentUser.getUid() + "/followedGame/"+game.getAppId());
        ref.setValue(game.getName());
    }

    public void unFollowGame(GameModel game) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = database.getReference("users/" + currentUser.getUid() + "/followedGame/"+game.getAppId());
        ref.removeValue();
    }

    public void likeGame(GameModel game) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = database.getReference("games/" + game.getAppId() + "/like/" + currentUser.getUid());
        ref.setValue(currentUser.getUid());
    }

    public void unlikeGame(GameModel game) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = database.getReference("games/" + game.getAppId() + "/like/" + currentUser.getUid());
        ref.removeValue();
    }

    public boolean isUserLikeGame(GameModel game) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = database.getReference("games/" + game.getAppId() + "/like/" + currentUser.getUid());
        if(ref == null) {
            return false;
        } else {
            return true;
        }
    }
}
