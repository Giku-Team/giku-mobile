package com.mobile.giku.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.giku.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.giku.databinding.FragmentHomeBinding
import com.mobile.giku.view.adapter.CalendarAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val today = LocalDate.now()
    private val weekDates = mutableListOf<LocalDate>().apply {
        repeat(7) { add(today.plusDays(it.toLong())) }
    }
    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("MMMM, yyyy", Locale.getDefault())
    private val currentDate: String = dateFormat.format(calendar.time)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCalendarRecyclerView()
        setupIconsListener()
    }

    private fun setupCalendarRecyclerView() {
        val adapter = CalendarAdapter(weekDates)
        binding.mtrlCalendarDaySelectorFrame.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
        binding.textMonthAndYearCalendar.text = currentDate
    }

    private fun setupIconsListener() {
        binding.iconChildProfile.setOnClickListener {

        }
        binding.iconAnalysis.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_analysisFragment)
        }
        binding.iconArticles.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}