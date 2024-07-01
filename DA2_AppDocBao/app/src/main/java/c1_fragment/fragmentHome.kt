package c1_fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import b_adapter.adpaterViewPager
import com.example.da2_appdocbao.R
import com.example.da2_appdocbao.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentHome : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterViewPager: adpaterViewPager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            adapterViewPager = adpaterViewPager(childFragmentManager, lifecycle)
            binding.viewPager.adapter = adapterViewPager
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
                when (pos) {
                    0 -> tab.text = "Xã hội"
                    1 -> tab.text = "Thể thao"
                    2 -> tab.text = "Giáo dục"
                    3 -> tab.text = "Luật pháp"
                    4 -> tab.text = "Kinh doanh"
                    5 -> tab.text = "Thế giới"
                }
            }.attach()

    }
    }

