package com.petr.example.mapapp.ui.photo

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.petr.example.mapapp.R
import com.petr.example.mapapp.databinding.FragmentPhotoDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotoDetailFragment : Fragment() {

    val photoId: Long = 0
    val args: PhotoDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentPhotoDetailBinding
    @Inject
    lateinit var photoDetailViewModelFactory: PhotoDetailViewModel.AssistedFactory
    private val photoDetailViewModel: PhotoDetailViewModel by viewModels {
        PhotoDetailViewModel.provideFactory(photoDetailViewModelFactory, args.photoId)
    }
    private var visible: Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPhotoDetailBinding.inflate(inflater, container, false).apply {
            viewModel = photoDetailViewModel
            lifecycleOwner = viewLifecycleOwner

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarPhotoDetail.inflateMenu(R.menu.main_menu)
        binding.toolbarPhotoDetail.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> {
                    // Navigate to settings screen
                    true
                }
                R.id.action_about -> {
                    // Save profile changes
                    true
                }
                else -> false
            }
        }

        binding.toolbarPhotoDetail.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        binding.photoDetail.setOnClickListener {
            if (visible) {
                hideSystemUI()
            } else {
                showSystemUI()
            }

        }

        (activity as? AppCompatActivity)?.supportActionBar?.hide()

       /* activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        activity?.window?.statusBarColor = Color.parseColor("#3CFFFFFF")
        activity?.window?.navigationBarColor = Color.parseColor("#3CFFFFFF")*/
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        activity?.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        visible = false
/*        (activity as? AppCompatActivity)?.supportActionBar?.hide()*/
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        activity?.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        visible = true
/*        (activity as? AppCompatActivity)?.supportActionBar?.show()*/


/*        val actionBar: View = (activity as? AppCompatActivity)?.supportActionBar
        ViewCompat.setOnApplyWindowInsetsListener(actionBar) { v, insets ->
            v.updatePadding(top = insets.systemWindowInsets.top)
            // Return the insets so that they keep going down the view hierarchy
            insets
        }*/

    }

    override fun onResume() {
        super.onResume()
/*        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)*/
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        // Clear the systemUiVisibility flag
        activity?.window?.decorView?.systemUiVisibility = 0
        showSystemUI()
    }

}