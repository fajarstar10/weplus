package id.weplus.belipolis.mobil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.model.CarType;
import id.weplus.model.Plat;
import id.weplus.model.Years;
import id.weplus.model.request.CarProductListRequest;
import id.weplus.model.response.CarBrandResponse;
import id.weplus.model.response.CarFilterResponse;
import id.weplus.model.response.CarTypeAndPriceResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormDataMobilActivityOld extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.datamobil_merek_mobil) Spinner spinnerBrand;
    @BindView(R.id.datamobil_plat_mobil) Spinner spinnerPlate;
    @BindView(R.id.datamobil_thn_prod) Spinner spinnerYear;
    @BindView(R.id.datamobil_seri_mobil) Spinner spinnerSeri;
    @BindView(R.id.datamobil_kondisi_mobil) Spinner spinnerKondisi;
    @BindView(R.id.datamobil_warna_mobil) EditText etWarna;
    @BindView(R.id.spinnerPassengerPremi) EditText etNoRangka;
    @BindView(R.id.spinnerCarPrice) EditText etNoMesin;
    @BindView(R.id.spinnerThirdParty) EditText etAksesoris;
    @BindView(R.id.datamobil_aksesoris) EditText etNoStnk;

    HashMap<Integer,String> mapPlate = new HashMap<Integer, String>();
    HashMap<Integer,CarType> mapType = new HashMap<Integer, CarType>();

    String TAG = "formMobil";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data_mobil);
        ButterKnife.bind(this);
        title.setText("Mobil");
        description.setText(getResources().getString(R.string.lengkapidatamobilyangdiasuransikan));
        fetchCarBrand();
        fetchCarFilter();
    }

    private void setupCarConditionSpinner(){
        ArrayList<String> conditionList = new ArrayList<>();
        conditionList.add("baru");
        conditionList.add("bekas");
        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, conditionList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKondisi.setAdapter(spinnerArrayAdapter);
        spinnerKondisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupCarBrandSpinner(ArrayList<String> spinnerArray){
        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBrand.setAdapter(spinnerArrayAdapter);
        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinnerBrand.getSelectedItem()!=null && spinnerYear.getSelectedItem()!=null) {
                    fetchCarTypeAndPrice(
                            Integer.parseInt(spinnerYear.getSelectedItem().toString())
                            , spinnerBrand.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupCarPlateSpinner(ArrayList<Plat> plates){
        ArrayList<String> plateList = new ArrayList<>();
        Log.d(TAG,"size : "+plates.size());
        for(int i=0;i<plates.size();i++){
            if(plates.get(i).getPlat()!=null) {
                plateList.add(plates.get(i).getPlat());
                mapPlate.put(plates.get(i).getId(), plates.get(i).getPlat());
            }
        }
        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, plateList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlate.setAdapter(spinnerArrayAdapter);
    }

    private void setupCarProductionYear(ArrayList<Years> years){
        ArrayList<String> yearList = new ArrayList<>();
        for(int i=0;i<years.size();i++){
            if(years.get(i).getYear()!=null) {
                yearList.add(years.get(i).getYear());
            }
        }
        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(spinnerArrayAdapter);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinnerBrand.getSelectedItem()!=null && spinnerYear.getSelectedItem()!=null) {
                    fetchCarTypeAndPrice(
                            Integer.parseInt(spinnerYear.getSelectedItem().toString())
                            , spinnerBrand.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupCarTypeSpinner(ArrayList<CarType> car){
        ArrayList<String> plateList = new ArrayList<>();
        if(car.size()>0) {

            for (int i = 0; i < car.size(); i++) {
                if (car.get(i).getName() != null) {
                    plateList.add(car.get(i).getName());
                    mapType.put(car.get(i).getId(), car.get(i));
                }
            }
        }else{
            plateList.add("Seri mobil tidak ditemukan");
        }
        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, plateList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSeri.setAdapter(spinnerArrayAdapter);
    }

    private void fetchCarTypeAndPrice(int year,String brand){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getCarTypeAndPrice(brand,year);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    CarTypeAndPriceResponse resp = gson.fromJson(response.body(), CarTypeAndPriceResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            setupCarTypeSpinner(resp.getData().getType());
                        } else {
                            showError(resp.getMessage());
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG,"asu: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError("Time Out");
                    Log.i(TAG,"ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(getString(R.string.network_error));
        }
    }

    private void fetchCarFilter(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getCarFilter();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    CarFilterResponse resp = gson.fromJson(response.body(), CarFilterResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            setupCarProductionYear(resp.getData().getYear());
                            setupCarPlateSpinner(resp.getData().getPlat());
                            setupCarConditionSpinner();
                        } else {
                            showError(resp.getMessage());
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG,"asu: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError("Time out");
                    Log.i(TAG,"ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(getString(R.string.network_error));
        }
    }
    private void fetchCarBrand(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getCarBrand();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    CarBrandResponse resp = gson.fromJson(response.body(), CarBrandResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            ArrayList<String> brands = new ArrayList<>();
                            for(int i=0;i<resp.getData().getBrand().size();i++){
                                brands.add(resp.getData().getBrand().get(i).getBrand());
                            }
                            setupCarBrandSpinner(brands);
                        } else {
                            showError(FormDataMobilActivityOld.this,resp.getMessage());
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG,"asu: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError("Time Out");
                    Log.i(TAG,"ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(getString(R.string.network_error));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.viewback_buttonback)
    public void backLengkapiDataMobil(){finish();}

    @OnClick(R.id.datamobil_btn_simpan)
    public void simpanLengkapiDataMobil(){
        if(validate()) {
            Intent intent = new Intent(this, CarProductListActivity.class);
            intent.putExtra("requestBody", new CarProductListRequest(
                    0, "", 5, 0, getCarId(spinnerSeri.getSelectedItem().toString()), getCarPrice(spinnerSeri.getSelectedItem().toString()), 2, 0, 21, 22, 24, "", 8, getPlatId(spinnerPlate.getSelectedItem().toString()), "tahunan"
            ));
            startActivity(intent);
        }else{
            showError(this,"Semua field wajib di-isi");
        }
    }

    private boolean validate(){
        return !etWarna.getText().toString().isEmpty() &&
                !etAksesoris.getText().toString().isEmpty() &&
                !etNoMesin.getText().toString().isEmpty() &&
                !etNoStnk.getText().toString().isEmpty() &&
                !etNoRangka.getText().toString().isEmpty();
    }

    private int getPlatId(String plat){
        int platId=0;
        for(Map.Entry<Integer, String> entry : mapPlate.entrySet()){
            if(entry.getValue().equals(plat)){
                platId=entry.getKey();
                break;
            }
        }
        return platId;
    }

    private int getCarId(String carType){
        int carId=0;
        for(Map.Entry<Integer, CarType> entry : mapType.entrySet()){
            if(entry.getValue().getName().equals(carType)){
                carId=entry.getKey();
                break;
            }
        }
        return carId;
    }

    private int getCarPrice(String carType){
        Long carPrice=0L;
        for(Map.Entry<Integer, CarType> entry : mapType.entrySet()){
            if(entry.getValue().getName().equals(carType)){
                carPrice=entry.getValue().getPrice();
                break;
            }
        }
        return (carPrice.intValue());
    }

}
