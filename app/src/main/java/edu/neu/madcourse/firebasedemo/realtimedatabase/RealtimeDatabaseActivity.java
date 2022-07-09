package edu.neu.madcourse.firebasedemo.realtimedatabase;
/****************************************************************/
import android.os.Bundle;
import android.speech.RecognizerIntent;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.neu.madcourse.firebasedemo.LinkAdapter;
import edu.neu.madcourse.firebasedemo.R;
import edu.neu.madcourse.firebasedemo.realtimedatabase.models.User;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RealtimeDatabaseActivity extends AppCompatActivity {

    private static final String TAG = RealtimeDatabaseActivity.class.getSimpleName();

    private DatabaseReference mDatabase;
    private TextView userName;
    private TextView score;
    private TextView userName2;
    private TextView score2;
    private RadioButton player1;
    private RadioButton user1;
    private Button add5;
    private Button addSticker;

    RecyclerView linkRecyclerView;
    List<User> urlList;

    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_realtime_database);
        setContentView(R.layout.new_realtime_db);

        urlList = new ArrayList<>();
        urlList.add(new User("User1","10"));
        urlList.add(new User("User2","10"));

        linkRecyclerView = findViewById(R.id.link_recycler_view);

        linkRecyclerView.setHasFixedSize(true);

        linkRecyclerView.setLayoutManager(new LinearLayoutManager( this));
        myAdapter = new LinkAdapter(urlList, this);

        linkRecyclerView.setAdapter(myAdapter);
        user1 = (RadioButton)findViewById(R.id.User1Button);
        /*
        userName = (TextView) findViewById(R.id.username);
        score = (TextView) findViewById(R.id.score);
        userName2 = (TextView) findViewById(R.id.username2);
        score2 = (TextView) findViewById(R.id.score2);

        player1 = (RadioButton)findViewById(R.id.player1);
        */
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //add5 = (Button)findViewById(R.id.add5);
        /*addSticker = (Button)findViewById(R.id.add_sticker);
        addSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealtimeDatabaseActivity.this.onAddScore(mDatabase, player1.isChecked() ? "user1" : "user2");
            }
        });*/



        mDatabase.child("users").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);
                        urlList.add(user);
                        myAdapter.notifyDataSetChanged();

                        /*if (dataSnapshot.getKey().equalsIgnoreCase("user1")) {
                            score.setText(user.score);
                            userName.setText(user.username);
                        } else {
                            score2.setText(String.valueOf(user.score));
                            userName2.setText(user.username);
                        }*/
                        Log.d("SK", "onChildAdded: dataSnapshot = " + dataSnapshot.getValue());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);
                        urlList.add(user);
                        myAdapter.notifyDataSetChanged();

                        /*if (dataSnapshot.getKey().equalsIgnoreCase("user1")) {
                            score.setText(user.score);
                            userName.setText(user.username);
                        } else {
                            score2.setText(String.valueOf(user.score));
                            userName2.setText(user.username);
                        }*/
                        Log.d("SK", "onChildChanged: "+dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("SK", "onCancelled:" + databaseError);
                    }
                }
        );
    }

    /**
     * Called on score add
     * @param postRef
     * @param user
     */
    public void onAddScore(DatabaseReference postRef, String user) {
        //addImage();
        /*postRef
                .child("users")
                .child(user)
                .runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                User u = mutableData.getValue(User.class);
                if (u == null) {
                    return Transaction.success(mutableData);
                }

                u.score = String.valueOf(Integer.valueOf(u.score) + 5);

                mutableData.setValue(u);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });*/
    }

    public void resetUsers(View view){

        User user = new User("user1", "0");
        mDatabase.child("users").child(user.username).setValue(user);

        User user2 = new User("user2", "0");
        mDatabase.child("users").child(user2.username).setValue(user2);
    }

    public void addImage(View view){

        Log.d("SK","Inside addImage");

        DatabaseReference mdb;
        mdb=FirebaseDatabase.getInstance().getReference().child("users");

        Random rand = new Random();

        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(5);
        String username;
        if(user1.isChecked()){
            username = "user1";
        }
        else{
            username = "user2";
        }
        User user = new User(username, String.valueOf(rand_int1));
        //mDatabase.child("users").child(user.username).setValue(user);
        mdb.push().setValue(user);
    }

    public void doAddDataToDb(View view){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        // Read from the database by listening for a change to that item.
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("SK", "Value is: " + value);
                TextView tv = (TextView)findViewById(R.id.dataUpdateTextView);
                tv.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("SK", "Failed to read value.", error.toException());
            }
        });

    }


}




/***********************************************************/
/*
import android.os.Bundle;
import android.speech.RecognizerIntent;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

import edu.neu.madcourse.firebasedemo.R;
import edu.neu.madcourse.firebasedemo.realtimedatabase.models.User;
*/
/*
public class RealtimeDatabaseActivity extends AppCompatActivity {

    private static final String TAG = RealtimeDatabaseActivity.class.getSimpleName();

    private DatabaseReference mDatabase;
    private TextView userName;
    private TextView score;
    private TextView userName2;
    private TextView score2;
    private RadioButton player1;
    private Button add5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_database);

        userName = (TextView) findViewById(R.id.username);
        score = (TextView) findViewById(R.id.score);
        userName2 = (TextView) findViewById(R.id.username2);
        score2 = (TextView) findViewById(R.id.score2);

        player1 = (RadioButton)findViewById(R.id.player1);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        add5 = (Button)findViewById(R.id.add5);
        add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealtimeDatabaseActivity.this.onAddScore(mDatabase, player1.isChecked() ? "user1" : "user2");
            }
        });



        mDatabase.child("users").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);

                        if (dataSnapshot.getKey().equalsIgnoreCase("user1")) {
                            score.setText(user.score);
                            userName.setText(user.username);
                        } else {
                            score2.setText(String.valueOf(user.score));
                            userName2.setText(user.username);
                        }
                        Log.e(TAG, "onChildAdded: dataSnapshot = " + dataSnapshot.getValue());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);

                        if (dataSnapshot.getKey().equalsIgnoreCase("user1")) {
                            score.setText(user.score);
                            userName.setText(user.username);
                        } else {
                            score2.setText(String.valueOf(user.score));
                            userName2.setText(user.username);
                        }
                        Log.v(TAG, "onChildChanged: "+dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled:" + databaseError);
                    }
                }
        );
    }

    **
     * Called on score add
     * @param postRef
     * @param user
     *
    private void onAddScore(DatabaseReference postRef, String user) {
        postRef
                .child("users")
                .child(user)
                .runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                User u = mutableData.getValue(User.class);
                if (u == null) {
                    return Transaction.success(mutableData);
                }

                u.score = String.valueOf(Integer.valueOf(u.score) + 5);

                mutableData.setValue(u);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }

    public void resetUsers(View view){

        User user = new User("user1", "0");
        mDatabase.child("users").child(user.username).setValue(user);

        User user2 = new User("user2", "0");
        mDatabase.child("users").child(user2.username).setValue(user2);
    }

    public void doAddDataToDb(View view){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        // Read from the database by listening for a change to that item.
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                TextView tv = (TextView)findViewById(R.id.dataUpdateTextView);
                tv.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }


}*/


