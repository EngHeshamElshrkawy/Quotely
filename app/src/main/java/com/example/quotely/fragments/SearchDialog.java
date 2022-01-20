package com.example.quotely.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.quotely.R;


public class SearchDialog extends DialogFragment {
    private EditText searchField;
    private SearchDialogListener listener;
    RadioGroup radioGroup;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (SearchDialogListener) context;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();


        View view = inflater.inflate(R.layout.dialog_search, null);
        searchField = view.findViewById(R.id.searchField);
        radioGroup = view.findViewById(R.id.searchRadioGroup);

        builder.setView(view)
                .setTitle("Search")
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String query = String.valueOf(searchField.getText());
                        listener.getQuery(query, radioGroup.getCheckedRadioButtonId());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SearchDialog.this.getDialog().cancel();
                    }
                })
                .setNeutralButton("Reset", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.reset();
                    }
                });
        return builder.create();
    }


    public interface SearchDialogListener{
        void getQuery(String query, int id);
        void reset();
    }

}
