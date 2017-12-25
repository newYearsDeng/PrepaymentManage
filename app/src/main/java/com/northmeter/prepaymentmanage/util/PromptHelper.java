package com.northmeter.prepaymentmanage.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.DatePicker;

import com.northmeter.prepaymentmanage.ui.widget.TimerPickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Lht
 * on 2016/12/5.
 * des:
 */
public class PromptHelper {
    public static DatePicker  createTimePicker(Context context, final boolean isShowDay, final boolean isShowMonth, final boolean isShowYear, DatePickerDialog.OnDateSetListener listener, DialogInterface.OnDismissListener listener2) {
        final Calendar calendar = Calendar.getInstance();
        TimerPickerDialog pickerDialog = new TimerPickerDialog(context, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), isShowDay, isShowMonth, isShowYear);
        DatePicker picker = pickerDialog.getDatePicker();
        picker.setMaxDate(DateUtil.strToDate("yyyy-MM-dd", new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())).getTime());
        pickerDialog.show();
        pickerDialog.setOnDismissListener(listener2);
        return picker;
    }
}
