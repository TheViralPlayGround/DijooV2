package com.example.diplomat.dijoov2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.diplomat.dijoov2.Stling.SimpleDividerItemDecoration;
import com.example.diplomat.dijoov2.db.FirebaseHandler;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class HomeActivity extends BaseActivity {

    RecyclerView recyclerView;
    BaseActivity mBaseActivity;
    SharedPreferences settings;
    ImageButton addNewIcon;
    ImageView dijooListImage;
    FirebaseRecyclerAdapter mAdapter;
    Firebase dijooRecBase;

    FragmentManager fm;

    LinearLayoutManager linearLayoutManager;
    String userID = "jv";

    LinkedHashMap<String, Dijoo> allDijoos;
    private static final int CONTENT_VIEW_ID = 10101010;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.dijooToolbar);
        setSupportActionBar(toolbar);
        final String PREFS_NAME = "MyPrefsFile";

        context = this.getApplicationContext();
        settings = getSharedPreferences(PREFS_NAME, 0);
        mBaseActivity = new BaseActivity();
        context = getApplicationContext();
        dijooFireBase.setAndroidContext(context);
        dijooFireBase = new Firebase("https://luminous-inferno-8047.firebaseio.com/");
        fbHandler = new FirebaseHandler();
        linearLayoutManager = new LinearLayoutManager(context);

        dijooRecBase = dijooFireBase.child("allDijoos");
        dijooListImage = (ImageView) findViewById(R.id.dijoo_list_item_image);
        buildToolBar(toolbar);
        setFirebaseAdapter();
        setOnClickForeRec();


        fbHandler.resetDailyTotal(dijooFireBase, "allDijoos");

//        setDailyValuesToZero(dijooFireBase, fbHandler,allKeysForDijoos);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));

//        if (settings.getBoolean("my_first_time", true)) {
//            settings.edit().putBoolean("my_first_time", false).commit();
//        }
    }

    private void setDailyValuesToZero(Firebase fb, FirebaseHandler fbHandler, ArrayList<String> allKeys){
        ArrayList<String> KK;
            KK = allKeys;
        Firebase ref = fb.child("allDijoos");
        for(int i = 0; i <KK.size(); i++) {
            fbHandler.setDailyToZero(ref,allKeys.get(i));
        }
    }

    private void updateDailyTotals(FirebaseHandler fb, ArrayList<String> allKeys) {

        for (int i = 0; i<allKeys.size(); i++) {
            fb.getDailyTotalForDijoo(dijooFireBase, allKeys.get(i));
        }
    }

    public void setFirebaseAdapter() {

        recyclerView = (RecyclerView) findViewById(R.id.dijoo_list_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new FirebaseRecyclerAdapter<Dijoo, DijooViewHolder>(Dijoo.class, R.layout.dijoo_item_layout, DijooViewHolder.class, dijooRecBase.orderByChild("dijooTitle")) {



            @Override
            protected void populateViewHolder(DijooViewHolder viewHolder, Dijoo dijoo, int position) {

                viewHolder.dijooTitle.setText(dijoo.getDijooTitle());
                viewHolder.dijooCategory.setText(dijoo.getDijooCategory());
                viewHolder.dijooDailyTotal.setText("" + dijoo.getDijooDailyTotal());
                viewHolder.dijooListItemLetter.setText(dijoo.getDijooTitle().substring(0,1));
                viewHolder.dijooUnits.setText(dijoo.getDijooUnits());
            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    private void setOnClickForeRec (){


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        String key = mAdapter.getRef(position).getKey();

                        checkInDialog(key);
                    }
                }));
    }

     private void checkInDialog (String key) {

         Firebase fbref = dijooFireBase;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        CheckInDialogFragment newFragment = CheckInDialogFragment.newInstance(key, fbref);
        newFragment.show(ft, "dialog");

     }


    private void buildToolBar(android.support.v7.widget.Toolbar toolbar) {

        Resources resources = this.getResources();
        int white = resources.getColor(R.color.white);
        int blue = resources.getColor(R.color.material_green_200);
        toolbar.setSubtitleTextColor(white);
        toolbar.setTitleTextColor(white);
        toolbar.setBackgroundColor(blue);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        mAdapter.cleanup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.add_new_dijoo_icon:   //this item has your app icon

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("addDijooFragment");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                // Create and show the dialog.
                AddDijooFragment newFragment = AddDijooFragment.newInstance(userID);
                newFragment.show(ft, "addDijooFragment");

                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
