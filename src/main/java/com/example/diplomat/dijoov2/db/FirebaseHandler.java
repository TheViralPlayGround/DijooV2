package com.example.diplomat.dijoov2.db;

import com.example.diplomat.dijoov2.Dijoo;
import com.example.diplomat.dijoov2.AnotherOne;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Diplomat on 2/27/2016.
 */


public class FirebaseHandler {

    String user_id;
    final String DIJOO_TABLE = "Dijoo";
    final String TITLE_COLUMN = "dijooTitle";
    final String CATEGORY_COLUMN = "dijooCategory";
    final String DIJOO_ID_COLUMN = "dijooID";
    final String UNITS_COLUMN = "dijooUnits";
    final String DIJOO_USERS = "dijooUsers";
    final String DIJOO_ALL = "allDijoos";
    final String DIJOO_CHECKIN_HEAD = "DijooCommits";
    public int dijooDailyTotal;
    ArrayList<Integer> allValues = new ArrayList<Integer>();

    LinkedHashMap<String, Dijoo> allDijoos;

    public FirebaseHandler() {
    }

    public FirebaseHandler(String userID) {
        this.user_id = userID;
    }


    public void addNewDijoo(Firebase firebase, String userID, String title, String category, String units) {

        Firebase dijoo = firebase.child(DIJOO_ALL);

        String today = new SimpleDateFormat("MMddyyyy", Locale.getDefault()).format(new Date());

        Dijoo addNewDijoo = new Dijoo(userID, today, title, category, units, today, 0, 0);

        dijoo.push().setValue(addNewDijoo);
    }


    public String getDijooByCategory(Firebase firebase, String userID) {

        String category = "";

        Firebase dijoo = firebase.child(DIJOO_USERS).child(userID);

        return category;

    }

    public void getUnits(Firebase firebase, String userID, String title) {

        String units;

        Firebase dijooFirebase = firebase.child(DIJOO_USERS).child(userID).child(title);

        Query dijooQuer = dijooFirebase.orderByChild(title).equalTo(UNITS_COLUMN);

    }

    public void getDijooKeyOrder(Firebase fb, String userID) {

        Firebase dijooFirebase = fb.child(DIJOO_USERS).child(userID);

        dijooFirebase.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot snapshot) {
                                                // do some stuff once
                                for (DataSnapshot dijooSnapshot : snapshot.getChildren()) {
                                                        allDijoos = (LinkedHashMap<String, Dijoo>) dijooSnapshot.getValue();


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {
                                                }
                                            }

        );
    }

    public void getDailyTotalForDijoo(final Firebase firebase, final String key) {


        final String upDatingKey = key;
        final Firebase refDij = firebase.child(DIJOO_ALL).child(key);
        Firebase ref = firebase.child("CheckIns");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot checkInSnapshot : dataSnapshot.getChildren()) {
                    AnotherOne checkIn = checkInSnapshot.getValue(AnotherOne.class);

                    String today = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(new Date());
                    if (Objects.equals(checkIn.getItemKey(), upDatingKey) && Objects.equals(checkIn.getCommitDate(), today)) {
                        int value = checkIn.getValueString();
                        allValues.add(value);

                    }
                }
                int addEmUp = 0;
                int numberOfRecords = allValues.size();
                for (int i = 0; i < numberOfRecords; i++) {
                    addEmUp = addEmUp + allValues.get(i);
                }
                Map<String, Object> dailyTotal = new HashMap<String, Object>();
                dailyTotal.put("dijooDailyTotal", addEmUp);
                addEmUp = 0;
                allValues.clear();
                refDij.child("dijooDailyTotal").removeValue();
                refDij.updateChildren(dailyTotal);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }


        });


    }

    public void getAllChildKeys(final Firebase fb, String node) {

        final Firebase ref = fb.child(node);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot checkInSnapshot : snapshot.getChildren()) {
                    String aKeys = checkInSnapshot.getKey();
                    String day = new SimpleDateFormat("MMddyyyy", Locale.getDefault()).format(new Date());
                    String getDate = checkInSnapshot.getValue(Dijoo.class).getDijooToday();

                    if (!Objects.equals(getDate, day)) {
                        setDailyToZero(ref, aKeys);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }


        });


    }

    public void setDailyToZero(Firebase fb, String key) {

        Map<String, Object> today = new HashMap<>();

        String day = new SimpleDateFormat("MMddyyyy", Locale.getDefault()).format(new Date());

        Firebase ref = fb.child(key);
        today.put("dijooDailyTotal", 0);
        ref.updateChildren(today);


    }

    public void deleteDijoo(Firebase allFirebase, final String key) {

        Firebase ref = allFirebase.child("allDijoos").child(key);
        final Firebase refCheckin = allFirebase.child("CheckIns");
        refCheckin.addListenerForSingleValueEvent(new ValueEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot checkInSnapshot : snapshot.getChildren()) {
                    String aKeys = checkInSnapshot.getValue(AnotherOne.class).getItemKey();
                    String pushKey = checkInSnapshot.getKey();
                    if (Objects.equals(key, aKeys)) {
                        refCheckin.child(pushKey).removeValue();
                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
            ref.removeValue();
    }

    public void addUpdateToOverallTotal(Firebase allFirebase, final String key, int updateCount) {

        final String k = "" + key;
        final int checkIn = updateCount;
        final Firebase ref = allFirebase.child(DIJOO_ALL).child(k);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                int  updateCount = dataSnapshot.getValue(Dijoo.class).getDijooOverallTotal() + checkIn;

                Map<String, Object> overallTotal = new HashMap<String, Object>();
                overallTotal.put("dijooOverallTotal", updateCount);
                ref.updateChildren(overallTotal);

                ref.removeEventListener(this);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }
//        public void updateDailyTotals(Firebase fb, String key){
//
//        int newTotal = 0;
//        Firebase checkInRef = fb.child("CheckIns").child(key);
//
//        int valueArray =  getDailyTotalForDijoo(fb, key);
//
////            for (int i= 0; i <valueArray.size(); i++){
//                newTotal = newTotal + valueArray;
////            }
//
//        Map<String, Object> dailyTotal = new HashMap<String, Object>();
//
//
//        dailyTotal.put("dijooDailyTotal", newTotal);
//    }

}

