package a_screen

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import c2_module.dataModel
import c4_rss.ItemFeed
import c5_database.Database
import com.example.da2_appdocbao.R
import com.example.da2_appdocbao.databinding.ActivityScreenDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class ScreenDetail : AppCompatActivity() {

    private lateinit var binding: ActivityScreenDetailBinding
    private lateinit var animation: Animation
    private lateinit var item: ItemFeed
    private lateinit var database: Database


    private val dataModel: dataModel by viewModels()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenDetailBinding.inflate(layoutInflater) // viewbinding
        setContentView(binding.root)
        database = Database(this@ScreenDetail)
        animation = AnimationUtils.loadAnimation(this@ScreenDetail, R.anim.animation_button) // animation

        item = intent.getParcelableExtra<ItemFeed>("item")!! // item from last activity

        getDataFromLastActivity()
        shareAndSave()
        back()



    }

    private fun back() {
        binding.btnBack.setOnClickListener {
            binding.btnBack.startAnimation(animation)
            val i = Intent(this@ScreenDetail, ScreenHome :: class.java)
            startActivity(i)
            finish()
        }
    }

    private fun shareAndSave() {
        // share link
        binding.btnShare.setOnClickListener{
            binding.btnShare.startAnimation(animation)
            shareLink(dataModel.link.value.toString())
        }


        // save item
        binding.checkbox.setOnCheckedChangeListener{ _, it->
            if(it){
                // when user click checkbox, alertdialog appear
            val alertDialog = AlertDialog.Builder(this@ScreenDetail)
                .apply {
                    setTitle("Bạn muốn lưu bài viết này?")
                    // when user choose Xác nhận -> a item is add to database
                    setPositiveButton("[Xác nhận]"){ dialogInterface: DialogInterface, i: Int ->
                         save_a_feed()

                    }
                    // when user choose Xác nhận -> a item is not add to database and cancel alert dialog
                    setNegativeButton("[Hủy]"){ dialogInterface: DialogInterface, i: Int ->
                        make_a_notification("Bài viết chưa được lưu!")
                        binding.checkbox.isChecked = false
                    }
                        .show()
                }
            }
        }


    }

    private fun save_a_feed() {
          CoroutineScope(Dispatchers.IO).launch {
              database.insertItemFeed(item)
              withContext(Dispatchers.Main){
                 make_a_notification("Bài viết đã được lưu!")

              }

          }

    }

    private fun make_a_notification(string: String) { // show a notification
        val snackbar = Snackbar.make(this@ScreenDetail,binding.txtTitle,string, Toast.LENGTH_SHORT).show()
    }

    private fun shareLink(link: String) {

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, link)
        }
        startActivity(Intent.createChooser(intent, "Chia sẻ bài viết"))
    }

    private fun getDataFromLastActivity() {
        dataModel.setContent(
            intent.getStringExtra("linkImage").toString(),
            intent.getStringExtra("link").toString(),
            intent.getStringExtra("date").toString(),
            intent.getStringExtra("title").toString(),
            "null"
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


    // get data from a web by HTML
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

//    override fun onDestroy() {
//        super.onDestroy()
//        if( dataModel.likeOrNot.value == 1){ // thực hiện việc thêm data ở đây
//            Toast.makeText(this@ScreenDetail, "1", Toast.LENGTH_SHORT).show()
//
//
//        }else{
//            Toast.makeText(this@ScreenDetail, "0", Toast.LENGTH_SHORT).show()
//
//        }
//    }

}