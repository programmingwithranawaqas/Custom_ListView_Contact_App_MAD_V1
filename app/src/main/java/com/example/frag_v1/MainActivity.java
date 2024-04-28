package com.example.frag_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ContactAdapter.ContactClicked {

    // hooks of content fragment
    TextView tvName;
    ImageView ivCall, ivUrl, ivAddress;

    FragmentManager manager;
    Fragment listFrag, contentFrag;

    Contact c;
    View viewofContentFrag;

    LinearLayout portrait, landscape;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        portrait = findViewById(R.id.portrait_mode);
        landscape = findViewById(R.id.landscape_mode);
        manager = getSupportFragmentManager();
        listFrag = manager.findFragmentById(R.id.listfrag);
        contentFrag = manager.findFragmentById(R.id.contentfrag);
        viewofContentFrag = contentFrag.requireView();

        tvName = viewofContentFrag.findViewById(R.id.tvName);
        ivCall= viewofContentFrag.findViewById(R.id.ivCall);
        ivAddress = viewofContentFrag.findViewById(R.id.ivAddress);
        ivUrl = viewofContentFrag.findViewById(R.id.ivUrl);


        if(portrait!=null)
        {
            manager.beginTransaction()
                    .show(listFrag)
                    .hide(contentFrag)
                    .commit();
        }
        else
        {
            manager.beginTransaction()
                    .show(listFrag)
                    .show(contentFrag)
                    .commit();
        }


    }

    @Override
    public void onContactClick(int position) {
        c = MyApplication.contacts.get(position);
        tvName.setText(c.getName());
    }
}