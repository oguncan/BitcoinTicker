package mobi.okmobile.bitcointicker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mobi.okmobile.bitcointicker.data.repositories.UserRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}