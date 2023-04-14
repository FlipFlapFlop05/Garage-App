package com.example.garage_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Dashboard extends AppCompatActivity {
    Button logout, addCar;
    SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<CourseModel> arrList = new ArrayList<>();

    ArrayList<String> makeNames = new ArrayList<String>();
    ArrayList<Integer> makeIds = new ArrayList<Integer>();

    ArrayList<String> modelNames = null;
    ArrayList<Integer> modelIds = null;
    CardView cd1, cd2, cd3, cd4, cd5, cd6, cd7, cd8, cd9, cd10, cd11, cd12, cd13, cd14, cd15, cd16;
    String fCarName = null;
    String fCarModel = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Log.d("dashb", "reazhed");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        logout = findViewById(R.id.logout);
        addCar = findViewById(R.id.add_car);

        RecyclerView recyclerVeiw = findViewById(R.id.recyclerContact);

        recyclerVeiw.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                CourseAdapter adapter = new CourseAdapter(Dashboard.this, arrList);
                recyclerVeiw.setAdapter(adapter);
            }
        });


        Spinner car_make = findViewById(R.id.car_make);
        Spinner car_model = findViewById(R.id.car_model);
        EfHttpGet.stringResponse("https://vpic.nhtsa.dot.gov/api/vehicles/getallmakes?format=json",
                new EfHttpGet.efResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        //   Log.d("apiResponse", ""+response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if(json.has("Results"))
                            {

                                JSONArray jsonArray = json.getJSONArray("Results");
                                int Length = Integer.valueOf(jsonArray.length());
                                int fLength = Length-1;

                                for(int i=0;i<=fLength;i++)
                                {
                                    makeNames.add(jsonArray.getJSONObject(i).getString("Make_Name"));
                                    makeIds.add(jsonArray.getJSONObject(i).getInt("Make_ID"));

                                }

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Dashboard.this, android.R.layout.simple_spinner_item, makeNames);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                car_make.setAdapter(arrayAdapter);
                                car_make.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        int makeId= makeIds.get(position);
                                        fCarName = makeNames.get(position);
                                        modelNames = new ArrayList<String>();
                                        modelIds = new ArrayList<Integer>();
                                        EfHttpGet.stringResponse("https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMakeId/"+makeId+"?format=json",
                                                new EfHttpGet.efResponseListener() {
                                                    @Override
                                                    public void onSuccess(String response) {
                                                           Log.d("apiResponse", ""+response);
                                                        try {
                                                            JSONObject json = new JSONObject(response);
                                                            int count=0;
                                                            if(json.has("Count"))
                                                            {
                                                                count = json.getInt("Count");
                                                            }
                                                            if(json.has("Results"))
                                                            {
                                                                JSONArray jsonArray = json.getJSONArray("Results");


                                                                for(int i=0;i<=count-1;i++)
                                                                {
                                                                    modelNames.add(jsonArray.getJSONObject(i).getString("Model_Name"));
                                                                    modelIds.add(jsonArray.getJSONObject(i).getInt("Model_ID"));

                                                                }
                                                                Log.d("model",modelNames.toString());
                                                                Toast.makeText(Dashboard.this, ""+makeIds.get(3), Toast.LENGTH_SHORT).show();
                                                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Dashboard.this, android.R.layout.simple_spinner_item, modelNames);
                                                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                car_model.setAdapter(arrayAdapter);
                                                                car_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                    @Override
                                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                        fCarModel = modelNames.get(position);
                                                                    }
                                                                    @Override
                                                                    public void onNothingSelected(AdapterView <?> parent) {
                                                                    }
                                                                });

                                                            }

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        finally {

                                                        }

                                                    }

                                                    @Override
                                                    public void onError(String errorResponse) {

                                                    }
                                                });

                                        //Toast.makeText(Dashboard.this, ""+makeIds1.get(position), Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView <?> parent) {
                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {

                        }

                    }

                    @Override
                    public void onError(String errorResponse) {

                    }
                });

        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Log.d("CarName",fCarName);
              //  Log.d("CarModel",fCarModel);

                if(fCarModel!=null)
                {
                    arrList.add(new CourseModel(1, ""+fCarName, ""+fCarModel));
                    CourseAdapter adapter = new CourseAdapter(Dashboard.this, arrList);
                    recyclerVeiw.setAdapter(adapter);
                    fCarName = null;
                    fCarModel = null;

                }else
                {
                    Toast.makeText(Dashboard.this, "Please select a new car!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}