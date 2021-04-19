package com.petr.example.mapapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.petr.example.mapapp.R
import com.petr.example.mapapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    var itemId: Long = 0
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding
    @Inject
    lateinit var detailViewModelFactory: DetailViewModel.AssistedFactory
    private val detailViewModel: DetailViewModel by viewModels {
        DetailViewModel.provideFactory(detailViewModelFactory, itemId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentDetailBinding>(inflater, R.layout.fragment_detail, container, false).apply {

            viewModel = detailViewModel
            lifecycleOwner = viewLifecycleOwner

            fabEditButton.setOnClickListener {
                val action = DetailFragmentDirections.actionNavigationDetailToNavigationEdit(itemId)
                findNavController().navigate(action)

            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

}