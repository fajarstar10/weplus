package id.weplus.belipolis.perjalanan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.helper.DatePickerFragment;
import id.weplus.helper.FullScreenFilterActivity;
import id.weplus.helper.OnDateSetListener;
import id.weplus.helper.OnOptionsSelect;
import id.weplus.helper.RoundedBottomSheet;
import id.weplus.model.BaseFilter;
import id.weplus.model.City;
import id.weplus.model.Country;
import id.weplus.model.Province;
import id.weplus.model.request.TravelProductListRequest;
import id.weplus.model.response.CityListResponse;
import id.weplus.model.response.CountryListResponse;
import id.weplus.model.response.TravelFilterResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeliPolisPerjalananActivity extends BaseActivity implements OnDateSetListener, OnOptionsSelect {
    String TAG = "BeliPolisTravelForm";
    @BindView(R.id.viewback_title)
    TextView title;
    @BindView(R.id.viewback_description)
    TextView description;
    @BindView(R.id.formbelipolis_tipe_perjalanan)
    TextView tvTipePerjalanan;
    @BindView(R.id.formbelipolis_tujuan_perjalanan)
    TextView tvTujuanPerjalanan;
    @BindView(R.id.layoutWrapperNegara)
    RelativeLayout layoutNegara;
    @BindView(R.id.layoutWrapperProvinsi)
    RelativeLayout layoutProvinsi;
    @BindView(R.id.layoutWrapperKota)
    RelativeLayout layoutKota;
    @BindView(R.id.etNegara)
    TextView etNegara;
    @BindView(R.id.etProvinsi)
    TextView etProvinsi;
    @BindView(R.id.etKota)
    TextView etKota;
    @BindView(R.id.formbelipolis_tglkeberangkatan)
    TextView tglBrgkat;
    @BindView(R.id.formbelipolis_tglkedatangan)
    TextView tglDatang;
    @BindView(R.id.formbelipolis_jumlah_penumpang_spinner)
    TextView tvPassengerCount;
    @BindView(R.id.formbelipolis_tipe_penumpang_spinner)
    TextView tvTipePeserta;

    //error view
    @BindView(R.id.errorTextCountry)
    TextView errorTextCountry;
    @BindView(R.id.errorTextProvince)
    TextView errorTextProvince;
    @BindView(R.id.errorTextCity)
    TextView errorTextCity;
    @BindView(R.id.errorTextDateStart)
    TextView errorTextDateStart;
    @BindView(R.id.errorTextDateEnd)
    TextView errorTextDateEnd;
    @BindView(R.id.errorTextPassengerType)
    TextView errorTextPassengerType;
    @BindView(R.id.errorTextPassengerCount)
    TextView errorTextPassengerCount;

    private String tipePerjalanan = "";
    private String tujuanPerjalanan = "";
    private String dateStart = "";
    private String dateEnd = "";
    private String country = "";
    private String province = "";
    private String city = "";
    private int packageType = 0;
    private String passengerType = "";
    private ArrayList<BaseFilter> additionProtection = new ArrayList<>();
    private ArrayList<BaseFilter> groupType = new ArrayList<>();
    private ArrayList<BaseFilter> groupPackage = new ArrayList<>();
    private ArrayList<Country> countries = new ArrayList<Country>();
    private ArrayList<City> cities = new ArrayList<City>();
    private String passengerCountTitle = "Pilih Jumlah Peserta";
    private String passengerTypeTitle = "Pilih Tipe Peserta";
    private HashMap<Integer, String> provinceMap = new HashMap<>();
    private HashMap<Integer, String> cityMap = new HashMap<>();
    private Calendar calStart;
    private Calendar calEnd;

    private int catId = 0;
    private int partnerId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli_polis_perjalanan);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.perjalanan));
        description.setText(getResources().getString(R.string.urutkansesuaidataperjalanan));
        catId = getIntent().getIntExtra("cat_id", 0);
        getData();
        fetchFilterData();
        setupDatePicker();
    }

    private void setupTipePeserta(ArrayList<BaseFilter> baseFilters) {
        tvTipePeserta.setOnClickListener(view -> {
            RoundedBottomSheet roundedBottomSheet = new RoundedBottomSheet(this);
            Bundle bundle = new Bundle();
            bundle.putString("title", passengerTypeTitle);
            bundle.putParcelableArrayList("baseFilter", baseFilters);
            roundedBottomSheet.setArguments(bundle);
            roundedBottomSheet.show(BeliPolisPerjalananActivity.this.getSupportFragmentManager(), "test");
        });
    }

    private void setupSelectPassengerType() {
        tvPassengerCount.setOnClickListener(view -> {
            RoundedBottomSheet roundedBottomSheet = new RoundedBottomSheet(this);
            Bundle bundle = new Bundle();
            bundle.putString("title", passengerCountTitle);
            bundle.putParcelableArrayList("baseFilter", groupPackage);
            roundedBottomSheet.setArguments(bundle);
            roundedBottomSheet.show(BeliPolisPerjalananActivity.this.getSupportFragmentManager(), "test");
        });
    }


    private void setupDatePicker() {
        tglBrgkat.setOnClickListener(view -> {
            DialogFragment newFragment = DatePickerFragment.newInstance("pergi", this);
            newFragment.show(getSupportFragmentManager(), "datePicker");
        });

        tglDatang.setOnClickListener(view -> {
            DialogFragment newFragment = DatePickerFragment.newInstance("datang", this);
            newFragment.show(getSupportFragmentManager(), "datePicker");
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 21) {
            country = data.getStringExtra("result");
            etNegara.setText(country);
            tglBrgkat.setEnabled(true);
        }
//        if(requestCode==12 && resultCode==22){
//            province = data.getStringExtra("result");
//            etProvinsi.setText(province);
//            etKota.setEnabled(true);
//            Log.d("kota enabled","kota enabled");
//            fetchKota(getProvinceId(province));
//        }
        if (requestCode == 13 && resultCode == 23) {
            city = data.getStringExtra("result");
            etKota.setText(city);
            tglBrgkat.setEnabled(true);
        }
    }

    private void setupSelectCountry() {
        etNegara.setOnClickListener(view -> {
            ArrayList<String> countryList = new ArrayList<>();
            for (int i = 0; i < countries.size(); i++) {
                countryList.add(countries.get(i).getName());
            }
            Intent intentBrand = new Intent(BeliPolisPerjalananActivity.this, FullScreenFilterActivity.class);
            intentBrand.putStringArrayListExtra("searchList", countryList);
            intentBrand.putExtra("resultCode", 21);
            startActivityForResult(intentBrand, 11);
        });
    }

    private void setupSelectProvince(ArrayList<Province> provinces) {
        provinceMap.clear();
        ArrayList<String> provinceNames = new ArrayList<>();
        for (Province province : provinces) {
            provinceNames.add(province.getName());
            provinceMap.put(province.getId(), province.getName());
        }
        etProvinsi.setOnClickListener(view -> {
            Intent intentBrand = new Intent(BeliPolisPerjalananActivity.this, FullScreenFilterActivity.class);
            intentBrand.putStringArrayListExtra("searchList", provinceNames);
            intentBrand.putExtra("resultCode", 22);
            startActivityForResult(intentBrand, 12);
        });
    }


    private void setupSelectCity() {
        etKota.setOnClickListener(view -> {
            ArrayList<String> cityNames = new ArrayList<>();
            for (City city : cities) {
                cityNames.add(city.getName());
                cityMap.put(city.getId(), city.getName());
            }
            Intent intentBrand = new Intent(BeliPolisPerjalananActivity.this, FullScreenFilterActivity.class);
            intentBrand.putStringArrayListExtra("searchList", cityNames);
            intentBrand.putExtra("resultCode", 23);
            startActivityForResult(intentBrand, 13);
        });
    }

    private void getData() {
        tipePerjalanan = getIntent().getStringExtra("tipePerjalanan");
        tujuanPerjalanan = getIntent().getStringExtra("tujuanPerjalanan");
        partnerId = getIntent().getIntExtra("partner_id",0);
        boolean isAbroad = tujuanPerjalanan.equals("abroad");
        tvTipePerjalanan.setText(tipePerjalanan.equals("onetime") ? "Sekali Jalan" : "Tahunan");
        tvTujuanPerjalanan.setText(isAbroad ? "Luar Negeri" : "Domestik");
        layoutNegara.setVisibility(isAbroad ? View.VISIBLE : View.GONE);
        layoutKota.setVisibility(!isAbroad ? View.VISIBLE : View.GONE);
        if (isAbroad) {
            fetchCountry();
            etNegara.setEnabled(true);
            setupSelectCountry();
        } else {
            fetchKota();
            etKota.setEnabled(true);
            setupSelectCity();
        }
    }

    private void fetchFilterData() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getTravelFilter();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    TravelFilterResponse resp = gson.fromJson(response.body(), TravelFilterResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)) {
                            additionProtection = resp.getData().getAdditionTravel();
                            groupType = resp.getData().getTypeGroup();
                            setupTipePeserta(groupType);
                            setupSelectPassengerType();
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(BeliPolisPerjalananActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            showError(BeliPolisPerjalananActivity.this, resp.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError(BeliPolisPerjalananActivity.this, "Time out");
                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(BeliPolisPerjalananActivity.this, getString(R.string.network_error));
        }
    }

    private void fetchCountry() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getCountryList();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    CountryListResponse resp = gson.fromJson(response.body(), CountryListResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)) {
                            countries = resp.getData().getCountryArrayList();
                            //setupSelectCountry();
                        } else {
                            showError(BeliPolisPerjalananActivity.this, resp.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError(BeliPolisPerjalananActivity.this, "Time out");
                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(BeliPolisPerjalananActivity.this, getString(R.string.network_error));
        }
    }

    private void fetchKota() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getCityList();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    CityListResponse resp = gson.fromJson(response.body(), CityListResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)) {
                            cities.clear();
                            cities.addAll(resp.getData().getCityArrayList());
                            setupSelectCity();
                        } else {
                            showError(BeliPolisPerjalananActivity.this, resp.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError(BeliPolisPerjalananActivity.this, "Time out");
                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(BeliPolisPerjalananActivity.this, getString(R.string.network_error));
        }
    }


    @OnClick(R.id.viewback_buttonback)
    public void backBeliPolisPerjalanan() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.belipolis_perjalanan_btn_lanjutkan)
    public void actionLanjutkanPolisperjalanan() {
        if (validate()) {
            String destination = "";
            if (tujuanPerjalanan.equals("abroad")) {
                destination = country;
            } else {
                destination = "Indonesia";
            }
            Intent intent = new Intent(BeliPolisPerjalananActivity.this, TambahanPerlindunganTravel.class);
            intent.putParcelableArrayListExtra("addProtection", additionProtection);
            TravelProductListRequest requestBody = new TravelProductListRequest(
                    0, "", 7, partnerId, 0, 0, 0,
                    destination, tipePerjalanan, dateStart, dateEnd, packageType, passengerType
            );
            requestBody.setType_group_name(tvTipePeserta.getText().toString());
            requestBody.setDeparture_city(etKota.getText().toString());
            intent.putExtra("requestBody",requestBody );
            startActivity(intent);
        } else {

        }
    }

    private boolean validate() {
        try {
            if (calStart.after(calEnd)) {
                showError(this, "Tanggal Keberangkatan harus sebelum tanggal kedatangan");
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        boolean abroad = tujuanPerjalanan.equals("abroad");
        boolean dateStartValidate = !dateStart.equals("");
        boolean dateEndValidate = !dateEnd.equals("");
        boolean passTypeValidate = !passengerType.equals("");
        boolean passCountValidate = packageType != 0;
        boolean countryValidate = !country.equals("");
        boolean cityValidate = !city.equals("");

        //error message
        errorTextDateStart.setVisibility(dateStartValidate ? View.GONE : View.VISIBLE);
        errorTextDateEnd.setVisibility(dateEndValidate ? View.GONE : View.VISIBLE);
        errorTextPassengerCount.setVisibility(passTypeValidate ? View.GONE : View.VISIBLE);
        errorTextPassengerType.setVisibility(passCountValidate ? View.GONE : View.VISIBLE);


        if (abroad) {
            errorTextCountry.setVisibility(countryValidate ? View.GONE : View.VISIBLE);
            return (dateStartValidate
                    && dateEndValidate
                    && passTypeValidate
                    && passCountValidate
                    && countryValidate);
        } else {
            errorTextCity.setVisibility(cityValidate ? View.GONE : View.VISIBLE);
            return (dateStartValidate
                    && dateEndValidate
                    && passTypeValidate
                    && passCountValidate
                    && cityValidate);
        }
    }

    @Override
    public void onDateSet(Calendar c, String cat) {
        SimpleDateFormat postFormater = new SimpleDateFormat(" dd MMMM yyyy");
        String newDateStr = postFormater.format(c.getTime());
        if (cat.equals("pergi")) {
            calStart = c;
            dateStart = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH);
            tglBrgkat.setText(newDateStr);
            tglDatang.setEnabled(true);
        }
        if (cat.equals("datang")) {
            calEnd = c;
            dateEnd = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH);
            tglDatang.setText(newDateStr);
            tvTipePeserta.setEnabled(true);
        }
    }

    @Override
    public void onOptionSelect(@NotNull BaseFilter baseFilter, @NotNull String title) {
        if (title.equals(passengerCountTitle)) {
            packageType = baseFilter.getId();
            tvPassengerCount.setText(baseFilter.getFilterText());
        }
        if (title.equals(passengerTypeTitle)) {
            passengerType = baseFilter.getFilterText().toLowerCase();
            tvTipePeserta.setText(baseFilter.getFilterText());
            groupPackage = baseFilter.getUnitType();
            tvPassengerCount.setEnabled(true);

        }
    }
}
