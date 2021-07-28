package mobi.okmobile.bitcointicker.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import mobi.okmobile.bitcointicker.data.repositories.UserRepository

class AuthViewModel(
    private val repository : UserRepository
) : ViewModel() {


    // email and password for the input
    var email: String? = null
    var password: String? = null

    // auth listener
    var authListener: AuthListener? = null

    // disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }

    /**
     * function to perform login
     */
    fun login() {
        // validating email and password
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        //authentication started
        authListener?.onStarted()

        // calling login from repository to perform the actual authentication
        val disposable = repository.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    /**
     * function to perform register
     */
    fun register() {
        // validating email and password
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Please input all values")
            return
        }

        //authentication started
        authListener?.onStarted()

        // calling login from repository to perform the actual authentication
        val disposable = repository.register(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    /**
     * disposing the disposables
     */
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}