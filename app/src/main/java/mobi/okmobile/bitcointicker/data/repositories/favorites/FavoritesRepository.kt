package mobi.okmobile.bitcointicker.data.repositories.favorites

import androidx.lifecycle.LiveData
import mobi.okmobile.bitcointicker.data.local.database.CoinsListEntity
import mobi.okmobile.bitcointicker.data.local.preferences.PreferenceStorage
import mobi.okmobile.bitcointicker.data.repositories.coinlist.CoinsListDataSource
import mobi.okmobile.bitcointicker.data.repositories.coinlist.CoinsListRemoteDataSource
import mobi.okmobile.bitcointicker.util.Constants
import mobi.okmobile.bitcointicker.api.Result
import mobi.okmobile.bitcointicker.api.succeeded
import javax.inject.Inject
import java.util.*

class FavoritesRepository @Inject constructor(
    private val favoritesDataSource: FavoritesDataSource,
    private val coinsListDataSource: CoinsListDataSource,
    private val preferenceStorage: PreferenceStorage,
    private val coinsListRemoteDataSource: CoinsListRemoteDataSource
) {

    fun loadData(): Boolean {
        val lastLoadedDate = preferenceStorage.timeLoadedAt
        val currentDataMillis = Date().time
        return currentDataMillis - lastLoadedDate > 10 * 1000
    }

    val favoriteCoins: LiveData<List<CoinsListEntity>> = favoritesDataSource.favoriteCoins

    suspend fun favoriteSymbols(): List<String> = favoritesDataSource.favouriteSymbols()

    /**
     * update favourites in local database
     */
    suspend fun updateFavouriteStatus(symbol: String): Result<CoinsListEntity> {
        val result = favoritesDataSource.updateFavouriteStatus(symbol)
        return result?.let {
            Result.Success(it)
        } ?: Result.Error(Constants.GENERIC_ERROR)
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