package com.example.asset_management.recycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asset_management.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
/**
 * DeviceAdapter
 * <p>
 *     Version 1.0
 * </p>
 * 11.05.2020
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private OnNoteListener mOnNoteListener;
    private ArrayList<Device> devices;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,
                parent,false);
        return new ViewHolder(v, mOnNoteListener);
    }
    public DeviceAdapter(ArrayList<Device>devices, OnNoteListener onNoteListener){
        this.devices = devices;
        this.mOnNoteListener = onNoteListener;
}

    /**
     * Which informations should be displayed on the view
     * @param holder which informations should be displayed on the view
     * @param position from the Screen
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Device device = devices.get(position);
        holder.inventoryNumber.setText(device.getInventoryNumber());
        holder.manufacturer.setText(device.getManufacturer());
        holder.model.setText(device.getModel());
        holder.deviceCategorie.setText(device.getDeviceCategorie());
        holder.status.setText(device.getStatus());

    }

    @Override
    public int getItemCount() {
        if (devices != null) {
            return devices.size();
        } else {
            return 0;
        }
    }

    /**
     * ViewHolder
     * <p>
     *     Version 1.0
     * </p>
     * 11.05.2020
     * creates the view with the TextFields
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View view;
        public final TextView inventoryNumber;
        public final TextView manufacturer;
        public final TextView model;
        public final TextView status;
        public final TextView deviceCategorie;
        public final ImageView image;
        OnNoteListener onNoteListener;

        public ViewHolder(View view, OnNoteListener onNoteListener) {
            super(view);
            this.view = view;
            inventoryNumber = view.findViewById(R.id.inventoryNumber);
            manufacturer = view.findViewById(R.id.manufacturer);
            model = view.findViewById(R.id.model);
            status = view.findViewById(R.id.status);
            deviceCategorie = view.findViewById(R.id.deviceCategorie);
            image = view.findViewById(R.id.image);
            itemView.setOnClickListener(this);
            this.onNoteListener = onNoteListener;
        }

        @Override
        public void onClick(View v) {
        onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
