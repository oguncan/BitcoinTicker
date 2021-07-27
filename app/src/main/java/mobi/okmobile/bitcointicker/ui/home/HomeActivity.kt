package mobi.okmobile.bitcointicker.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import mobi.okmobile.bitcointicker.R
import mobi.okmobile.bitcointicker.ui.auth.AuthActivity
import mobi.okmobile.bitcointicker.ui.base.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

@AndroidEntryPoint
class HomeActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        finish()
        startActivity(Intent(this, AuthActivity::class.java))
    }

}