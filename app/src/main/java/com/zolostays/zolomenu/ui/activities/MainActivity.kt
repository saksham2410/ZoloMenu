package com.zolostays.zolomenu.ui.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zolostays.zolomenu.App
import com.zolostays.zolomenu.R
import com.zolostays.zolomenu.db.models.Kitchen
import com.zolostays.zolomenu.db.vm.KitchenVM
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private val myCalendar by lazy { Calendar.getInstance() }
    private val sp by lazy { PreferenceManager.getDefaultSharedPreferences(this) }
    private val model by lazy { ViewModelProvider.AndroidViewModelFactory(App.instance).create(KitchenVM::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!isUserLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }

        init()
        initDatePicker()
        initSpinners()

    }

    private fun init() {

        submitData.setOnClickListener {

            val myFormat = "EEEE dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val date = sdf.format(myCalendar.time)

            val mealType = spinnerMealType.selectedItem.toString()
            val property = spinnerProperty.selectedItem.toString()

            model.secondPostRequest(date, mealType, property)

            Intent(this, DataActivity::class.java).apply {
                putExtra("date", myCalendar.timeInMillis)
                putExtra("mealType", mealType)
                putExtra("property", property)
                startActivity(this)
            }
        }
    }

    private fun initDatePicker() {

        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "EEEE dd/MM/yyyy" //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            editTextDate.setText(sdf.format(myCalendar.time))
        }

        editTextDate.setOnClickListener {
            DatePickerDialog(
                this@MainActivity,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun initSpinners() {
        ArrayAdapter.createFromResource(this, R.array.meal_type, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMealType.adapter = adapter
        }

        model.getKitchenList().observe(this, Observer<List<Kitchen>> { list ->
            progress.hide()
            val array = ArrayList<String>()
            for (kitchen in list) {
                array.add(kitchen.LOCALNAME)
            }
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array).also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerProperty.adapter = it
            }
        })


    }

    private fun isUserLoggedIn(): Boolean {
        return sp.getBoolean("is-logged-in", true)
    }

    // http://localhost:3000/Kitchen_menu/userdatacity --> get request for item_name
    // http://localhost:3000/vessel_data/userdata1 --> to get the vessel id to auto populate in vessel spinner
    // http://localhost:3000/meal_analysis/insert --> to finally insert date, meal_type, type_of_entry (dummy data), kitchen, property (dummy data), form-type (select from form spinner-> dispatch, wastage, received), vessel_id
}
