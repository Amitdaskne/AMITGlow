package com.amit.glow;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int OVERLAY_PERMISSION_REQ_CODE = 1234;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        Button btnOverlay = findViewById(R.id.btnOverlayPerm);
        Button btnAccess = findViewById(R.id.btnAccessPerm);
        Switch switchGlow = findViewById(R.id.switchGlow);

        btnOverlay.setOnClickListener(v -> {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            } else {
                Toast.makeText(this, "Overlay already granted", Toast.LENGTH_SHORT).show();
            }
        });

        btnAccess.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, "Find 'AMIT Glow' and enable it", Toast.LENGTH_LONG).show();
        });

        switchGlow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "Please grant overlay permission first!", Toast.LENGTH_SHORT).show();
                    switchGlow.setChecked(false);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(new Intent(this, GlowService.class));
                    } else {
                        startService(new Intent(this, GlowService.class));
                    }
                }
            } else {
                stopService(new Intent(this, GlowService.class));
            }
        });
    }
}
