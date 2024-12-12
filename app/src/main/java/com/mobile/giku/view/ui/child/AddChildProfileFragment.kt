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
import java.util.Calendar
import java.util.Locale

class AddChildProfileFragment : Fragment() {

    private var _binding: FragmentAddChildProfileBinding? = null
    private val binding get() = _binding!!
    private var isMaleSelected = false
    private var isFemaleSelected = false
    private var isBloodTypeASelected = false
    private var isBloodTypeBSelected = false
    private var isBloodTypeABSelected = false
    private var isBloodTypeOSelected = false

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
        setBloodTypeButtonState()

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

        binding.btnBloodTypeA.setOnClickListener {
            resetBloodTypeStates()
            isBloodTypeASelected = true
            setBloodTypeButtonState()
        }

        binding.btnBloodTypeB.setOnClickListener {
            resetBloodTypeStates()
            isBloodTypeBSelected = true
            setBloodTypeButtonState()
        }

        binding.btnBloodTypeAB.setOnClickListener {
            resetBloodTypeStates()
            isBloodTypeABSelected = true
            setBloodTypeButtonState()
        }

        binding.btnBloodTypeO.setOnClickListener {
            resetBloodTypeStates()
            isBloodTypeOSelected = true
            setBloodTypeButtonState()
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
        var genderValue = -1

        binding.btnMaleGender.apply {
            backgroundTintList = if (isMaleSelected) {
                genderValue = 0
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
                genderValue = 1
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

    private fun resetBloodTypeStates() {
        isBloodTypeASelected = false
        isBloodTypeBSelected = false
        isBloodTypeABSelected = false
        isBloodTypeOSelected = false
    }

    private fun setBloodTypeButtonState() {
        binding.btnBloodTypeA.apply {
            backgroundTintList = if (isBloodTypeASelected) {
                resources.getColorStateList(R.color.md_theme_primary, null)
            } else {
                resources.getColorStateList(R.color.md_theme_gray, null)
            }
            setTextColor(
                if (isBloodTypeASelected) {
                    resources.getColor(R.color.md_theme_onPrimary, null)
                } else {
                    resources.getColor(R.color.md_theme_scrim, null)
                }
            )
        }

        binding.btnBloodTypeB.apply {
            backgroundTintList = if (isBloodTypeBSelected) {
                resources.getColorStateList(R.color.md_theme_primary, null)
            } else {
                resources.getColorStateList(R.color.md_theme_gray, null)
            }
            setTextColor(
                if (isBloodTypeBSelected) {
                    resources.getColor(R.color.md_theme_onPrimary, null)
                } else {
                    resources.getColor(R.color.md_theme_scrim, null)
                }
            )
        }

        binding.btnBloodTypeAB.apply {
            backgroundTintList = if (isBloodTypeABSelected) {
                resources.getColorStateList(R.color.md_theme_primary, null)
            } else {
                resources.getColorStateList(R.color.md_theme_gray, null)
            }
            setTextColor(
                if (isBloodTypeABSelected) {
                    resources.getColor(R.color.md_theme_onPrimary, null)
                } else {
                    resources.getColor(R.color.md_theme_scrim, null)
                }
            )
        }

        binding.btnBloodTypeO.apply {
            backgroundTintList = if (isBloodTypeOSelected) {
                resources.getColorStateList(R.color.md_theme_primary, null)
            } else {
                resources.getColorStateList(R.color.md_theme_gray, null)
            }
            setTextColor(
                if (isBloodTypeOSelected) {
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