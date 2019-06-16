package com.example.dicoding_submisi2.ui.main;

import android.support.v4.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dicoding_submisi2.Item;
import com.example.dicoding_submisi2.MainActivity;
import com.example.dicoding_submisi2.MyAdapter;
import com.example.dicoding_submisi2.R;

import java.util.ArrayList;


public class SerialTvFragment extends Fragment {
    private  String[] dataJudul;
    private String[] dataDescription;
    private  String[] dataTanggalRilis;
    private TypedArray dataPoster;
    public MyAdapter serialTvAdapter;


    private RecyclerView rvMovie;
    private ArrayList<Item> serialTvList ;


    public SerialTvFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_movie, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovie = getView().findViewById(R.id.rv_movie);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        prepare();
        addItem();
        rvMovie.setAdapter(serialTvAdapter);
    }

    private void  prepare(){

        dataJudul = getResources().getStringArray(R.array.data_judul_film);
        dataDescription = getResources().getStringArray(R.array.data_deskripsi_film);
        dataTanggalRilis = getResources().getStringArray(R.array.data_tanggalRilis_film);
        dataPoster = getResources().obtainTypedArray(R.array.data_photo_film);

    }

    public  void addItem(){
        serialTvList = new ArrayList<>();
        for (int i = 0;i <dataJudul.length; i++){
            Item movie = new Item();
            movie.setJudul(dataJudul[i]);
            Log.i("judul", dataJudul[i]);
            movie.setDeskripsi(dataDescription[i]);
            movie.setTanggalRilis(dataTanggalRilis[i]);
            movie.setPoster(dataPoster.getResourceId(i, -1));
            serialTvList.add(movie);
        }
        for(int i = 0 ; i<serialTvList.size();i++){
            Log.i("serialTvList judul", serialTvList.get(i).getJudul());
            Log.i("serialTvList deskripsi", serialTvList.get(i).getDeskripsi());
            Log.i("serialTvList tanggal", serialTvList.get(i).getTanggalRilis());
        }
        serialTvAdapter = new MyAdapter(serialTvList, getActivity());
        Log.i("count", String.valueOf(serialTvAdapter.getItemCount()));
    }
}
