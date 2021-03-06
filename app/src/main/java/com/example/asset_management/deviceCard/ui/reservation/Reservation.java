package com.example.asset_management.deviceCard.ui.reservation;

import android.content.Context;
import android.widget.Toast;

import com.example.asset_management.R;
import com.example.asset_management.connection.Connection;
import com.example.asset_management.jsonhandler.JsonHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Reservation {
    Date loanDay;
    Date loanEnd;
    String name;
    String surname;
    int projectId;
    String buildingSite;
    String inventoryNumber;
    int workerId;

    Calendar start;
    Calendar end;

    public Reservation(String inventoryNumber, Calendar start, Calendar end, int workerId) {
        this.inventoryNumber = inventoryNumber;
        this.start = start;
        this.end = end;
        this.workerId = workerId;
    }

    public Reservation(Date loanDay, Date loanEnd, String name, String surname, int projectId,
                       String buildingSite, String inventoryNumber, Calendar start, Calendar end) {
        this.loanDay = loanDay;
        this.loanEnd = loanEnd;
        this.name = name;
        this.surname = surname;
        this.projectId = projectId;
        this.buildingSite = buildingSite;
        this.inventoryNumber = inventoryNumber;
        this.start = start;
        this.end = end;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public Reservation(){

    }

    public Date getLoanDay() {
        return loanDay;
    }

    public void setLoanDay(Date loanDay) {
        this.loanDay = loanDay;
    }

    public Date getLoanEnd() {
        return loanEnd;
    }

    public void setLoanEnd(Date loanEnd) {
        this.loanEnd = loanEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBuildingSite() {
        return buildingSite;
    }

    public void setBuildingSite(String buildingSite) {
        this.buildingSite = buildingSite;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public Calendar getStart() {
        return start;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public boolean isCorrectFilled (String start, String end, Reservation reservation,
                                    Context context) throws IOException {

        if (start.equals("")
                || end.equals("")) {
            return false;
        } else {
            String reservationNameJson = context.getString(R.string.reservationNameJSON);
            ArrayList<Reservation> reservationList = JsonHandler.getReservationList
                    (reservationNameJson,context);
            reservation.getStart();
            Connection connection = new Connection();
            connection.postNewReservation(reservation, context);
            return true;
        }
    }
}
