package dollar.com.travel_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private HomeFragment homeFragment;
    private TravelActivity travelActivity;
    private MoneyFragment moneyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btn_search).setOnClickListener(this);
        findViewById(R.id.btn_travel).setOnClickListener(this);
        findViewById(R.id.btn_money).setOnClickListener(this);

        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        replaceFragment(homeFragment);
    }

    private void replaceFragment(Fragment fragment) {
        Fragment f = getSupportFragmentManager()
                .findFragmentByTag(fragment.getClass().getName());
        if (f != null && f == fragment) {
            if (fragment.isVisible()) {
                return;
            }
            getSupportFragmentManager().beginTransaction()
                    .show(fragment)
                    .commit();
            return;
        }

        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, fragment.getClass().getName())
                .addToBackStack(null)
                .commit();
        /*getSupportFragmentManager().beginTransaction()
                .add(R.id.content, fragment).show(fragment)
                .commit();*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                replaceFragment(homeFragment);
                break;
            case R.id.btn_search:

                break;
            case R.id.btn_travel:
                Intent intent = new Intent(this, TravelActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_money:
                if (moneyFragment == null) {
                    moneyFragment = new MoneyFragment();
                }
                replaceFragment(moneyFragment);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
