package mobi.okmobile.bitcointicker.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> = _toastError
}