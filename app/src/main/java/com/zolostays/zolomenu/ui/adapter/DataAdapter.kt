package com.zolostays.zolomenu.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zolostays.zolomenu.R
import com.zolostays.zolomenu.db.models.Item
import com.zolostays.zolomenu.db.models.Vessel

class DataAdapter(private val context: Context, private val items: List<Item>, private val vessels: List<Vessel>) :
    RecyclerView.Adapter<DataAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_view, null, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemname.text = items[position].item_name

        val array = arrayOf("Dispatch", "Received", "Wastage")
        ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, array).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            holder.spinnerFormType.adapter = it
        }

        val x = ArrayList<String>()
        for (vessel in vessels) x.add(vessel.vessel_id)

        ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, x).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            holder.spinnerVesselType.adapter = it
        }
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemname: TextView = view.findViewById(R.id.item_name)
        val spinnerFormType: Spinner = view.findViewById(R.id.spinnerFormType)
        val spinnerVesselType: Spinner = view.findViewById(R.id.spinnerVesselType)
        val mealWeight: EditText = view.findViewById(R.id.mealWeight)
    }
}