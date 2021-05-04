package id.weplus.belipolis.mobil;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import id.weplus.belipolis.FormDataMobil;
import id.weplus.helper.FullScreenFilterActivity;
import id.weplus.helper.OnOptionsSelect;
import id.weplus.helper.RoundedBottomSheet;
import id.weplus.model.BaseFilter;
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

import static id.weplus.belipolis.mobil.AddCarAccesoriesActivity.accResultCode;
import static id.weplus.utility.TextHelper.currencyFormatter;

public class FormDataMobilActivity extends BaseActivity implements OnOptionsSelect {
    @BindView(R.id.viewback_title)
    TextView title;
    @BindView(R.id.viewback_description)
    TextView description;
    @BindView(R.id.datamobil_merek_mobil)
    TextView spinnerBrand;
    @BindView(R.id.datamobil_plat_mobil)
    TextView spinnerPlate;
    @BindView(R.id.spinnerYear)
    TextView spinnerYear;
    @BindView(R.id.datamobil_seri_mobil)
    TextView spinnerSeri;
    @BindView(R.id.datamobil_kondisi_mobil)
    Spinner spinnerKondisi;
    @BindView(R.id.spinnerNominalDriver)
    TextView spinnerDriver;
    @BindView(R.id.spinnerPassengerCount)
    TextView spinnerPassengerCount;
    @BindView(R.id.spinnerPassengerPremi)
    TextView spinnerPassenger;
    @BindView(R.id.spinnerDuration)
    TextView spinnerDuration;
    @BindView(R.id.formmotor_mikro_tipe_perlindungan)
    TextView spinnerInsuranceType;
    @BindView(R.id.spinnerCarPrice)
    EditText spinnerCarPrice;
    @BindView(R.id.spinnerThirdParty)
    TextView spinnerThirdParty;
    @BindView(R.id.checkAccesories)
    CheckBox checkBoxAksesoris;
    @BindView(R.id.aksesorisWrapper)
    RelativeLayout aksesorisWrapper;
    @BindView(R.id.layoutJumlahPenumpang)
    RelativeLayout passengerCountWrapper;
    @BindView(R.id.accCheckWrapper)
    RelativeLayout accWrapper;
    @BindView(R.id.tvUbah)
    TextView tvAcc;
    @BindView(R.id.tvAccotal)
    TextView tvAccTotal;

    //Error View
    @BindView(R.id.errorTextCarPlate)
    TextView errorTextCarPlate;
    @BindView(R.id.errorTextDuration)
    TextView errorTextCarDuration;
    @BindView(R.id.errorTextInsuranceType)
    TextView errorTextInsuranceType;
    @BindView(R.id.errorTextYear)
    TextView errorTextYear;
    @BindView(R.id.errorTextSeries)
    TextView errorTextSeries;
    @BindView(R.id.errorTextMerek)
    TextView errorTextBrand;
    @BindView(R.id.errorTextCarPrice)
    TextView errorTextPrice;

    HashMap<Integer, String> mapPlate = new HashMap<Integer, String>();
    HashMap<Integer, CarType> mapType = new HashMap<Integer, CarType>();
    HashMap<Integer, String> mapDriverIncident = new HashMap<>();
    HashMap<Integer, String> mapPassengerIncident = new HashMap<>();
    HashMap<Integer, String> mapTpl = new HashMap<>();

    private ArrayList<BaseFilter> addProtections = new ArrayList<>();
    private ArrayList<String> selectedAccessories = new ArrayList<>();
    private String selectYearTitle = "Pilih Tahun Produksi";
    private String selectDriverProtection = "Pilih Nominal Perlindungan Pengemudi";
    private String selectPassengerProtection = "Pilih Nominal Perlindungan Penumpang";
    private String selectThirdParty = "Pilih Nominal";

    private String insuranceDuration = "";
    private String year = "";
    private String brand = "";
    private String series = "";
    private String plateName = "";
    private int driverProtectionId = 0;
    private int passengerProtectionId = 0;
    private int tplId = 0;

    private int insuranceType = 0;
    private int jumlahPenumpang = 0;
    private int plateId = 0;

    private int reqPlate = 10;
    private int resPlate = 20;
    private int reqBrand = 12;
    private int resBrand = 22;
    private int reqSeries = 13;
    private int resSeries = 23;

    public static int carAccReqCode = 11;
    private int aksesorisPrice = 500000;
    private String nik = "";
    private boolean isAgent = false;
    private int partnerId = 0;
    private int partnerWeplusId = 0;

    String TAG = "formMobil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data_mobil);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        title.setText("Mobil");
        description.setText(getResources().getString(R.string.lengkapidatamobilyangdiasuransikan));
        spinnerBrand.setEnabled(false);

        setupCheckBoxBehaviour();
        setupSpinnerCarPrice();
        setupCarAcc();
        fetchCarBrand();
        fetchCarFilter();
        getData();
        spinnerSeri.setEnabled(false);
    }

    private void getData() {
        nik = getIntent().getStringExtra("nik");
        isAgent = getIntent().getBooleanExtra("is_agent", false);
        partnerId = getIntent().getIntExtra("partner_id",0);
        partnerWeplusId = getIntent().getIntExtra("partner_weplus_id",0);
        Log.d("isAgent", "isagent : " + isAgent);

    }

    private void setupSpinnerDuration(String title, ArrayList<BaseFilter> filter) {
        spinnerDuration.setOnClickListener(view -> {
            RoundedBottomSheet roundedBottomSheet = new RoundedBottomSheet(this);
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putParcelableArrayList("baseFilter", filter);
            roundedBottomSheet.setArguments(bundle);
            roundedBottomSheet.show(FormDataMobilActivity.this.getSupportFragmentManager(), "test");
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == carAccReqCode) {
            if (resultCode == accResultCode) {
                int checkedItemCount = data.getIntExtra("checked", 0);
                selectedAccessories = data.getStringArrayListExtra("checked_labels");
                aksesorisPrice = data.getIntExtra("acc_price", 500000);
                tvAccTotal.setText("Rp." + currencyFormatter("" + aksesorisPrice));
                checkBoxAksesoris.setChecked(checkedItemCount > 0);
                if (checkedItemCount > 0) {
                    aksesorisWrapper.setVisibility(View.VISIBLE);
                } else {
                    aksesorisWrapper.setVisibility(View.GONE);
                }
            }
        } else if (requestCode == reqPlate) {
            if (resultCode == resPlate) {
                plateId = getPlatId(data.getStringExtra("result"));
                plateName = data.getStringExtra("result");
                spinnerPlate.setText(data.getStringExtra("result"));
            }
        } else if (requestCode == reqBrand) {
            if (resultCode == resBrand) {
                spinnerSeri.setEnabled(true);
                spinnerSeri.setText("Pilih Seri Mobil");
                series = "";
                brand = data.getStringExtra("result");
                fetchCarTypeAndPrice(
                        Integer.parseInt(year)
                        , data.getStringExtra("result"));
                spinnerBrand.setText(brand);
                spinnerCarPrice.setHint("Isi nominal Harga Mobil");
            } else {
                spinnerSeri.setEnabled(false);
            }
        } else if (requestCode == reqSeries) {
            if (resultCode == resSeries) {
                series = data.getStringExtra("result");
                spinnerSeri.setText(series);
                spinnerCarPrice.setEnabled(true);
                spinnerCarPrice.setText(currencyFormatter("" + getCarPrice(series)));
                spinnerPlate.setEnabled(true);
            }
        }
    }

    private void setupCarAcc() {
        tvAcc.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddCarAccesoriesActivity.class);
            Log.d("label ed test", "selcted acc " + selectedAccessories.size());
            intent.putExtra("checked_labels", selectedAccessories);
            intent.putExtra("acc_price", aksesorisPrice);

            startActivityForResult(intent, carAccReqCode);
        });
    }


    private void setupSpinnerDriver(ArrayList<BaseFilter> driverIncidents) {
        for (int i = 1; i < driverIncidents.size(); i++) {
            mapDriverIncident.put(driverIncidents.get(i).getId(), driverIncidents.get(i).getFilterText());
        }
        spinnerDriver.setOnClickListener(view -> openRoundedButtonSheet(selectDriverProtection, driverIncidents));

    }

    private void setupSpinnerPassager(ArrayList<BaseFilter> passengerIncidents) {

        for (int i = 1; i < passengerIncidents.size(); i++) {

            mapPassengerIncident.put(passengerIncidents.get(i).getId(), passengerIncidents.get(i).getFilterText());
        }
        spinnerPassenger.setOnClickListener(view -> openRoundedButtonSheet(selectPassengerProtection, passengerIncidents));

    }

    private void openFullScreenFilter(ArrayList<String> optionsArray, int requestCode, int resultCode) {
        Intent intentBrand = new Intent(FormDataMobilActivity.this, FullScreenFilterActivity.class);
        intentBrand.putStringArrayListExtra("searchList", optionsArray);
        intentBrand.putExtra("resultCode", resultCode);
        startActivityForResult(intentBrand, requestCode);
    }

    private void openRoundedButtonSheet(String title, ArrayList<BaseFilter> baseFilters) {
        RoundedBottomSheet roundedBottomSheet = new RoundedBottomSheet(FormDataMobilActivity.this);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putParcelableArrayList("baseFilter", baseFilters);
        roundedBottomSheet.setArguments(bundle);
        roundedBottomSheet.show(FormDataMobilActivity.this.getSupportFragmentManager(), "test");
    }

    private void setupSpinnerPassegerCount(ArrayList<BaseFilter> filter, String title) {
        spinnerPassengerCount.setOnClickListener(view -> {
            RoundedBottomSheet roundedBottomSheet = new RoundedBottomSheet(this);
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putParcelableArrayList("baseFilter", filter);
            roundedBottomSheet.setArguments(bundle);
            roundedBottomSheet.show(FormDataMobilActivity.this.getSupportFragmentManager(), "test");
        });
    }

    private void setupSpinnerCarPrice() {
        spinnerCarPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String price = charSequence.toString();
                price = price.replace(".", "");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setupSpinnerThirdParty(ArrayList<BaseFilter> addTpl) {
        for (int i = 0; i < addTpl.size(); i++) {
            mapTpl.put(addTpl.get(i).getId(), addTpl.get(i).getFilterText());
        }
        spinnerThirdParty.setOnClickListener(view -> openRoundedButtonSheet(selectThirdParty, addTpl));

    }

    private void setupCheckBoxBehaviour() {
        accWrapper.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddCarAccesoriesActivity.class);
            intent.putExtra("checked_labels", selectedAccessories);
            intent.putExtra("acc_price", aksesorisPrice);
            startActivityForResult(intent, carAccReqCode);
        });
        checkBoxAksesoris.setOnCheckedChangeListener((compoundButton, b) -> {
            if (checkBoxAksesoris.isChecked()) {
                aksesorisWrapper.setVisibility(View.VISIBLE);
            } else {
                aksesorisWrapper.setVisibility(View.GONE);
            }
        });
    }

    private void setupCarConditionSpinner() {
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

    private void setupCarBrandSpinner(ArrayList<String> spinnerArray) {
        spinnerBrand.setOnClickListener(view -> {
            Intent intentBrand = new Intent(FormDataMobilActivity.this, FullScreenFilterActivity.class);
            intentBrand.putStringArrayListExtra("searchList", spinnerArray);
            intentBrand.putExtra("resultCode", resBrand);
            startActivityForResult(intentBrand, reqBrand);
        });
    }

    private void setupCarPlateSpinner(ArrayList<Plat> plates) {
        ArrayList<String> plateList = new ArrayList<>();
        for (int i = 0; i < plates.size(); i++) {
            if (plates.get(i).getPlat() != null) {
                plateList.add(plates.get(i).getPlat());
                mapPlate.put(plates.get(i).getId(), plates.get(i).getPlat());
            }
        }

        spinnerPlate.setOnClickListener(view -> {
            Intent intentBrand = new Intent(FormDataMobilActivity.this, FullScreenFilterActivity.class);
            intentBrand.putStringArrayListExtra("searchList", plateList);
            intentBrand.putExtra("resultCode", resPlate);
            startActivityForResult(intentBrand, reqPlate);
        });
    }

    private void setupCarProductionYear(ArrayList<Years> years) {
        ArrayList<String> yearList = new ArrayList<>();
        ArrayList<BaseFilter> baseFilters = new ArrayList<>();


        yearList.add("Pilih Tahun Produksi");
        for (int i = 0; i < years.size(); i++) {
            if (years.get(i).getYear() != null) {
                yearList.add(years.get(i).getYear());
                baseFilters.add(new BaseFilter((i + 1), years.get(i).getYear(), years.get(i).getYear()));
            }
        }
        spinnerYear.setOnClickListener(view -> openRoundedButtonSheet(selectYearTitle, baseFilters));
    }


    private void setupCarTypeSpinner(ArrayList<CarType> car) {
        ArrayList<String> seriesList = new ArrayList<>();
        seriesList.add("Pilih Seri Mobil");
        if (car.size() > 0) {
            for (int i = 0; i < car.size(); i++) {
                if (car.get(i).getName() != null) {
                    seriesList.add(car.get(i).getName());
                    mapType.put(car.get(i).getId(), car.get(i));
                }
            }
            spinnerSeri.setOnClickListener(view -> openFullScreenFilter(seriesList, reqSeries, resSeries));

        } else {
            seriesList.add("Seri mobil tidak ditemukan");
        }
    }

    private void fetchCarTypeAndPrice(int year, String brand) {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getCarTypeAndPrice(brand, year);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    CarTypeAndPriceResponse resp = gson.fromJson(response.body(), CarTypeAndPriceResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)) {
                            if (resp.getData().getType().size() == 0) {
                                spinnerSeri.setText("Seri mobil tidak ditemukan");
                            } else {
                                setupCarTypeSpinner(resp.getData().getType());
                                spinnerCarPrice.setText("");
                            }
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(FormDataMobilActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            showError(FormDataMobilActivity.this, resp.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError(FormDataMobilActivity.this, "Time Out");
                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(FormDataMobilActivity.this, getString(R.string.network_error));
        }
    }

    private void fetchCarFilter() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
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
                        if (resp.getCode().equals(ErrorCode.ERROR_00)) {
                            addProtections = resp.getData().getAddProtection();
                            setupCarProductionYear(resp.getData().getYear());
                            setupCarPlateSpinner(resp.getData().getPlat());
                            setupCarConditionSpinner();
                            setupSpinnerDriver(resp.getData().getDriverIncidents());
                            setupSpinnerPassager(resp.getData().getPassagerIncidents());
                            setupSpinnerThirdParty(resp.getData().getAddTpl());
                            setupSpinnerPassegerCount(resp.getData().getPassenger(), "Pilih Jumlah Penumpang");
                            setupSpinnerDuration("Pilih Durasi Perlindungan", resp.getData().getDuration());
                            setupInsuranceTypeBottomSheet(resp.getData().getTypeInsurance(), "Pilih Tipe Perlindungan");
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(FormDataMobilActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            showError(FormDataMobilActivity.this, resp.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError(FormDataMobilActivity.this, "Time out");
                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(this, getString(R.string.network_error));
        }
    }

    private void fetchCarBrand() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
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
                        if (resp.getCode().equals(ErrorCode.ERROR_00)) {
                            ArrayList<String> brands = new ArrayList<>();
                            for (int i = 0; i < resp.getData().getBrand().size(); i++) {
                                brands.add(resp.getData().getBrand().get(i).getBrand());
                            }
                            setupCarBrandSpinner(brands);
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(FormDataMobilActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            showError(FormDataMobilActivity.this, resp.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError(FormDataMobilActivity.this, "Time Out");
                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(FormDataMobilActivity.this, getString(R.string.network_error));
        }
    }

    private void setupInsuranceTypeBottomSheet(ArrayList<BaseFilter> typeInsurance, String title) {
        spinnerInsuranceType.setOnClickListener(view -> {
            RoundedBottomSheet roundedBottomSheet = new RoundedBottomSheet(FormDataMobilActivity.this);
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putParcelableArrayList("baseFilter", typeInsurance);
            roundedBottomSheet.setArguments(bundle);
            roundedBottomSheet.show(FormDataMobilActivity.this.getSupportFragmentManager(), "test");
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.viewback_buttonback)
    public void backLengkapiDataMobil() {
        finish();
    }

    @OnClick(R.id.datamobil_btn_simpan)
    public void simpanLengkapiDataMobil() {
        if (validate()) {
            if (!checkBoxAksesoris.isChecked()) {
                aksesorisPrice = 0;
            }
            CarProductListRequest request = new CarProductListRequest(
                    partnerWeplusId,
                    nik,
                    5,
                    partnerId,
                    getCarId(series),
                    getCarPrice(series),
                    jumlahPenumpang,
                    aksesorisPrice,
                    driverProtectionId,
                    passengerProtectionId,
                    tplId,
                    "",
                    insuranceType,
                    plateId,
                    insuranceDuration.toLowerCase()
            );
            request.setAccessories(selectedAccessories.toString());
            request.setAccessories_price(aksesorisPrice);
            request.setSeries(series);
            request.setBrand(brand);
            request.setPlateName(plateName);
            request.setProductionYear(year.toLowerCase());
            Intent intent = new Intent(this, TambahanPerlindunganMobilActivity.class);
            intent.putParcelableArrayListExtra("addProtection", addProtections);
            intent.putExtra("requestBody", request);
            intent.putExtra("is_agent", isAgent);
            startActivity(intent);
        } else {
            //showError(this,"Semua field wajib di-isi");
        }
    }

    private boolean validate() {
        boolean insuranceTypeValidate = insuranceType != 0;
        boolean durationValidate = !insuranceDuration.toLowerCase().equals("");
        boolean yearValidate = !year.toLowerCase().equals("");
        boolean merekValidate = !brand.toLowerCase().equals("");
        boolean seriValidate = !series.equals("");
        String price = spinnerCarPrice.getText().toString();
        boolean priceValidate = (!price.toLowerCase().trim().equals("") && !price.toLowerCase().trim().equals("0"));
        boolean plateValidate = plateId != 0;


        errorTextInsuranceType.setVisibility(insuranceTypeValidate ? View.GONE : View.VISIBLE);
        errorTextCarDuration.setVisibility(durationValidate ? View.GONE : View.VISIBLE);
        errorTextYear.setVisibility(yearValidate ? View.GONE : View.VISIBLE);
        errorTextBrand.setVisibility(merekValidate ? View.GONE : View.VISIBLE);
        errorTextSeries.setVisibility(seriValidate ? View.GONE : View.VISIBLE);
        errorTextPrice.setVisibility(priceValidate ? View.GONE : View.VISIBLE);
        errorTextCarPlate.setVisibility(plateValidate ? View.GONE : View.VISIBLE);

        return (insuranceTypeValidate
                && durationValidate
                && yearValidate
                && merekValidate
                && priceValidate
                && plateValidate
                && seriValidate);
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

    private int getDriverIncidentId(String driverIncident) {
        int driverIncidentId = 0;
        for (Map.Entry<Integer, String> entry : mapDriverIncident.entrySet()) {
            if (entry.getValue().equals(driverIncident)) {
                driverIncidentId = entry.getKey();
                break;
            }
        }
        return driverIncidentId;
    }

    private int getPassengerIncidentId(String driverIncident) {
        int passengerIncidentId = 0;
        for (Map.Entry<Integer, String> entry : mapPassengerIncident.entrySet()) {
            if (entry.getValue().equals(driverIncident)) {
                passengerIncidentId = entry.getKey();
                break;
            }
        }
        return passengerIncidentId;
    }

    private int getTplIncidentId(String tplIncident) {
        int tplIncidentId = 0;
        for (Map.Entry<Integer, String> entry : mapTpl.entrySet()) {
            if (entry.getValue().equals(tplIncident)) {
                tplIncidentId = entry.getKey();
                break;
            }
        }
        return tplIncidentId;
    }

    @Override
    public void onOptionSelect(@NotNull BaseFilter baseFilter, @NotNull String title) {
        if (title.toLowerCase().contains("tipe")) {
            insuranceType = baseFilter.getId();
            spinnerInsuranceType.setText(baseFilter.getFilterText());
            spinnerDuration.setEnabled(true);
        } else if (title.toLowerCase().contains("durasi")) {
            spinnerDuration.setText(baseFilter.getFilterText());
            insuranceDuration = baseFilter.getFilterText();
            spinnerYear.setEnabled(true);
        } else if (title.toLowerCase().contains("jumlah penumpang")) {
            spinnerPassengerCount.setText(baseFilter.getFilterText());
            jumlahPenumpang = baseFilter.getId();
        } else if (title.equals(selectYearTitle)) {
            spinnerBrand.setEnabled(true);
            year = baseFilter.getName();
            spinnerYear.setText(year);
            spinnerBrand.setText("Pilih Merek Mobil");
            brand = "";
            spinnerSeri.setText("Pilih Seri Mobil");
            spinnerSeri.setEnabled(false);
            series = "";
            spinnerCarPrice.setText("");
            spinnerCarPrice.setEnabled(false);
            spinnerPlate.setText("Pilih Plate Wilayah");
            spinnerPlate.setEnabled(false);
        } else if (title.equals(selectDriverProtection)) {
            driverProtectionId = baseFilter.getId();
            spinnerDriver.setText(baseFilter.getFilterText());
        } else if (title.equals(selectPassengerProtection)) {
            int i = baseFilter.getId();
            passengerProtectionId = i;
            spinnerPassenger.setText(baseFilter.getFilterText());
            if (i != 0) {
                passengerCountWrapper.setVisibility(View.VISIBLE);
            } else {
                passengerCountWrapper.setVisibility(View.GONE);
            }
        } else if (title.equals(selectThirdParty)) {
            spinnerThirdParty.setText(baseFilter.getFilterText());
            tplId = baseFilter.getId();
        }
    }
}
