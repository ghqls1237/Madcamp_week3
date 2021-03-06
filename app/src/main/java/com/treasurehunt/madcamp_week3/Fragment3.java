package com.treasurehunt.madcamp_week3;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.service.notification.NotificationListenerService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String[] Time;
    private Double[] Latitude;
    private Double[] Longitude;

    private String[] Name;
    private Integer[] Score;

    private String server_result;
    private String server_result2;

    public Fragment3() {
        // Required empty public constructor
    }

    void doJSONParser() throws IOException {
        // json 데이터

        StringBuffer sb = new StringBuffer();
        String str = server_result;


        try {
            JSONArray jarray = new JSONArray(str);
            int len = jarray.length();

            Time = new String[len];
            Latitude = new Double[len];
            Longitude = new Double[len];

            for (int i=0;i<len;i++){
                JSONObject jObject = jarray.getJSONObject(i);

                String time = jObject.getString("timeSought");
                Double latitude = jObject.getDouble("latitude");
                Double longitude = jObject.getDouble("longitude");



                Time[i] = time;
                Latitude[i] = latitude;
                Longitude[i] = longitude;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void doJSONParser2() throws IOException {
        // json 데이터

        StringBuffer sb = new StringBuffer();
        String str = server_result2;


        try {
            JSONArray jarray = new JSONArray(str);
            int len = jarray.length();

            Name = new String[len];
            Score = new Integer[len];

            for (int i=0;i<len;i++){
                JSONObject jObject = jarray.getJSONObject(i);

                String name = jObject.getString("nickname");
                int score = jObject.getInt("score");


                Name[i] = name;
                Score[i] = score;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        RetrofitClient retrofitClient = new RetrofitClient();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ImageView image = (ImageView)view.findViewById(R.id.userImage);
        TextView textLevel = view.findViewById(R.id.level);
        TextView textScore = view.findViewById(R.id.score);
        Call<Score> call = retrofitClient.apiService.score(uid);
        ListView listView;
        ListViewAdapter adapter;

        ListView listview2;
        RankingListViewAdapter adapter2;

        adapter = new ListViewAdapter();


        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(adapter);


        adapter2 = new RankingListViewAdapter();
        listview2 = (ListView) view.findViewById(R.id.rankinglistView);
        listview2.setAdapter(adapter2);

//        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),R.font.harbinger);
        Typeface type = ResourcesCompat.getFont(getActivity(), R.font.harbinger);
        textLevel.setTypeface(type);
        textScore.setTypeface(type);

        call.enqueue(new Callback<Score>() {
            @Override
            public void onResponse(Call<Score> call, Response<Score> response) {
                if (response.isSuccessful()) {
                    Integer score = response.body().getScore();

                    if (score == 0){
                        image.setImageResource(R.drawable.level0);
                        textLevel.setText("Level  0");
                        textScore.setText("Score " + score);
                    }
                    else if (score == 1){
                        image.setImageResource(R.drawable.level1);
                        textLevel.setText("Level  1");
                        textScore.setText("Score  " + score);
                    }
                    else if(score == 2){
                        image.setImageResource(R.drawable.level2);
                        textLevel.setText("Level  2");
                        textScore.setText("Score  " + score);
                    }
                    else if(score > 2){
                        image.setImageResource(R.drawable.level3);
                        textLevel.setText("Level  3");
                        textScore.setText("Score  " + score);
                    }
                }
            }
            @Override
            public void onFailure(Call<Score> call, Throwable t) {
                System.out.println("실패함ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ");
            }
        });


        RetrofitClient retrofitClient2 = new RetrofitClient();

        Call<JsonArray> call2 = retrofitClient2.apiService.myTreasure(uid);
        call2.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    server_result = response.body().toString();
                    try {
                        doJSONParser();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0 ; i<Time.length ; i++){
                        adapter.addItem(Time[i], "" + Latitude[i], "" + Longitude[i]);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                System.out.println("실패함ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ");
            }
        });


        Call<JsonArray> call3 = retrofitClient2.apiService.ranking();
        call3.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    server_result2 = response.body().toString();
                    try {
                        doJSONParser2();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0 ; i < Name.length; i ++){

                        if (i ==0 ){
                            adapter2.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.medalfirst),Name[i],Score[i]);
                        }
                        else if (i==1){
                            adapter2.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.medalsecond),Name[i],Score[i]);
                        }
                        else if (i==2){
                            adapter2.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.medalthird),Name[i],Score[i]);
                        }
                    }
                    adapter2.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                System.out.println("실패함ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ");
            }
        });



        return view;
    }
}