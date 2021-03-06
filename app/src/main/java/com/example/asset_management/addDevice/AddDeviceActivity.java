package com.example.asset_management.addDevice;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.example.asset_management.R;
import com.example.asset_management.connection.Connection;
import com.example.asset_management.deviceCard.SwitchEditable;
import com.example.asset_management.deviceCard.ui.reservation.DatePickerFragment;
import com.example.asset_management.jsonhandler.JsonHandler;
import com.example.asset_management.recycleViewDeviceList.Device;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.asset_management.deviceCard.ui.card.CardFragment.blockInput;
import static com.example.asset_management.deviceCard.ui.card.CardFragment.setVisibility;


/**
 * AddDeviceActivity
 * <p>
 *     Version 1.5
 * </p>
 * 11.05.2020
 * AUTHOR: Dominik Dziersan
 */

public class AddDeviceActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {
    private String clickedCalendar = "";
    Date date = new Date();

    EditText editGuarantee;
    EditText editTuev;
    EditText editUvv;
    EditText editRepair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        Toolbar toolbar = findViewById(R.id.toolbardevicecard);
        setSupportActionBar(toolbar);


        final EditText editInventoryNumber = findViewById(R.id.editInventoryNumber);
        final Spinner editStatus = findViewById(R.id.editStatus);
        final Spinner editCategory = findViewById(R.id.editCategory);
        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_item);
        editStatus.setAdapter(adapterStatus);

        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_item);
        editCategory.setAdapter(adapterCategory);


        final EditText editManufacturer = findViewById(R.id.editManufacturer);
        final EditText editModel = findViewById(R.id.editModel);
        final EditText editSerialnumber = findViewById(R.id.editSerialnumber);
        final EditText editNotes = findViewById(R.id.editNotes);
        editGuarantee = findViewById(R.id.editGuarantee);
        editTuev = findViewById(R.id.editTuev);
        editUvv = findViewById(R.id.editUvv);
        editRepair = findViewById(R.id.editRepair);

        final Button btnTuev = findViewById(R.id.btnTuev);
        final View viewTuev = findViewById(R.id.btnTuev);
        final View viewSave = findViewById(R.id.btnSave);
        final Button btnSave = findViewById(R.id.btnSave);
        final Button btnUvv = findViewById(R.id.btnUvv);
        final View viewUvv = findViewById(R.id.btnUvv);
        final Button btnRepair = findViewById(R.id.btnRepair);
        final View viewRepair = findViewById(R.id.btnRepair);
        final Button btnGuarantee = findViewById(R.id.btnGuarantee);
        final View viewGuarantee = findViewById(R.id.btnGuarantee);

        btnTuev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedCalendar = "tuev";
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Start");
            }
        });
        btnGuarantee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedCalendar = "guarantee";
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Start");
            }
        });

        btnUvv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedCalendar = "uvv";
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Start");
            }
        });

        btnRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedCalendar = "repair";
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Start");

            }
        });

        setVisibility(editTuev, btnTuev);
        setVisibility(editUvv, btnUvv);
        setVisibility(editRepair, btnRepair);
        setVisibility(editGuarantee, btnGuarantee);
        viewSave.setVisibility(View.VISIBLE);
        blockInput(editTuev);
        blockInput(editUvv);
        blockInput(editRepair);
        blockInput(editGuarantee);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String serialnumber = editSerialnumber.getText().toString();
                String manufacturer = editManufacturer.getText().toString();
                String model = editModel.getText().toString();
                String status;
                if (editStatus.getSelectedItem().toString().equals("")) {
                    status = "Verfügbar";
                } else {
                    status = editStatus.getSelectedItem().toString();
                }

                String category;
                if(editCategory.getSelectedItem().toString().equals("")){
                    category = getString(R.string.deviceCategory8);
                } else {
                    category = editStatus.getSelectedItem().toString();
                }

                String notes = editNotes.getText().toString();
                String tuev = editTuev.getText().toString();
                String uvv = editUvv.getText().toString();
                String guarantee = editGuarantee.getText().toString();

                String repair = editRepair.getText().toString();


                Device device = new Device();

                device.setSerialnumber(serialnumber);
                device.setModel(model);
                device.setStatus(status);
                device.setCategory(category);
                device.setDeviceCategory(device.getPositionCategory(getApplicationContext())+1);
                device.setManufacturer(manufacturer);
                device.setNote(notes);


                device.setDeviceStatus(device.getPosition(getApplicationContext())+1);
                SimpleDateFormat format = new SimpleDateFormat(getString(R.string.datePattern),
                        Locale.GERMAN);
                Date date = null;

                try {
                    date = format.parse(repair);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                device.setLastRepair(date);
                date = null;

                try {
                    date = format.parse(tuev);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                device.setLastTuev(date);
                date = null;

                try {
                    date = format.parse(uvv);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                device.setLastUvv(date);
                date = null;

                try {
                    date = format.parse(guarantee);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                device.setGuarantee(date);

                if(!status.equals("")
                && !manufacturer.equals("")
                && !model.equals("")
                && !serialnumber.equals("")){
                    String newDeviceNameJson = getString(R.string.newDeviceNameJSON);
                    JsonHandler.createJsonFromDevice(device, newDeviceNameJson,
                            getApplicationContext());

                    Connection connection = new Connection();
                    connection.postNewDevice(device, getApplicationContext());
                    finish();
                } else
                {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.fillConstraintsMessage),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = c.getTime();

        String calendarPicker0 = getString(R.string.calendarPicker0);
        String calendarPicker1 = getString(R.string.calendarPicker1);
        String calendarPicker2 = getString(R.string.calendarPicker2);
        String calendarPicker3 = getString(R.string.calendarPicker3);

        SimpleDateFormat format = new SimpleDateFormat(getString(R.string.datePattern),
                Locale.GERMAN);
        if(clickedCalendar.equals(calendarPicker0)){
            editTuev.setText(format.format(date));
        }
        if(clickedCalendar.equals(calendarPicker1)){
            editUvv.setText(format.format(date));
        }
        if(clickedCalendar.equals(calendarPicker2)){
            editRepair.setText(format.format(date));
        }
        if(clickedCalendar.equals(calendarPicker3)){
            editGuarantee.setText(format.format(date));
        }
    }

    @Override
    public void onBackPressed() {
        {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.discardTitle))
                    .setMessage(getString(R.string.discardText))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SwitchEditable switchEditable = new SwitchEditable(false);
                            SwitchEditable.createSwitch(switchEditable, getApplicationContext());
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}


