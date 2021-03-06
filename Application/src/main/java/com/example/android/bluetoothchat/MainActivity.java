/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.example.android.bluetoothchat;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.ViewAnimator;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity  extends SampleActivityBase{

    public static final String TAG = "MainActivity";
    Bundle bundle = new Bundle();

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;
    private TextView txtShowTextResult1,txtShowTextResult2,txtShowTextResult3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = getIntent();
        txtShowTextResult1 = findViewById(R.id.txtDisplay1);
        txtShowTextResult2 = findViewById(R.id.txtDisplay2);
        txtShowTextResult3 = findViewById(R.id.txtDisplay3);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "https://pezzzapi.herokuapp.com/api/getcurrentmeds?id=959595";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.patientData);
                    StringBuilder formattedResult = new StringBuilder();
                    JSONArray responseJSONArray = response.getJSONObject("message").getJSONArray("meds");
                //    for (int i = 0; i < responseJSONArray.length(); i++) {
                   //     formattedResult.append("\n" + responseJSONArray.getJSONObject(i).get("name"));
                    //    TextView txt1 = new TextView(MainActivity.this)

                    txtShowTextResult1.setText("\n" + responseJSONArray.getJSONObject(0).get("name")+
                            "\nPills per day:" + responseJSONArray.getJSONObject(0).getJSONObject("timeTable").get("timesPerDay")
                            + "\nPeriod:" + responseJSONArray.getJSONObject(0).getJSONObject("timeTable").get("numberOfDays"));
                    txtShowTextResult2.setText("\n" + responseJSONArray.getJSONObject(1).get("name")+
                            "\nPills per day:" + responseJSONArray.getJSONObject(1).getJSONObject("timeTable").get("timesPerDay")
                            + "\nPeriod:" + responseJSONArray.getJSONObject(1).getJSONObject("timeTable").get("numberOfDays"));
                    txtShowTextResult3.setText("\n" + responseJSONArray.getJSONObject(2).get("name")+
                            "\nPills per day:" + responseJSONArray.getJSONObject(2).getJSONObject("timeTable").get("timesPerDay")
                            + "\nPeriod:" + responseJSONArray.getJSONObject(2).getJSONObject("timeTable").get("numberOfDays"));

                        //linearLayout.setBackgroundColor(Color.TRANSPARENT);
//                        relativeLayout.addView(txt1);
//                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                        params.setMargins(100,100,100,100);
//
//                        txt1.setLayoutParams(params);
                 //   }
                 //   txtShowTextResult.setText("Patient Data: \n" + response.get("message"));

                    bundle.putString("PATIENT_DATA", response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               txtShowTextResult1.setText("An Error occurred while making the request");
            }
        });
        requestQueue.add(jsonObjectRequest);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            fragment.setArguments(bundle);
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
    //      logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
    //     logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

//        return super.onPrepareOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()) {
//            case R.id.menu_toggle_log:
//                mLogShown = !mLogShown;
//                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
//                if (mLogShown) {
//                    output.setDisplayedChild(1);
//                } else {
//                    output.setDisplayedChild(0);
//                }
//                supportInvalidateOptionsMenu();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /** Create a chain of targets that will receive log data */
//    @Override
//    public void initializeLogging() {
//        // Wraps Android's native log framework.
//        LogWrapper logWrapper = new LogWrapper();
//        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
//        Log.setLogNode(logWrapper);
//
//        // Filter strips out everything except the message text.
//        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
//        logWrapper.setNext(msgFilter);
//
//        // On screen logging via a fragment with a TextView.
//        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.log_fragment);
//        msgFilter.setNext(logFragment.getLogView());
//
//        Log.i(TAG, "Ready");
//    }
}
