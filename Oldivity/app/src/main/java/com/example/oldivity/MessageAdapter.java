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

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{


    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;
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
        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {
        mAuth = FirebaseAuth.getInstance();
        String curUerID = mAuth.getCurrentUser().getUid();
        Messages c = mMessageList.get(i);
        String from_user = c.getFrom();
        String message_type = c.getType();
        final String time = c.getTime();




        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Display user name and time
                String firstName = dataSnapshot.child("firstName").getValue().toString();
                String lastName = dataSnapshot.child("lastName").getValue().toString();

                viewHolder.displayName.setText(firstName + " " + lastName);
                viewHolder.time_text_layout.setText(time);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        viewHolder.messageText.setVisibility(View.GONE);
        viewHolder.messageImage.setVisibility(View.GONE);
        //Display text
        if(message_type.equals("text")) {
            viewHolder.messageText.setVisibility(View.VISIBLE);
            viewHolder.messageImage.setVisibility(View.INVISIBLE);
            viewHolder.messageText.setText(c.getMessage());
            //Different colours for the user and others
            if(from_user.equals(curUerID)){
                viewHolder.messageText.setBackgroundResource(R.drawable.message_text_background2);
                viewHolder.messageText.setTextColor(Color.BLACK);
                viewHolder.displayName.setGravity(Gravity.RIGHT);
                viewHolder.messageText.setGravity(Gravity.RIGHT);

            }else{
                viewHolder.messageText.setBackgroundResource(R.drawable.message_text_background);
                viewHolder.messageText.setTextColor(Color.WHITE);
                viewHolder.displayName.setGravity(Gravity.LEFT);
                viewHolder.messageText.setGravity(Gravity.LEFT);

            }

            System.out.println(c.getMessage());


        //Display picture
        } else {

            viewHolder.messageImage.setVisibility(View.VISIBLE);
            //Size the image too avoid too large pictures covers the whole page or even more.
            Picasso.get().load(c.getMessage()).resize(700, 500).into(viewHolder.messageImage);



        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }







}