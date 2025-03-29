package fr.umontpellier.tp3_android_persistence.utils;

import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fr.umontpellier.tp3_android_persistence.models.DateItem;

public class DateUtils {

    public static List<DateItem> generateDaysOfMonth(int year, int month, int selectedDay) {
        List<DateItem> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int day = 1; day <= maxDay; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            String label = new SimpleDateFormat("EEE d", Locale.getDefault()).format(calendar.getTime());
            String fullDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.getTime());

            boolean isSelected = (day == selectedDay);
            days.add(new DateItem(label, fullDate, isSelected));
        }

        return days;
    }

}


