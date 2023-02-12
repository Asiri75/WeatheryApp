package com.libertytech.weatheryapp.ui.citieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.libertytech.core.data.local.model.CityEntity

class CityListAdapter(
    private val cityClickListener: ((Any) -> Unit)
) : RecyclerView.Adapter<CityListAdapter.CityViewHolder>() {

    private var cities = emptyList<CityEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false),
            cityClickListener
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.onBind(cities[position])
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun updateData(cities: List<CityEntity>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    class CityViewHolder(
        view: View,
        private val cityClickListener: (CityEntity) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view as TextView
        private var city: CityEntity? = null

        init {
            view.setOnClickListener {
                city?.let {
                    cityClickListener(it)
                }
            }
        }

        fun onBind(city: CityEntity) {
            this.city = city
            textView.text = city.name
        }
    }
}