package kg.geektech.wheatherapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import kg.geektech.wheatherapp.common.Resource;
import kg.geektech.wheatherapp.data.models.MainResponse;
import kg.geektech.wheatherapp.data.remote.WeatherApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepositories {

    private WeatherApi api;
    private String city;

    public MainRepositories(WeatherApi api) {
        this.api = api;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public MutableLiveData<Resource<MainResponse>> getWeather(String city) {

        MutableLiveData<Resource<MainResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getApi(city, "d9e978de62ed707695b5c7cf778db061", "metric").enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(Resource.success(response.body()));
                    Log.e("TAG", "onResponse: " + response.body().toString());
                } else {
                    liveData.setValue(Resource.error(response.message(), null));

                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                liveData.setValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
        return liveData;
    }
}

