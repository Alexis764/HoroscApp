package com.example.horoscapp.ui.palmistry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.example.horoscapp.databinding.FragmentPalmistryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PalmistryFragment : Fragment() {

    //ViewBinding
    private var _binding: FragmentPalmistryBinding? = null
    private val binding get() = _binding!!

    //Constants
    companion object {
        const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
    }

    //Permissions
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startCamera()
        } else {
            Toast.makeText(requireContext(), "Acepta los permisos", Toast.LENGTH_LONG).show()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }


    private fun initUi() {
        if (checkCameraPermission()) {
            startCamera()
        } else {
            requestPermissionLauncher.launch(CAMERA_PERMISSION)
        }
    }


    private fun checkCameraPermission(): Boolean {
        return PermissionChecker.checkSelfPermission(
            requireContext(),
            CAMERA_PERMISSION
        ) == PermissionChecker.PERMISSION_GRANTED
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview
                .Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)

            } catch (e: Exception) {
                Log.e("CameraError", "Error: ${e.message}")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPalmistryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}