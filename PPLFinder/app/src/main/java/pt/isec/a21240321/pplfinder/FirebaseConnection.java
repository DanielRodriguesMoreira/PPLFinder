package pt.isec.a21240321.pplfinder;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pt.isec.a21240321.pplfinder.Exceptions.FirebaseException;

/**
 * Created by drmor on 30/12/2016.
 */

public class FirebaseConnection {
    private DatabaseReference pplFinderReference = null;

    public FirebaseConnection() throws FirebaseException {
        this.getFirebaseReference();
    }

    public User getUserById(String id){
        DatabaseReference userReference = pplFinderReference.child(id);
        GetUserEventListener getUserEventListener = new GetUserEventListener();
        userReference.addListenerForSingleValueEvent(getUserEventListener);

        return getUserEventListener.getUser();
    }

    public void addUserToFirebase(User userToAdd){
        this.pplFinderReference.child(userToAdd.getId()).setValue(userToAdd);
    }

    private void getFirebaseReference() throws FirebaseException {
        if(this.pplFinderReference == null){
            this.pplFinderReference = FirebaseDatabase.getInstance().getReference();
            if(this.pplFinderReference == null) throw new FirebaseException();
        }
    }

    private class GetUserEventListener implements ValueEventListener {
        private User user = null;
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            this.user = dataSnapshot.getValue(User.class);
            if(this.user.getAge() > -2){
                this.getUser().getAge();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) { }

        public User getUser(){return this.user;}
    }
}
