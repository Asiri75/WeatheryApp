package com.libertytech.weatheryapp.ui.citieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.libertytech.weatheryapp.model.City

class CityListAdapter(
    private val cityClickListener: ((City) -> Unit)
) : RecyclerView.Adapter<CityListAdapter.CityViewHolder>() {

    private var cities = emptyList<City>()

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

    fun updateData(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    class CityViewHolder(
        view: View,
        private val cityClickListener: (City) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view as TextView
        private var city: City? = null

        init {
            view.setOnClickListener {
                city?.let {
                    cityClickListener(it)
                }
            }
        }

        fun onBind(city: City) {
            this.city = city
            textView.text = city.name
        }
    }
}