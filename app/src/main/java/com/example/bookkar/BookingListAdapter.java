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

        int val = dateCompare(diff,book_date);
        String msg;
        if(val<0){
            msg = "";
            Log.i("checking",val+msg );
        }
        else if(val<2){
            msg="PENDING";
            Log.i("checking",val +msg );
        }
        else if(val<4){
            msg = "BOOKING CONFIRMED";
            Log.i("checking",val +msg );
        }

        else if(val < 7){
            msg = "QUEUED FOR NEXT DELIVERY DATE";
            Log.i("checking",val +msg );
        }

        else{
            msg = "DELIVERED";
            Log.i("checking",val +msg );
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

    public static int dateCompare(String str1, String str2)
    {
        String[] date1 = str1.split("/");
        String[] date2 = str2.split("/");

        Log.i("checking",date1[0]+date1[1]+date1[2]);
        Log.i("checking",date2[0]+date2[1]+date2[2]);

        if (!(date1[2].equals(date1[2]))){
            return 365;
        }
        else if(!(date1[1].equals(date2[1]))){
            return 31;
        }

        int day1 = Integer.parseInt(date1[0]);
        int day2 = Integer.parseInt(date2[0]);

        return day1-day2;
    }
}

