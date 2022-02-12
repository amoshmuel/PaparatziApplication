package com.main.paparatzi.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;


public class PhotoComparator implements Comparator<Photo> {
    @Override
    public int compare(Photo photo, Photo t1) {
        return StringToDate(photo.getDate()).compareTo(StringToDate(t1.getDate()));
    }

    public Date StringToDate(String dStr) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
            Date date = format.parse(dStr);
            System.out.println(date);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
