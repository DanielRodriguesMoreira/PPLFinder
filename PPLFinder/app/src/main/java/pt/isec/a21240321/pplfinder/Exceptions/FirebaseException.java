package pt.isec.a21240321.pplfinder.Exceptions;

import pt.isec.a21240321.pplfinder.R;

/**
 * Created by drmor on 30/12/2016.
 */

public class FirebaseException extends Exception {

    private int error;

    public FirebaseException(){
        this.error = R.string.firebase_error_message;
    }

    public String toString(){
        return Integer.toString(this.error);
    }
}
