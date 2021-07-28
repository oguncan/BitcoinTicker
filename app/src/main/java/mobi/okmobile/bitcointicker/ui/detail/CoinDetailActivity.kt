package mobi.okmobile.bitcointicker.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import mobi.okmobile.bitcointicker.R
@AndroidEntryPoint
class CoinDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
    }
}