package com.example.da2_appdocbao

import a_screen.ScreenHome
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.da2_appdocbao.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        to_home()


    }

    private fun to_home() {
        val countdownTimer = object: CountDownTimer(5000, 3000){
            override fun onTick(p0: Long) {
                val downtime = ((5000- p0)/50).toInt()
            }

            override fun onFinish() {
               if( checkedInternet(this@MainActivity)){
                   to_screen_home()
               }else{
                   requireInternet(this@MainActivity)
               }
            }
        }
        countdownTimer.start()
    }

    private fun requireInternet(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Không có kết nối mạng")
            .setMessage("Vui lòng bật kết nối mạng để tiếp tục.")
            .setPositiveButton("Cài đặt") { _, _ ->
                // Mở cài đặt mạng
                context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
            }
            .setNegativeButton("Hủy") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }

    private fun checkedInternet(context: Context): Boolean {
        val connect = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connect.activeNetwork?: return false
        val networKCapabilities = connect.getNetworkCapabilities(network) ?: return false

        return  networKCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }

    private fun to_screen_home() {
        startActivity(Intent(this@MainActivity, ScreenHome :: class.java))
        finish()
    }
}