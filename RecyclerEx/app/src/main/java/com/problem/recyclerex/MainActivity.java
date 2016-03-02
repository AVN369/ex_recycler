package com.problem.recyclerex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.problem.recyclerex.adapters.ImageListAdapter;
import com.problem.recyclerex.database.ImageItemModel;
import com.problem.recyclerex.database.ImageItemsSQLiteHelper;
import com.problem.recyclerex.services.RecyclerExService;
import com.problem.recyclerex.utils.Constants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.imagelist_rv) RecyclerView mImageListRV;
    @Bind(R.id.loader_container) View mLoaderContainer;
    @Bind(R.id.failure_container) View mFailureContainer;
    @Bind(R.id.root_view) View mRootView;

    private LinearLayoutManager mLinearLayoutManager;
    private ImageListAdapter mImageListAdapter;
    private Context mContext;
    private ImageSetsReceiver mImageSetsReceiver;
    private ArrayList<ImageItemModel> mImageItemModels;

    private boolean isDataBeingFetched = false;

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

        fetchLocalData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchLocalData();
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchLocalData(){
        ArrayList<ImageItemModel> imageItemModels = ImageItemsSQLiteHelper.getImageItemModels(mContext);
        if(imageItemModels == null || imageItemModels.size() == 0){
            mLoaderContainer.setVisibility(View.VISIBLE);
            mFailureContainer.setVisibility(View.GONE);
            fetchData();
        }else{
            mImageItemModels = imageItemModels;
            initializeList();
        }

    }

    private void fetchData(){
        if(isDataBeingFetched){
            Snackbar.make(mRootView, R.string.is_being_fetched, Snackbar.LENGTH_SHORT).show();
            return;
        }
        isDataBeingFetched = true;
        RecyclerExService.startActionDownload(mContext, Constants.URL);
    }

    private void initializeList(){
        if(mImageListAdapter == null){
            mImageListAdapter = new ImageListAdapter(mImageItemModels, mContext);
            mImageListRV.setAdapter(mImageListAdapter);
        }else{
            mImageListAdapter.changeSet(mImageItemModels);
        }
    }

    private void processData(ArrayList<ImageItemModel> imageItemModels){
        isDataBeingFetched = false;
        mLoaderContainer.setVisibility(View.GONE);
        if(imageItemModels != null && imageItemModels.size() > 0){
            mFailureContainer.setVisibility(View.GONE);
            mImageItemModels = imageItemModels;
            initializeList();
        }else{
            mFailureContainer.setVisibility(View.VISIBLE);
        }
    }

    public class ImageSetsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(!intent.getBooleanExtra("status", false)){
                processData(null);
                return;
            }

            if(intent.hasExtra("data")) {
                ArrayList<ImageItemModel> imageItemModels = intent.getParcelableArrayListExtra("data");
                processData(imageItemModels);
            }else{
                processData(null);
            }
        }
    }
}
