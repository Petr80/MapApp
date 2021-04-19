package com.petr.example.mapapp.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
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

}