package org.o7planning.androidcamerademo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyListFragment extends Fragment {
    private String[] nameArray = {"PlaceHolder"};
    private String[] dateArray = {"DateHolder"};
    private Integer[] idArray = {R.drawable.rowlett};
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AdaptActivity adaptor = new AdaptActivity(getActivity(), nameArray, dateArray, idArray);
        listView = getActivity().findViewById (R.id.ViewList);
        listView.setAdapter(adaptor);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (String name : nameArray) {
                    Intent intent = new Intent(getActivity(), DetailFragment.class);
                    String message = nameArray[position];
                    String ext = name.substring(name.lastIndexOf("."));
                    if (ext.equals("jpg") || ext.equals("png") || ext.equals("webp")) {
                        intent.putExtra("img", message);
                    } else {
                        intent.putExtra("vid", message);
                    }
                    startActivity(intent);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, container, false);
    }
}
