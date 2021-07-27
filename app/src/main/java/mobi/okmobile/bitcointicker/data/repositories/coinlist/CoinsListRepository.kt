package mobi.okmobile.bitcointicker.data.repositories.coinlist

import javax.inject.Inject
import mobi.okmobile.bitcointicker.data.local.database.CoinsListEntity
import mobi.okmobile.bitcointicker.data.local.preferences.PreferenceStorage
import mobi.okmobile.bitcointicker.api.Result
import mobi.okmobile.bitcointicker.api.succeeded
import mobi.okmobile.bitcointicker.util.Constants
import java.util.*

class CoinsListRepository @Inject constructor(
    private val coinsListRemoteDataSource: CoinsListRemoteDataSource,
    private val coinsListDataSource: CoinsListDataSource,
    private val preferenceStorage: PreferenceStorage
) {
    val allCoinsLD = coinsListDataSource.allCoinsLD

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

    fun loadData(): Boolean {
        val lastLoadedDate = preferenceStorage.timeLoadedAt
        val currentDataMillis = Date().time
        return currentDataMillis - lastLoadedDate > 10 * 1000
    }
}