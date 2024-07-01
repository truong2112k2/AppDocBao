package c1_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import b_adapter.adapterAllFeeds
import b_adapter.adapterSaveFeeds
import c4_rss.ItemFeed
import c5_database.Database
import com.example.da2_appdocbao.R
import com.example.da2_appdocbao.databinding.FragmentSaveNewsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragmentSaveNews.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentSaveNews : Fragment(R.layout.fragment_save_news) {
    private  var _binding : FragmentSaveNewsBinding?= null
     private val binding get() = _binding!!
    private lateinit var list: List<ItemFeed>
    private lateinit var adapterAllFeeds: adapterSaveFeeds
    private lateinit var database : Database
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveNewsBinding.inflate(inflater, container, false)
        database = Database(requireContext())
        show_data()
        return binding.root
    }

    private fun show_data() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            if( isAdded){
                binding.recycleview.layoutManager = LinearLayoutManager(requireContext())
                list = database.getAllItemFeeds()
                adapterAllFeeds = adapterSaveFeeds(requireContext(),list)
            }
            withContext(Dispatchers.Main){
                if(list.size != 0){
                    binding.recycleview.adapter = adapterAllFeeds
                }else{
                     binding.recycleview.visibility = View.GONE
                    binding.txt.visibility = View.VISIBLE
                }
            }
        }
    }


}