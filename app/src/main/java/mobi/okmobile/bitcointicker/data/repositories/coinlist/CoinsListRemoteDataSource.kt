package mobi.okmobile.bitcointicker.data.repositories.coinlist

import javax.inject.Inject
import mobi.okmobile.bitcointicker.api.ApiInterface
import mobi.okmobile.bitcointicker.api.BaseRemoteDataSource
import mobi.okmobile.bitcointicker.api.Result
import mobi.okmobile.bitcointicker.api.models.Coin

class CoinsListRemoteDataSource @Inject constructor(private val service: ApiInterface) :
    BaseRemoteDataSource() {

    suspend fun coinsList(targetCur: String): Result<List<Coin>> =
        getResult {
            service.coinsList(targetCur)
        }
}