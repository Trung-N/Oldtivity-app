package com.example.oldivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    private List<Event> events;
    private final ClickListener listener;
    //add or remove colours from here at will
    private String[] colours = {"#a8c0fc", "#fffdaf","#FFE5CE","#E4BD9C","#E94D3C","#E8925F","#92BFC1","#E57A80","#EFE0A3"};

    //The viewholder for the RecylerView that holds the events
    //initialises everything as specified in list_event_display.xml and defines onClick behaviour
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTextView;
        public Button messageButton;
        private WeakReference<ClickListener> listenerRef;

        public MyViewHolder(View itemView, ClickListener listener) {
            super(itemView);

            listenerRef = new WeakReference<>(listener);
            nameTextView = (TextView) itemView.findViewById(R.id.eventListName);
            messageButton = (Button) itemView.findViewById(R.id.eventListButton);

            nameTextView.setOnClickListener(this);
            messageButton.setOnClickListener(this);
        }

        //on clicking any of the items in the view, send the user to a specific events page
        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), "PRESSED: " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            listenerRef.get().onPositionClicked(getAdapterPosition());
            viewEvent(view, events.get(getAdapterPosition()));
        }
    }

    //constructor
    public EventsAdapter(ArrayList<Event> ev, ClickListener lis){
        events = ev;
        listener = lis;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_event_display, viewGroup, false);

        // Return a new holder instance
        MyViewHolder viewHolder = new MyViewHolder(contactView, listener);
        return viewHolder;
    }

    //binds each item to the view when initially loaded
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Get the data model based on position
        Event event = events.get(i);

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

    //randomises event colours to look "nice" (pls help with the colours)
    public int getRandomColour(){
        int index = new Random().nextInt(colours.length);
        return Color.parseColor(colours[index]);
    }

    //moves to specific event page for clicked event
    public void viewEvent(View view, Event event){
        Context context = view.getContext();
        Intent intent = new Intent(context, EventActivity.class);
        //formats event information into a string[] to be passed. Indexes: 0:title, 1:description,
        //2:date, 3:host, 4:location, 5:phoneNumber, 6:distance, 7:eventId
        String[] eventString = {event.getTitle(),event.getDescription(),event.getDate(),event.getHost(),event.getLocation(),event.getPhoneNumber(),event.getDistance(),event.getId()};
        intent.putExtra("eventDets",eventString);
        context.startActivity(intent);
    }
}
