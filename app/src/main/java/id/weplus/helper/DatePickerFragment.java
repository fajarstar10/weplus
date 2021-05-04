package id.weplus.helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import id.weplus.R;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private OnDateSetListener onDateSetListener;
    private String cat = "";
    private int minAge = 0;
    private int maxAge = 0;

    public static DatePickerFragment newInstance(String cat, OnDateSetListener onDateSetListener) {
        DatePickerFragment df = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString("cat", cat);
        df.setArguments(args);
        return df;
    }

    public static DatePickerFragment newInstance(String cat, int minAge, int maxAge, OnDateSetListener onDateSetListener) {
        DatePickerFragment df = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString("cat", cat);
        args.putInt("minAge", minAge);
        args.putInt("maxAge", maxAge);
        df.setArguments(args);
        return df;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        cat = getArguments().getString("cat");
        minAge = getArguments().getInt("minAge");
        maxAge = getArguments().getInt("maxAge");
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialog;
        if (cat.equals("pergi") || cat.equals("datang")) {
            dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMinDate(new Date().getTime());
        } else {
            dialog = new DatePickerDialog(getActivity(), R.style.HoloDialog, this, year, month, day);
            if (cat.equals("dob_covid")) {
                Log.d("test", "min : " + minAge + " - max: " + maxAge);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, minAge);
                Date minDate = cal.getTime();
                //Date minDate = new DateTime().minusMonths(3).toDate();

                dialog.getDatePicker().setMinDate(minDate.getTime());

                Calendar cal2 = Calendar.getInstance();
                cal2.set(Calendar.YEAR, maxAge);
                //Date maxDate = cal2.getTime();

                DateTime maxDate = new DateTime().minusMonths(3);
                dialog.getDatePicker().setMaxDate(maxDate.getMillis());
            }
            if (cat.equals("dob")) {
                Log.d("test", "min : " + minAge + " - max: " + maxAge);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, minAge);
                Date minDate = cal.getTime();
                dialog.getDatePicker().setMinDate(minDate.getTime());

                Calendar cal2 = Calendar.getInstance();
                cal2.set(Calendar.YEAR, maxAge);
                Date maxDate = cal2.getTime();
                dialog.getDatePicker().setMaxDate(maxDate.getTime());
            }
            if (cat.equals("dob_life")) {
                dialog.getDatePicker().setMaxDate(new Date().getTime());
            }
            if (cat.equals("start_date")) {
                dialog.getDatePicker().setMinDate(new Date().getTime() - 10000);
            }
        }
        return dialog;
    }

    public static Date getDateMonthsAgo(int numOfMonthsAgo) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1 * numOfMonthsAgo);
        return c.getTime();
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        try {
            ((OnDateSetListener) Objects.requireNonNull(getActivity())).onDateSet(cal, cat);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Not implement OnDateListener", Toast.LENGTH_LONG).show();
            dismiss();
        }
        dismiss();
    }
}
