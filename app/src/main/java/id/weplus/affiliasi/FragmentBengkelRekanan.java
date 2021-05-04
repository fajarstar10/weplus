package id.weplus.affiliasi;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.R;

public class FragmentBengkelRekanan extends Fragment {
    @BindView(R.id.bengkelrekanan_proteksi_partner_asuransi_spinner)
    Spinner proteksi;
    @BindView(R.id.bengkelrekanan_search_partner_asuransi_spinner) Spinner search;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_bengkel_rekanan, container, false);
        ButterKnife.bind(this, rootView);

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.daftar_bengkel_rekanan));
        adapterSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        proteksi.setAdapter(adapterSpinner);

        return rootView;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.bengkelrekanan_btn_search)
    public void actionSearchBengkelRekanan(){
        Intent intent = new Intent(getActivity(), DaftarBengkelRekananActivity.class);
        startActivity(intent);
    }
}
