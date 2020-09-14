package org.o7planning.androidcamerademo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String imgExtra = getActivity().getIntent().getStringExtra("img");
        ImageView img = getView().findViewById(R.id.ImgDetail);
        img.setImageResource(Integer.parseInt(imgExtra));

        String vidExtra = getActivity().getIntent().getStringExtra("vid");
        VideoView vid = getView().findViewById(R.id.VidDetail);
        vid.setVideoPath(vidExtra);

        return inflater.inflate(R.layout.activity_detail, container, false);
    }
}

