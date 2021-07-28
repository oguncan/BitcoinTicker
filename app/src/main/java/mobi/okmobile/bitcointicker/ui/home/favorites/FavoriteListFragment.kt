package mobi.okmobile.bitcointicker.ui.home.favorites

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_list.*
import mobi.okmobile.bitcointicker.R
import mobi.okmobile.bitcointicker.adapters.CoinsListAdapter
import mobi.okmobile.bitcointicker.adapters.OnItemClickCallback
import mobi.okmobile.bitcointicker.data.repositories.coindetail.CoinDetailRepository
import mobi.okmobile.bitcointicker.databinding.FragmentFavoriteListBinding
import mobi.okmobile.bitcointicker.ui.base.MainNavigationFragment
import mobi.okmobile.bitcointicker.ui.detail.CoinDetailActivity
import mobi.okmobile.bitcointicker.util.Constants
import mobi.okmobile.bitcointicker.util.extensions.doOnChange

@AndroidEntryPoint
class FavoriteListFragment : MainNavigationFragment(), OnItemClickCallback {

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteListBinding
    private var favouritesAdapter = CoinsListAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@FavoriteListFragment.viewModel
            }
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
    }

    override fun initializeViews() {
        favouritesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favouritesAdapter
        }

        swipeFavoritesRefreshLayout.setOnRefreshListener {
            viewModel.loadCoinsFromApi()
            showToast(getString(R.string.refresh_msg))
            swipeFavoritesRefreshLayout.isRefreshing = false
        }
    }

    override fun observeViewModel() {
        viewModel.favoriteCoinsList.doOnChange(this) {
            favouritesAdapter.updateList(it)

            if (it.isEmpty()) {
                viewModel.isFavouritesEmpty(true)
            }
        }

        viewModel.toastError.doOnChange(this) {
            showToast(it)
        }

        viewModel.favouriteStock.doOnChange(this) {
            showToast(
                getString(if (it.isFavourite) R.string.added_to_favourite else R.string.removed_to_favourite).format(
                    it.symbol
                )
            )
        }
    }

    override fun onItemClick(symbol: String, id: String) {
        requireActivity().run {
            startActivity(
                Intent(this, CoinDetailActivity::class.java)
                    .apply {
                        putExtra(Constants.EXTRA_SYMBOL, symbol)
                        putExtra(Constants.EXTRA_SYMBOL_ID, id)
                    }
            )
        }
    }

    override fun onFavouriteClicked(symbol: String) {

    }

}