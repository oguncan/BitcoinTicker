package mobi.okmobile.bitcointicker.ui.home.favorites

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mobi.okmobile.bitcointicker.data.local.database.CoinsListEntity
import mobi.okmobile.bitcointicker.data.repositories.UserRepository
import mobi.okmobile.bitcointicker.data.repositories.favorites.FavoritesRepository
import mobi.okmobile.bitcointicker.ui.base.BaseViewModel

class FavoritesViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    private val repository: FavoritesRepository
) :
    BaseViewModel() {

    val favoriteCoinsList: LiveData<List<CoinsListEntity>> = repository.favoriteCoins

    private val _favouriteStock = MutableLiveData<CoinsListEntity>()
    val favouriteStock: LiveData<CoinsListEntity> = _favouriteStock

    private val _favouritesEmpty = MutableLiveData<Boolean>()
    val favouritesEmpty: LiveData<Boolean> = _favouritesEmpty

    fun isFavouritesEmpty(empty: Boolean) {
        _favouritesEmpty.postValue(empty)
    }

    fun loadCoinsFromApi(targetCur: String = "usd") {
        if (repository.loadData()) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                repository.coinsList(targetCur)
                _isLoading.postValue(false)
            }
        }
    }

    fun logout(view: View) {
        userRepository.logout()
    }

}