package com.projectprovip.h1eu.giasu.presentation.profile.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.tutor.model.TutorRegisterInput
import com.projectprovip.h1eu.giasu.domain.tutor.usecase.RegisterTutorUseCase
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseRegisterState
import com.projectprovip.h1eu.giasu.presentation.profile.model.TutorRegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorRegisterViewModel @Inject constructor(
    private val registerTutorUseCase: RegisterTutorUseCase
) : ViewModel() {
    private val uriState = mutableStateOf(Uri.parse(""))
    private var _storagePath = ""
    private var downloadPath = Uri.parse("")
    private var _tutorRegisterState = mutableStateOf(TutorRegisterState())
    val tutorRegisterState : State<TutorRegisterState> =  _tutorRegisterState
    fun uploadImage(uri: Uri) = viewModelScope.launch {
        uriState.value = uri

        val timeStamp = System.currentTimeMillis()
        _storagePath = "images/${uriState.value.lastPathSegment}_$timeStamp"

        val storageRef = FirebaseStorage.getInstance().reference
        val mountainsRef = storageRef.child(_storagePath)

        mountainsRef.putFile(uriState.value).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            mountainsRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadPath = task.result
            }
        }
    }

    fun registerTutor(auth: String, academicLevel: String, university: String, major: String) {
        registerTutorUseCase(
            auth, TutorRegisterInput(
                academicLevel,
                university,
                listOf(major),
                listOf(uriState.value.toString())
            )
        ).onEach { result -> 
            when(result) {
                is EDSResult.Loading -> {
                    _tutorRegisterState.value = TutorRegisterState(isLoading = true)
                    Log.d("Test state", "loading")
                }
                is EDSResult.Error -> {
                    _tutorRegisterState.value = TutorRegisterState(error = result.message!!)
                    Log.d("Test state", "error")
                }
                is EDSResult.Success -> {
                    _tutorRegisterState.value = TutorRegisterState(success = true, error = "Register successfully")
                    Log.d("Test state", "success")
                }
            }
        }.launchIn(viewModelScope)
    }
}