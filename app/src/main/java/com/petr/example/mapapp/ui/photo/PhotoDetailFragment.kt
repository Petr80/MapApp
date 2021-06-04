package com.petr.example.mapapp.ui.photo

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.petr.example.mapapp.R
import com.petr.example.mapapp.databinding.FragmentPhotoDetailBinding
import com.petr.example.mapapp.ui.common.hideUI
import com.petr.example.mapapp.ui.common.showUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
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
    private var visible: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoDetailBinding.inflate(inflater, container, false).apply {
            viewModel = photoDetailViewModel
            lifecycleOwner = viewLifecycleOwner

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.toolbarPhotoDetail.inflateMenu(R.menu.main_menu)
//        binding.toolbarPhotoDetail.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.action_settings -> {
//                    // Navigate to settings screen
//                    true
//                }
//                R.id.action_about -> {
//                    // Save profile changes
//                    true
//                }
//                else -> false
//            }
//        }
//


        binding.photoDetail.setOnClickListener {
            visible = if (visible) {
                activity?.hideUI()
                false
            } else {
                activity?.showUI()
                true
            }
        }

        binding.photoDetail.setOnLongClickListener {
            (activity as? AppCompatActivity)?.supportActionBar?.hide()
            activity?.startActionMode(ActionModeCallback())
            true
        }
    }


    fun uploadImage(fileUri : Uri){

        // zmensit velikost bitmapy

        // vytvorit tmp file z bitmapy

        // compressovat do JPEG / PNG

        // vratit hotovy file
    }


    inner class ActionModeCallback : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.getItemId()) {
                R.id.action_delete -> {

                    return true
                }
            }
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mode?.getMenuInflater()
            inflater?.inflate(R.menu.custom_main_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menu?.findItem(R.id.action_delete)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            (activity as? AppCompatActivity)?.supportActionBar?.show()
        }
    }
}