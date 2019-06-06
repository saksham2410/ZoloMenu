package com.zolostays.zolomenu.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zolostays.zolomenu.db.models.Kitchen
import io.reactivex.Observable

@Dao
abstract class KitchensDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(kitchen: Kitchen)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveAll(list: List<Kitchen>)

    @Query("SELECT * FROM Kitchen WHERE LOCALNAME = :name")
    abstract fun get(name: String): Observable<Kitchen>

    @Query("SELECT * FROM Kitchen")
    abstract fun getAll(): LiveData<List<Kitchen>>

    @Query("DELETE FROM Kitchen")
    abstract fun deleteALl()
}