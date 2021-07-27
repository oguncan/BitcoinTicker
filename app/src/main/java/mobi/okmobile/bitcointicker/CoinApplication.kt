package mobi.okmobile.bitcointicker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

@HiltAndroidApp
class CoinApplication : Application(), KodeinAware{

    override fun onCreate() {
        super.onCreate()

    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@CoinApplication))
    }
}