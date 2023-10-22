package com.example.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.GregorianCalendar;

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