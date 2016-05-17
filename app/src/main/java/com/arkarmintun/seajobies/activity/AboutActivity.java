package com.arkarmintun.seajobies.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arkarmintun.seajobies.R;

import org.mmaug.mmfont.utils.FontUtils;
import org.mmaug.mmfont.utils.Fonts;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView profileNameMM = (TextView) findViewById(R.id.user_profile_name_mm);
        FontUtils fontUtils = new FontUtils(this);

        profileNameMM.setText("အာကာမင်းထွန်း");
        fontUtils.setTypeFace(Fonts.NOTO_REGULAR, profileNameMM);

        TextView txtDeveloperInfo = (TextView) findViewById(R.id.text_developer_info);
        txtDeveloperInfo.setText("This app is brought to you by Arkar Min Tun who was MMU student majored in Nautical Science.");

        RelativeLayout rlPhoneNumber = (RelativeLayout) findViewById(R.id.rl_phone_number);
        rlPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtPhoneNumber = (TextView) findViewById(R.id.text_phone_number);
                String phoneNumber = txtPhoneNumber.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
