package com.busfilter.movieshowtime;

/**
 * Created by User on 13/06/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movie> moviesList;
    EndlessScrollListener endlessScrollListener;
    ArrayList<HashMap<String, String>> data=null;
    HashMap<String, String> resultp = new HashMap<String, String>();
    private static final String TAG = "Main";
    Context context;
    Activity cntx;
    ViewGroup parent;
    private LinearLayout ll;
    View itemView1;
    public void setEndlessScrollListener(EndlessScrollListener endlessScrollListener) {
        this.endlessScrollListener = endlessScrollListener;
    }
    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, address;

        public MyViewHolder(View view) {
            super(view);
            itemView1=view;
            title = (TextView) view.findViewById(R.id.title);
            address = (TextView) view.findViewById(R.id.address);
            ll = (LinearLayout) view.findViewById(R.id.show_linear_layout);
        }
    }


    public MoviesAdapter(Activity context, ArrayList<HashMap<String, String>> arraylist) {
        this.context=context;
        cntx=context;
        this.data = arraylist;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_list_view, parent, false);

        this.parent=parent;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        // Get the position
        resultp = getItem(position);
        View itemView = LayoutInflater.from(this.parent.getContext())
                .inflate(R.layout.show_list_view, this.parent, false);
        context = this.parent.getContext();
        holder.title.setText(resultp.get("theatre_name"));
        holder.address.setText(resultp.get("address"));
        try {
            ll = (LinearLayout) itemView1.findViewById(R.id.show_linear_layout);
            JSONArray showtime_array = new JSONArray(resultp.get("theatre_info"));
            int count1 = 0;
            LinearLayout l2 = null;
            LinearLayout lj = null;
            // Log.d("Total Count",""+showtime_array.length());
            if (showtime_array.length() > 4) {

                for (int j = 0; j < showtime_array.length(); j++) {

                    JSONObject showrec = showtime_array.getJSONObject(j);
                    Button btn = new Button(context);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.leftMargin=3;
                    params.rightMargin=3;
                    params.bottomMargin=3;
                    btn.setLayoutParams(params);
                    if (count1 == 0) {
                        lj = null;

                        lj = new LinearLayout(context);
                        lj.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                        lj.setWeightSum(4);
                        lj.setOrientation(LinearLayout.HORIZONTAL);
                    }

                    btn.setText(showrec.getString("showtime"));
                    btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);
                    int idx = ll.indexOfChild(btn);
                    String showURL =showrec.getString("showtime");
                    btn.setTag(showURL);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //Log.d("Url", String.valueOf(v.getTag()));
                            String str = (String) v.getTag();
                            Toast.makeText(context, "ShowTime Clicked" + str, Toast.LENGTH_LONG).show();

                        }
                    });
                    btn.setTextColor(Color.WHITE);
                    btn.setBackgroundResource(R.drawable.ticket_icon);

                    btn.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                    btn.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                    btn.setMinHeight(0);
                    btn.setMinWidth(0);
                    lj.addView(btn);
                    count1++;

                    if (modulo(count1, 4) == 0) {
                        count1 = 0;
                        ll.addView(lj);
                        lj = null;
                    }
                }
                if (modulo(count1, 4) != 0) {
                    ll.addView(lj);

                    lj = null;
                }
            } else {
                l2 = new LinearLayout(context);
                l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                l2.setWeightSum(4);
                l2.setOrientation(LinearLayout.HORIZONTAL);
                for (int j = 0; j < showtime_array.length(); j++) {
                    JSONObject showrec = showtime_array.getJSONObject(j);
                    Button btn = new Button(context);
                    FrameLayout frame_layout=new FrameLayout(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.leftMargin=3;
                    params.rightMargin=3;
                    params.bottomMargin=3;
                    btn.setLayoutParams(params);

                    //btn.setLayoutParams(params);
                    btn.setText(showrec.getString("showtime"));
                    btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);
                    //btn.setWidth(100);
                    //btn.setOnClickListener(buttonClick);
                    //ll.addView(btn);
                    int idx = ll.indexOfChild(btn);
                    String showURL =showrec.getString("showtime");
                    btn.setTag(showURL);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String str = (String) v.getTag();
                            Toast.makeText(context, "ShowTime Clicked" + str, Toast.LENGTH_LONG).show();
                        }
                    });
                    btn.setTextColor(Color.WHITE);
                    btn.setBackgroundResource(R.drawable.ticket_icon);
                    btn.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                    btn.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                    btn.setMinHeight(0);
                    btn.setMinWidth(0);
                    l2.addView(btn);
                }
                ll.addView(l2);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int modulo(int m, int n) {
        int mod = m % n;

        return (mod < 0) ? mod + n : mod;
    }
    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        else
        return data.size();
    }
    public interface EndlessScrollListener {
        /**
         * Loads more data.
         * @param position
         * @return true loads data actually, false otherwise.
         */
        boolean onLoadMore(int position);
    }
    public HashMap<String, String> getItem(int position)
    {
        return data.get(position);
    }
}