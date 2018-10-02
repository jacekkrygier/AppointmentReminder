package com.example.jacek.appointmentreminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddAppointmentActivity extends AppCompatActivity {

    TextView txtDate;
    TextView txtTime;

    private int year;
    private int month;
    private int day;

    private int hour;
    private int minute;

    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        setCurrentDateAndTimeOnView();
    }
    //display current date and time
    private void setCurrentDateAndTimeOnView() {
        txtDate = (TextView)findViewById(R.id.txttvDate);
        txtTime = (TextView)findViewById(R.id.txttvTime);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // set current time into textview
        txtTime.setText(new StringBuilder().append(pad(hour))
        .append(":").append(pad(minute)));

        //set current date into textview
        txtDate.setText(new StringBuilder()
        // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
    }
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            txtDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;

            // set current time into textview
            txtTime.setText(new StringBuilder().append(pad(hour))
                    .append(":").append(pad(minute)));
        }
    };

    //Displays a new dialog for date picker or time picker
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        datePickerListener, year, month,day);
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute,false);
        }
        return null;
    }

    /**When Date Text View is touched, opens a Date Picker Dialog **/
    public void edittxtDate(View view){
        showDialog(DATE_DIALOG_ID);
    }

    /**When Time Text View is touched, opens a Time Picker Dialog**/
    public void edittxtTime(View view){
        showDialog(TIME_DIALOG_ID);
    }

    //Closes AddAppointmentActivity
    public void btnCancel(View view) { finish();}

    //Closes AddAppointmentActivity and returns the information entered in each field
    public void btnAddAppointment(View view) {
        EditText editAppointmentName = (EditText) findViewById(R.id.editTextName);
        Spinner spinnerAppointmentType = (Spinner) findViewById(R.id.spnTaskType);
        if(!(editAppointmentName.getText().toString()).isEmpty()) {
            Intent intent = new Intent();

            intent.putExtra("name", editAppointmentName.getText().toString());

            intent.putExtra("type", spinnerAppointmentType.getSelectedItem().toString());

            intent.putExtra("monthOfYear", DisplayTheMonthInCharacters(month));
            intent.putExtra("dayOfMonth", day);
            intent.putExtra("year", year);

            intent.putExtra("hour", FormatTheHour(hour));
            intent.putExtra("minute", minute);
            intent.putExtra("AMorPM", AMorPM(hour));

            setResult(RESULT_OK, intent);

            finish();
        }
        else{
            Toast toast = Toast.makeText(AddAppointmentActivity.this, "Please enter an Appointment Name", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    /* Helper Methods */

    //Returns the Month Abbreviation for the corresponding number.
    private String DisplayTheMonthInCharacters(int passedMonth){
        switch(passedMonth){
            case 0:
                return "Jan";
            case 1:
                return"Feb";
            case 2:
                return"Mar";
            case 3:
                return"Apr";
            case 4:
                return"May";
            case 5:
                return"Jun";
            case 6:
                return"Jul";
            case 7:
                return"Aug";
            case 8:
                return"Sept";
            case 9:
                return"Oct";
            case 10:
                return"Nov";
            case 11:
                return"Dec";

        }
        return "";
    }

    //Converts the 24 hours PassedHour to a 12 Hour time.
    private int FormatTheHour(int passedHour){
        if (passedHour > 12){ passedHour -= 12; }
        return passedHour;
    }

    //Returns AM or PM depending on the hour (1-24)
    private String AMorPM(int passedHour){
        if (passedHour > 12){ return "PM"; }
        else{ return "AM"; }
    }



    private String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

}
