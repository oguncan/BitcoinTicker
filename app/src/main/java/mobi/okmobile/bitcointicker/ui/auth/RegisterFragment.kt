package mobi.okmobile.bitcointicker.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import mobi.okmobile.bitcointicker.R
import mobi.okmobile.bitcointicker.databinding.FragmentLoginBinding
import mobi.okmobile.bitcointicker.databinding.FragmentRegisterBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance


class RegisterFragment : Fragment(), AuthListener, KodeinAware {

    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.authListener = this

        return binding.root
    }

    override fun onStarted() {
        binding.registerProgressBar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        binding.registerProgressBar.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        binding.registerProgressBar.visibility = View.GONE
    }

    override val kodein = Kodein.lazy {

    }

    override fun onStart() {
        super.onStart()
        viewModel.user?.let {

        }
    }

}