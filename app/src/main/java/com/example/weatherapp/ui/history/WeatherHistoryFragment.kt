package com.example.weatherapp.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.data.local.entity.WeatherInfoEntity
import com.example.weatherapp.databinding.FragmentWeatherHistoryBinding
import com.example.weatherapp.util.makeToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherHistoryFragment : Fragment(),DeleteListener {

    private var _binding: FragmentWeatherHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherHistoryViewModel by activityViewModels()
    private lateinit var weatherHistoryAdapter: WeatherHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherHistoryBinding.inflate(inflater, container, false)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "History"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.getHistory()
        setupRecyclerView()

        binding.deleteAll.setOnClickListener {
            viewModel.deleteAll()
        }

        viewModel.history.observe(viewLifecycleOwner, Observer {
            if(it == null){
                return@Observer
            }
            when {
                it.isLoading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                it.errorMessage != null -> {
                    binding.progressBar.visibility = View.GONE
                    makeToast(view.context,it.errorMessage.toString())
                }
                else -> {
                    binding.progressBar.visibility= View.GONE
                    val weatherInfoHistory: List<WeatherInfoEntity> = it.weatherInfoHistory!!
                    weatherHistoryAdapter.differ.submitList(weatherInfoHistory)
                }
            }

        })
    }

    override fun deleteSpecificWeatherHistoryEntry(weatherInfoEntity: WeatherInfoEntity){
        viewModel.deleteSpecific(weatherInfoEntity)
    }

    private fun setupRecyclerView(){
        weatherHistoryAdapter = WeatherHistoryAdapter(this)
        binding.rwHistory.apply {
            adapter = weatherHistoryAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}