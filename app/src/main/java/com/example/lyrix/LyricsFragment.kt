package com.example.lyrix

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lyrix.repository.ApiRepository
import com.example.lyrix.viewModel.MainViewModelFactory
import com.example.lyrix.viewModel.RetrofitViewModel
import kotlinx.android.synthetic.main.fragment_lyrics.view.*

class LyricsFragment : Fragment() {
    private val args by navArgs<LyricsFragmentArgs>()
    private lateinit var mViewModel: RetrofitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lyrics, container, false)
        setupBackPressed()
        setupTitle(view)
        setupViewModel(view)
        return view
    }

    private fun setupTitle(view: View) {
        view.trackName.text = args.trackData.track_name
        view.artistName.text = args.trackData.artist_name
    }

    private fun setupBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_lyricsFragment_to_mainFragment)
        }
    }

    private fun setupViewModel(view: View) {
        val commontrackId = args.trackData.commontrack_id
        val repository = ApiRepository(requireContext())
        val viewModelFactory = MainViewModelFactory(repository)
        mViewModel = ViewModelProvider(this, viewModelFactory).get(RetrofitViewModel::class.java)
        mViewModel.getLyrics(commontrackId)
        mViewModel.lyrics.observe(viewLifecycleOwner) { response ->
            view.lyrics_body.text = response
        }
    }
}