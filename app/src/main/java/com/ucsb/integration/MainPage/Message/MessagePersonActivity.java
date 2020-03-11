package com.ucsb.integration.MainPage.Message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ucsb.integration.MainPage.Profile.ProfileActivity;
import com.ucsb.integration.MainPage.Profile.UserInformation;
import com.ucsb.integration.R;
import com.ucsb.integration.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagePersonActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;

    FirebaseUser fuser;
    DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;

    Intent intent;

    boolean clickedToolbar = false;
    boolean changeInDatabase;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_person);

        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setTitle("MessagePersonActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagePersonActivity.this, MessageActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        intent = getIntent();
        final String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        final String [] receiverData = new String[6];
        changeInDatabase = false;

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(fuser.getUid(), userid, msg);
                } else {
                    Toast.makeText(MessagePersonActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (clickedToolbar)
                    return;
                UserInformation user = dataSnapshot.child(userid).getValue(UserInformation.class); //use this to set values
                username.setText(user.getUsername());
                if (user.getImageURL() == null || user.getImageURL().equals("Not provided")) { //had to change to null
                    profile_image.setImageResource(R.drawable.default_user);
                } else {
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                }

                receiverData[0] = user.getEmail();
                receiverData[1] = user.getFullname();
                receiverData[2] = user.getImageURL();
                receiverData[3] = user.getPhonenumber();
                receiverData[4] = user.getUsername();
                receiverData[5] = user.getVenmo();

                readMessages(fuser.getUid(), userid, user.getImageURL());
                changeInDatabase = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInDatabase = false;
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (clickedToolbar)
                            return;
                        if (changeInDatabase) {
                            changeInDatabase = !changeInDatabase;
                            return;
                        }
                        Map <String, Object> data = (Map<String, Object>) dataSnapshot.getValue();

                        if (!userid.equals(fuser.getUid()) &&
                                (!(data.get("email").equals(receiverData[0]) &&
                                        data.get("fullname").equals(receiverData[1]) &&
                                        data.get("imageURL").equals(receiverData[2]) &&
                                        data.get("phonenumber").equals(receiverData[3]) &&
                                        data.get("username").equals(receiverData[4])
                                        && data.get("venmo").equals(receiverData[5]))))
                            return;

                        Intent intent = new Intent(MessagePersonActivity.this, ProfileActivity.class);
                        intent.putExtra("username", data.get("username").toString());
                        intent.putExtra("fullname", data.get("fullname").toString());
                        intent.putExtra("email", data.get("email").toString());
                        intent.putExtra("phonenumber", data.get("phonenumber").toString());
                        intent.putExtra("id", data.get("id").toString());
                        intent.putExtra("imageURL", data.get("imageURL").toString());
                        intent.putExtra("venmo", data.get("venmo").toString());
                        startActivityForResult(intent, 1);
                        clickedToolbar = true;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessages(final String myid, final String userid, final String imageurl) {
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {
                        mchat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(MessagePersonActivity.this, mchat, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                clickedToolbar = false;
                changeInDatabase = false;
            }
        }
    }
}
