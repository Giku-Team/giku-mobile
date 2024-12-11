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

class AnalysisFragment : Fragment() {

    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding!!

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.previewImageView.setImageURI(it)
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

        val capturedImagePath = arguments?.getString("capturedImagePath")

        capturedImagePath?.let {
            val imageUri = Uri.parse(it)
            binding.previewImageView.setImageURI(imageUri)
        }

        binding.galleryCard.setOnClickListener {
            checkAndRequestPermissionsForGallery()
        }

        binding.cameraCard.setOnClickListener {
            checkAndRequestPermissionsForCamera()
        }

        binding.analyzeButton.setOnClickListener {
            Toast.makeText(requireContext(), "Analysis started", Toast.LENGTH_SHORT).show()
        }
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