package com.mobile.giku.view.ui.analysis

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.giku.R
import com.mobile.giku.databinding.FragmentAnalysisBinding
import com.mobile.giku.viewmodel.analysis.AnalysisViewModel
import com.mobile.giku.viewmodel.state.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class AnalysisFragment : Fragment() {

    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnalysisViewModel by viewModel()
    private var _selectedImageFile: File? = null

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                handleSelectedImage(it)
            } ?: Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
        }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            findNavController().navigate(R.id.action_analysisFragment_to_cameraFragment)
        } else {
            Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clearNutritionResponse()

        val capturedImagePath = arguments?.getString("capturedImagePath")

        capturedImagePath?.let {
            val imageUri = Uri.parse(it)
            handleSelectedImage(imageUri)
        }

        binding.galleryCard.setOnClickListener {
            checkAndRequestPermissionsForGallery()
        }

        binding.cameraCard.setOnClickListener {
            checkAndRequestPermissionsForCamera()
        }

        binding.analyzeButton.setOnClickListener {
            val selectedFile = _selectedImageFile
            if (selectedFile == null || !selectedFile.exists()) {
                Toast.makeText(requireContext(), "Please select an image first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.analyze(selectedFile)
        }

        viewModel.analysisState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }
                is UIState.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    findNavController().navigate(R.id.action_analysisFragment_to_analysisDetailsFragment)
                }
                is UIState.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    binding.progressIndicator.visibility = View.GONE
                }
            }
        }
    }

    private fun handleSelectedImage(uri: Uri) {
        binding.previewImageView.setImageURI(uri)
        _selectedImageFile = uriToFile(uri)
    }

    private fun uriToFile(uri: Uri): File? {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        inputStream?.use { input ->
            val file = File(requireContext().cacheDir, "selected_image.jpg")
            file.outputStream().use { output ->
                input.copyTo(output)
            }
            return file
        }
        return null
    }

    private fun checkAndRequestPermissionsForGallery() {
        val permissions = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        val missingPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isEmpty()) {
            openGallery()
        } else {
            requestPermissionsLauncher.launch(missingPermissions.toTypedArray())
        }
    }

    private fun checkAndRequestPermissionsForCamera() {
        val cameraPermission = Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(requireContext(), cameraPermission) == PackageManager.PERMISSION_GRANTED) {
            findNavController().navigate(R.id.action_analysisFragment_to_cameraFragment)
        } else {
            cameraPermissionLauncher.launch(cameraPermission)
        }
    }

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { entry ->
            when (entry.key) {
                Manifest.permission.CAMERA -> {
                    if (entry.value) {
                        findNavController().navigate(R.id.action_analysisFragment_to_cameraFragment)
                    } else {
                        Toast.makeText(requireContext(), "Camera Permission Denied", Toast.LENGTH_SHORT).show()
                    }
                }
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_MEDIA_IMAGES -> {
                    if (entry.value) {
                        openGallery()
                    } else {
                        Toast.makeText(requireContext(), "Gallery Permission Denied", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}