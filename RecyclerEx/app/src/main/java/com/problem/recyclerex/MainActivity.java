package com.problem.recyclerex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.problem.recyclerex.adapters.ImageListAdapter;
import com.problem.recyclerex.database.ImageItemModel;
import com.problem.recyclerex.services.RecyclerExService;
import com.problem.recyclerex.utils.Constants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.imagelist_rv) RecyclerView mImageListRV;

    private LinearLayoutManager mLinearLayoutManager;
    private ImageListAdapter mImageListAdapter;
    private Context mContext;
    private ImageSetsReceiver mImageSetsReceiver;
    private ArrayList<ImageItemModel> mImageItemModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;

        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mImageListRV.setLayoutManager(mLinearLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerExService.startActionDownload(mContext, Constants.URL);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mImageSetsReceiver == null) {
            mImageSetsReceiver = new ImageSetsReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RecyclerExService.DATA_FETCH_ACTION_STRING);
        registerReceiver(mImageSetsReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mImageSetsReceiver);
        super.onStop();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeList(){
        if(mImageListAdapter == null){
            mImageListAdapter = new ImageListAdapter(mImageItemModels, mContext);
            mImageListRV.setAdapter(mImageListAdapter);
        }
    }

    public class ImageSetsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if(!intent.getBooleanExtra("status", false)){
                //TODO : Do something
                return;
            }

            if(intent.hasExtra("data")) {
                ArrayList<ImageItemModel> imageItemModels = intent.getParcelableArrayListExtra("data");
                if(imageItemModels != null && imageItemModels.size() > 0){
                    mImageItemModels = imageItemModels;
                    initializeList();
                }
            }
        }

    }
}
