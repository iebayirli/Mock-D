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

    /**
     * Basic api call function works with Kotlin Flows.
     *
     * Takes Flow<T> value to make api calls.
     * Has three lambda function to manage loading, success and error states.
     *
     * Loading : Its returns block as a function. You can think this function as Loading state.
     * You can do any operations in it but at the end of the block you need to return boolean value.
     * This boolean value is about progress dialog state. If you want to show progress dialog to the user in service call, you must pass true, otherwise you must pass false.
     *
     * Collect : As you can understand from the name, it returns fetched data coming from API after successfully service call.
     *
     * Catch : You can manage the errors if there is a error while service call.
     *
     */
    suspend fun <T> apiCallWithFlow(
        request: Flow<T>,
        loading: () -> (Boolean),
        collect: suspend (T) -> (Boolean),
        catch: (Throwable) -> Unit
    ) {
        request.onStart {
            _progressDialog.postValue(Event(loading.invoke()))
        }.catch { error ->
            catch(error)
            _progressDialog.postValue(Event(false))
        }.collect { result ->
            _progressDialog.postValue(Event(collect(result)))
        }
    }

}