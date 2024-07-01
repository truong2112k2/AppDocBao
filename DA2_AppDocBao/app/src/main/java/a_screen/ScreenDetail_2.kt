package a_screen

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import c2_module.dataModel
import c5_database.Database
import com.example.da2_appdocbao.databinding.ActivityScreenDetail2Binding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class ScreenDetail_2 : AppCompatActivity() {
    private lateinit var binding : ActivityScreenDetail2Binding
    private lateinit var database: Database
    private val dataModel: dataModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenDetail2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Database(this@ScreenDetail_2)
        getDataFromLastActivity()
        delete_aFeed()
        back()




    }

    private fun back() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun delete_aFeed() {
        binding.btnDelete.setOnClickListener {
            delete()

        }
    }

    private fun delete() {


        val alert_dialog = AlertDialog.Builder(this@ScreenDetail_2)
            .apply {
                setTitle("Bạn muốn xóa tin này ?")
                    .setPositiveButton("[Xác nhận]"){ dialogInterface: DialogInterface, i: Int ->
                        CoroutineScope(Dispatchers.IO).launch {
                           val kq= database.deleteItemFeed(intent.getStringExtra("id").toString().toInt())
                            withContext(Dispatchers.Main) {
                                if( kq > 0 ){
                                    Toast.makeText(this@ScreenDetail_2, "Xóa thành công!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }else{
                                    Toast.makeText(this@ScreenDetail_2, "Không thể xóa tin này!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                    .setNegativeButton("[Hủy]"){ dialogInterface: DialogInterface, i: Int ->
                            dialogInterface.dismiss()
                    }
            }
            .show()





    }


    private fun getDataFromLastActivity() {
        dataModel.setContent(
            intent.getStringExtra("linkImage").toString(),
            intent.getStringExtra("link").toString(),
            intent.getStringExtra("date").toString(),
            intent.getStringExtra("title").toString(),
            intent.getStringExtra("id").toString()
        )

        show_image(dataModel.linkImage.value.toString())
        show_title(dataModel.title.value.toString())
        show_date(dataModel.date.value.toString())
        show_content(dataModel.link.value.toString())
    }

    private fun show_content(link: String) {
        if( link != null ){
            CoroutineScope(Dispatchers.IO).launch {
                val content=  getContentFromHTML(link)
                withContext(Dispatchers.Main){
                    binding.txtContent.text = Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT)
                }
            }
        }

    }


    @SuppressLint("SetTextI18n")
    private fun show_date(date: String) { // hiển thị ngày
        binding.txtDate.text = "Ngày đăng : ${date}"
    }
    private fun show_title(title: String) { // hiển thị tiêu đề
        binding.txtTitle.text = title
    }
    private fun show_image(linkImage: String) {   // hiển thị ảnh lên imageview
        Picasso.get().load(linkImage).into(binding.imgShowImg)
    }


    private suspend fun getContentFromHTML(link: String): String {
        return withContext(Dispatchers.IO) {
            val document: Document = Jsoup.connect(link).get()
            val paragraphs = document.select("p")  // get data from each <p> card
            val content = StringBuilder() // create a string to save content get form HTML
            for (paragraph in paragraphs) { // duplicate text and join them(duyệt qua văn bản và nối chúng lại)
                content.append(paragraph.html()).append("<br/><br/>")
                // append : text concatenation method(phương thức nối văn bản)
                // "<br/><br/>" both are add space in content
            }
            content.toString()
            // convert content to String
        }
    }

}