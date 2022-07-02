package com.example.lyrix

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lyrix.adapter.RcAdapter
import com.example.lyrix.repository.ApiRepository
import com.example.lyrix.repository.RoomRepository
import com.example.lyrix.room.TrackDatabase
import com.example.lyrix.viewModel.MainViewModelFactory
import com.example.lyrix.viewModel.RetrofitViewModel
import com.example.lyrix.viewModel.RoomViewModel
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var retrofitViewModel: RetrofitViewModel
    private lateinit var roomViewModel: RoomViewModel
    private lateinit var adapter: RcAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        setupViewModels()
        setupRecyclerView(view)
        setHasOptionsMenu(true)
        return view
    }

    private fun setupRecyclerView(view: View) {
        adapter = RcAdapter()
        adapter.setOnItemClickListener(object : RcAdapter.OnClickListener {
            override fun setOnItemClick(position: Int) {
                roomViewModel.addTrack(adapter.getTracks()[position])
            }
        })
        view.recyclerView.adapter = adapter
        view.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupViewModels() {
        val apiRepository = ApiRepository(requireContext())
        val roomRepository = RoomRepository(TrackDatabase.getDatabase(requireContext()).trackDao())
        val viewModelFactory = MainViewModelFactory(apiRepository)

        retrofitViewModel = ViewModelProvider(this, viewModelFactory).get(RetrofitViewModel::class.java)
        roomViewModel = RoomViewModel(roomRepository)
        roomViewModel.recentTracks.observe(viewLifecycleOwner) { list ->
            adapter.setTracks(list)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)

        val search = menu.findItem(R.id.search_menu)
        val searchView = search?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            retrofitViewModel.getSortedTracks(query)
            retrofitViewModel.trackList.observe(this) { response ->
                adapter.setTracks(response)
            }
        }
        return true
    }
}