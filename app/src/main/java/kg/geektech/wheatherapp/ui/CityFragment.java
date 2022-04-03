package kg.geektech.wheatherapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.geektech.wheatherapp.R;
import kg.geektech.wheatherapp.base.BaseFragment;
import kg.geektech.wheatherapp.databinding.FragmentCityBinding;

public class CityFragment extends BaseFragment<FragmentCityBinding> {

    @Override
    protected FragmentCityBinding bind() {
        return FragmentCityBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void setupListeners() {
        binding.btnCity.setOnClickListener(view -> {
            String city = binding.etCity.getText().toString();
            controller.navigate((NavDirections) CityFragmentDirections.actionCityFragmentToWeatherFragment().setCity(city));
        })  ;

    }

    @Override
    protected void setupObservers() {

    }

    @Override
    protected void callRequests() {

    }
}