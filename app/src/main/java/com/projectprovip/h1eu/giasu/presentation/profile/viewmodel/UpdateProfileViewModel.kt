package com.projectprovip.h1eu.giasu.presentation.profile.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.domain.profile.model.Profile
import com.projectprovip.h1eu.giasu.domain.profile.usecase.GetUserProfileUseCase
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateProfileParams
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateProfileUseCase
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateTutorInfoParams
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateTutorInformationUseCase
import com.projectprovip.h1eu.giasu.domain.subject.model.toSubjectItem
import com.projectprovip.h1eu.giasu.domain.subject.usecase.GetSubjectUseCase
import com.projectprovip.h1eu.giasu.presentation.profile.model.GetProfileState
import com.projectprovip.h1eu.giasu.presentation.profile.model.SubjectState
import com.projectprovip.h1eu.giasu.presentation.profile.model.UpdateProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val updateTutorInfoUseCase: UpdateTutorInformationUseCase,
    private val getSubjectUseCase: GetSubjectUseCase
) : ViewModel() {
    private var _updateProfileState = mutableStateOf(UpdateProfileState())
    val updateProfileState: State<UpdateProfileState> = _updateProfileState

    private var _getProfileState = mutableStateOf(GetProfileState())
    val getProfileState: State<GetProfileState> = _getProfileState

    private var _subjectState = mutableStateOf(SubjectState())
    val subjectState: State<SubjectState> = _subjectState
    private var _profile = Profile()

    fun getProfile(token: String) {
        getUserProfileUseCase(token).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _getProfileState.value = GetProfileState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _getProfileState.value = GetProfileState(error = result.message!!)
                    Log.d("Test get profile error", result.message)
                }

                is EDSResult.Success -> {
                    _getProfileState.value = GetProfileState(data = result.data!!)
                    _profile = result.data
                    Log.d("Test get profile", result.data.toString())

                }
            }

        }.launchIn(viewModelScope)
    }

    fun updateProfile(token: String, updateProfileParams: UpdateProfileParams) {
        viewModelScope.launch {
            val uri = Uri.parse(updateProfileParams.avatar)
            val timeStamp = System.currentTimeMillis()

            if (uri.toString().isNotEmpty()) {
                val deferredResults = async {
                    val storagePath = "images/${uri.lastPathSegment}_$timeStamp"
                    val storageRef = FirebaseStorage.getInstance().reference
                    val mountainsRef = storageRef.child(storagePath)

                    // Put file to Firebase Storage
                    val uploadTask = mountainsRef.putFile(uri)
                        .continueWithTask { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let { ex ->
                                    throw ex
                                }
                            }
                            mountainsRef.downloadUrl
                        }.await()
                    uploadTask
                }

                updateProfileParams.avatar = deferredResults.await().toString()
                _profile = _profile.copy(
                    avatar = updateProfileParams.avatar
                )
                Log.d("Test register tutor before", updateProfileParams.avatar)
            }

            Log.d("Test updateProfileParams", updateProfileParams.toString())

            updateProfileUseCase(token, updateProfileParams).onEach { result ->
                when (result) {
                    is EDSResult.Loading -> {
                        _updateProfileState.value = UpdateProfileState(isLoading = true)
                    }

                    is EDSResult.Error -> {
                        _updateProfileState.value = UpdateProfileState(error = result.message!!)
                        Log.d("Test update profile error", result.message)
                    }

                    is EDSResult.Success -> {
                        _updateProfileState.value = UpdateProfileState(
                            data = _profile
                        )
                        Log.d("Test update profile", result.data.toString())
                    }
                }
            }.launchIn(this)
        }
    }

    fun getSubject() {
        getSubjectUseCase().onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _subjectState.value = SubjectState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _subjectState.value = SubjectState(error = result.message!!)
                    Log.d("Test get subject error", result.message)
                }

                is EDSResult.Success -> {
                    _subjectState.value = SubjectState(data = result.data!!.map {
                        it.toSubjectItem()
                    })
                    Log.d("Test get subject", result.data.toString())

                }
            }

        }.launchIn(viewModelScope)
    }

    fun updateTutorInfo(token: String, updateTutorInfoParams: UpdateTutorInfoParams) {
        updateTutorInfoUseCase(token, updateTutorInfoParams).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _updateProfileState.value = UpdateProfileState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _updateProfileState.value = UpdateProfileState(error = result.message!!)
                    Log.d("Test update tutor error", result.message)
                }

                is EDSResult.Success -> {
                    _updateProfileState.value = UpdateProfileState()
                    Log.d("Test update tutor", result.data.toString())

                }
            }

        }.launchIn(viewModelScope)
    }
}