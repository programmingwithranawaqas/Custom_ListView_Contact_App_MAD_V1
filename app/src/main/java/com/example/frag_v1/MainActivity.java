package com.example.frag_v1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements ContactAdapter.ContactClicked {

    FloatingActionButton fabAdd;
    // hooks of content fragment
    TextView tvName;
    ImageView ivCall, ivUrl, ivAddress, ivProfile;

    // hooks of listFrag
    ListView lvContact;
    FragmentManager manager;
    Fragment listFrag, contentFrag;
    Contact c;
    View viewofContentFrag, viewofListFrag;
    RelativeLayout portrait, landscape;

    ContactAdapter adapter;

    Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        controlOrientation();

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+contact.getPhone()));
                startActivity(intent);
            }
        });

        ivUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(contact.getUrl()));
                startActivity(intent);
            }
        });

        ivAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:(0,0)?q="+contact.getAddress()));
                startActivity(intent);
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName, etPhone, etUrl, etAddress;


                AlertDialog.Builder editDialog = new AlertDialog.Builder(MainActivity.this);
                editDialog.setTitle("Add New Contact");
                View view = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.contact_edit_form, null, false);

                etName = view.findViewById(R.id.etName);
                etPhone = view.findViewById(R.id.etPhone);
                etUrl = view.findViewById(R.id.etUrl);
                etAddress = view.findViewById(R.id.etAddress);


                editDialog.setView(view);

                editDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString().trim();
                        String phone = etPhone.getText().toString().trim();
                        String address = etAddress.getText().toString().trim();
                        String url = etUrl.getText().toString().trim();
                        // url validation

                        Contact newContact = new Contact(name, phone, url, address);
                        MyApplication.contacts.add(newContact);
                        adapter.notifyDataSetChanged();
                    }
                });
                editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                editDialog.show();
            }
        });
    }

    private void controlOrientation()
    {
        portrait = findViewById(R.id.portrait_mode);
        landscape = findViewById(R.id.landscape_mode);
        if(portrait!=null)
        {
            manager.beginTransaction()
                    .hide(contentFrag)
                    .show(listFrag)
                    .commit();
        }
        else
        {
            manager.beginTransaction()
                    .show(contentFrag)
                    .show(listFrag)
                    .commit();
        }
    }

    private void init()
    {
        fabAdd = findViewById(R.id.fabAdd);

        manager = getSupportFragmentManager();
        listFrag = manager.findFragmentById(R.id.listfrag);
        contentFrag = manager.findFragmentById(R.id.contentfrag);

        viewofContentFrag = contentFrag.getView();
        viewofListFrag = listFrag.getView();

        tvName = viewofContentFrag.findViewById(R.id.tvName);
        ivCall= viewofContentFrag.findViewById(R.id.ivCall);
        ivAddress = viewofContentFrag.findViewById(R.id.ivAddress);
        ivUrl = viewofContentFrag.findViewById(R.id.ivUrl);
        ivProfile = viewofContentFrag.findViewById(R.id.ivProfilePic);

        lvContact = viewofListFrag.findViewById(R.id.lvContacts);

        hideContent();

        // initializing adapter
        adapter = new ContactAdapter(MainActivity.this, MyApplication.contacts);
        lvContact.setAdapter(adapter);
    }

    private void hideContent()
    {
        ivAddress.setVisibility(View.GONE);
        ivCall.setVisibility(View.GONE);
        ivUrl.setVisibility(View.GONE);
        ivProfile.setVisibility(View.GONE);

        tvName.setText("EMPTY");

    }

    private void unhideContent()
    {
        ivAddress.setVisibility(View.VISIBLE);
        ivCall.setVisibility(View.VISIBLE);
        ivUrl.setVisibility(View.VISIBLE);
        ivProfile.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContactOnContentF(int position) {
        contact = MyApplication.contacts.get(position);
        tvName.setText(contact.getName());
        unhideContent();

        if(portrait!=null)
        {
            manager.beginTransaction()
                    .show(contentFrag)
                    .hide(listFrag)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void deleteContact(int position) {
        Toast.makeText(this, "position : "+position, Toast.LENGTH_SHORT).show();
        MyApplication.contacts.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateContact(Contact updatedContact, int position) {
        Contact oldContact = MyApplication.contacts.get(position);
        oldContact.setName(updatedContact.getName());
        oldContact.setAddress(updatedContact.getAddress());
        oldContact.setPhone(updatedContact.getPhone());
        oldContact.setUrl(updatedContact.getUrl());
        adapter.notifyDataSetChanged();
    }
}