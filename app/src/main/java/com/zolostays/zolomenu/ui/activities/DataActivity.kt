package com.zolostays.zolomenu.ui.activities

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zolostays.zolomenu.App
import com.zolostays.zolomenu.R
import com.zolostays.zolomenu.db.models.Item
import com.zolostays.zolomenu.db.models.Vessel
import com.zolostays.zolomenu.db.vm.KitchenVM
import com.zolostays.zolomenu.http.APIClient
import com.zolostays.zolomenu.ui.adapter.DataAdapter
import kotlinx.android.synthetic.main.activity_data.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class DataActivity : AppCompatActivity() {

    private val sp by lazy { PreferenceManager.getDefaultSharedPreferences(this) }
    private val model by lazy { ViewModelProvider.AndroidViewModelFactory(App.instance).create(KitchenVM::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        loadData()
    }

    private fun loadData() {

        doAsync {

            Log.d("suthar", "Okay ANKO")

            val itemStr = URL("${APIClient.BASE_URL}Kitchen_menu/userdatacity").readText()
            val vesselsStr = URL("${APIClient.BASE_URL}vessel_data/userdata1").readText()

            val items = Gson().fromJson<List<Item>>(itemStr, object : TypeToken<List<Item>>() {}.type)
            val vessels = Gson().fromJson<List<Vessel>>(vesselsStr, object : TypeToken<List<Vessel>>() {}.type)

            Log.d("suthar", "$itemStr, $vesselsStr, ${items.size}, ${vessels.size}")

            uiThread {
                val adapter = DataAdapter(this@DataActivity, items, vessels)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = GridLayoutManager(this@DataActivity, 1)

                submit.setOnClickListener {
                    postData(items, vessels)
                }
            }
        }
    }

    private fun postData(items: List<Item>, vessels: List<Vessel>) {


        val timeInMillis = intent.getLongExtra("date", 0)
        val mealType = intent.getStringExtra("mealType")
        val property = intent.getStringExtra("property")

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        val myFormat = "EEEE dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val date = sdf.format(calendar.time)

        for (i in 0..items.size) {
            val holder = (recyclerView.findViewHolderForAdapterPosition(i) as DataAdapter.MyViewHolder?) ?: continue
            val weight = holder.mealWeight.text?.toString()
            val spinnerFormType = holder.spinnerFormType.selectedItem.toString()
            val spinnerVesselType = holder.spinnerVesselType.selectedItem.toString()
            Log.d("suthar", "$weight, $spinnerFormType, $spinnerVesselType")


//            model.postDate(date, mealType, "type_of_entry", property, "property", spinnerFormType, vessels)
        }

        // http://localhost:3000/meal_analysis/insert --> to finally insert date, meal_type, type_of_entry (dummy data), kitchen, property (dummy data), form-type (select from form spinner-> dispatch, wastage, received), vessel_id

    }
}


