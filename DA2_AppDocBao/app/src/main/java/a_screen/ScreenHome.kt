package a_screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import c1_fragment.fragmentHome
import c1_fragment.fragmentSaveNews
import com.example.da2_appdocbao.R
import com.example.da2_appdocbao.databinding.ActivityScreenHomeBinding

@Suppress("UNUSED_EXPRESSION")
class ScreenHome : AppCompatActivity() {
    private lateinit var binding : ActivityScreenHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        change_fragment()


    }

    private fun change_fragment() {

        // cần node chỗ này vào, quan trọng nè

        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragmentHome()).commit()

        binding.bottMenu.setOnNavigationItemSelectedListener { item->
            var selectedFragment : Fragment? = null
            when(item.itemId){
                R.id.icon_fragmentHome -> selectedFragment = fragmentHome()
                R.id.icon_fragmentSaveNews -> selectedFragment = fragmentSaveNews()
            }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout,it ).commit()
            }
            true
        }


    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout ,fragment )
            commit()
        }
    }

    override fun onRestart() {
        super.onRestart()
        val intent = intent
        finish()
        startActivity(intent)
        // reload
    }
}