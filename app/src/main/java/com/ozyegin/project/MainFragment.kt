package com.ozyegin.project

import com.ozyegin.project.adapters.GameListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozyegin.project.data.GameList as GameListEntity
import com.ozyegin.project.viewmodels.MainViewModel

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GameListAdapter
    private lateinit var viewModel: MainViewModel
    private val games = mutableListOf<GameListEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Error getting trending games
        val getTrendingGamesError = Observer<Exception> {
            (childFragmentManager.findFragmentByTag("progress") as? DialogFragment)?.dismiss()

            Toast.makeText(
                requireContext(),
                requireActivity().getString(R.string.error_unknown),
                Toast.LENGTH_SHORT
            ).show()
        }

        // Success getting trending games
        val getTrendingGamesSuccessful = Observer<List<GameListEntity>> {
            setRecycleViewList(it)
            (childFragmentManager.findFragmentByTag("progress") as? DialogFragment)?.dismiss()
        }

        // Observe the liveData of the viewModel
        viewModel.getTrendingGamesError.observe(this, getTrendingGamesError)
        viewModel.getTrendingGamesSuccessful.observe(this, getTrendingGamesSuccessful)
    }

    private fun setRecycleViewList(it: List<GameListEntity>) {
        games.clear()
        games.addAll(it)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view)
        adapter = GameListAdapter(games)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}

