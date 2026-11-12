package com.amit.glow;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

public class GlowAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            // Check if lock screen or notification shade opened
            if (event.getClassName() != null && 
               (event.getClassName().equals("com.android.systemui.keyguard.KeyguardViewMediator") || 
                event.getClassName().toString().contains("Keyguard"))) {
                // Trigger glow service if not running
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(new Intent(this, GlowService.class));
                } else {
                    startService(new Intent(this, GlowService.class));
                }
            }
        }
    }

    @Override
    public void onInterrupt() {}
}
