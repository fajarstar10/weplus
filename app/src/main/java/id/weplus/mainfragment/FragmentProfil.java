package id.weplus.mainfragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.affiliasi.AffiliationActivity;
import id.weplus.model.LoginData;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.notifikasi.NotifikasiActivity;
import id.weplus.pemegangpolis.DataPemegangPolisActivity;
import id.weplus.profil.KontakKamiActivity;
import id.weplus.profil.ProfilAdapter;
import id.weplus.profil.UbahProfil;
import id.weplus.ubahkatasandi.UbahSandiActivity;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;

public class FragmentProfil extends Fragment {
    private static String TAG = "FragmentProfil";
    private LoginData user;
    TextView nama;
    TextView email;
    TextView phone;

    @BindView(R.id.profil_title)
    TextView titleProfil;
    @BindView(R.id.profil_icon_image)
    CircularImageView profil_icon;
    @BindView(R.id.profil_name)
    TextView name;
    @BindView(R.id.profil_email)
    TextView emailprofile;
    @BindView(R.id.profil_phone)
    TextView phoneNumber;
    @BindView(R.id.profil_recycleview)
    RecyclerView recyclerViewProfil;
    @BindView(R.id.profil_version)
    TextView version;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_profil, container, false);
        nama = view.findViewById(R.id.profil_name);
        email = view.findViewById(R.id.profil_email);
        phone = view.findViewById(R.id.profil_phone);

        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String json_respon = WeplusSharedPreference.getUser(getContext());
        Gson gsonhen = new Gson();
        /**
         *
         * penyebab crash , response server tidak menyertakan object User
         * melainkan hanya variable saja(user_id,name,email,etc)
         * sehingga saat di bentuk object variable user pada sharepreferences berisi null
         *
         */
        LoginData user = gsonhen.fromJson(json_respon, LoginData.class);
        if (user != null) {
            setUserInformation(user.getName(), user.getEmail(), user.getPhone(), user.getPicture());
        }
        recyclerViewProfil.setNestedScrollingEnabled(true);
        ProfilAdapter profilAdapter = new ProfilAdapter(getActivity());
        LinearLayoutManager profilAdapterLM = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewProfil.setLayoutManager(profilAdapterLM);
        recyclerViewProfil.setAdapter(profilAdapter);
        ProfilAdapter.OnClickItemProfile listener = pos -> actionOnClicked(pos);
        profilAdapter.setListener(listener);
    }

    private void getUserData() {
        String json_response = WeplusSharedPreference.getUser(Objects.requireNonNull(getActivity()));
        Gson gson = new Gson();
        user = gson.fromJson(json_response, LoginData.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserData();
        if (nama != null) {
            setUserInformation(user.getName(), user.getEmail(), user.getPhone(), user.getPicture());
        }
    }

    private void setUserInformation(String name, String email_v, String phone_v, String picture) {
        nama.setText(name);
        email.setText(email_v);
        phone.setText(phone_v);
        Glide.with(this).load(picture).error(R.drawable.ic_baseline_account_circle_24).into(profil_icon);
    }

    private void actionOnClicked(int pos) {
        switch (pos) {
//            case 0:
//                Intent pemegangPolisActivity = new Intent(getActivity(), ListPemegangPolisActivity.class);
//                startActivity(pemegangPolisActivity);
//                break;
//            case 1:
//                Intent kontakkami = new Intent(getActivity(), KontakKamiActivity.class);
//                startActivity(kontakkami);
//                break;
//            case 2:
//                Intent ubahKataSandi = new Intent(getActivity(), UbahSandiActivity.class);
//                startActivity(ubahKataSandi);
//                break;
            case 0:
                Intent notifikasi = new Intent(getActivity(), NotifikasiActivity.class);
                startActivity(notifikasi);
                break;
//            case 1:
//                Intent afiliasi = new Intent(getActivity(), AffiliationActivity.class);
//                startActivity(afiliasi);
//                break;
            case 1:
                break;
            case 3:
                final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.weplus")));

//                }
                break;

            default:
                break;
        }
    }

    @OnClick(R.id.afiliasi_next)
    public void onAfiliasiClick() {
        Intent afiliasi = new Intent(getActivity(), AffiliationActivity.class);
        startActivity(afiliasi);
    }

    @OnClick(R.id.profil_layout_pemegang_polis)
    public void onClickedPemegangPolis() {
        Intent pemegangPolisActivity = new Intent(getActivity(), DataPemegangPolisActivity.class);
        startActivity(pemegangPolisActivity);
    }

    @OnClick(R.id.profil_layout_hubungi_kami)
    public void onClickedHuungiKami() {
        Intent kontakkami = new Intent(getActivity(), KontakKamiActivity.class);
        startActivity(kontakkami);
    }

    @OnClick(R.id.profil_layout_ganti_sandi)
    public void onClickedgantiKataSandi() {
        Intent ubahKataSandi = new Intent(getActivity(), UbahSandiActivity.class);
        startActivity(ubahKataSandi);
    }

    @OnClick(R.id.profil_btn_ubah)
    public void actionUbahProfil() {
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_EDIT_PROFILE);

        Intent ubahProfil = new Intent(getActivity(), UbahProfil.class);
        startActivity(ubahProfil);
    }

    @OnClick(R.id.profil_btn_keluar)
    public void actionLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.view_logout, null);
        builder.setView(customLayout);

        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView btnBatal = customLayout.findViewById(R.id.dialog_btn_batal);
        TextView btnYa = customLayout.findViewById(R.id.dialog_btn_ya);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeplusSharedPreference.setLoggedIn(getContext(), false);
                Intent intent = new Intent(getContext(), WelcomeActivity.class);
                intent.putExtra("finish", true); // if you are checking for this in your other Activities
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }
}
