package com.kazim.rickandmorty

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.kazim.rickandmorty.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var timer:CountDownTimer
    private lateinit var sh:SharedPreferences
    private var veri =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Welcome or Hello
        sh =this.getSharedPreferences("helleOrWelcome", MODE_PRIVATE)

        countDowntimer(6)
    }


    private fun countDowntimer(zaman: Long) {
        timer=object: CountDownTimer(zaman*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.text.text="${(millisUntilFinished/1000).toString()}"


                veri =sh.getBoolean("veri",false)

                if (veri){
                    binding.infotext.text ="Hello!"
                }
                else if (!veri){
                    binding.infotext.text ="Welcome!"

                }
            }
            override fun onFinish() {
                var editor =sh.edit()
                editor.putBoolean("veri",true).apply()
                binding.text.text="Açılıyor..."

                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } }.start() }



}