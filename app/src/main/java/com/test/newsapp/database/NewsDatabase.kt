package com.test.newsapp.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase
import com.test.newsapp.data.NewsModel

@Database(entities = [NewsModel::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    companion object {
        private var newsDatabase: NewsDatabase? = null

        fun getSavedItems(context: Context): NewsDatabase {
            if (newsDatabase == null)
                newsDatabase = Room.databaseBuilder(context, NewsDatabase::class.java, "NewsDB")
                    .allowMainThreadQueries().build()
            return newsDatabase!!
        }
    }

    abstract fun getNewsDao(): NewsDao
}
