package com.example.frag_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    ContactClicked parentActivity;
    public  interface  ContactClicked
    {
        public void onContactClick(int position);
    }


    public ContactAdapter(@NonNull Context context,  @NonNull ArrayList<Contact> objects) {
        super(context, 0, objects);
        parentActivity = (ContactClicked) context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v==null)
        {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_contact_item_design, parent, false);
        }

        // hooks of design file of single contact item
        TextView tvName = v.findViewById(R.id.tvName);
        Contact c = getItem(position);
        tvName.setText(c.getName());

        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.onContactClick(position);
            }
        });

        return v;
    }
}
