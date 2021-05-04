package id.weplus.voucher;

import android.os.Bundle;
import androidx.annotation.NonNull;import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.weplus.R;

public class FragmentKetentuan extends Fragment {
    public static FragmentKetentuan newInstance(String content)
    {
        FragmentKetentuan myFragment = new FragmentKetentuan();

        Bundle args = new Bundle();
        args.putString("content", content);
        myFragment.setArguments(args);
        return myFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String content = getArguments().getString("content");
        TextView tvContent = view.findViewById(R.id.textView);
        tvContent.setText(Html.fromHtml(content));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ketentuan, container, false);
        return rootView;
    }
}
