package mobi.okmobile.bitcointicker.api

import mobi.okmobile.bitcointicker.api.models.Coin
import mobi.okmobile.bitcointicker.api.models.HistoricalPriceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET(EndPoints.COINS_LIST)
    suspend fun coinsList(@Query("vs_currency") targetCurrency: String): Response<List<Coin>>

    @GET(EndPoints.COINS)
    suspend fun historicalPrice(
        @Path("id") id: String,
        @Query("vs_currency") targetCurrency: String,
        @Query("days") days: Int
    ): Response<HistoricalPriceResponse>

}