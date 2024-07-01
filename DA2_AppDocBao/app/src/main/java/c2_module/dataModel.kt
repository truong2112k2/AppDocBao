package c2_module

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import c4_rss.ItemFeed

class dataModel: ViewModel() {
    private var _linkImage = MutableLiveData<String>()
    val linkImage : LiveData<String> get() =  _linkImage

    private var _link = MutableLiveData<String>()
    val link: LiveData<String> get() = _link

    private var _date = MutableLiveData<String>()
    val date : LiveData<String> get() = _date

    private var _title = MutableLiveData<String>()
    val title : LiveData<String> get() = _title


    private var _id = MutableLiveData<String>()
    val id : LiveData<String> get() = _id




    // create first value
    init {
        _title.value = "null"
        _link.value = "null"
        _linkImage.value = "null"
        _date.value = "null"
        _id.value = "null"


    }

    // set value for linkImage, link, date, title
    fun setContent(linkImage: String,
                   link: String,
                   date: String,
                   title: String,
                   id: String,
                   ){
        _linkImage.value = linkImage
        _link.value = link
        _date.value = date
        _title.value = title
        _id.value = id

    }



}