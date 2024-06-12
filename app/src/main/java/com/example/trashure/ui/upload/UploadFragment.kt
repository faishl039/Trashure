package com.example.trashure.ui.upload

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.trashure.databinding.FragmentUploadBinding
import com.example.trashure.util.getImageUri
import com.example.trashure.util.reduceFileImage
import com.example.trashure.util.uriToFile

class UploadFragment : Fragment() {
    private var _binding: FragmentUploadBinding? = null
    private val viewModel: UploadVM by viewModels()
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        obsUploadTrash()
        obsLoading()

        binding.cameraButton.setOnClickListener {
            openCamera()
        }
        binding.galleryButton.setOnClickListener {
            openGallery()
        }
        binding.uploadButton.setOnClickListener {
            uploadTrash()
        }

        return root
    }

    private fun obsLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun obsUploadTrash() {
        viewModel.uploadTrash.observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d(TAG, "upload successful: ${it.message}")
                makeToast("upload successful: ${it.message}")
            } else {
                Log.e(TAG, "upload failed: No response from server")
            }
        }
    }

    private val openGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("picker galeri", "gada foto yg dipilih")
        }
    }

    private fun openGallery() {
        openGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcerIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun openCamera() {
        currentImageUri = getImageUri(requireContext())
        launcerIntentCamera.launch(currentImageUri!!)
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.photo.setImageURI(it)
        }
    }

    private fun uploadTrash() {
        currentImageUri?.let {
            val imageFile = uriToFile(it, requireContext()).reduceFileImage()
            Log.d(TAG, "show path image: ${imageFile.path}")
            viewModel.postImageTrash(imageFile)
        }?: makeToast("foto tidak ada")
    }

//    private fun moveToResult() {
//        val intent = Intent(requireContext(), UploadSuccessFragment)
//    }

    private fun makeToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    companion object {
        const val TAG = "UPLOAD FRGMNT"
    }
}