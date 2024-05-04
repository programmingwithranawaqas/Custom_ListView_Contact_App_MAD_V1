package com.example.frag_v1;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {


    public interface ContactClicked{
        public void showContactOnContentF(int position);
        public void deleteContact(int position);
        public void updateContact(Contact updatedContact, int position);
    }

    ContactClicked parentActivity;

    public ContactAdapter(Context c, ArrayList<Contact> data)
    {
        super(c, 0, data); // we are sending 0 as a resource id of view.
        parentActivity = (ContactClicked) c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =convertView;

        if(v == null)
        {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_contact_item_design, parent, false);
        }

        TextView tvName = v.findViewById(R.id.tvName);
        Contact contact = getItem(position);
        tvName.setText(contact.getName());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 parentActivity.showContactOnContentF(position);
            }
        });

        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EditText etName, etPhone, etUrl, etAddress;


                AlertDialog.Builder editDialog = new AlertDialog.Builder(parent.getContext());
                editDialog.setTitle("Update Contact");
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.contact_edit_form, null, false);

                etName = view.findViewById(R.id.etName);
                etPhone = view.findViewById(R.id.etPhone);
                etUrl = view.findViewById(R.id.etUrl);

                etAddress = view.findViewById(R.id.etAddress);
                etName.setText(contact.getName());
                etPhone.setText(contact.getPhone());
                etAddress.setText(contact.getAddress());
                etUrl.setText(contact.getUrl());

                editDialog.setView(view);

                editDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString().trim();
                        String phone = etPhone.getText().toString().trim();
                        String address = etAddress.getText().toString().trim();
                        String url = etUrl.getText().toString().trim();
                        // url validation

                        Contact updateContact = new Contact(name, phone, url, address);
                        parentActivity.updateContact(updateContact, position);
                        parentActivity.showContactOnContentF(position);
                    }
                });
                editDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        parentActivity.deleteContact(position);
                    }
                });


                editDialog.show();



                return false;
            }
        });

        return v;
    }
}
