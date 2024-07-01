package c4_rss

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed @JvmOverloads  constructor(
    @field: Element(name = "channel") var channel: Channel? = null
){

}
