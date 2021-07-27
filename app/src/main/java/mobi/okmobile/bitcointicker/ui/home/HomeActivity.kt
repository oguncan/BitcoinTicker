package mobi.okmobile.bitcointicker.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mobi.okmobile.bitcointicker.R
import mobi.okmobile.bitcointicker.ui.auth.AuthActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        finish()
        startActivity(Intent(this, AuthActivity::class.java))
    }
}