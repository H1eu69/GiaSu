package com.projectprovip.h1eu.giasu.presentation.profile.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.domain.profile.usecase.GetUserProfileUseCase
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateProfileParams
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateProfileUseCase
import com.projectprovip.h1eu.giasu.presentation.profile.model.UpdateProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    private var _state = mutableStateOf(UpdateProfileState())
    val state: State<UpdateProfileState> = _state

    private var _storagePath = ""
    private var downloadPath = Uri.parse("")

    fun getProfile(token: String) {
        getUserProfileUseCase(token).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _state.value = UpdateProfileState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _state.value = UpdateProfileState(error = result.message!!)
                    Log.d("Test get profile error", result.message)
                }

                is EDSResult.Success -> {
                    _state.value = UpdateProfileState(data = result.data!!)
                    Log.d("Test get profile", result.data.toString())

                }
            }

        }.launchIn(viewModelScope)
    }

    fun updateProfile(token: String, updateProfileParams: UpdateProfileParams) {
        viewModelScope.launch {
            val uri = Uri.parse(updateProfileParams.avatar)
            val timeStamp = System.currentTimeMillis()
            _storagePath = "images/${uri.lastPathSegment}_$timeStamp"

            val storageRef = FirebaseStorage.getInstance().reference
            val mountainsRef = storageRef.child(_storagePath)

            mountainsRef.putFile(uri).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                mountainsRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadPath = task.result
                    updateProfileParams.avatar = downloadPath.toString()
                    Log.d("Test updateProfileParams in callback", downloadPath.toString())
                }
            }.await()

            Log.d("Test updateProfileParams", updateProfileParams.toString())

            updateProfileUseCase(token, updateProfileParams).onEach { result ->
                when (result) {
                    is EDSResult.Loading -> {
                        _state.value = UpdateProfileState(isLoading = true)
                    }

                    is EDSResult.Error -> {
                        _state.value = UpdateProfileState(error = result.message!!)
                        Log.d("Test update profile error", result.message)
                    }

                    is EDSResult.Success -> {
                        _state.value = UpdateProfileState(isUpdateDone = true)
                        Log.d("Test update profile", result.data.toString())
                    }
                }
            }.launchIn(this)
        }
    }
}