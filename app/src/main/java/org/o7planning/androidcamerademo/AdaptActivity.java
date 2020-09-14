package org.o7planning.androidcamerademo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.VideoView;
import android.widget.TextView;

public class AdaptActivity extends ArrayAdapter {
    private final Context  context;
    private final Integer[] iDarray;
    private final String[] nameArray;
    private final String[] dateArray;
    private static class Placeholder {
        public TextView name;
        public TextView date;
        public ImageView img;
        public VideoView vid;
    }

    public AdaptActivity(Activity context, String[] nameArrayParam, String[] dateArrayParam, Integer[] iDArrayParam){
        super(context, R.layout.activity_view , nameArrayParam);

        this.context = context;
        this.iDarray = iDArrayParam;
        this.nameArray = nameArrayParam;
        this.dateArray = dateArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = view;
        Placeholder holder = new Placeholder();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            rowView = inflater.inflate(R.layout.activity_view, null, true);

            for (String name : nameArray) {
                holder.name = rowView.findViewById(R.id.viewName);
                holder.date = rowView.findViewById(R.id.viewDate);

                String ext = name.substring(name.lastIndexOf("."));
                if (ext.equals("jpg") || ext.equals("png") || ext.equals("webp")) {
                    holder.img = rowView.findViewById(R.id.viewImg);
                } else {
                    holder.vid = rowView.findViewById(R.id.viewVid);
                }
                rowView.setTag(holder);
            }
        } else {
            for (String name : nameArray) {
                holder = (Placeholder) rowView.getTag();

                holder.name.setText(nameArray[position]);
                holder.date.setText(dateArray[position]);

                String ext = name.substring(name.lastIndexOf("."));
                if (ext.equals("jpg") || ext.equals("png") || ext.equals("webp")) {
                    holder.img.setImageResource(iDarray[position]);
                } else {
                    holder.vid.setVideoPath(iDarray[position].toString());
                }
            }
        }
        return rowView;
    }
}
