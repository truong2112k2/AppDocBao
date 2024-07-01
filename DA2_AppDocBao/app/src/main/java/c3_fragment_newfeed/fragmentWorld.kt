package c3_fragment_newfeed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import b_adapter.adapterAllFeeds
import b_adapter.adapterNewFeeds
import c4_rss.ItemFeed
import c4_rss.RssFetcher
import com.example.da2_appdocbao.R
import com.example.da2_appdocbao.databinding.FragmentSocialBinding
import com.example.da2_appdocbao.databinding.FragmentWorldBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragmentWorld.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentWorld : Fragment(R.layout.fragment_world) {
    private lateinit var binding : FragmentWorldBinding

    private lateinit var listNewFeeds: List<ItemFeed>
    private lateinit var adapterNewFeeds: adapterNewFeeds
    private lateinit var rssFetcher: RssFetcher

    private lateinit var listAllFeeds: List<ItemFeed>
    private lateinit var adapterAllFeeds: adapterAllFeeds
    val url = "https://dantri.com.vn/rss/the-gioi.rss"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorldBinding.inflate(layoutInflater)
        rssFetcher = RssFetcher()
        show_newfeeds()
        show_allfeeds()
        return binding.root
    }


    private fun show_newfeeds() {
        viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main)
        {
            if (isAdded) {
                val rssFeed = rssFetcher.fetchRSS(url)
                if (rssFeed != null) {
                    listNewFeeds = rssFeed.channel?.list!!
                    if (listNewFeeds != null) {
                        binding.recycleNewfeeds.layoutManager =
                            LinearLayoutManager(requireContext())
                        adapterNewFeeds = adapterNewFeeds(requireContext(),listNewFeeds)
                        binding.recycleNewfeeds.adapter = adapterNewFeeds
                    }
                } else {
                    make_notification("Không thể tải Data ")
                }
            }else{
                // xử lý khi fragment chưa được gắn
                make_notification("Không thể tải Data ")

            }
        }
    }

    private fun show_allfeeds(){
        viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
            val rssFeed = rssFetcher.fetchRSS(url)
            withContext(Dispatchers.Main) {
                if (isAdded) {
                    if (rssFeed != null) {
                        val list = rssFeed.channel?.list!!
                        if (list != null) {
                            binding.recycleAllfeeds.layoutManager =
                                LinearLayoutManager(requireContext())
                            listAllFeeds = list.subList(10, list.size)
                            adapterAllFeeds = adapterAllFeeds(requireContext(),listAllFeeds)
                            binding.recycleAllfeeds.adapter = adapterAllFeeds
                        }
                    } else {
                        make_notification("Không thể tải Data ")
                    }
                }else{
                    // xử lý khi fragment chưa được gắn
                    make_notification("Không thể tải Data ")

                }
            }

        }
    }
    private fun make_notification(string: String){
        val snackbar = Snackbar.make(binding.txtInfo, string , Toast.LENGTH_SHORT).show()
    }

}