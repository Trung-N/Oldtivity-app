package com.example.oldivity;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{


    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;
    public String curTime;
    private FirebaseAuth mAuth;
    public MessageAdapter(List<Messages> mMessageList) {

        this.mMessageList = mMessageList;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout ,parent, false);

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public TextView displayName;
        public ImageView messageImage;
        public TextView time_text_layout;
        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            displayName = (TextView) view.findViewById(R.id.name_text_layout);
            messageImage = (ImageView) view.findViewById(R.id.message_image_layout);
            time_text_layout = (TextView) view.findViewById(R.id.time_text_layout);
            messageText.setGravity(Gravity.RIGHT | Gravity.END);
        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {
        mAuth = FirebaseAuth.getInstance();
        String curUerID = mAuth.getCurrentUser().getUid();
        Messages c = mMessageList.get(i);
        String from_user = c.getFrom();
        String message_type = c.getType();
        if(from_user.equals(curUerID)){
            viewHolder.messageText.setBackgroundColor(Color.WHITE);
            viewHolder.messageText.setTextColor(Color.BLACK);

        }else{
            viewHolder.messageText.setBackgroundColor(Color.BLUE);
            viewHolder.messageText.setTextColor(Color.WHITE);
        }


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String firstName = dataSnapshot.child("firstName").getValue().toString();
                String lastName = dataSnapshot.child("lastName").getValue().toString();

                viewHolder.displayName.setText(firstName + " " + lastName);
                viewHolder.time_text_layout.setText(curTime);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(message_type.equals("text")) {

            viewHolder.messageText.setText(c.getMessage());
            viewHolder.messageImage.setVisibility(View.INVISIBLE);
            System.out.println("message displayed");


        } else {

            viewHolder.messageText.setVisibility(View.INVISIBLE);
            Picasso.get().load(c.getMessage()).into(viewHolder.messageImage);
            System.out.println("image displayed");



        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }
    public void setTime(){
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat curDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String curDate = curDateFormat.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat curTimeFormat = new SimpleDateFormat("hh:mm a");
        curTime = curTimeFormat.format(calForTime.getTime()) + " " + curDate;
    }






}