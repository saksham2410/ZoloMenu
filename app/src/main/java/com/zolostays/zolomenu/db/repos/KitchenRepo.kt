package com.zolostays.zolomenu.db.repos

import android.util.Log
import androidx.lifecycle.LiveData
import com.zolostays.zolomenu.db.dao.KitchensDao
import com.zolostays.zolomenu.db.db
import com.zolostays.zolomenu.db.models.Kitchen
import com.zolostays.zolomenu.http.APIClient
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class KitchenRepo {

    private val kitchensDao: KitchensDao by lazy { db.kitchensDao() }

    fun getKitchenList(): LiveData<List<Kitchen>> {
        refreshKitchenList()
        return kitchensDao.getAll()
    }

    private fun refreshKitchenList(): Disposable? {

        return APIClient.getClient()
            .getKitchenList()
            .subscribeOn(Schedulers.newThread())
            // .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe({
                Log.d("suthar", "Kitchen List ${it.size}")
                kitchensDao.deleteALl()
                kitchensDao.saveAll(it)
            }, {
                Log.d("suthar", "Error:  $it")
                // Toast.makeText(App.instance, "Something went wrong!", Toast.LENGTH_LONG).show()
            })
    }

    fun secondPostRequest(date: String, mealType: String, property: String): Disposable? {
        return APIClient.getClient()
            .createUser(date, mealType, property)
            .subscribeOn(Schedulers.newThread())
            // .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe({
                Log.d("suthar", "Kitchen Response $it")
            }, {
                Log.d("suthar", "Error:  $it")
                // Toast.makeText(App.instance, "Something went wrong!", Toast.LENGTH_LONG).show()
            })
    }
}