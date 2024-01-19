package com.ozyegin.project
import androidx.navigation.NavController

enum class FragmentName{
    Favorites,Search,Main,Login,Signup,Profile
}

class MainFragmentDirections(private val navController: NavController) {

    fun navigateToDestination(from: FragmentName, to: FragmentName) {
        when (from) {
            FragmentName.Favorites -> navigateFromFavorites(to)
            FragmentName.Search -> navigateFromSearch(to)
            FragmentName.Profile -> navigateFromProfile(to)
            FragmentName.Main -> navigateFromMain(to)
            FragmentName.Signup -> navigateFromSignup(to)
            FragmentName.Login -> navigateFromLogin(to)
        }
    }

    private fun navigateFromFavorites(to: FragmentName) {
        when (to) {
            FragmentName.Favorites -> println("Already In Favorites")
            FragmentName.Search -> navController.navigate(R.id.action_favoritesFragment_to_searchFragment)
            FragmentName.Main -> navController.navigate(R.id.action_favoritesFragment_to_mainFragment)
            FragmentName.Profile -> navController.navigate(R.id.action_favoritesFragment_to_profileFragment)
            FragmentName.Login -> {}
            FragmentName.Signup -> {}

        }
    }

    private fun navigateFromSearch(to: FragmentName) {
        when (to) {
            FragmentName.Search -> println("Already in Search")
            FragmentName.Favorites -> navController.navigate(R.id.action_searchFragment_to_favoritesFragment)
            FragmentName.Main -> navController.navigate(R.id.action_searchFragment_to_mainFragment)
            FragmentName.Profile -> navController.navigate(R.id.action_searchFragment_to_profileFragment)
            FragmentName.Login -> {}
            FragmentName.Signup -> {}

        }
    }

    private fun navigateFromMain(to: FragmentName) {
        when (to) {
            FragmentName.Main -> println("Already in Main")
            FragmentName.Favorites -> navController.navigate(R.id.action_mainFragment_to_favoritesFragment)
            FragmentName.Search -> navController.navigate(R.id.action_mainFragment_to_searchFragment)
            FragmentName.Profile -> navController.navigate(R.id.action_mainFragment_to_profileFragment)
            FragmentName.Signup -> {}
            FragmentName.Login -> {}
        }
    }

    private fun navigateFromLogin(to: FragmentName) {
        when (to) {
            FragmentName.Login -> println("Already in Login")
            FragmentName.Signup -> navController.navigate(R.id.action_loginFragment_to_signupFragment)
            FragmentName.Favorites -> {}
            FragmentName.Search -> {}
            FragmentName.Main -> {}
            FragmentName.Profile -> {}
        }
    }

    private fun navigateFromSignup(to: FragmentName) {
        when (to) {
            FragmentName.Signup -> println("Already in Signup")
            FragmentName.Login -> navController.navigate(R.id.action_signupFragment_to_loginFragment)
            FragmentName.Main -> navController.navigate(R.id.action_signupFragment_to_mainFragment)
            FragmentName.Favorites -> {}
            FragmentName.Search -> {}
            FragmentName.Profile -> {}
        }
    }

    private fun navigateFromProfile(to: FragmentName) {
        when (to) {
            FragmentName.Profile -> println("Already in Profile")
            FragmentName.Favorites -> navController.navigate(R.id.action_profileFragment_to_favoritesFragment)
            FragmentName.Search -> navController.navigate(R.id.action_profileFragment_to_searchFragment)
            FragmentName.Main -> navController.navigate(R.id.action_profileFragment_to_mainFragment)
            FragmentName.Signup -> {}
            FragmentName.Login -> {}

        }
    }

}
