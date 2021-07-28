package mobi.okmobile.bitcointicker.data.repositories.coindetail

import androidx.lifecycle.LiveData
import mobi.okmobile.bitcointicker.data.local.database.CoinsListEntity
import mobi.okmobile.bitcointicker.data.local.preferences.PreferenceStorage
import mobi.okmobile.bitcointicker.data.repositories.coinlist.CoinsListDataSource
import mobi.okmobile.bitcointicker.data.repositories.coinlist.CoinsListRemoteDataSource
import java.util.*
import mobi.okmobile.bitcointicker.api.Result
import mobi.okmobile.bitcointicker.api.succeeded
import mobi.okmobile.bitcointicker.util.Constants
import javax.inject.Inject

class CoinDetailRepository @Inject constructor(
    private val coinDetailDataSource: CoinDetailRemoteDataSource,
    private val localDataSource: CoinDetailDataSource,
    private val remoteDataSource: CoinDetailRemoteDataSource,
    private val coinsListDataSource: CoinsListDataSource,
    private val preferenceStorage: PreferenceStorage,
    private val coinsListRemoteDataSource: CoinsListRemoteDataSource
) {

    val favoriteCoins: LiveData<List<CoinsListEntity>> = coinDetailDataSource.favoriteCoins

    suspend fun favoriteSymbols(): List<String> = coinDetailDataSource.favouriteSymbols()

    fun projectBySymbol(symbol: String) = localDataSource.projectBySymbol(symbol)

    suspend fun historicalPrice(symbol: String, days: Int, targetCur: String = "usd") =
        remoteDataSource.historicalPrice(symbol, days, targetCur)

    suspend fun updateFavouriteStatus(symbol: String): Result<CoinsListEntity> {
        val result = coinsListDataSource.updateFavouriteStatus(symbol)
        return result?.let {
            Result.Success(it)
        } ?: Result.Error(Constants.GENERIC_ERROR)
    }

    fun loadData(): Boolean {
        val lastLoadedDate = preferenceStorage.timeLoadedAt
        val currentDataMillis = Date().time
        return currentDataMillis - lastLoadedDate > 10 * 1000
    }

    suspend fun coinsList(targetCur: String) {
        when (val result = coinsListRemoteDataSource.coinsList(targetCur)) {
            is Result.Success -> {
                if (result.succeeded) {
                    val favSymbols = coinsListDataSource.favouriteSymbols()

                    val customStockList = result.data.let {
                        it.filter { item -> item.symbol.isNullOrEmpty().not() }
                            .map { item ->
                                CoinsListEntity(
                                    item.symbol ?: "",
                                    item.id,
                                    item.name,
                                    item.price,
                                    item.changePercent,
                                    item.image,
                                    favSymbols.contains(item.symbol)
                                )
                            }
                    }

                    coinsListDataSource.insertCoinsIntoDB(customStockList)

                    preferenceStorage.timeLoadedAt = Date().time

                    Result.Success(true)
                } else {
                    Result.Error(Constants.GENERIC_ERROR)
                }
            }
            else -> result as Result.Error
        }
    }
}