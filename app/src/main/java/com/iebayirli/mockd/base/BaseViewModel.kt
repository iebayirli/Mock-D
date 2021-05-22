package com.iebayirli.mockd.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.iebayirli.mockd.navigation.NavigationCommand
import com.iebayirli.mockd.util.Event
import com.iebayirli.mockd.util.error_dialog.ErrorModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

abstract class BaseViewModel : ViewModel() {

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    private val _progressDialog = MutableLiveData<Event<Boolean>>()
    val progressDialog: LiveData<Event<Boolean>> = _progressDialog

    private val _errorDialog = MutableLiveData<Event<ErrorModel>>()
    val errorDialog: LiveData<Event<ErrorModel>> = _errorDialog

    fun navigate(direction: NavDirections) {
        _navigation.value = Event(NavigationCommand.ToDirection(direction))
    }

    fun handleError(errorModel: ErrorModel) {
        _errorDialog.value = Event(errorModel)
    }

    suspend fun <T> apiCallWithFlow(
        request: Flow<T>,
        loading: () -> (Boolean),
        collect: suspend (T) -> Unit,
        catch: (Throwable) -> Unit
    ) {
        request.onStart {
            _progressDialog.postValue(Event(loading.invoke()))
        }.catch { error ->
            catch(error)
            _progressDialog.postValue(Event(false))
        }.collect { result ->
            collect(result)
            _progressDialog.postValue(Event(false))
        }
    }

}