package com.petr.example.mapapp.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.petr.example.mapapp.data.Item
import com.petr.example.mapapp.databinding.FragmentEditBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditFragment : Fragment() {

    private val args: EditFragmentArgs by navArgs()
    private lateinit var binding: FragmentEditBinding
    @Inject
    lateinit var editViewModelFactory: EditViewModel.AssistedFactory
    private val editViewModel: EditViewModel by viewModels {
        EditViewModel.provideFactory(editViewModelFactory, args.itemId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditBinding.inflate(inflater, container, false).apply {
            viewModel = editViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var item: Item? = null
        val editState = if (args.itemId > 0) EditState.EXISTING_ITEM else EditState.NEW_ITEM
        if (editState == EditState.EXISTING_ITEM) {
            editViewModel.getItem(args.itemId).observe(viewLifecycleOwner) {
                item = it
            }
        }

        binding.fabSave.setOnClickListener {
            editViewModel.saveItem(
                item?.itemId ?: 0,
                43.12233,
                58.87867676,
                binding.editText1.editText?.text.toString(),
                binding.editText2.editText?.text.toString(),
                binding.editText3.editText?.text.toString(),
                binding.editRoll1.editText?.text.toString(),
                binding.editRoll2.editText?.text.toString(),
                imageUrl1 = "https://upload.wikimedia.org/wikipedia/commons/5/55/Apple_orchard_in_Tasmania.jpg"
            )
        }
        binding.imageDialogClose.setOnClickListener {
            Toast.makeText(requireContext(), "Action closed", Toast.LENGTH_SHORT).show()
        }
    }
}