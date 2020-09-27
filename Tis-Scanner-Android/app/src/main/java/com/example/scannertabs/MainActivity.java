package com.example.scannertabs;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import com.google.android.material.tabs.TabLayout;
import java.util.Locale;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {

    public static String language;

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    TabLayout tabLayout;
    private static final int REQUEST_CAMERA = 1;
    SharedPreferences sharedpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedpref = getSharedPreferences(getString(R.string.sharedPreferences), 0); // 0 - for private mode
        setAppLocale(sharedpref.getString(getString(R.string.language), ""));
        language = sharedpref.getString(getString(R.string.language), "cz");
        if (language.equals("")) {
            language = "cz";
        }

        setContentView(R.layout.activity_main);
        requestPermission();

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPosition = 0;
            int previousPosition = 1;

            @Override
            public void onPageSelected(int position) {
                previousPosition = currentPosition;
                currentPosition = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    FragmentLifecycle fragmentToShow = (FragmentLifecycle) sectionsPagerAdapter.getItem(currentPosition);
                    fragmentToShow.onResumeFragment();

                    FragmentLifecycle fragmentToHide = (FragmentLifecycle) sectionsPagerAdapter.getItem(previousPosition);
                    fragmentToHide.onPauseFragment();
                }
            }
        });

        setupviewPager(viewPager, sectionsPagerAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.qrcodescan_icon);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.settings_icon_white);
    }

    public void setupviewPager(ViewPager viewPager, final SectionsPagerAdapter adapter) {
        adapter.addFragmet(new ScannerClass(), getString(R.string.tab_text_1));
        adapter.addFragmet(new SettingsViewFlipperClass(language), getString(R.string.tab_text_2));

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[] {CAMERA}, REQUEST_CAMERA);
        }
    }

    public void setAppLocale(String language) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.locale = new Locale(language.toLowerCase());
        res.updateConfiguration(config, dm);
    }
}