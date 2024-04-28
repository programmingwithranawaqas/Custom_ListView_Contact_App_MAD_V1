package com.example.frag_v1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListF extends Fragment {

    public ListF() {
        // Required empty public constructor
    }

    /*
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast.makeText(requireContext(), ""+position, Toast.LENGTH_SHORT).show();
    }
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView lvContacts = view.findViewById(R.id.lvContacts);
        ContactAdapter adapter = new ContactAdapter(requireContext(), MyApplication.contacts);
        lvContacts.setAdapter(adapter);

        /*
          // build-in design with build-in adapter
        ArrayList<String> names = new ArrayList<>();
        names.add("Ali");
        names.add("Raza");
        names.add("Mubeen");
        names.add("Osama");
        names.add("Khadim");
        names.add("Scroll");
        names.add("Abu");
        setListAdapter(new ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, names));
*/

    }
}





