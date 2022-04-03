package kg.geektech.wheatherapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kg.geektech.wheatherapp.App;
import kg.geektech.wheatherapp.common.Resource;
import kg.geektech.wheatherapp.data.models.MainResponse;
import kg.geektech.wheatherapp.repositories.MainRepositories;
@HiltViewModel
public class WeatherViewModel extends ViewModel {
    public LiveData<Resource<MainResponse>> liveData;
    public MainRepositories repositories;
@Inject
    public WeatherViewModel(MainRepositories repositories) {
        this.repositories = repositories;
    }

    public void getWeather(String city) {
        liveData = repositories.getWeather(city);
    }




}
