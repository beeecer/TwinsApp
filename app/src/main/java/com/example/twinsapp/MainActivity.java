package com.example.twinsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonCalendar;
    TextView textViewStatus;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStatus = findViewById(R.id.textViewStatus);

        setupButtonCalendar();
        setupButtonTickets();
        displayTeamInfoWhenNameIsTwins(this, textViewStatus); // Make sure to replace 'this' with the appropriate context
    }

    private void setupButtonTickets() {
        Button buttonTickets = findViewById(R.id.buttonTickets);
        buttonTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTwinsTicketsWebsite(v);
            }
        });
    }

    public void displayTeamInfoWhenNameIsTwins(Context context, TextView textViewStatus) {
        String apiUrl = "https://statsapi.mlb.com/api/v1/teams";
        Log.d("repository", "Accessing Data..." + apiUrl);

        RequestQueue requestQueue = Volley.newRequestQueue(context); // Initialize the RequestQueue with the provided context

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray teamsArray = response.getJSONArray("teams");
                            Log.d("repository", "JSON Array Length: " + teamsArray.length());

                            for (int i = 0; i < teamsArray.length(); i++) {
                                JSONObject teamJson = teamsArray.getJSONObject(i);
                                String name = teamJson.getString("name");
                                Log.d("repository","Name is" + name);
                                if (name.equalsIgnoreCase("Minnesota Twins")) {
                                    Log.d("repository", "Check name is" + name);
                                    // Process the data for the specific team
                                    String teamInfo = "Name: " + name + "\n" + "First Year of Play: " + teamJson.getString("firstYearOfPlay");
                                    textViewStatus.setText(teamInfo);
                                }
                            }
                        } catch (JSONException e) {
                            // Handle JSON parsing error
                            Log.e("repository", "JSON Parsing Error: " + e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("repository", "Data Access Error: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }



    private void openTwinsTicketsWebsite(View view) {
        String url = "https://www.mlb.com/twins/tickets/single-game-tickets";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    private void setupButtonCalendar(){

        buttonCalendar = findViewById(R.id.buttonCalendar);
        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewStatus.setText("Code should set a calendar event for performance 1");
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setType("vnd.android.cursor.item/event");
                calIntent.putExtra(CalendarContract.Events.TITLE, "Twins Opening Day");
                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Target Field");
                GregorianCalendar openingDay = new GregorianCalendar(2024, 2, 28, 15, 10);
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, openingDay.getTimeInMillis());
                startActivity(calIntent);
            }
        });




    }



}