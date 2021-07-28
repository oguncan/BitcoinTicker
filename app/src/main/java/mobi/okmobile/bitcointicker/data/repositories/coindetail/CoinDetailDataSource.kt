package mobi.okmobile.bitcointicker.data.repositories.coindetail

import mobi.okmobile.bitcointicker.data.local.database.CoinsDatabase
import javax.inject.Inject

class CoinDetailDataSource @Inject constructor(private val db: CoinsDatabase) {

    fun projectBySymbol(symbol: String) = db.coinsListDao().projectLiveDataFromSymbol(symbol)
}