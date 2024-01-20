package com.ozyegin.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.squareup.picasso.Picasso
import com.ozyegin.project.databinding.DetailsFragmentBinding
import com.ozyegin.project.data.GameDetail as GameDetailEntity
import com.ozyegin.project.LoadingDialog as LoadingDialog
import com.ozyegin.project.viewmodels.GameDetailsViewModel

class GameDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = GameDetailsFragment()
    }

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: GameDetailsViewModel by viewModels()

    /**
     * Function launched with the creation of the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Function launched when the fragment is started
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            viewModel.gameId = bundle.getString("gameId", "")
        }

        val getGameDetailSuccessful = Observer<GameDetailEntity> {
            setGameData()
            (childFragmentManager.findFragmentByTag("progress") as? DialogFragment)?.dismiss()
        }

        val getGameDetailError = Observer<Exception> {
            (childFragmentManager.findFragmentByTag("progress") as? DialogFragment)?.dismiss()
            Toast.makeText(
                requireContext(),
                requireActivity().getString(R.string.error_unknown),
                Toast.LENGTH_SHORT
            ).show()
            requireActivity().onBackPressed()
        }

        viewModel.gameDetails.observe(this, getGameDetailSuccessful)
        viewModel.getGameDetailError.observe(this, getGameDetailError)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            LoadingDialog().show(childFragmentManager, "progress")
            viewModel.getGameDetail()
        } else {
            setGameData()
        }

        binding.btnAddList.setOnClickListener {

        }
    }

    private fun setGameData() {
        binding.gameName.text = viewModel.gameDetails.value!!.name
        binding.gameRate.text = viewModel.gameDetails.value!!.rating.toString()
        binding.textGameDate.text = viewModel.gameDetails.value!!.firstReleaseDate
        binding.textGameGenres.text = viewModel.gameDetails.value!!.genres
        binding.textGamePlatforms.text = viewModel.gameDetails.value!!.platforms

        if (viewModel.gameDetails.value!!.summary.length > 1) {
            binding.description.text = viewModel.gameDetails.value!!.summary
        } else {
            binding.description.text = viewModel.gameDetails.value!!.storyline
        }

        Picasso.get()
            .load(imageBuilder(viewModel.gameDetails.value!!.imageId, ImageSize.COVER_BIG, ImageType.PNG))
            .error(R.drawable.ic_baseline_no_image_24)
            .into(binding.gameCoverImage)

        if (viewModel.gameDetails.value!!.artworksIds.isNotEmpty()) {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT
            )
            params.setMargins(5, 0, 5, 0)

            val horizontalScrollLinearLayout = binding.horizontalScrollLinearLayout
            viewModel.gameDetails.value!!.artworksIds.forEachIndexed { _, image ->
                val imageView = layoutInflater.inflate(R.layout.carousel_image, null) as ImageView
                Picasso.get()
                    .load(imageBuilder(image, ImageSize.COVER_BIG, ImageType.PNG))
                    .error(R.drawable.ic_baseline_no_image_24)
                    .into(imageView)
                imageView.layoutParams = params
                horizontalScrollLinearLayout.addView(imageView)
            }

        } else {
            binding.textNoImages.visibility = View.VISIBLE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}