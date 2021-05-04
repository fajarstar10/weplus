package id.weplus.belipolis.motor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.WelcomeActivity;
import id.weplus.helper.FullScreenFilterActivity;
import id.weplus.helper.OnOptionsSelect;
import id.weplus.helper.RoundedBottomSheet;
import id.weplus.model.BaseFilter;
import id.weplus.model.CarType;
import id.weplus.model.Plat;
import id.weplus.model.Years;
import id.weplus.model.request.MotorProductListRequest;
import id.weplus.model.response.CarBrandResponse;
import id.weplus.model.response.CarTypeAndPriceResponse;
import id.weplus.model.response.MotorFilterResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsuransiMotorKonvensionalActivity extends BaseActivity implements OnOptionsSelect {
    @BindView(R.id.viewback_title)
    TextView title;
    @BindView(R.id.viewback_description)
    TextView description;
    @BindView(R.id.datamotor_konvensional_tipeasuransi_name)
    TextView spinnerInsuranceType;
    @BindView(R.id.formmotor_mikro_platmotor)
    TextView spinnerPlate;
    @BindView(R.id.formmotor_mikro_merek_motor)
    TextView spinnerBrand;
    @BindView(R.id.formmotor_mikro_tahun_produksi)
    TextView spinnerYear;
    @BindView(R.id.formmotor_mikro_seri_motor)
    TextView spinnerSeri;
    @BindView(R.id.datamobil_kondisi_mobil)
    Spinner spinnerCondition;
    @BindView(R.id.formmotor_mikro_tipe_perlindungan)
    TextView spinnerType;
    @BindView(R.id.formmotor_mikro_driver)
    TextView tvNominalDriver;
    @BindView(R.id.formmotor_mikro_passenger)
    TextView tvNominalPassenger;

    //Error View
    @BindView(R.id.errorTextPlate)
    TextView errorTextCarPlate;
    @BindView(R.id.errorTextInsuranceType)
    TextView errorTextInsuranceType;
    @BindView(R.id.errorTextYear)
    TextView errorTextYear;
    @BindView(R.id.errorTextSeries)
    TextView errorTextSeries;
    @BindView(R.id.errorTextMerek)
    TextView errorTextBrand;
    private String insuranceType = "";
    private String TAG = "formMotor";
    private int tipeAsuransi = 0;
    private int partnerId = 0;
    private int partnerWeplusId = 0;
    private String nik="";

    private String yearBottomSheetTitle = "Pilih Tahun Produksi";
    private String insuranceTypeBottomSheetTitle = "Pilih Tipe Perlindungan";
    private String driverProtectionTypeBottomSheetTitle = "Pilih Nominal Perlindungan Pengemudi";
    private String passengerProtectionTypeBottomSheetTitle = "Pilih Nominal Perlingungan Penumpang";

    private ArrayList<BaseFilter> additonalProtection = new ArrayList<>();
    private ArrayList<BaseFilter> driverIncidents = new ArrayList<>();
    private ArrayList<BaseFilter> passengerIncidents = new ArrayList<>();


    private String year = "";
    private String brand = "";
    private String plate = "";
    private String series = "";
    private int driverIncidentsId = 0;
    private int passengerIncidentsId = 0;

    private int reqPlate = 10;
    private int resPlate = 20;
    private int reqBrand = 12;
    private int resBrand = 12;
    private int resSeries = 13;
    private int reqSeries = 23;


    HashMap<Integer, String> mapPlate = new HashMap<Integer, String>();
    HashMap<Integer, CarType> mapType = new HashMap<Integer, CarType>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asuransi_motor_konvensional);
        ButterKnife.bind(this);
        title.setText("Motor");
        description.setText(getResources().getString(R.string.urutkansesuaidatamotor));
        getData();
        setupMotorConditionSpinner();
        fetchMotorFilter();
        fetchMotorBrand();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == reqPlate) {
            if (resultCode == resPlate) {
                plate = data.getStringExtra("result");
                spinnerPlate.setText(plate);
            }
        }
        if (requestCode == reqBrand) {
            if (resultCode == resBrand) {
                brand = data.getStringExtra("result");
                if (!brand.equals("")) {
                    spinnerSeri.setText("Pilih Seri Motor");
                    series = "";
                    //spinnerSeri.setEnabled(true);
                    spinnerBrand.setText(brand);
                    fetchMotorTypeAndPrice(Integer.parseInt(year), brand);
                } else {
                    spinnerSeri.setEnabled(false);
                }
            }
        }
        if (requestCode == reqSeries) {
            if (resultCode == resSeries) {
                series = data.getStringExtra("result");
                spinnerSeri.setText(series);
            }
        }
    }

    private void getData() {
        insuranceType = getIntent().getStringExtra("type");
        partnerId = getIntent().getIntExtra("partner_id", 0);
        partnerWeplusId = getIntent().getIntExtra("partner_weplus_id", 0);
        nik = getIntent().getStringExtra("nik");
        spinnerInsuranceType.setText(insuranceType);
    }

    private void fetchMotorFilter() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getMotorFilter(1);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    MotorFilterResponse resp = gson.fromJson(response.body(), MotorFilterResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)) {
                            setupMotorProductionYear(resp.getData().getYear());
                            setupMotorPlateSpinner(resp.getData().getPlat());
                            setupMotorInsuranceType(resp.getData().getTypeInsurance(), insuranceTypeBottomSheetTitle);

                            driverIncidents.addAll(resp.getData().getDriverIncidents());
                            passengerIncidents.addAll(resp.getData().getPassagerIncidents());
                            setupDriverProtectionNominal(driverProtectionTypeBottomSheetTitle, driverIncidents);
                            setupPassengerProtectionNominal(passengerProtectionTypeBottomSheetTitle, passengerIncidents);
                            additonalProtection.addAll(resp.getData().getAddProtection());
                        } else if (resp.getCode().equals(ErrorCode.ERROR_03)) {
                            Intent intent = new Intent(AsuransiMotorKonvensionalActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            showError(AsuransiMotorKonvensionalActivity.this, resp.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError(AsuransiMotorKonvensionalActivity.this, "Time out");
                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(AsuransiMotorKonvensionalActivity.this, getString(R.string.network_error));
        }
    }

    private void fetchMotorBrand() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getMotorBrand();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    CarBrandResponse resp = gson.fromJson(response.body(), CarBrandResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)) {
                            ArrayList<String> brands = new ArrayList<>();
                            for (int i = 0; i < resp.getData().getBrand().size(); i++) {
                                brands.add(resp.getData().getBrand().get(i).getBrand());
                            }
                            setupMotorBrandSpinner(brands);
                        } else if (resp.getCode().equals(ErrorCode.ERROR_03)) {
                            Intent intent = new Intent(AsuransiMotorKonvensionalActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            showError(AsuransiMotorKonvensionalActivity.this, resp.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError(AsuransiMotorKonvensionalActivity.this, "Time Out");
                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(AsuransiMotorKonvensionalActivity.this, getString(R.string.network_error));
        }
    }

    private void openFullScreenFilter(ArrayList<String> optionsArray, int requestCode, int resultCode) {
        Intent intentBrand = new Intent(AsuransiMotorKonvensionalActivity.this, FullScreenFilterActivity.class);
        intentBrand.putStringArrayListExtra("searchList", optionsArray);
        intentBrand.putExtra("resultCode", resultCode);
        startActivityForResult(intentBrand, requestCode);
    }

    private void openRoundedButtonSheet(String title, ArrayList<BaseFilter> baseFilters) {
        RoundedBottomSheet roundedBottomSheet = new RoundedBottomSheet(AsuransiMotorKonvensionalActivity.this);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putParcelableArrayList("baseFilter", baseFilters);
        roundedBottomSheet.setArguments(bundle);
        roundedBottomSheet.show(AsuransiMotorKonvensionalActivity.this.getSupportFragmentManager(), "test");
    }

    private void fetchMotorTypeAndPrice(int year, String brand) {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getMotorTypeAndPrice(brand, year);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    CarTypeAndPriceResponse resp = gson.fromJson(response.body(), CarTypeAndPriceResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)) {
                            setupMotorTypeSpinner(resp.getData().getType());
                        } else if (resp.getCode().equals(ErrorCode.ERROR_03)) {
                            Intent intent = new Intent(AsuransiMotorKonvensionalActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            showError(AsuransiMotorKonvensionalActivity.this, resp.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError(AsuransiMotorKonvensionalActivity.this, "Time Out");
                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(AsuransiMotorKonvensionalActivity.this, getString(R.string.network_error));
        }
    }


    private void setupMotorInsuranceType(ArrayList<BaseFilter> typeInsurance, String title) {
        spinnerType.setOnClickListener(view -> openRoundedButtonSheet(title, typeInsurance));
    }

    private void setupYearInsuranceType(ArrayList<BaseFilter> yearList, String title) {
        spinnerYear.setOnClickListener(view -> openRoundedButtonSheet(title, yearList));
    }

    private void setupMotorProductionYear(ArrayList<Years> years) {
        ArrayList<BaseFilter> yearFilter = new ArrayList<>();
        for (int i = 0; i < years.size(); i++) {
            if (years.get(i).getYear() != null) {
                yearFilter.add(new BaseFilter(i, years.get(i).getYear(), years.get(i).getYear()));
            }
        }
        setupYearInsuranceType(yearFilter, yearBottomSheetTitle);
    }


    private void setupMotorBrandSpinner(ArrayList<String> spinnerArray) {
        spinnerBrand.setOnClickListener(view -> openFullScreenFilter(spinnerArray, reqBrand, resBrand));
    }


    private void setupMotorConditionSpinner() {
        ArrayList<String> conditionList = new ArrayList<>();
        conditionList.add("baru");
        conditionList.add("bekas");
        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, conditionList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondition.setAdapter(spinnerArrayAdapter);
        spinnerCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void setupMotorTypeSpinner(ArrayList<CarType> car) {
        mapType.clear();
        ArrayList<String> plateList = new ArrayList<>();
        if (car.size() > 0) {
            spinnerSeri.setEnabled(true);
            for (int i = 0; i < car.size(); i++) {
                if (car.get(i).getName() != null) {
                    plateList.add(car.get(i).getName());
                    mapType.put(car.get(i).getId(), car.get(i));
                }
            }
            spinnerSeri.setOnClickListener(view -> openFullScreenFilter(plateList, reqSeries, resSeries));

        } else {
            plateList.add("Seri motor tidak ditemukan");
            spinnerSeri.setEnabled(false);
            spinnerSeri.setText("Seri motor tidak ditemukan");
        }

    }


    private void setupMotorPlateSpinner(ArrayList<Plat> plates) {
        ArrayList<String> plateList = new ArrayList<>();
        for (int i = 0; i < plates.size(); i++) {
            if (plates.get(i).getPlat() != null) {
                plateList.add(plates.get(i).getPlat());
                mapPlate.put(plates.get(i).getId(), plates.get(i).getPlat());
            }
        }
        spinnerPlate.setOnClickListener(view -> {
            openFullScreenFilter(plateList, reqPlate, resPlate);
        });
    }

    private void setupDriverProtectionNominal(String title, ArrayList<BaseFilter> baseFilters) {
        tvNominalDriver.setOnClickListener(view -> {
            openRoundedButtonSheet(title, baseFilters);
        });
    }

    private void setupPassengerProtectionNominal(String title, ArrayList<BaseFilter> baseFilters) {
        tvNominalPassenger.setOnClickListener(view -> {
            openRoundedButtonSheet(title, baseFilters);
        });
    }


    @OnClick(R.id.viewback_buttonback)
    public void actionBackPembayaran() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.tipeasuransimotor_btn_lanjutkan)
    public void lanjutkan() {
        int isCommercial = 0;
        int isMicro = 0;
        if (insuranceType.toLowerCase().equals("konvensional")) {
            isCommercial = 0;
        } else {
            isMicro = 1;
        }

        if (validate()) {
            MotorProductListRequest request =
                    new MotorProductListRequest(partnerWeplusId,
                            nik,
                            1,
                            partnerId,
                            getCarId(series),
                            series,
                            driverIncidentsId,
                            passengerIncidentsId,
                            "4,5,6,1",
                            tipeAsuransi,
                            getPlatId(plate),
                            isCommercial,
                            isMicro,
                            brand,
                            plate,
                            year);

            Intent intent = new Intent(this, TambahanPerlindunganAsuransiMotorActivity.class);
            intent.putExtra("requestBody", request);
            intent.putParcelableArrayListExtra("addProtection", additonalProtection);
//            intent.putParcelableArrayListExtra("driver",driverIncidents);
            //intent.putParcelableArrayListExtra("passenger",passengerIncidents);
            startActivity(intent);
        } else {
            //showError(AsuransiMotorKonvensionalActivity.this,"Semua field wajib di-isi");
        }
    }

    private boolean validate() {
        boolean insuranceTypeValidate = tipeAsuransi != 0;
        boolean yearValidate = !year.toLowerCase().equals("");
        boolean merekValidate = !brand.toLowerCase().equals("");
        boolean seriesValidate = !series.toLowerCase().equals("");
        boolean plateValidate = !plate.toLowerCase().equals("");

        errorTextInsuranceType.setVisibility(insuranceTypeValidate ? View.GONE : View.VISIBLE);
        errorTextYear.setVisibility(yearValidate ? View.GONE : View.VISIBLE);
        errorTextBrand.setVisibility(merekValidate ? View.GONE : View.VISIBLE);
        errorTextSeries.setVisibility(seriesValidate ? View.GONE : View.VISIBLE);
        errorTextCarPlate.setVisibility(plateValidate ? View.GONE : View.VISIBLE);

        return (insuranceTypeValidate
                && yearValidate
                && merekValidate
                && seriesValidate
                && plateValidate);
    }

    private int getPlatId(String plat) {
        int platId = 0;
        for (Map.Entry<Integer, String> entry : mapPlate.entrySet()) {
            if (entry.getValue().equals(plat)) {
                platId = entry.getKey();
                break;
            }
        }
        return platId;
    }

    private int getCarId(String carType) {
        int carId = 0;
        for (Map.Entry<Integer, CarType> entry : mapType.entrySet()) {
            if (entry.getValue().getName().equals(carType)) {
                carId = entry.getKey();
                break;
            }
        }
        Log.e("CARID", String.valueOf(carId));
        return carId;
    }

    private int getCarPrice(String carType) {
        Long carPrice = 0L;
        for (Map.Entry<Integer, CarType> entry : mapType.entrySet()) {
            if (entry.getValue().getName().equals(carType)) {
                carPrice = entry.getValue().getPrice();
                break;
            }
        }
        return (carPrice.intValue());
    }


    @Override
    public void onOptionSelect(@NotNull BaseFilter baseFilter, @NotNull String title) {
        if (title.equals(insuranceTypeBottomSheetTitle)) {
            tipeAsuransi = baseFilter.getId();
            spinnerType.setText(baseFilter.getFilterText());
        } else if (title.equals(yearBottomSheetTitle)) {
            year = baseFilter.getName();
            spinnerYear.setText(baseFilter.getFilterText());
            if (!year.equals("")) {
                spinnerBrand.setEnabled(true);
            } else {
                spinnerBrand.setEnabled(false);
            }
        } else if (title.equals(driverProtectionTypeBottomSheetTitle)) {
            tvNominalDriver.setText(baseFilter.getFilterText());
            driverIncidentsId = baseFilter.getId();
        } else if (title.equals(passengerProtectionTypeBottomSheetTitle)) {
            tvNominalPassenger.setText(baseFilter.getFilterText());
            passengerIncidentsId = baseFilter.getId();
        }

    }
}
