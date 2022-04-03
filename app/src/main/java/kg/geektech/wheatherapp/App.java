package kg.geektech.wheatherapp;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;
import kg.geektech.wheatherapp.data.remote.RetrofitClient;
import kg.geektech.wheatherapp.data.remote.WeatherApi;
import kg.geektech.wheatherapp.repositories.MainRepositories;

@HiltAndroidApp
public class App extends Application {

}
