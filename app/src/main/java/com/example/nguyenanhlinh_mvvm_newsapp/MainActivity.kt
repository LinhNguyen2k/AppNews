package com.example.nguyenanhlinh_mvvm_newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.example.nguyenanhlinh_mvvm_newsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var navHost: NavHostFragment
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        val newsRepository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelProvider(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]
        navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        var controller: NavController = navHost.navController
        bottom_NVT.setupWithNavController(controller)

    }

    override fun onResume() {
        if (viewModel.isCheckStartActivity()) {
            val intent = Intent(this, PassWordMainActivity::class.java)
            intent.putExtra("isCheckStartActivity", true)
            startActivity(intent)
        }
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.setData()
    }
}