package mobi.okmobile.bitcointicker.ui.home.coinlist

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_coin_list.*
import mobi.okmobile.bitcointicker.R
import mobi.okmobile.bitcointicker.adapters.CoinsListAdapter
import mobi.okmobile.bitcointicker.adapters.OnItemClickCallback
import mobi.okmobile.bitcointicker.databinding.FragmentCoinListBinding
import mobi.okmobile.bitcointicker.ui.base.MainNavigationFragment
import mobi.okmobile.bitcointicker.ui.detail.CoinDetailActivity
import mobi.okmobile.bitcointicker.util.Constants
import mobi.okmobile.bitcointicker.util.extensions.doOnChange


@AndroidEntryPoint
class CoinListFragment : MainNavigationFragment(), OnItemClickCallback {

    private val viewModel: CoinListViewModel by viewModels()
    private lateinit var binding: FragmentCoinListBinding
    private var coinsListAdapter = CoinsListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCoinListBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@CoinListFragment.viewModel
            }
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        viewModel.loadCoinsFromApi()
    }

    override fun initializeViews() {
        coinsListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coinsListAdapter
        }

        val search = toolbar.menu.findItem(R.id.toolbar_search_bar)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)

        val magIcon: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon)
        val searchEditText: EditText =
            searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        val closeIcon: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        val icon: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_button)
        val searchPlate: View = searchView.findViewById(androidx.appcompat.R.id.search_plate)

        magIcon.visibility = View.GONE
        searchEditText.setTextColor(Color.WHITE)
        searchEditText.setHintTextColor(Color.WHITE)
        closeIcon.setImageResource(R.drawable.ic_cancel)
        icon.setImageResource(R.drawable.ic_search)
        searchPlate.background.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                coinsListAdapter.filter.filter(newText)
                return false
            }
        })

        swipeListRefreshLayout.setOnRefreshListener {
            viewModel.loadCoinsFromApi()
            showToast(getString(R.string.refresh_msg))
            searchEditText.setText("")
            if (!searchView.isIconified) {
                searchView.onActionViewCollapsed()
            }
            swipeListRefreshLayout.isRefreshing = false
        }
    }

    override fun observeViewModel() {
        viewModel.isLoading.doOnChange(this) {
            coinsListLoading.visibility =
                if (viewModel.isListEmpty() && it) View.VISIBLE else View.GONE

            if (it) {
                coinsListErrorView.visibility = View.GONE
            }
        }

        viewModel.coinsListData.doOnChange(this) {
            coinsListAdapter.updateList(it)

            coinsListErrorView.visibility =
                if (viewModel.isListEmpty()) View.VISIBLE else View.GONE
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