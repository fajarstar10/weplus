package id.weplus.kontakklaim;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;

public class FragmentKontakKlaim extends Fragment {
    @BindView(R.id.recycleview) RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_kontak_klaim,container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setNestedScrollingEnabled(true);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        KontakKlaimAdapter adapter = new KontakKlaimAdapter(getActivity());
        KontakKlaimAdapter.onKontakKlaimClicked listener = new KontakKlaimAdapter.onKontakKlaimClicked() {
            @Override
            public void onItemClicked(int pos) {
                Intent daftarKontakKlaim = new Intent(getActivity(), DaftarKontakKlaimActivity.class);
                startActivity(daftarKontakKlaim);
            }
        };
        adapter.setKontakKlaimListener(listener);
        recyclerView.setAdapter(adapter);

    }
}
