package c5_database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import c4_rss.ItemFeed

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "itemFeedDatabase"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "itemFeeds"
        const val ID = "_id"
        const val TITLE = "_title"
        const val PUB_DATE = "_pubDate"
        const val LINK = "_link"
        const val GUID = "_guid"
        const val DESCRIPTION = "_description"
        const val CREATOR = "_creator"
        const val CATEGORY = "_category"



    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            " CREATE TABLE $TABLE_NAME (\n" +
                    "                $ID INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                    "                $TITLE TEXT, \n" +
                    "                $PUB_DATE TEXT, \n" +
                    "                $LINK TEXT, \n" +
                    "                $GUID TEXT, \n" +
                    "                $DESCRIPTION TEXT,\n" +
                    "                $CREATOR TEXT, \n" +
                    "                $CATEGORY TEXT \n" + // Đã xóa dòng $LIKE INTEGER
                    "            )"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Xử lý nâng cấp database nếu cần
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }

    suspend  fun insertItemFeed(itemFeed: ItemFeed) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(TITLE, itemFeed.title)
            put(PUB_DATE, itemFeed.pubDate)
            put(LINK, itemFeed.link)
            put(GUID, itemFeed.guid)
            put(DESCRIPTION, itemFeed.description)
            put(CREATOR, itemFeed.creator)
            put(CATEGORY, itemFeed.category)

        }
        db.insert(TABLE_NAME, null, values)
    }

  suspend  fun updateItemFeed(itemFeed: ItemFeed): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(TITLE, itemFeed.title)
            put(PUB_DATE, itemFeed.pubDate)
            put(LINK, itemFeed.link)
            put(GUID, itemFeed.guid)
            put(DESCRIPTION, itemFeed.description)
            put(CREATOR, itemFeed.creator)
            put(CATEGORY, itemFeed.category)

        }
        return db.update(TABLE_NAME, values, "$ID = ?", arrayOf(itemFeed.id.toString()))
    }

    suspend  fun deleteItemFeed(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$ID = ?", arrayOf(id.toString()))
    }

    suspend fun getAllItemFeeds(): List<ItemFeed> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val itemFeeds = mutableListOf<ItemFeed>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ID))
                val title = getString(getColumnIndexOrThrow(TITLE))
                val pubDate = getString(getColumnIndexOrThrow(PUB_DATE))
                val link = getString(getColumnIndexOrThrow(LINK))
                val guid = getString(getColumnIndexOrThrow(GUID))
                val description = getString(getColumnIndexOrThrow(DESCRIPTION))
                val creator = getString(getColumnIndexOrThrow(CREATOR))
                val category = getString(getColumnIndexOrThrow(CATEGORY))

                itemFeeds.add(
                    ItemFeed(
                        id,
                        title,
                        pubDate,
                        link,
                        guid,
                        description,
                        creator,
                        category,

                    )
                )
            }
        }
        cursor.close()
        return itemFeeds
    }
}