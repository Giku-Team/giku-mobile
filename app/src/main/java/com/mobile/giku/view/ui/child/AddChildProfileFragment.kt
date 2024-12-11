package com.mobile.giku.view.ui.child

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobile.giku.R
import com.mobile.giku.databinding.FragmentAddChildProfileBinding
import java.text.SimpleDateFormat
import java.util.*

class AddChildProfileFragment : Fragment() {

    private var _binding: FragmentAddChildProfileBinding? = null
    private val binding get() = _binding!!
    private var isMaleSelected = false
    private var isFemaleSelected = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddChildProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etDateOfBirth.setOnClickListener {
            showDatePicker()
        }
        setGenderButtonState()

        binding.btnMaleGender.setOnClickListener {
            isMaleSelected = true
            isFemaleSelected = false
            setGenderButtonState()
        }

        binding.btnFemaleGender.setOnClickListener {
            isMaleSelected = false
            isFemaleSelected = true
            setGenderButtonState()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                val formattedDate = formatDate(year, monthOfYear, dayOfMonth)
                binding.etDateOfBirth.setText(formattedDate)
            },
            year, month, dayOfMonth
        )

        datePickerDialog.show()
    }

    private fun formatDate(year: Int, monthOfYear: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, monthOfYear, dayOfMonth)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun setGenderButtonState() {
        binding.btnMaleGender.apply {
            backgroundTintList = if (isMaleSelected) {
                resources.getColorStateList(R.color.md_theme_primary, null)
            } else {
                resources.getColorStateList(R.color.md_theme_gray, null)
            }
            setTextColor(
                if (isMaleSelected) {
                    resources.getColor(R.color.md_theme_onPrimary, null)
                } else {
                    resources.getColor(R.color.md_theme_scrim, null)
                }
            )
        }

        binding.btnFemaleGender.apply {
            backgroundTintList = if (isFemaleSelected) {
                resources.getColorStateList(R.color.md_theme_primary, null)
            } else {
                resources.getColorStateList(R.color.md_theme_gray, null)
            }
            setTextColor(
                if (isFemaleSelected) {
                    resources.getColor(R.color.md_theme_onPrimary, null)
                } else {
                    resources.getColor(R.color.md_theme_scrim, null)
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}