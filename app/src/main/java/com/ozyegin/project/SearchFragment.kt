package com.ozyegin.project

import GameListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozyegin.project.viewmodels.SearchViewModel
class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        val searchView = view.findViewById<SearchView>(R.id.search_view)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchGames(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchGames(newText.orEmpty())
                return false
            }
        })

        val adapter = GameListAdapter(emptyList())

        viewModel.searchResults.observe(viewLifecycleOwner) { results ->
            adapter.games = results
            recyclerView = view.findViewById(R.id.game_list)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
            adapter.notifyItemInserted(results.size)
        }
    }

}
