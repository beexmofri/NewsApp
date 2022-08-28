package com.test.newsapp.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase
import com.test.newsapp.data.NewsModel

// defining database entities
@Database(entities = [NewsModel::class],version = 1,exportSchema = false)
abstract class NewsDatabase :RoomDatabase() {
// checks if database is created, if not a database will be created
    companion object
    {
        private var newsDatabase: NewsDatabase?=null

        fun getSavedItems(context: Context) : NewsDatabase
        {
            if (newsDatabase ==null)
                newsDatabase =Room.databaseBuilder(context, NewsDatabase::class.java,"NewsDB").allowMainThreadQueries().build()
            return newsDatabase!!
        }
    }
// allows access to NewsDao
    abstract fun getNewsDao(): NewsDao
}
