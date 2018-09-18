package com.example.oldivity;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    private List<Event> events;
    private String[] colours = {"#a8c0fc", "#fffdaf","#FFE5CE","#E4BD9C","#E94D3C","#E8925F","#92BFC1","#E57A80","#EFE0A3"};

    //copied from online to be changed later
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public Button messageButton;

        public MyViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.eventListName);
            messageButton = (Button) itemView.findViewById(R.id.eventListButton);
        }
    }

    //constructor
    public EventsAdapter(ArrayList<Event> ev){
        events = ev;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_event_display, viewGroup, false);

        // Return a new holder instance
        MyViewHolder viewHolder = new MyViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Get the data model based on position
        Event event = events.get(i);

        // Set item views based on your views and data model
        TextView textView = myViewHolder.nameTextView;
        textView.setText(event.getTitle());
        Button button = myViewHolder.messageButton;
        button.setText("View");
        //randomises how each view looks
        myViewHolder.itemView.setBackgroundColor(getRandomColour());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    //randomises event colours to look nice
    public int getRandomColour(){
        int index = new Random().nextInt(colours.length);
        return Color.parseColor(colours[index]);
    }
}
