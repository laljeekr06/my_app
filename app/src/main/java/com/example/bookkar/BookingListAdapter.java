package com.example.bookkar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BookingListAdapter extends ArrayAdapter<Booking> {

    private Context mContext;
    private List<Booking> bookingList = new ArrayList<>();



    public BookingListAdapter(@NonNull Context context,  ArrayList<Booking> list) {
        super(context, 0 , list);
        mContext = context;
        bookingList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.booking_list_item,parent,false);

        Booking booking = bookingList.get(position);

        ImageView status_image = listItem.findViewById(R.id.booking_status_image);


        TextView bookingDate = listItem.findViewById(R.id.booking_date);
        bookingDate.setText("Booking Date \n"+booking.getBookingDate());

        TextView deliveryDate = listItem.findViewById(R.id.delivery_date);
        deliveryDate.setText("Delivery Date\n" +booking.getDeliveryDate());

        TextView bookingStatus = listItem.findViewById(R.id.booking_status);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        String diff = sdf.format(c.getTime());
        String book_date = booking.getBookingDate();

        int val = stringCompare(diff,book_date);
        String msg;
        if(val<0){
            msg = "";
        }
        else if(val<2){
            msg="PENDING";
        }
        else if(val<4){
            msg = "BOOKING CONFIRMED";
        }

        else if(val < 7){
            msg = "QUEUED FOR NEXT DELIVERY DATE";
        }

        else{
            msg = "DELIVERED";
        }

        if(val<7){
            status_image.setImageResource(R.drawable.processing);
        }

        else{
            status_image.setImageResource(R.drawable.delivered);
        }

        bookingStatus.setText(msg);
        return listItem;
    }

    public static int stringCompare(String str1, String str2)
    {

        int l1 = str1.length();
        int l2 = str2.length();
//        int lmin = Math.min(l1, l2);
        int diff = 0;

        for (int i = 0; i < l1; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);

            diff += str1_ch - str2_ch;
        }
            return diff;

    }
}

