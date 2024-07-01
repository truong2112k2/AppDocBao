package c4_rss

import retrofit2.http.GET
import retrofit2.http.Url

interface RssService {
    @GET
    suspend fun getDataFromRss(@Url Url: String): RssFeed
}