package com.example.menutest.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menutest.MainActivity;
import com.example.menutest.R;
import com.example.menutest.adapters.NewsAdapter;
import com.example.menutest.api.ApiNews;
import com.example.menutest.api.Apiservice;
import com.example.menutest.pojo.Attachment;
import com.example.menutest.pojo.Item;
import com.example.menutest.pojo.Response;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment implements PresenterHomeFragment {


    private RecyclerView recyclerViewNews;
    private NewsAdapter newsAdapter;
    private HomeViewModel homeViewModel;
    private List<Item> itemsStart = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel = new HomeViewModel();
        homeViewModel.loadData();

        View root = inflater.inflate(R.layout.fragment_home, container, false);

//        homeViewModel = new HomeViewModel();
//        homeViewModel.loadData();
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Add news
//        newsAdapter = new NewsAdapter();
//        newsAdapter.setItems(itemsStart);
        recyclerViewNews = view.findViewById(R.id.recyclerViewNews);
//        recyclerViewNews.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerViewNews.setAdapter(newsAdapter);

        newsAdapter = new NewsAdapter();
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(getContext()));
        homeViewModel.itemsLiveData.observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if(itemsStart != null) {
                    itemsStart.addAll(items);
                    newsAdapter.setItems(itemsStart);
                    recyclerViewNews.setAdapter(newsAdapter);
                } else {
                    newsAdapter.setItems(items);
                    recyclerViewNews.setAdapter(newsAdapter);
                }
            }
        });


    }

    @Override
    public void onDestroy() {
        homeViewModel.closeLoadData();
        super.onDestroy();
    }

    @Override
    public void showLoadData(List<String> items) {
        Log.i("ShowLoadData", "called");
    }
}