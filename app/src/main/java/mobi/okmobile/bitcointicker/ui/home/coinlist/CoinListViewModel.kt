package mobi.okmobile.bitcointicker.ui.home.coinlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mobi.okmobile.bitcointicker.data.repositories.coinlist.CoinsListRepository
import mobi.okmobile.bitcointicker.ui.base.BaseViewModel

class CoinListViewModel @ViewModelInject constructor(private val repository: CoinsListRepository) :
    BaseViewModel() {

    val coinsListData = repository.allCoinsLD


    fun isListEmpty(): Boolean {
        return coinsListData.value?.isEmpty() ?: true
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

}