package com.mobile.giku.view.ui.analysis

import android.Manifest
import android.content.pm.PackageManager
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

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { entry ->
            when (entry.key) {
                Manifest.permission.CAMERA -> {
                    if (entry.value) {
                        Toast.makeText(requireContext(), "Camera Permission Granted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Camera Permission Denied", Toast.LENGTH_SHORT).show()
                    }
                }
                Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    if (entry.value) {
                        Toast.makeText(requireContext(), "Gallery Permission Granted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Gallery Permission Denied", Toast.LENGTH_SHORT).show()
                    }
                }
            }
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

        binding.galleryCard.setOnClickListener {
            checkAndRequestPermissionsForGallery()
        }

        binding.cameraCard.setOnClickListener {
            checkAndRequestPermissionsForCamera()
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
            openCamera()
        } else {
            requestPermissionsLauncher.launch(arrayOf(cameraPermission))
        }
    }

    private fun openGallery() {
        Toast.makeText(requireContext(), "Opening Gallery...", Toast.LENGTH_SHORT).show()
    }

    private fun openCamera() {
        Toast.makeText(requireContext(), "Opening Camera...", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.cameraFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
