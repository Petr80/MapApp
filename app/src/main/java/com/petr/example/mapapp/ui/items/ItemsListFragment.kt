package com.petr.example.mapapp.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.petr.example.mapapp.R
import com.petr.example.mapapp.databinding.FragmentItemsListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsListFragment : Fragment() {

    private lateinit var binding: FragmentItemsListBinding
    private val itemsViewModel: ItemsListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentItemsListBinding.inflate(inflater, container, false)
        val listAdapter = ItemsListAdapter()
        binding.itemsList.adapter = listAdapter
        subscribeUi(listAdapter, binding)

        return binding.root
    }

    private fun subscribeUi(adapter: ItemsListAdapter, binding: FragmentItemsListBinding) {
        itemsViewModel.items.observe(viewLifecycleOwner) { result ->
            binding.hasItem = !result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_edit)
        }
    }




}