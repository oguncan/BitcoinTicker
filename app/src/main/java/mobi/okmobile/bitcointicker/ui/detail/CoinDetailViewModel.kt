package mobi.okmobile.bitcointicker.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import mobi.okmobile.bitcointicker.api.Result
import mobi.okmobile.bitcointicker.api.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mobi.okmobile.bitcointicker.data.local.database.CoinsListEntity
import mobi.okmobile.bitcointicker.data.repositories.coindetail.CoinDetailRepository
import mobi.okmobile.bitcointicker.data.repositories.coinlist.CoinsListRepository
import mobi.okmobile.bitcointicker.ui.base.BaseViewModel

class CoinDetailViewModel @ViewModelInject constructor(private val repository: CoinDetailRepository) :
    BaseViewModel() {

    val favoriteCoinsList: LiveData<List<CoinsListEntity>> = repository.favoriteCoins

    private val _favouritesEmpty = MutableLiveData<Boolean>()
    val favouritesEmpty: LiveData<Boolean> = _favouritesEmpty

    private val _favouriteStock = MutableLiveData<CoinsListEntity>()
    val favouriteStock: LiveData<CoinsListEntity> = _favouriteStock

    fun isFavouritesEmpty(empty: Boolean) = _favouritesEmpty.postValue(empty)

    fun projectBySymbol(symbol: String) = repository.projectBySymbol(symbol)

    private val _dataError = MutableLiveData<Boolean>()
    val dataError: LiveData<Boolean> = _dataError

    private val _historicalData = MutableLiveData<List<DoubleArray>>()
    val historicalData: LiveData<List<DoubleArray>> = _historicalData

    fun historicalData(symbol: String?, days: Int) {
        symbol?.let {
            days.let {
                viewModelScope.launch(Dispatchers.IO) {
                    _isLoading.postValue(true)
                    val result = repository.historicalPrice(symbol, it)
                    _isLoading.postValue(false)

                    when (result) {
                        is Result.Success -> {
                            _historicalData.postValue(result.data.prices)
                            _dataError.postValue(false)
                        }
                        is Result.Error -> _dataError.postValue(true)
                    }
                }
            }
        }
    }

    fun updateFavouriteStatus(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.updateFavouriteStatus(symbol)) {
                is Result.Success -> _favouriteStock.postValue(result.data!!)
                is Result.Error -> _toastError.postValue(result.message)
            }
        }
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