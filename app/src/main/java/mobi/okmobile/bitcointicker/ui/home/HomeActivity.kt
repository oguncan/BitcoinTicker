package mobi.okmobile.bitcointicker.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import mobi.okmobile.bitcointicker.R
import mobi.okmobile.bitcointicker.ui.auth.AuthActivity
import mobi.okmobile.bitcointicker.ui.base.BaseActivity
import mobi.okmobile.bitcointicker.ui.base.NavigationHost
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

@AndroidEntryPoint
class HomeActivity : BaseActivity(), NavigationHost {

    companion object {
        private val TOP_LEVEL_DESTINATIONS = setOf(
            R.id.navigation_coins_list,
            R.id.navigation_favourites
        )
    }

    private lateinit var navController: NavController
    private var navHostFragment: NavHostFragment? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.homeNavHostFragment) as? NavHostFragment
                ?: return

        navController = findNavController(R.id.homeNavHostFragment)
        appBarConfiguration = AppBarConfiguration(TOP_LEVEL_DESTINATIONS)

        homeBottomNavView.setupWithNavController(navController)
    }

    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

}