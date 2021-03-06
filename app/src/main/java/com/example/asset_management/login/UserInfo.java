package com.example.asset_management.login;

import android.content.Context;
import android.widget.Toast;

import com.example.asset_management.R;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private boolean access;
    private int workerId;
    private String password;
    private String eMail;
    private String surname;
    private String firstname;
    private String role;
    int bookingDevice;
    int editDevice;
    int addDevice;
    int viewDevice;
    int deleteDevice;
    int addUser;
    int deleteUser;
    int editUser;
    int deleteBooking;
    int editBooking;
    int picking;

    public UserInfo(boolean access, int workerId, String password, String eMail, String surname, String firstname,
                    String role, int bookingDevice, int editDevice, int addDevice, int viewDevice,
                    int deleteDevice, int addUser, int deleteUser, int editUser, int deleteBooking,
                    int editBooking, int picking) {
        this.access = access;
        this.workerId = workerId;
        this.password = password;
        this.eMail = eMail;
        this.surname = surname;
        this.firstname = firstname;
        this.role = role;
        this.bookingDevice = bookingDevice;
        this.editDevice = editDevice;
        this.addDevice = addDevice;
        this.viewDevice = viewDevice;
        this.deleteDevice = deleteDevice;
        this.addUser = addUser;
        this.deleteUser = deleteUser;
        this.editUser = editUser;
        this.deleteBooking = deleteBooking;
        this.editBooking = editBooking;
        this.picking = picking;
    }

    public UserInfo(){

    }

    public int boolToInt(boolean bool){
        if (bool){
            return 1;
        } else {
            return 0;
        }
    }

    public boolean intToBool(int i){
        if(i == 1){
            return true;
        } else {
            return false;
        }
    }

    public boolean intToBool(boolean bool){
        return bool;
    }

    public boolean getAccess() {
        return access;
    }

    public int getWorkerId() {
        return workerId;
    }

    public String getWorkerIdString() {
        return String.valueOf(workerId);
    }


    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = Integer.parseInt(workerId);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getBookingDevice() {
        return bookingDevice;
    }

    public void setBookingDevice(int bookingDevice) {
        this.bookingDevice = bookingDevice;
    }

    public int getEditDevice() {
        return editDevice;
    }

    public void setEditDevice(int editDevice) {
        this.editDevice = editDevice;
    }

    public int getAddDevice() {
        return addDevice;
    }

    public void setAddDevice(int addDevice) {
        this.addDevice = addDevice;
    }

    public int getViewDevice() {
        return viewDevice;
    }

    public void setViewDevice(int viewDevice) {
        this.viewDevice = viewDevice;
    }

    public int getDeleteDevice() {
        return deleteDevice;
    }

    public void setDeleteDevice(int deleteDevice) {
        this.deleteDevice = deleteDevice;
    }

    public int getAddUser() {
        return addUser;
    }

    public void setAddUser(int addUser) {
        this.addUser = addUser;
    }

    public int getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(int deleteUser) {
        this.deleteUser = deleteUser;
    }

    public int getEditUser() {
        return editUser;
    }

    public void setEditUser(int editUser) {
        this.editUser = editUser;
    }

    public int getDeleteBooking() {
        return deleteBooking;
    }

    public void setDeleteBooking(int deleteBooking) {
        this.deleteBooking = deleteBooking;
    }

    public int getEditBooking() {
        return editBooking;
    }

    public void setEditBooking(int editBooking) {
        this.editBooking = editBooking;
    }

    public void noAccessMessage(Context context){
        Toast.makeText(context, context.getString(R.string.noAccessMessage)
                , Toast.LENGTH_SHORT).show();
    }

}