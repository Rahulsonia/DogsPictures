package com.rahulsonia.dogspictures.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rahulsonia.dogspictures.R
import com.rahulsonia.dogspictures.databinding.FragmentImageBinding

class ImageFragment : Fragment(R.layout.fragment_image) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentImageBinding.bind(view)
        val args: ImageFragmentArgs by navArgs()


        binding.apply {
            Glide.with(view).load(args.url).fitCenter().into(imageViewDialog)
            progressBarDialog.isVisible = false
        }
    }
}