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
import com.projectprovip.h1eu.giasu.domain.subject.model.toSubjectItem
import com.projectprovip.h1eu.giasu.domain.subject.usecase.GetSubjectUseCase
import com.projectprovip.h1eu.giasu.domain.tutor.usecase.RegisterTutorUseCase
import com.projectprovip.h1eu.giasu.presentation.profile.model.SubjectState
import com.projectprovip.h1eu.giasu.presentation.profile.model.TutorRegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class TutorRegisterViewModel @Inject constructor(
    private val registerTutorUseCase: RegisterTutorUseCase,
    private val getSubjectUseCase: GetSubjectUseCase
) : ViewModel() {

    private var downloadPath = mutableListOf<Uri>()
    private var _tutorRegisterState = mutableStateOf(TutorRegisterState())
    val tutorRegisterState: State<TutorRegisterState> = _tutorRegisterState

    private var _subjectState = mutableStateOf(SubjectState())
    val subjectState: State<SubjectState> = _subjectState

    fun registerTutor(auth: String, academicLevel: String, university: String, major: List<Int>, uri: List<Uri>) {
        viewModelScope.launch {
            val timeStamp = System.currentTimeMillis()

            val deferredResults = uri.map { async {
                val storagePath = "images/${it.lastPathSegment}_$timeStamp"
                val storageRef = FirebaseStorage.getInstance().reference
                val mountainsRef = storageRef.child(storagePath)

                // Put file to Firebase Storage
                val uploadTask = mountainsRef.putFile(it)
                    .continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {ex ->
                                throw ex
                            }
                        }
                        mountainsRef.downloadUrl
                    }.await()

                // Add the download URL to the list
                downloadPath.add(uploadTask)
            }}
            Log.d("Test register tutor before", downloadPath.toString())

            // Wait for all async tasks to complete
            deferredResults.awaitAll()

            Log.d("Test register tutor", downloadPath.toString())

            registerTutorUseCase(
                auth, TutorRegisterInput(
                    academicLevel,
                    university,
                    major,
                    downloadPath.map {
                        it.toString()
                    }.apply {
                        Log.d("Test register tutor after apply", this.toString())
                    }
                )
            ).onEach { result ->
                Log.d("Test register tutor after", downloadPath.toString())

                when (result) {
                    is EDSResult.Loading -> {
                        _tutorRegisterState.value = TutorRegisterState(isLoading = true)
                        Log.d("Test state", "loading")
                    }

                    is EDSResult.Error -> {
                        _tutorRegisterState.value = TutorRegisterState(error = result.message!!)
                        Log.d("Test state", "error")
                    }

                    is EDSResult.Success -> {
                        _tutorRegisterState.value =
                            TutorRegisterState(success = true, error = "Register successfully")
                        Log.d("Test state", "success")
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
}