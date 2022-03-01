package com.example.nguyenanhlinh_mvvm_newsapp.application

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.nguyenanhlinh_mvvm_newsapp.activities.PassWordMainActivity
import kotlin.system.measureTimeMillis

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
    //ở CallBack này người dùng đã có thể tương tác được với giao diện -> isCheckView = false
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        isCheckView = false
        val time = measureTimeMillis {
            Log.d("TAG","onResume")
        }
        Log.d("TAG","time onResume: $time")
    }
    //callBack này sẽ được ưu tiên được gọi trước callBack onRsume()
    override fun onActivityResumed(p0: Activity) {
        if (isCheckView && p0 !is PassWordMainActivity) {
            val intent = Intent(this, PassWordMainActivity::class.java)
            intent.putExtra("isCheckStartActivity", true)
            p0.startActivity(intent)
            isCheckView = false
        }
        val time = measureTimeMillis {
            Log.d("TAG","onActivityResumed")
        }
        Log.d("TAG","time onActivityResumed: $time")
    }
    override fun onActivityStopped(p0: Activity) = Unit
    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) = Unit
    override fun onActivityDestroyed(p0: Activity) = Unit
    override fun onActivityCreated(p0: Activity, p1: Bundle?) = Unit
    override fun onActivityStarted(p0: Activity) = Unit
    override fun onActivityPaused(p0: Activity) = Unit

}