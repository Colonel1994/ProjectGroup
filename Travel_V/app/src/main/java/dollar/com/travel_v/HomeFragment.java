package dollar.com.travel_v;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Administrator on 2/7/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{

    private MoneyFragment moneyFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.travel_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_travel).setOnClickListener(this);
        view.findViewById(R.id.btn_money).setOnClickListener(this);
        view.findViewById(R.id.play_video).setOnClickListener(this);
        TextView tvName = (TextView)view.findViewById(R.id.tv_name);

        /*Intent intent = new Intent();
        intent.getAction();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            String hello = (String)bundle.get("Name");
            tvName.setText(hello);
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_travel:
                Intent intent = new Intent(getContext(), TravelActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_money:
                Toast.makeText(getContext(),"Đăng phát triển ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.play_video:
                Intent playVideo = new Intent(getContext(), PlayVideoActivity.class);
                startActivity(playVideo);
                break;
            default:
                break;
        }
    }
}
