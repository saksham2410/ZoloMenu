package com.zolostays.zolomenu.db.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zolostays.zolomenu.db.models.Kitchen
import com.zolostays.zolomenu.db.repos.KitchenRepo

class KitchenVM : ViewModel() {

    private val repo by lazy { KitchenRepo() }

    fun getKitchenList(): LiveData<List<Kitchen>> {
        return repo.getKitchenList()
    }

    fun secondPostRequest(date: String, mealType: String, property: String) {
        repo.secondPostRequest(date, mealType, property)
    }

    fun getItems(){

    }
}