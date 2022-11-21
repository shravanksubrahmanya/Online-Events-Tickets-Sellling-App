package com.example.pramodn.myapplication.event_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.description;

import java.util.List;

public class purchase_adapter extends RecyclerView.Adapter<purchase_adapter.MyViewHolder> {

    private Context mContext;
    private List<purchase_model> albumList1;
    RequestQueue queue;


    @Override
    public purchase_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_adapter, parent, false);

        return new purchase_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final purchase_model album = albumList1.get(i);

        Log.d("val",album.getEvent_name());

        myViewHolder.event_name.setText(album.getEvent_name());
        myViewHolder.event_id.setText(album.getEvent_id());
        myViewHolder.bid.setText(album.getBid());
        myViewHolder.transactionid.setText(album.getTransactionid());
        myViewHolder.totalamount.setText(String.valueOf(album.getTotalamount()));
        myViewHolder.numberoftickets.setText(String.valueOf(album.getNumberoftickets()));
        myViewHolder.tickettype.setText(album.getTickettype());

        myViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,description.class);
                intent.putExtra("event_id",album.getEvent_id());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList1.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView bid,event_name,event_id,transactionid,numberoftickets,totalamount,tickettype;
        public Button button;

        public MyViewHolder(@NonNull View view) {
            super(view);

            event_name = (TextView) view.findViewById(R.id.event_name);
            event_id = view.findViewById(R.id.event_id);

            bid=view.findViewById(R.id.bid);
            tickettype=view.findViewById(R.id.tickettype);

            transactionid=view.findViewById(R.id.transactionid);
            numberoftickets=view.findViewById(R.id.numberoftickets);
            totalamount=view.findViewById(R.id.totalamount);
            button=view.findViewById(R.id.view);
            queue = Volley.newRequestQueue(mContext);
        }

    }
    public purchase_adapter(Context mContext, List<purchase_model> albumList1) {
        this.mContext = mContext;
        this.albumList1 = albumList1;
        Log.d("size_of_list",String.valueOf(albumList1.size()));
    }

}
