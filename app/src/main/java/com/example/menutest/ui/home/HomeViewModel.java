package com.example.menutest.ui.home;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.menutest.api.ApiNews;
import com.example.menutest.api.Apiservice;
import com.example.menutest.pojo.Example;
import com.example.menutest.pojo.Item;
import com.example.menutest.pojo.Response;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel{

    MutableLiveData<List<Item>> itemsLiveData;

    private Disposable disposable;
//    private PresenterHomeFragment presenterHomeFragment;
    private List<String> items;
    public static final String TOKEN = "5dab56bc5dab56bc5dab56bc525ddd054955dab5dab56bc3d8a54c1e4b840b5f7a23512";

    public HomeViewModel() {
        items = new ArrayList<>();
        items.add("Привет");
        itemsLiveData = new MutableLiveData<>();
    }


    public void loadData() {
        ApiNews apiNews = ApiNews.getInstance();
        Apiservice apiservice = apiNews.getApiNews();
        disposable = apiservice.getResponse(-31547740, 10, TOKEN, 5.21)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Example>() {

                    @Override
                    public void accept(Example example) throws Exception {
                        if (example != null) {
                        String str = String.valueOf(example.getResponse().getCount());
                        Log.i("Well done", str + example.getResponse().getItems().toString());
                        itemsLiveData.setValue(example.getResponse().getItems());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("Error", throwable.getMessage());
                    }
                });
    }


    public void closeLoadData() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

}