package com.example.projectxyz.utils.repository
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.projectxyz.ui.splashscreen.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserAuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val TAG = "UserAuthRepository"

    fun signIn(email: String, password: String, liveData: MutableLiveData<Resource<FirebaseUser>?>) {

        liveData.postValue(Resource.Loading)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success: ${task.result}")
                    Log.d(TAG, "signInWithEmail:success: ${auth.currentUser}")
                    val user = auth.currentUser
                    liveData.postValue(Resource.Success(user!!))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    liveData.postValue(Resource.Failure(task.exception!!))
                }
            }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}
