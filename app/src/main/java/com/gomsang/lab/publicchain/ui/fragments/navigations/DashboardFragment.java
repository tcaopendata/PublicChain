package com.gomsang.lab.publicchain.ui.fragments.navigations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gomsang.lab.publicchain.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFragment extends Fragment {

    private DatabaseReference database;

    public DashboardFragment() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View convertView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        database.child("campaigns").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) ((TextView) convertView.findViewById(R.id.campaignSum)).setText(dataSnapshot.getChildrenCount() + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        database.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) ((TextView) convertView.findViewById(R.id.userSum)).setText(dataSnapshot.getChildrenCount() + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return convertView;
    }
}
