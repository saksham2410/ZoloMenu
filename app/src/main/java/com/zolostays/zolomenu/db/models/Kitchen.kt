package com.zolostays.zolomenu.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Kitchen(
    val CITY: String,
    @PrimaryKey
    val LOCALNAME: String
)