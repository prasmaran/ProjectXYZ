package com.example.projectxyz.ui.splashscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projectxyz.utils.app.DataStoreRepository
import com.example.projectxyz.utils.repository.UserAuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val userAuthRepository = UserAuthRepository()
    private val dataStoreRepository: DataStoreRepository = DataStoreRepository(application.applicationContext)

    private val _loginFlow = MutableLiveData<Resource<FirebaseUser>?>(null)
    val loginFlow: LiveData<Resource<FirebaseUser>?> = _loginFlow

    private val userLoggedInTimeFlow: Flow<Long> = dataStoreRepository.userLoginTime

    // Function to save user login time
    fun saveUserLoginTime(time: Long) = viewModelScope.launch {
        dataStoreRepository.saveUserLoginTime(time)
    }

    val currentUser: FirebaseUser?
        get() = userAuthRepository.getCurrentUser()

    init {
        viewModelScope.launch {
            val currentUser = userAuthRepository.getCurrentUser()
            if (currentUser != null) {
                val isUserExpired = isLoginTimeExpired(userLoggedInTimeFlow)
                isUserExpired.collect { isExpired ->
                    if (!isExpired) {
                        _loginFlow.value = Resource.Success(currentUser)
                    } else {
                        logout()
                    }
                }
            }
        }
    }

    private fun isLoginTimeExpired(loginTimeFlow: Flow<Long>): Flow<Boolean> {
        return loginTimeFlow.map { currentTime ->
            val timeDifference = System.currentTimeMillis() - currentTime
            timeDifference > 60 * 60 * 1000 // 1 hour in milliseconds
        }
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = userAuthRepository.signIn(email, password, _loginFlow)
    }

    private suspend fun logout() {
        userAuthRepository.logout()
        dataStoreRepository.clearUserLoginTime()
        _loginFlow.value = null
    }
}


