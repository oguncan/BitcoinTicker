package mobi.okmobile.bitcointicker.data.repositories.coindetail

import androidx.lifecycle.LiveData
import mobi.okmobile.bitcointicker.api.ApiInterface
import mobi.okmobile.bitcointicker.api.BaseRemoteDataSource
import mobi.okmobile.bitcointicker.api.models.HistoricalPriceResponse
import mobi.okmobile.bitcointicker.data.local.database.CoinsDatabase
import mobi.okmobile.bitcointicker.data.local.database.CoinsListEntity
import mobi.okmobile.bitcointicker.api.Result
import javax.inject.Inject

class CoinDetailRemoteDataSource @Inject constructor(
    private val service: ApiInterface,
    private val db: CoinsDatabase
) : BaseRemoteDataSource() {

    val favoriteCoins: LiveData<List<CoinsListEntity>> = db.coinsListDao().favouriteCoins()

    suspend fun favouriteSymbols(): List<String> = db.coinsListDao().favouriteSymbols()

    suspend fun updateFavouriteStatus(symbol: String): CoinsListEntity? {
        val project = db.coinsListDao().projectFromSymbol(symbol)
        project?.let {
            val coinsListEntity = CoinsListEntity(
                it.symbol,
                it.id,
                it.name,
                it.price,
                it.changePercent,
                it.image,
                it.isFavourite.not()
            )

            if (db.coinsListDao().updateCoinsListEntity(coinsListEntity) > 0) {
                return coinsListEntity
            }
        }
        return null
    }

    /**
     * fetch historical price from backend
     */
    suspend fun historicalPrice(
        symbol: String,
        days: Int,
        targetCurrency: String
    ): Result<HistoricalPriceResponse> =
        getResult { service.historicalPrice(symbol, targetCurrency, days) }
}