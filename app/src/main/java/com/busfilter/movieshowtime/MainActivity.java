package com.busfilter.movieshowtime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    String jsonObject="{\"status\":200,\"data\":{\"MovieInfo\":[{\"id\":\"4379\",\"releasedate\":\"2016-10-05\",\"cast\":\"Anupam Kher, Kiara Advani, Sushant Singh Rajput\",\"chinese title\":\"English\",\"genre\":\"Drama\",\"duration\":\"190 min\",\"synopsis\":\"M. S. Dhoni The Untold Story chronicles the Indian Cricket Captain\\u2019s untold journey \\u2013 one which is shouldered with unflinching dedication through the years. It\\u2019s an ode to his silent responsibility to his craft, to an unerring sense of focus and perseverance through troubled times, eventually to emerge as one that rewrote a nation\\u2019s cricketing destiny \\u2013 and his own.\",\"director\":\"Neeraj Pandey\",\"language\":\"Hindi\",\"title\":\"MS Dhoni The Untold Story\",\"subtitles\":\"Neeraj Pandey\",\"movieimage\":\"gvgmovieimage\\/2016-10-05\\/MV5BOTY5ODQxMTY3M15BMl5BanBnXkFtZTgwOTA3NTA4OTE@._V1_SX400.jpg\",\"globalurl\":\"http:\\/\\/sg-movies.com\\/getmovies\\/\"}],\"today\":{\"theatre\":[{\"theatreid\":\"11\",\"title\":\"GV City Square\",\"address\":\"180 Kitchener Road #05-02\\/03 City Square Mall, Singapore 208539\",\"showtime\":[{\"id\":\"136683\",\"showtimeurl\":\"http:\\/\\/www.gv.com.sg\\/GVSeatSelection#\\/cinemaId\\/11\\/filmCode\\/4598\\/showDate\\/05-10-2016\\/showTime\\/1800\\/hallNumber\\/5\",\"theatreid\":\"11\",\"showtime\":\"06:00 AM\"},{\"id\":\"136684\",\"showtimeurl\":\"http:\\/\\/www.gv.com.sg\\/GVSeatSelection#\\/cinemaId\\/11\\/filmCode\\/4598\\/showDate\\/05-10-2016\\/showTime\\/2150\\/hallNumber\\/2\",\"theatreid\":\"11\",\"showtime\":\"09:50 AM\"},{\"id\":\"136684\",\"showtimeurl\":\"http:\\/\\/www.gv.com.sg\\/GVSeatSelection#\\/cinemaId\\/11\\/filmCode\\/4598\\/showDate\\/05-10-2016\\/showTime\\/2150\\/hallNumber\\/2\",\"theatreid\":\"11\",\"showtime\":\"12:50 PM\"},{\"id\":\"136684\",\"showtimeurl\":\"http:\\/\\/www.gv.com.sg\\/GVSeatSelection#\\/cinemaId\\/11\\/filmCode\\/4598\\/showDate\\/05-10-2016\\/showTime\\/2150\\/hallNumber\\/2\",\"theatreid\":\"11\",\"showtime\":\"01:50 PM\"},{\"id\":\"136684\",\"showtimeurl\":\"http:\\/\\/www.gv.com.sg\\/GVSeatSelection#\\/cinemaId\\/11\\/filmCode\\/4598\\/showDate\\/05-10-2016\\/showTime\\/2150\\/hallNumber\\/2\",\"theatreid\":\"11\",\"showtime\":\"02:50 PM\"}],\"globalurl\":\"http:\\/\\/sg-movies.com\\/getmovies\\/\"},{\"theatreid\":\"04\",\"title\":\"GV Jurong Point\",\"address\":\"#03-25B\\/26, 1 Jurong West Central 2, Jurong Point, Singapore 649846\",\"showtime\":[{\"id\":\"136624\",\"showtimeurl\":\"http:\\/\\/www.gv.com.sg\\/GVSeatSelection#\\/cinemaId\\/04\\/filmCode\\/4598\\/showDate\\/05-10-2016\\/showTime\\/2125\\/hallNumber\\/2\",\"theatreid\":\"04\",\"showtime\":\"09:25 PM\"}],\"globalurl\":\"http:\\/\\/sg-movies.com\\/getmovies\\/\"},{\"theatreid\":\"12\",\"title\":\"GV Suntec City\",\"address\":\"3 Temasek Boulevard #03-373 Suntec City Mall, 038983.\",\"showtime\":[{\"id\":\"136439\",\"showtimeurl\":\"http:\\/\\/www.gv.com.sg\\/GVSeatSelection#\\/cinemaId\\/12\\/filmCode\\/4598\\/showDate\\/05-10-2016\\/showTime\\/2050\\/hallNumber\\/4\",\"theatreid\":\"12\",\"showtime\":\"08:50 PM\"}],\"globalurl\":\"http:\\/\\/sg-movies.com\\/getmovies\\/\"}]}}}";
    ListView list;
    private String movie_url = "";
    private ImageView imageView;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MoviesAdapter mAdapter;
        imageView = (ImageView) findViewById(R.id.movie_image);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        try {
            JSONObject jObj = new JSONObject(jsonObject);
            int status = Integer.parseInt(jObj.getString("status"));
            String data = jObj.getString("data");
            JSONObject data_jObj = new JSONObject(data);
            String movieInfo = data_jObj.getString("MovieInfo");
            if (status == 200) {
                JSONArray movie_jarray = new JSONArray(movieInfo);
                for (int i = 0; i < movie_jarray.length(); ++i) {
                    JSONObject rec = movie_jarray.getJSONObject(i);
                    movie_url = rec.getString("globalurl") + rec.getString("movieimage");
                    getSupportActionBar().setTitle(rec.getString("title"));
                    Glide.with(getApplicationContext())
                            .load(movie_url)
                            .into(imageView);
                }
                String show_time_data=data_jObj.getString("today");
                JSONObject theatreObj = new JSONObject(show_time_data);
                String get_theatre_data = theatreObj.getString("theatre");
                if (get_theatre_data != null) {

                    JSONArray jarray = new JSONArray(get_theatre_data);
                    ArrayList<HashMap<String, String>> titles = new ArrayList<>();
                    for (int i = 0; i < jarray.length(); ++i) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject rec = jarray.getJSONObject(i);
                        JSONArray showtime_array = new JSONArray(rec.getString("showtime"));
                        if (showtime_array.length() > 0) {
                            map.put("theatre_id", rec.getString("theatreid"));
                            map.put("theatre_name", rec.getString("title"));
                            map.put("address", rec.getString("address"));
                            map.put("theatre_info", String.valueOf(rec.getString("showtime")));
                            titles.add(map);
                        }
                    }

                    recyclerView.setNestedScrollingEnabled(true);
                    mAdapter = new MoviesAdapter(MainActivity.this, titles);

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(mLayoutManager);

                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.invalidate();
                    mAdapter.notifyDataSetChanged();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
