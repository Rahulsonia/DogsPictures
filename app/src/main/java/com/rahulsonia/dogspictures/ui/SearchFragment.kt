package com.rahulsonia.dogspictures.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rahulsonia.dogspictures.R
import com.rahulsonia.dogspictures.databinding.FragmentSearchBinding
import com.rahulsonia.dogspictures.model.BreedList
import com.rahulsonia.dogspictures.model.BreedListItem
import com.rahulsonia.dogspictures.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val TAG = "SearchFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchBinding.bind(view)

        lateinit var breeds: ArrayList<BreedListItem>
        val viewModel by viewModels<GalleryViewModel>()
        val adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line)
        binding.apply {

            listView.adapter = adapter

            listView.setOnItemClickListener { _, _, i, _ ->
                Log.d(TAG, "onViewCreated: ${breeds[i].name}")
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToGalleryFragment(
                        breeds[i].id
                    )
                )
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if (p0 != null) {
                        adapter.clear()
                        viewLifecycleOwner.lifecycleScope.launch {
                            binding.progressBarSearch.isVisible = true
                            val arrayOfNames = ArrayList<String>()
                            breeds = viewModel.getBreedsByName(p0)
                            for (breed in breeds as BreedList) {
                                arrayOfNames.add(breed.name)
                            }
                            adapter.addAll(arrayOfNames)
                            adapter.notifyDataSetChanged()
                            binding.progressBarSearch.isVisible = false
                        }
                    }
                    return true
                }

            })
        }
    }
}