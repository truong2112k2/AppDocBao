package c4_rss

import android.os.Parcel
import android.os.Parcelable
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class ItemFeed @JvmOverloads constructor(
    var id: Int?= 0 ,
    @field: Element(name = "title") var title: String? = null,
    @field: Element(name = "pubDate") var pubDate: String?= null,
    @field: Element(name = "link") var link : String? = null ,
    @field: Element(name = "guid") var guid: String? = null,
    @field: Element(name ="description") var description: String?= null,
    @field: Element(name = "creator") var creator: String? = null,
    @field: Element(name ="category") var category: String? = null,

): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),

    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeValue(id) // đọc giá trị kiểu như số
        parcel.writeString(title) // đọc các text
        parcel.writeString(pubDate)
        parcel.writeString(link)
        parcel.writeString(guid)
        parcel.writeString(description)
        parcel.writeString(creator)
        parcel.writeString(category)


    }

    companion object CREATOR : Parcelable.Creator<ItemFeed> {
        override fun createFromParcel(parcel: Parcel): ItemFeed {
            return ItemFeed(parcel)
        }

        override fun newArray(size: Int): Array<ItemFeed?> {
            return arrayOfNulls(size)
        }
    }
}
