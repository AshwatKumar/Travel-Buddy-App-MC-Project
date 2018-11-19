package com.example.application.travelbuddyapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.application.travelbuddyapp.R;

import java.util.Calendar;

public class StayDialog extends AppCompatDialogFragment {

    EditText editHostName, editPlace, editDate, editCity;
    ImageButton calendarImageButton;
    private  StayDialogListener listner;
    private int mYear, mMonth, mDay;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addstaydialog, null);

        editHostName = (EditText) view.findViewById(R.id.editHostName);
        editPlace = (EditText) view.findViewById(R.id.editPlace);
        editDate = (EditText) view.findViewById(R.id.editDate);
        editCity = (EditText) view.findViewById(R.id.editCity);
        calendarImageButton = (ImageButton) view.findViewById(R.id.calendarImageButton);

        calendarImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().dismiss();
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hostName = editHostName.getText().toString();
                String place = editPlace.getText().toString();
                String date = editDate.getText().toString();
                String city = editCity.getText().toString();
                if (editHostName.getText().toString().equals("") ||
                        editPlace.getText().toString().equals("") ||
                        editDate.getText().toString().equals("") ||
                        editCity.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please fill all fields \n   Nothing to Add", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    listner.sendBackToFragment(hostName, place, date, city);
                    getDialog().dismiss();
                }
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listner = (StayDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement StayDialogListner");
        }
    }

    public interface StayDialogListener{
        void sendBackToFragment(String hostName, String Place, String Date, String City);
    }
}