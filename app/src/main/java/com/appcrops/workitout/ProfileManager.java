package com.appcrops.workitout;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mraavi on 21/05/17.
 */

public class ProfileManager {

    private static ProfileManager sProfileManager;
    private ProfileManager(){

    }

    public static ProfileManager instance() {
        if(sProfileManager == null) {
           sProfileManager = new ProfileManager();
        }
        return sProfileManager;
    }

    FirebaseUser mFirebaseUser;

    public void setUser(FirebaseUser firebaseUser) {
        mFirebaseUser = firebaseUser;
    }

    public String getName(){
        if(mFirebaseUser != null) {
            return mFirebaseUser.getDisplayName();
        }
        return null;
    }
}
