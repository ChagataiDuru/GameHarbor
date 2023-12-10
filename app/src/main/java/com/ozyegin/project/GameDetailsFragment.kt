package com.ozyegin.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ozyegin.project.viewmodels.GameDetailsViewModel

class GameDetailsFragment : Fragment() {

    private lateinit var viewModel: GameDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(GameDetailsViewModel::class.java)

        val gameId = arguments?.getLong("gameId") ?: return

        //viewModel.loadGame(gameId)

        observeLiveData(view)
    }

    private fun observeLiveData(view: View) {

            // Update favorite button state future

    }
}
