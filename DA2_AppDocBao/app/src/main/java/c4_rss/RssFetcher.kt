package c4_rss

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class RssFetcher {
    val baseURL = "https://dantri.com.vn/"
   private val retrofit : Retrofit = Retrofit.Builder()
       .baseUrl(baseURL)
       .addConverterFactory(SimpleXmlConverterFactory.create())
       .build()
    private val service: RssService = retrofit.create( RssService :: class.java)

    suspend fun fetchRSS(url: String): RssFeed?{
        return withContext(Dispatchers.IO){
            try {
                service.getDataFromRss(url)
            }catch (e : Exception){
                e.printStackTrace()
                null
            }
        }
    }

}