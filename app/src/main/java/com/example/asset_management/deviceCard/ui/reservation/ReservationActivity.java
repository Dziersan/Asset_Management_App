package com.example.asset_management.deviceCard.ui.reservation;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.asset_management.R;
import com.example.asset_management.connection.Connection;
import com.example.asset_management.jsonhandler.JsonHandler;
import com.example.asset_management.login.UserInfo;
import com.example.asset_management.mainHub.MainHubActivity;
import com.example.asset_management.recycleViewDeviceList.Device;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * ReservationActivity
 * <p>
 *     Version 1.0
 * </p>
 * 11.05.2020
 * AUTHOR: Dominik Dziersan
 */

public class ReservationActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {
    private boolean isStart;

    ArrayList<Reservation> list = new ArrayList<>();
    Reservation reservation = new Reservation();
    UserInfo user = new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Button buttonStart = findViewById(R.id.btnStartDate);
        Button buttonReserve = findViewById(R.id.btnReserve);
        Button buttonEnd = findViewById(R.id.btnEndDate);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Start");
                isStart = true;
            }
        });

        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textStart = findViewById(R.id.textReservationStart);
                TextView textEnd = findViewById(R.id.textReservationEnd);
                String start = textStart.getText().toString();
                String end = textEnd.getText().toString();
                try {
                    reservation.isCorrectFilled(start, end, reservation,getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EditText editProjectId = findViewById(R.id.editProjectId);
                String projectId = editProjectId.getText().toString();
                int projectIdInt = 0;
                try{
                    projectIdInt = Integer.parseInt(projectId);
                    reservation.setProjectId(projectIdInt);
                } catch (Exception e){

                }


                if(projectIdInt == 0){
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                } else {
                    Connection connection = new Connection();
                    connection.postNewReservation(reservation, getApplicationContext());
                    finish();
                }

            }
        });

        buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "End");
                isStart = false;
            }
        });
    }

    /**
     * Sets the date to the textviews
     * @param view
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        TextView textStart = findViewById(R.id.textReservationStart);
        TextView textEnd = findViewById(R.id.textReservationEnd);
        EditText editConstruction = findViewById(R.id.editConstruction);
        String inventoryNumber = null;

        reservation.setBuildingSite(editConstruction.getText().toString());
        String deviceNameJson = getString(R.string.deviceNameJSON);
        try {
            Device device = JsonHandler.getDevice
                    (deviceNameJson,this);
            inventoryNumber = device.getInventoryNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }

        user = MainHubActivity.getUser();
        reservation.setWorkerId(user.getWorkerId());
        reservation.setInventoryNumber(inventoryNumber);
        SimpleDateFormat format = new SimpleDateFormat(getString(R.string.datePattern));
        Date date = new Date();

        //If DatePicker of START is clicked
        if(isStart){
            date = c.getTime();
            textStart.setText(format.format(date));
            reservation.setStart(c);
            reservation.setLoanDay(date);
        } else {
            if(reservation.getStart().compareTo(c) <= 0
            ){
                date = c.getTime();
                textEnd.setText(format.format(date));
                reservation.setLoanEnd(date);
                reservation.setStart(null);
                list.add(reservation);
                JsonHandler.createJsonFromCalendarList(list,getString(R.string.reservationNameJSON),
                        this);
            } else {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                Toast.makeText(getApplicationContext(),getString(R.string.failedToReserve),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
