package com.tv.service.basic_live;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * @author guyushu
 */
public class LiveActivity extends AppCompatActivity {

    private static final String TAG = "LiveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        TextView tvTitleView = findViewById(R.id.tvTitle);
        ContentService service = ServiceFactory.getInstance().getService(ContentService.class);
        if (service != null) {
            String title = service.getTitle();
            tvTitleView.setText(title);
            Log.i(TAG, "title: " + title);
        }
        ContentService  service2 = ServiceFactory.getInstance().getService(ContentService.class);
        if (service2 != null) {
            String title = service2.getTitle();
            tvTitleView.setText(title);
            Log.i(TAG, "title: " + title);
        }
    }
}