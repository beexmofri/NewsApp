package com.test.newsapp.database

import androidx.room.*
import com.test.newsapp.data.NewsModel

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: NewsModel)

    @Query("SELECT * FROM news_table")
    fun getAllSavedNews(): List<NewsModel>

    @Query("SELECT COUNT(title) FROM news_table WHERE  title= :ti")
    fun getCount(ti: String): Int

    @Delete
    fun delete(news: NewsModel)
}

