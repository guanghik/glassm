package com.yowenlove.mobile.glassm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by yowenlove on 14-7-26.
 */
public class PhoneMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, ARPreviewActivity.class));
        finish();
    }
}
