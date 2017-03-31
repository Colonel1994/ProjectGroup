package dollar.com.travel_v;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2/7/2017.
 */

public class OtherFragment extends Fragment implements View.OnClickListener {
    private TextView tvName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.other_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = (TextView)view.findViewById(R.id.tv_name);
        tvName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_name:
                Toast.makeText(getContext(),"Đăng phát triển ",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
