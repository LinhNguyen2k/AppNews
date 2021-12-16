package com.example.nguyenanhlinh_mvvm_newsapp

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

class NewsApplication : Application(), Application.ActivityLifecycleCallbacks,
    DefaultLifecycleObserver {
    private var isCheckView = false
    override fun onCreate() {
        super<Application>.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        registerActivityLifecycleCallbacks(this)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        isCheckView = true
    }

    override fun onActivityResumed(p0: Activity) {
        if (isCheckView && p0 !is PassWordMainActivity) {
            val intent = Intent(this, PassWordMainActivity::class.java)
            intent.putExtra("isCheckStartActivity", true)
            p0.startActivity(intent)
            isCheckView = false
        }
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) = Unit
    override fun onActivityStarted(p0: Activity) = Unit
    override fun onActivityPaused(p0: Activity) = Unit
    override fun onActivityStopped(p0: Activity) = Unit
    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) = Unit
    override fun onActivityDestroyed(p0: Activity) = Unit
}