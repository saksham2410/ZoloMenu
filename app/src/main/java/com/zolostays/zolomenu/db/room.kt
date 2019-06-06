package com.zolostays.zolomenu.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zolostays.zolomenu.App
import com.zolostays.zolomenu.db.dao.KitchensDao
import com.zolostays.zolomenu.db.models.Kitchen

@Database(
    entities = [Kitchen::class],
    version = 1,
    exportSchema = false
)


// @TypeConverters(EventListConverter::class, WorkshopConverter::class, UserProfileConverter::class)
abstract class MyDatabase : RoomDatabase() {
    // @TypeConverters(EventListConverter::class)
    // abstract fun eventCategoryDao(): EventCategoryDao

    abstract fun kitchensDao(): KitchensDao
}

val db = Room.databaseBuilder(App.instance, MyDatabase::class.java, "database")
    .allowMainThreadQueries()
    .fallbackToDestructiveMigration()
    .build()