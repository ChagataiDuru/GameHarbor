package com.ozyegin.project
import androidx.navigation.NavController

enum class FragmentName{
    Favorites,Search,Main,Profile
}

class MainFragmentDirections(private val navController: NavController) {

    fun navigateToDestination(from: FragmentName, to: FragmentName) {
        when (from) {
            FragmentName.Favorites -> navigateFromFavorites(to)
            FragmentName.Search -> navigateFromSearch(to)
            FragmentName.Profile -> navigateFromProfile(to)
            FragmentName.Main -> navigateFromMain(to)
        }
    }

    private fun navigateFromFavorites(to: FragmentName) {
        when (to) {
            FragmentName.Favorites -> println("Already In Favorites")
            FragmentName.Search -> navController.navigate(R.id.action_favoritesFragment_to_searchFragment)
            FragmentName.Main -> navController.navigate(R.id.action_favoritesFragment_to_mainFragment)
            FragmentName.Profile -> navController.navigate(R.id.action_favoritesFragment_to_profileFragment)
        }
    }

    private fun navigateFromSearch(to: FragmentName) {
        when (to) {
            FragmentName.Search -> println("Already in Search")
            FragmentName.Favorites -> navController.navigate(R.id.action_searchFragment_to_favoritesFragment)
            FragmentName.Main -> navController.navigate(R.id.action_searchFragment_to_mainFragment)
            FragmentName.Profile -> navController.navigate(R.id.action_searchFragment_to_profileFragment)
        }
    }

    private fun navigateFromMain(to: FragmentName) {
        when (to) {
            FragmentName.Main -> println("Already in Main")
            FragmentName.Favorites -> navController.navigate(R.id.action_mainFragment_to_favoritesFragment)
            FragmentName.Search -> navController.navigate(R.id.action_mainFragment_to_searchFragment)
            FragmentName.Profile -> navController.navigate(R.id.action_mainFragment_to_profileFragment)
        }
    }

    private fun navigateFromProfile(to: FragmentName) {
        when (to) {
            FragmentName.Profile -> println("Already in Profile")
            FragmentName.Favorites -> navController.navigate(R.id.action_profileFragment_to_favoritesFragment)
            FragmentName.Search -> navController.navigate(R.id.action_profileFragment_to_searchFragment)
            FragmentName.Main -> navController.navigate(R.id.action_profileFragment_to_mainFragment)
        }
    }

}
