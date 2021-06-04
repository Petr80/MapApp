package com.petr.example.mapapp.ui.photo

import android.app.Activity
import android.app.Activity.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.petr.example.mapapp.R
import com.petr.example.mapapp.databinding.FragmentPhotoListBinding
import com.petr.example.mapapp.ui.photo.PhotoDetailViewModel.Companion.provideFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PhotoListFragment : Fragment() {

    private lateinit var binding: FragmentPhotoListBinding
    private val photoListViewModel: PhotoListViewModel by viewModels()

    private lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var currentPhotoPath: String
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        val photoListAdapter = PhotoListAdapter()
        binding.photoList.adapter = photoListAdapter

        subscribeUi(photoListAdapter, binding)

        // Botoom Sheet Dialog
        bottomSheetDialog = BottomSheetDialog(this.requireContext(), R.style.BottomSheetTheme)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, bottom_sheet)
        bottomSheetDialog.setContentView(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fabPhoto.setOnClickListener {
            dispatchTakePictureIntent()
            photoListViewModel.savePhoto(currentPhotoPath)
        }
    }


    private fun subscribeUi(adapter: PhotoListAdapter, binding: FragmentPhotoListBinding) {
        photoListViewModel.photos.observe(viewLifecycleOwner) { result ->
            binding.hasPhotos = !result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.petr.example.mapapp.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "MyPet_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//          val takenImage = data?.extras?.get("data") as Bitmap
/*            val takenImage = BitmapFactory.decodeFile(photoPath)
            capture_image.setImageBitmap(takenImage)*/
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
