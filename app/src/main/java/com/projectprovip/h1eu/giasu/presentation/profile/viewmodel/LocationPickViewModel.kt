package com.projectprovip.h1eu.giasu.presentation.profile.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.domain.location.usecase.GetDistrictUseCase
import com.projectprovip.h1eu.giasu.domain.location.usecase.GetProvinceUseCase
import com.projectprovip.h1eu.giasu.domain.location.usecase.GetWardUseCase
import com.projectprovip.h1eu.giasu.presentation.profile.model.LocationPickState
import com.projectprovip.h1eu.giasu.presentation.profile.model.toDistrictItem
import com.projectprovip.h1eu.giasu.presentation.profile.model.toProvinceItem
import com.projectprovip.h1eu.giasu.presentation.profile.model.toWardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LocationPickViewModel @Inject constructor(
    private val getProvinceUseCase: GetProvinceUseCase,
    private val getDistrictUseCase: GetDistrictUseCase,
    private val getWardUseCase: GetWardUseCase
) : ViewModel() {
    private var _state = mutableStateOf(LocationPickState())
    val state: State<LocationPickState> = _state
    fun getProvince() {
        getProvinceUseCase().onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _state.value = LocationPickState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _state.value = LocationPickState(error = result.message!!)
                }

                is EDSResult.Success -> {
                    _state.value = LocationPickState(province = result.data!!.map {
                        it.toProvinceItem()
                    })
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDistrict(provinceId: String) {
        getDistrictUseCase(provinceId).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _state.value =  _state.value.copy(isLoading = true)
                }

                is EDSResult.Error -> {
                    _state.value =  _state.value.copy(error = result.message!!)
                }

                is EDSResult.Success -> {
                    val province = _state.value.province
                    _state.value = LocationPickState(province = province,
                        district = result.data!!.map {
                            it.toDistrictItem()
                        })
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getWard(districtId: String) {
        getWardUseCase(districtId).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _state.value =  _state.value.copy(isLoading = true)
                }

                is EDSResult.Error -> {
                    _state.value =  _state.value.copy(error = result.message!!)
                }

                is EDSResult.Success -> {
                    val province = _state.value.province
                    val district = _state.value.district
                    _state.value = LocationPickState(province = province,
                        district = district,
                        ward = result.data!!.map {
                            it.toWardItem()
                        })
                }
            }
        }.launchIn(viewModelScope)
    }

    fun selectProvince(index: Int) {
        val newList =  _state.value.province.toMutableList()
        newList.forEach {
            it.isSelected = false
        }

        val selectedProvince = newList[index]
        newList[index] =
            selectedProvince.copy(isSelected = !selectedProvince.isSelected)

        _state.value =  _state.value.copy(
            province = newList
        )
    }

    fun selectDistrict(index: Int) {
        val newList =  _state.value.district.toMutableList()
        newList.forEach {
            it.isSelected = false
        }

        val selectedDistrict = newList[index]
        newList[index] =
            selectedDistrict.copy(isSelected = !selectedDistrict.isSelected)

        _state.value =  _state.value.copy(
            district = newList
        )
    }

    fun selectWard(index: Int) {
        val newList =  _state.value.ward.toMutableList()
        newList.forEach {
            it.isSelected = false
        }

        val selectedWard = newList[index]
        newList[index] =
            selectedWard.copy(isSelected = !selectedWard.isSelected)

        _state.value =  _state.value.copy(
            ward = newList
        )
    }
}