package com.ozyegin.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozyegin.project.data.GameList as GameListEntity
import com.ozyegin.project.viewmodels.MainViewModel
import com.ozyegin.project.databinding.FragmentMainBinding
import com.ozyegin.project.adapters.GameListAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
class MainFragment : Fragment() {

    // ViewBinding
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            LoadingDialog().show(childFragmentManager, "progress")
            println("Loading games")
            viewModel.getTrendingGames()
        }

        val getTrendingGamesError = Observer<Exception> {
            (childFragmentManager.findFragmentByTag("progress") as? DialogFragment)?.dismiss()
            println(it.message)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycleViewList(emptyList())
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.getTrendingGamesSuccessful.value != null) {
            setRecycleViewList(viewModel.getTrendingGamesSuccessful.value!!)
            (childFragmentManager.findFragmentByTag("progress") as? DialogFragment)?.dismiss()
        }
    }


    private fun setRecycleViewList(gamesList: List<GameListEntity>) {
        val mAdapter = GameListAdapter(gamesList)
        val mLayoutManager = LinearLayoutManager(requireContext())

        mAdapter.setViewItemInterface(object : GameListAdapter.RecyclerViewGameInterface {
            override fun onItemClick(gameListEntity: GameListEntity) {
                val fragment = GameDetailsFragment()

                val arguments = Bundle()
                arguments.putString("gameId", gameListEntity.id)

                fragment.arguments = arguments
                val fragmentTransaction: FragmentTransaction =
                    activity!!.supportFragmentManager.beginTransaction()

                fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        })

        binding.recyclerView.layoutManager = mLayoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}