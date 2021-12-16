package com.example.nguyenanhlinh_mvvm_newsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nguyenanhlinh_mvvm_newsapp.viewmodel.PassWordViewModel
import kotlinx.android.synthetic.main.activity_pass_word_main.*

class PassWordMainActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this)[PassWordViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_word_main)
        supportActionBar?.hide()
        viewModel.isCheckStartActivity = intent.getBooleanExtra("isCheckStartActivity", false)
        tv_one.setOnClickListener { viewModel.checkPassCode(1) }
        tv_two.setOnClickListener { viewModel.checkPassCode(2) }
        tv_three.setOnClickListener { viewModel.checkPassCode(3) }
        tv_four.setOnClickListener { viewModel.checkPassCode(4) }
        tv_five.setOnClickListener { viewModel.checkPassCode(5) }
        tv_sixe.setOnClickListener { viewModel.checkPassCode(6) }
        tv_seven.setOnClickListener { viewModel.checkPassCode(7) }
        tv_eight.setOnClickListener { viewModel.checkPassCode(8) }
        tv_nine.setOnClickListener { viewModel.checkPassCode(9) }
        tv_zero.setOnClickListener { viewModel.checkPassCode(0) }
        tv_delete.setOnClickListener { viewModel.checkPassCode(11) }
        observe()

    }

    private fun observe() {
        var temp = 0
        viewModel.check.observe(this, Observer {
            temp = it!!
        })
        btn_login.setOnClickListener {
            when (temp) {
                0 -> {
                    Toast.makeText(applicationContext,"Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    Toast.makeText(applicationContext,"Mật khẩu chưa chính xác", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                3 -> finish()
            }
        }

        viewModel.passCode.observe(this, Observer {
            tv_passWord.text = it
        })
    }

}