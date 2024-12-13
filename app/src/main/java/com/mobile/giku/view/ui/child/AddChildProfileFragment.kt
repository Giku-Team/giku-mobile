package com.mobile.giku.view.ui.child

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.giku.R
import com.mobile.giku.databinding.FragmentAddChildProfileBinding
import com.mobile.giku.model.remote.child.AddChildRequest
import com.mobile.giku.model.remote.child.Allergy
import com.mobile.giku.viewmodel.child.ChildProfileViewModel
import com.mobile.giku.viewmodel.state.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddChildProfileFragment : Fragment() {

    private var _binding: FragmentAddChildProfileBinding? = null
    private val viewModel: ChildProfileViewModel by viewModel()
    private val binding get() = _binding!!
    private var isMaleSelected = false
    private var isFemaleSelected = false
    private var isBloodTypeASelected = false
    private var isBloodTypeBSelected = false
    private var isBloodTypeABSelected = false
    private var isBloodTypeOSelected = false
    private var selectedBloodType: String? = null
    private var selectedImageUri: Uri? = null
    private var photoFile: File? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            try {
                val inputStream = requireContext().contentResolver.openInputStream(it)
                photoFile = createTempImageFile()
                inputStream?.use { input ->
                    FileOutputStream(photoFile).use { output ->
                        input.copyTo(output)
                    }
                }
                // Update the preview
                binding.previewImageView.setImageURI(Uri.fromFile(photoFile))
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error processing image: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

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

        setupUI()
        observeViewModelStates()
        observeUserId()
    }

    private fun setupUI() {
        binding.etDateOfBirth.setOnClickListener { showDatePicker() }
        setupGenderSelection()
        setupBloodTypeSelection()
        setupPhotoUpload()
        binding.btnSubmit.setOnClickListener { submitChildProfile() }
    }

    private fun setupGenderSelection() {
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

    private fun setupBloodTypeSelection() {
        binding.btnBloodTypeA.setOnClickListener { selectBloodType("A") }
        binding.btnBloodTypeB.setOnClickListener { selectBloodType("B") }
        binding.btnBloodTypeAB.setOnClickListener { selectBloodType("AB") }
        binding.btnBloodTypeO.setOnClickListener { selectBloodType("O") }
    }

    private fun selectBloodType(type: String) {
        resetBloodTypeStates()
        when (type) {
            "A" -> isBloodTypeASelected = true
            "B" -> isBloodTypeBSelected = true
            "AB" -> isBloodTypeABSelected = true
            "O" -> isBloodTypeOSelected = true
        }
        selectedBloodType = type
        setBloodTypeButtonState()
    }

    private fun setupPhotoUpload() {
        binding.btnUploadPhoto.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    private fun submitChildProfile() {
        val name = binding.etName.text.toString().trim()
        val dateOfBirth = binding.etDateOfBirth.text.toString().trim()
        val weight = binding.etWeight.text.toString().toDoubleOrNull()
        val height = binding.etHeight.text.toString().toDoubleOrNull()
        val fatherHeight = binding.etFatherHeight.text.toString().toDoubleOrNull()
        val motherHeight = binding.etMotherHeight.text.toString().toDoubleOrNull()
        val allergiesText = binding.etAllergies.text.toString().trim()

        if (validateInputs(name, dateOfBirth, weight, height, fatherHeight, motherHeight)) {
            val allergies = allergiesText.split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .map { Allergy(it, "Unknown", "Unknown") }

            val childRequest = AddChildRequest(
                name = name,
                userId = viewModel.userId.value.toString(),
                dateOfBirth = dateOfBirth,
                gender = if (isMaleSelected) 0 else 1,
                weight = weight ?: 0.0,
                height = height ?: 0.0,
                bloodType = selectedBloodType ?: "",
                fatherHeight = fatherHeight ?: 0.0,
                motherHeight = motherHeight ?: 0.0,
                allergies = null,
                photo = photoFile?.absolutePath
            )

            viewModel.addChildProfile(childRequest)
        }
    }

    private fun validateInputs(
        name: String,
        dateOfBirth: String,
        weight: Double?,
        height: Double?,
        fatherHeight: Double?,
        motherHeight: Double?
    ): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.etName.error = "Name is required"
            isValid = false
        }

        if (dateOfBirth.isEmpty()) {
            binding.etDateOfBirth.error = "Date of Birth is required"
            isValid = false
        }

        if (weight == null) {
            binding.etWeight.error = "Weight is required"
            isValid = false
        }

        if (height == null) {
            binding.etHeight.error = "Height is required"
            isValid = false
        }

        if (fatherHeight == null) {
            binding.etFatherHeight.error = "Father's Height is required"
            isValid = false
        }

        if (motherHeight == null) {
            binding.etMotherHeight.error = "Mother's Height is required"
            isValid = false
        }

        if (!isMaleSelected && !isFemaleSelected) {
            Toast.makeText(requireContext(), "Please select gender", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (selectedBloodType.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please select blood type", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun observeViewModelStates() {
        viewModel.addChildState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.btnSubmit.isEnabled = false
                }

                is UIState.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Child profile added successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnSubmit.isEnabled = true
                    findNavController().navigate(R.id.action_addChildProfileFragment_to_childProfileFragment)
                }

                is UIState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    binding.btnSubmit.isEnabled = true
                }

                UIState.Idle -> TODO()
            }
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
            backgroundTintList = resources.getColorStateList(
                if (isMaleSelected) R.color.md_theme_primary else R.color.md_theme_gray,
                null
            )
            setTextColor(
                resources.getColor(
                    if (isMaleSelected) R.color.md_theme_onPrimary else R.color.md_theme_scrim,
                    null
                )
            )
        }

        binding.btnFemaleGender.apply {
            backgroundTintList = resources.getColorStateList(
                if (isFemaleSelected) R.color.md_theme_primary else R.color.md_theme_gray,
                null
            )
            setTextColor(
                resources.getColor(
                    if (isFemaleSelected) R.color.md_theme_onPrimary else R.color.md_theme_scrim,
                    null
                )
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
        val buttons = listOf(
            binding.btnBloodTypeA to isBloodTypeASelected,
            binding.btnBloodTypeB to isBloodTypeBSelected,
            binding.btnBloodTypeAB to isBloodTypeABSelected,
            binding.btnBloodTypeO to isBloodTypeOSelected
        )

        buttons.forEach { (button, isSelected) ->
            button.apply {
                backgroundTintList = resources.getColorStateList(
                    if (isSelected) R.color.md_theme_primary else R.color.md_theme_gray,
                    null
                )
                setTextColor(
                    resources.getColor(
                        if (isSelected) R.color.md_theme_onPrimary else R.color.md_theme_scrim,
                        null
                    )
                )
            }
        }
    }

    private fun observeUserId() {
        viewModel.userId.observe(viewLifecycleOwner) { userId ->
            if (userId == null) {
                // Handle the case when userId is null
            }
        }
    }

    private fun createTempImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        return File.createTempFile(
            imageFileName,
            ".jpg",
            requireContext().cacheDir
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}