package com.example.frag_v1;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {
    public static ArrayList<Contact> contacts;

    @Override
    public void onCreate() {
        super.onCreate();
        contacts = new ArrayList<>();
        contacts.add(new Contact("Amir","042-3111300200", "https://www.umt.edu.pk", "UMT Lahore"));
        contacts.add(new Contact("Ramzan","03234677035", "https://www.google.com", "Ucp Lahore"));
        contacts.add(new Contact("Rizwan","03002118562", "https://www.youtube.com", "Pizza Online"));
        contacts.add(new Contact("Raja Azhar","03344398654", "https://www.facebook.com", "Dagwood Lahore"));
    }
}
