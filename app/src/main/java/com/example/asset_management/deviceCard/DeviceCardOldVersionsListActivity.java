package com.example.asset_management.deviceCard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.asset_management.R;
import com.example.asset_management.jsonhandler.JsonHandler;
import com.example.asset_management.recycleViewDeviceList.Device;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class DeviceCardOldVersionsListActivity extends AppCompatActivity {
    ArrayList<Device> devices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_card_list_old_versions);
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        ArrayList<String> list = new ArrayList<>();
        ListView listView = findViewById(R.id.listOldDevice);
        String deviceOldVersionNameJson = getString(R.string.deviceOldVersionNameJSON);
        try {
            devices = JsonHandler.getDeviceList(deviceOldVersionNameJson, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleDateFormat format = new SimpleDateFormat(getString(R.string.datePattern));

        for(Device d : devices){
            list.add(format.format(d.getLastChange()));
        }

        if(list.size() == 0){
            list.add(getString(R.string.noOldVersionsFound));
        } else {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(DeviceCardOldVersionsListActivity.this,
                            DeviceCardActivity.class);
                    intent.putExtra(getString(R.string.deviceName), devices.get(position));
                    intent.putExtra(getString(R.string.isOldVersion), true);
                    startActivity(intent);
                }
            });
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(itemsAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}