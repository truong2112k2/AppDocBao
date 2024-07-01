package c4_rss

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "channel", strict = false )
data class Channel @JvmOverloads constructor(
    @field: ElementList(inline = true, entry = "item") var list: List<ItemFeed>? = null

){

}
