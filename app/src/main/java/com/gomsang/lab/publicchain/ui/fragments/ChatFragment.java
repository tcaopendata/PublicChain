package com.gomsang.lab.publicchain.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.FragmentChatBinding;
import com.gomsang.lab.publicchain.datas.ChatMessageData;
import com.gomsang.lab.publicchain.libs.Constants;
import com.gomsang.lab.publicchain.libs.PublicChainState;
import com.gomsang.lab.publicchain.ui.adapters.ChatAdpater;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatFragment extends Fragment {
    private static final String ARG_CHANNEL = "channel";

    private String channel;
    private DatabaseReference database;

    public ChatFragment() {
        // Required empty public constructor
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static ChatFragment newInstance(String channel) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CHANNEL, channel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            channel = getArguments().getString(ARG_CHANNEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentChatBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        binding.chatRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        final ChatAdpater chatAdpater = new ChatAdpater(getActivity());
        binding.chatRecycler.setAdapter(chatAdpater);

        database.child("chats").child(Constants.CHATCHANNEL_COMMUNITY).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatAdpater.addChat(dataSnapshot.getValue(ChatMessageData.class));
                chatAdpater.notifyDataSetChanged();
                binding.chatRecycler.scrollToPosition(chatAdpater.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        binding.sendButton.setOnClickListener(view -> {
            if (binding.messageEditText.getText().toString().isEmpty()) return;
            ChatMessageData chatMessageData = new ChatMessageData("text",
                    binding.messageEditText.getText().toString(),
                    PublicChainState.getInstance().getCurrentUserData().getUid());
            database.child("chats").child(Constants.CHATCHANNEL_COMMUNITY).child(System.currentTimeMillis() + "").setValue(chatMessageData);
            binding.messageEditText.setText("");
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
