package com.example.weatherapp.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.local.entity.WeatherInfoEntity
import com.example.weatherapp.databinding.ItemWeatherBinding
import com.example.weatherapp.util.speedAbbreviation
import com.example.weatherapp.util.temperatureAbbreviation

class WeatherHistoryAdapter(private val listener: DeleteListener) : RecyclerView.Adapter<WeatherHistoryAdapter.HistoryViewHolder>() {
    inner class HistoryViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindView(weatherInfoEntity: WeatherInfoEntity) {
            binding.apply {
                historyCity.text = weatherInfoEntity.name
                historyObservationTime.text =
                    "Observation Time: ${weatherInfoEntity.observation_time}"
                historyObservedTemperature.text = "Temperature: ${weatherInfoEntity.temperature} ${
                    temperatureAbbreviation(weatherInfoEntity)
                }"
                historyObservedWindSpeed.text = "Wind Speed: ${weatherInfoEntity.wind_speed} ${
                    speedAbbreviation(weatherInfoEntity)
                }"
                btDeleteHistory.setOnClickListener {
                    listener.deleteSpecificWeatherHistoryEntry(weatherInfoEntity)
                }
            }
        }
    }


    private val differCallback = object : DiffUtil.ItemCallback<WeatherInfoEntity>() {
        override fun areItemsTheSame(
            oldItem: WeatherInfoEntity,
            newItem: WeatherInfoEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WeatherInfoEntity,
            newItem: WeatherInfoEntity
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentWeatherInfoEntity: WeatherInfoEntity = differ.currentList[position]
        holder.bindView(currentWeatherInfoEntity)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}