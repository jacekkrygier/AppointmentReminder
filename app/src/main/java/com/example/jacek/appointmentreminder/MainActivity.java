package com.example.jacek.appointmentreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Appointment> appointmentArrayList = new ArrayList<Appointment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateSomeTestAppointmentsToStartWith();
    }

    private void CreateSomeTestAppointmentsToStartWith() {
        appointmentArrayList.add(new Appointment("Doctors Visit", "Health", "Oct", 9, 2016, 9, 00, "AM"));
        appointmentArrayList.add(new Appointment("Doctors Visit 2", "Health", "Oct", 5, 2016, 9, 00, "AM"));
        appointmentArrayList.add(new Appointment("Doctors Visit 3", "Health", "Oct", 4, 2016, 9, 00, "AM"));
        appointmentArrayList.add(new Appointment("Doctors Visit 4", "Health", "Oct", 21, 2016, 9, 00, "AM"));
        appointmentArrayList.add(new Appointment("Doctors Visit 5", "Health", "Oct", 30, 2016, 9, 00, "AM"));
        appointmentArrayList.add(new Appointment("Doctors Visit 6", "Health", "Oct", 17, 2016, 9, 00, "AM"));
        appointmentArrayList.add(new Appointment("Doctors Visit 7", "Health", "Oct", 8, 2016, 9, 00, "AM"));

        for(int i = 0; i < appointmentArrayList.size();i++) {
            PopulateTable(i);
        }

    }

    private String SetToDateAndTime(Appointment appointment) {
        long currentDateAndTime = System.currentTimeMillis(); //Todays Date
        SimpleDateFormat formatDate = new SimpleDateFormat("MMM d, yyyy"); //Date Format

        String todaysDate = formatDate.format(currentDateAndTime); //Today's date formated
        String passDate = appointment.monthDate + " " + appointment.dayDate +", " + appointment.yearDate; //Tasks date formated

        if(Objects.equals(todaysDate, passDate)) { //Compare today's date and passed date, return time if dates match
            return appointment.hourTime + ":" + appointment.minuteTime + " " + appointment.AMorPMTime;
        }
        return appointment.monthDate + " " +appointment.dayDate + ", " + appointment.yearDate; //Otherwise, return the date
    }


    @Override
    //Returns information passed from addAppointmentactivity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {

                //Creates a new appointment with the information passed
                appointmentArrayList.add(new Appointment(
                        data.getStringExtra("name"),data.getStringExtra("type"),
                        data.getStringExtra("monthOfYear"), data.getIntExtra("dayOfMonth", 0), data.getIntExtra("year", 1111),
                        data.getIntExtra("hour", 11),data.getIntExtra("minute", 11),data.getStringExtra("AMorPM")));
                //Displays new appointment on in the table
                PopulateTable(appointmentArrayList.size()-1);
            }
        }
    }

    private void PopulateTable(int arrayListCounter) {
        TableLayout appointmentTBL = (TableLayout) findViewById(R.id.tblTaskContent);

        TableRow newTableRow = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        newTableRow.setLayoutParams(lp);

        TextView txtvName = new TextView(this);
        txtvName.setLayoutParams(lp);
        txtvName.setGravity(Gravity.CENTER);
        txtvName.setText(appointmentArrayList.get(arrayListCounter).name);
        txtvName.setWidth(140);
        txtvName.setTextSize(12);
        txtvName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView txtvType = new TextView(this);
        txtvType.setLayoutParams(lp);
        txtvType.setGravity(Gravity.CENTER);
        txtvType.setText(appointmentArrayList.get(arrayListCounter).type);
        txtvType.setWidth(93);
        txtvType.setTextSize(12);
        txtvType.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView txtvDate = new TextView(this);
        txtvDate.setLayoutParams(lp);
        txtvDate.setGravity(Gravity.CENTER);
        txtvDate.setText(SetToDateAndTime(appointmentArrayList.get(arrayListCounter)));
        txtvDate.setWidth(97);
        txtvDate.setTextSize(12);
        txtvDate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        newTableRow.addView(txtvName);
        newTableRow.addView(txtvType);
        newTableRow.addView(txtvDate);
        appointmentTBL.addView(newTableRow,arrayListCounter+1);

    }
    public void AddAppointmentBtn(View view) {
        startActivityForResult(new Intent(this, AddAppointmentActivity.class), 1);
    }
}
