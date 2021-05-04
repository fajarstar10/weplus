package id.weplus.polissaya;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;

public class FragmentPolisSemua  extends Fragment {
    @BindView(R.id.polissemua_rec) RecyclerView recSemua;
    private String[] status = {"Berhasil", "Gagal"};

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recSemua.setNestedScrollingEnabled(true);
        PolisSemuaAdapter adapter = new PolisSemuaAdapter(getActivity(), status);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recSemua.setLayoutManager(linearLayoutManager);
        PolisSemuaAdapter.PolisSemuaOnItemClicked listener = new PolisSemuaAdapter.PolisSemuaOnItemClicked() {
            @Override
            public void onItem(int pos, String tag) {
                Intent intent = new Intent(getActivity().getApplicationContext(),DetailHistoryActivity.class);
                intent.putExtra("tag_history", tag);
                startActivity(intent);
            }
        };
        adapter.setListenerPolisSemua(listener);
        recSemua.setAdapter(adapter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_polis_semua, container,false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
