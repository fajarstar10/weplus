package id.weplus.voucher;

import android.os.Bundle;
import androidx.annotation.NonNull;import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import id.weplus.R;

public class FragmentCaraPakai  extends Fragment {

    public static FragmentCaraPakai newInstance(String content)
    {
        FragmentCaraPakai myFragment = new FragmentCaraPakai();

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

        TextView tvContent = view.findViewById(R.id.textView2);
        if(hasHTMLTags(content)) {
            tvContent.setText(Html.fromHtml(content));
        }else{
            tvContent.setText(content);
        }
    }

    private static final String HTML_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    private Pattern pattern = Pattern.compile(HTML_PATTERN);

    public boolean hasHTMLTags(String text){
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cara_pakai,container, false);
        return rootView;
    }
}
