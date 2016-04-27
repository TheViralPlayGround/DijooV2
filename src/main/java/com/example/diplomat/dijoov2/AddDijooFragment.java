package com.example.diplomat.dijoov2;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Diplomat on 1/23/2016.
 */
public class AddDijooFragment extends DialogFragment {

    EditText editTitle;
    EditText editCategory;
    EditText editUnits;
    Button addDijooButton;
    Button cancelButton;

    String newTitle;
    String newCategory;
    String newUnits;

    String userID;
    String emptyField = "Field cannot be empty";


    static AddDijooFragment newInstance(String id) {
        AddDijooFragment f = new AddDijooFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("UserID", id);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userID = getArguments().getString("UserID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_dijoo_layout, container, false);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) v.findViewById(R.id.toolbar);
        buildToolBar(toolbar);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        editTitle = (EditText) v.findViewById(R.id.addTitleEditText);
        editCategory = (EditText) v.findViewById(R.id.addCategoryEditText);
        editUnits = (EditText) v.findViewById(R.id.addUnitsEditText);
        cancelButton = (Button) v.findViewById(R.id.cancel_button);
        addDijooButton=(Button) v.findViewById(R.id.add_submit_button);
        addDijooButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTitle.getText().toString().length() < 1) {
                    editTitle.setError(emptyField);
                    editTitle.requestFocus();
                } else if (editCategory.getText().toString().length() < 1) {
                    editCategory.setError(emptyField);
                    editCategory.requestFocus();
                } else if (editUnits.getText().toString().length() < 1) {
                    editUnits.setError(emptyField);
                    editUnits.requestFocus();
                } else if
                        (editUnits.getText().toString().length() > 14) {
                    editUnits.setError("Field must be less than 14 characters");
                    editUnits.requestFocus();

                } else {

                    newTitle = String.valueOf(editTitle.getText());
                    newCategory = String.valueOf(editCategory.getText());
                    newUnits = String.valueOf(editUnits.getText());

                    HomeActivity.fbHandler.addNewDijoo(BaseActivity.dijooFireBase, userID, newTitle, newCategory, newUnits);
                    dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }


    private void buildToolBar(android.support.v7.widget.Toolbar toolbar) {

        Resources resources = this.getResources();
        int white = resources.getColor(R.color.white);
        int black = resources.getColor(R.color.material_green_200);
        toolbar.setSubtitleTextColor(white);
        toolbar.setTitleTextColor(white);
        toolbar.setBackgroundColor(black);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        toolbar.setTitle("Add new Dijoo");
        toolbar.inflateMenu(R.menu.new_dijoo_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                    if (editTitle.getText().toString().length() < 1) {
                        editTitle.setError(emptyField);
                        editTitle.requestFocus();
                    } else if (editCategory.getText().toString().length() < 1) {
                        editCategory.setError(emptyField);
                        editCategory.requestFocus();
                    } else if (editUnits.getText().toString().length() < 1) {
                        editUnits.setError(emptyField);
                        editUnits.requestFocus();
                    } else if
                            (editUnits.getText().toString().length() > 14) {
                        editUnits.setError("Field must be less than 14 characters");
                        editUnits.requestFocus();

                    } else {

                        newTitle = String.valueOf(editTitle.getText());
                        newCategory = String.valueOf(editCategory.getText());
                        newUnits = String.valueOf(editUnits.getText());

                        HomeActivity.fbHandler.addNewDijoo(BaseActivity.dijooFireBase, userID, newTitle, newCategory, newUnits);
                        dismiss();
                    }
                return false;
            }
        });}


}
