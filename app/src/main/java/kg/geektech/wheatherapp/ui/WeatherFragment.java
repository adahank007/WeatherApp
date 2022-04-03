package kg.geektech.wheatherapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import kg.geektech.wheatherapp.R;
import kg.geektech.wheatherapp.base.BaseFragment;
import kg.geektech.wheatherapp.data.models.MainResponse;
import kg.geektech.wheatherapp.databinding.FragmentWeatherBinding;

@AndroidEntryPoint
public class WeatherFragment extends BaseFragment<FragmentWeatherBinding> {
    private WeatherViewModel viewModel;
    private WeatherFragmentArgs args;
    private NavController controller;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            args = WeatherFragmentArgs.fromBundle(getArguments());

        }
    }

    @Override
    protected FragmentWeatherBinding bind() {
        return FragmentWeatherBinding.inflate(getLayoutInflater());
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void setupViews() {
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);


    }

    @Override
    protected void setupListeners() {
        binding.countryCity.setOnClickListener(view -> {
            controller.navigate(R.id.cityFragment);
        });

    }

    @Override
    protected void setupObservers() {
        viewModel.liveData.observe(getViewLifecycleOwner(), mainResponseResource -> {
            switch (mainResponseResource.status) {
                case SUCCESS: {
                    loadData(mainResponseResource.data);
                    binding.progressBar.setVisibility(View.GONE);
                    break;
                }
                case ERROR: {
                    binding.progressBar.setVisibility(View.GONE);
                    break;
                }
                case LOADING: {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                }
            }
        });


    }

    @Override
    protected void callRequests() {
        viewModel.getWeather(args.getCity());
    }

    @Override
    public void onResume() {
        super.onResume();
        Calendar uh = Calendar.getInstance();
        int timeOfDay = uh.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            binding.ivDayNight.setImageResource(R.drawable.city_night);
        } else if (timeOfDay >= 12 && timeOfDay < 24) {
            binding.ivDayNight.setImageResource(R.drawable.city_day);
        }
    }

    private void loadData(MainResponse response) {
        String urlImg = "https://openweathermap.org/img/wn/" + response.getWeather().get(0).getIcon() + ".png";
        String humidity = String.valueOf(response.getMain().getHumidity()) + "%";
        String barometer = String.valueOf(response.getMain().getPressure()) + "mBar";
        String mainWeather = response.getWeather().get(0).getMain();
        String tempNow = String.valueOf((int) Math.round(response.getMain().getTemp()));

        Glide.with(requireActivity()).load(urlImg).into(binding.weatherImg);

        binding.windPower.setText(String.valueOf((int) Math.round(response.getWind().getSpeed())) + " km/h");
        binding.tvHumidityNumber.setText(humidity);
        binding.tvTemp.setText(tempNow);
        binding.pressure.setText(barometer);
        binding.tvWeather.setText(mainWeather);
        binding.countryCity.setText(response.getName());
        binding.tvSunriseTime.setText(getDate(response.getSys().getSunrise(), "hh:mm") + " AM");
        binding.sunsetTime.setText(getDate(response.getSys().getSunset(), "hh:mm") + " PM");
        binding.tvDayTime.setText(getDate(response.getDt(), "hh:mm"));
        binding.tvTodayDate.setText(getDate(response.getDt(), "E, dd MM yyyy"));


        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("E, dd MM yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        binding.tvDayTime.setText(dateText);

    }


    public static String getDate(Integer milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}