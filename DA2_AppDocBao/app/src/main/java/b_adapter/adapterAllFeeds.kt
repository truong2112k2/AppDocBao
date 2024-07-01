package b_adapter

import a_screen.ScreenDetail
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import c4_rss.ItemFeed
import com.example.da2_appdocbao.databinding.ItemNewfeedBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

class adapterAllFeeds(val context: Context, val list: List<ItemFeed>): RecyclerView.Adapter<adapterAllFeeds.viewHolder>() {
    inner class viewHolder(var binding : ItemNewfeedBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterAllFeeds.viewHolder {
        val binding = ItemNewfeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding.txtTitle.text = list[position].title.toString()
        val date = formatDateToVietnamese(list[position].pubDate.toString())
        holder.binding.txtDate.text = date
        Picasso.get().load(extractImageUrl(list[position].description)).into(holder.binding.imgNewFeed)

        holder.itemView.setOnClickListener {// xét sự kiện
            val intent = Intent(context, ScreenDetail::class.java)
            intent.putExtra("linkImage", extractImageUrl(list[position].description))
            intent.putExtra("link", list[position].link )
            intent.putExtra("date", date )
            intent.putExtra("title", list[position].title )
            intent.putExtra("item", list[position])
            // Bạn có thể thêm dữ liệu vào Intent nếu cần (ví dụ: URL của item)
            // intent.putExtra("itemUrl", list[position].link)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {

        return list.size
    }

    private fun extractImageUrl(description: String?): String? {
        if (description == null) return null
        val imgTagStart = description.indexOf("<img ")
        if (imgTagStart == -1) return null
        val srcStart = description.indexOf("src='", imgTagStart)
        if (srcStart == -1) return null
        val srcEnd = description.indexOf("'", srcStart + 5)
        return if (srcEnd == -1) null else description.substring(srcStart + 5, srcEnd)
    }


    fun formatDateToVietnamese(inputDateString: String): String { // chuyển định dạng ngày
        val inputDateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
        val outputDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("vi", "VN"))

        return try {
            val date = inputDateFormat.parse(inputDateString)
            outputDateFormat.format(date)
        } catch (e: Exception) {
            "Lỗi khi chuyển đổi ngày tháng: ${e.message}"
        }
    }
}