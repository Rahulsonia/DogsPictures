package com.rahulsonia.dogspictures.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.rahulsonia.dogspictures.R
import com.rahulsonia.dogspictures.adapter.GalleryAdapter
import com.rahulsonia.dogspictures.databinding.FragmentGalleryBinding
import com.rahulsonia.dogspictures.model.ImageListItem
import com.rahulsonia.dogspictures.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private val TAG = "GalleryFragment"
    private val galleryViewModel by viewModels<GalleryViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGalleryBinding.bind(view)
        var breedId = ""
        val args = navArgs<GalleryFragmentArgs>()

        if (args.value.breedId != 0) {
            breedId = args.value.breedId.toString()
        }


        val adapter = GalleryAdapter(object : GalleryAdapter.OnItemClickListener {
            override fun onItemClick(imageListItem: ImageListItem) {
                Log.d(TAG, "onItemClick: ")
                findNavController().navigate(GalleryFragmentDirections.actionGalleryFragmentToImageFragment(imageListItem.url))
            }

        })
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {

            galleryViewModel.getImages(breedId).collect {
                adapter.submitData(it)
            }

        }
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            }
        }
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.gallery_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search_menu) {
            findNavController().navigate(GalleryFragmentDirections.actionGalleryFragmentToSearchFragment())
        }
        return true
    }

}
