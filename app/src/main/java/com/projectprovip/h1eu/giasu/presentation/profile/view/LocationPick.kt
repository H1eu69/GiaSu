package com.projectprovip.h1eu.giasu.presentation.profile.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.common.EDSTextStyle
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.model.DistrictItem
import com.projectprovip.h1eu.giasu.presentation.profile.model.LocationPickState
import com.projectprovip.h1eu.giasu.presentation.profile.model.ProvinceItem
import com.projectprovip.h1eu.giasu.presentation.profile.model.WardItem
import kotlinx.coroutines.delay

enum class LocationSelectPhase {
    PROVINCE, DISTRICT, WARD
}

@Preview
@Composable
fun LocationPickPreview() {
    LocationPickScreen(
        rememberNavController(),
        onGetDistrict = {
        },
        onGetWard = {}, {}, {}, {},
        LocationPickState()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickScreen(
    navController: NavController,
    onGetDistrict: (String) -> Unit,
    onGetWard: (String) -> Unit,
    onSelectProvince: (Int) -> Unit,
    onSelectDistrict: (Int) -> Unit,
    onSelectWard: (Int) -> Unit,
    state: LocationPickState,
) {
    val selectedText =
        remember { mutableStateOf(listOf<String>("Pick province", "Pick district", "Pick ward")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val searchText = remember { mutableStateOf("") }
    val _currentSelectPhase = remember {
        mutableStateOf(LocationSelectPhase.PROVINCE)
    }
    val enableSaveButton = remember {
        mutableStateOf(false)
    }

    val province = remember {
        mutableStateOf(state.province)
    }
    val district = remember { mutableStateOf(state.district) }
    val ward = remember { mutableStateOf(state.ward) }

    province.value = state.province
    district.value = state.district
    ward.value = state.ward

    Scaffold(
        containerColor = EDSColors.white,

        topBar = {
            LocationPickTopBar(
                navController = navController,
                searchText = searchText,
                enableBtn = enableSaveButton,
                onSaveClick = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("province", selectedText.value[0])

                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("district", selectedText.value[1])

                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("ward", selectedText.value[2])

                    navController.popBackStack()
                },
                callback = { text ->
                    Log.d("test debounce province before", state.province.toString())
                    Log.d("test debounce district before", state.district.toString())
                    Log.d("test debounce ward before", state.ward.toString())
                    when (_currentSelectPhase.value.name) {
                        LocationSelectPhase.PROVINCE.name -> {
                            province.value = state.province.filter {
                                it.provinceName.contains(text)
                            }
                        }

                        LocationSelectPhase.DISTRICT.name -> {
                            district.value = state.district.filter {
                                it.district_name.contains(text)
                            }
                        }

                        LocationSelectPhase.WARD.name -> {
                            ward.value = state.ward.filter {
                                it.ward_name.contains(text)
                            }
                        }
                    }
                    Log.d("test debounce province", province.toString())
                    Log.d("test debounce district", district.toString())
                    Log.d("test debounce ward", ward.toString())
                    Log.d("test debounce", text)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            selectedText.value.forEachIndexed { index, it ->
                val isSelected =
                    if ((index == 0 && _currentSelectPhase.value == LocationSelectPhase.PROVINCE) ||
                        (index == 1 && _currentSelectPhase.value == LocationSelectPhase.DISTRICT) ||
                        (index == 2 && _currentSelectPhase.value == LocationSelectPhase.WARD)
                    ) true else false
                if (it != "")
                    AddressItem(
                        isSelected = isSelected,
                        onSelected = {
                            _currentSelectPhase.value = when (index) {
                                0 -> LocationSelectPhase.PROVINCE
                                1 -> LocationSelectPhase.DISTRICT
                                else -> LocationSelectPhase.WARD
                            }
                        }, text = it
                    )
            }
            state.apply {
                when {
                    this.isLoading -> {
                        val show = remember { mutableStateOf(true) }

                        LaunchedEffect(key1 = Unit) {
                            delay(2000)
                            show.value = false
                        }
                        if (show.value)
                            CircularLoading()
                    }

                    _currentSelectPhase.value.name == LocationSelectPhase.PROVINCE.name -> {
                        val provinceList = province.value

                        ProvinceList(listProvince = provinceList,
                            onSelected = { name ->
                                //find and mark select
                                val newList = state.province.toMutableList()
                                newList.forEach {
                                    it.isSelected = false
                                }
                                val selectedProvince = newList.first { item ->
                                    item.provinceName == name
                                }
                                val index = newList.indexOf(selectedProvince)

                                newList[index] =
                                    selectedProvince.copy(isSelected = !selectedProvince.isSelected)
                                province.value = newList

                                //Add name to selectedText
                                val list = selectedText.value.toMutableList()
                                if (list.size < 1)
                                    list.add(0, selectedProvince.provinceName)
                                else {
                                    list[0] = selectedProvince.provinceName
                                    list[1] = "Pick district"
                                    list[2] = "Pick ward"
                                }

                                selectedText.value = list
                                onSelectProvince(index)


                                _currentSelectPhase.value =
                                    LocationSelectPhase.DISTRICT

                                onGetDistrict(selectedProvince.provinceId)

                                enableSaveButton.value = false

                                //Clear keyboard
                                searchText.value = ""
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            })
                    }

                    _currentSelectPhase.value.name == LocationSelectPhase.DISTRICT.name -> {
                        val districtList = district.value

                        DistrictList(districtList, onSelected = { name ->
                            //find and mark select
                            val newList = state.district.toMutableList()
                            newList.forEach {
                                it.isSelected = false
                            }
                            val selectedDistrict = newList.first { item ->
                                item.district_name == name
                            }
                            val index = newList.indexOf(selectedDistrict)

                            newList[index] =
                                selectedDistrict.copy(isSelected = !selectedDistrict.isSelected)
                            district.value = newList

                            //Add name to selectedText
                            val list = selectedText.value.toMutableList()
                            if (list.size < 2)
                                list.add(1, selectedDistrict.district_name)
                            else {
                                list[1] = selectedDistrict.district_name
                                list[2] = "Pick ward"
                            }
                            selectedText.value = list
                            onSelectDistrict(index)
                            _currentSelectPhase.value = LocationSelectPhase.WARD
                            onGetWard(selectedDistrict.district_id)

                            enableSaveButton.value = false

                            //Clear keyboard
                            searchText.value = ""
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        })
                    }

                    _currentSelectPhase.value.name == LocationSelectPhase.WARD.name -> {
                        val wardList = ward.value
                        if (wardList.isEmpty())
                            enableSaveButton.value = true

                        WardList(wardList, onSelected = { name ->
                            //find and mark select
                            val newList = state.ward.toMutableList()
                            newList.forEach {
                                it.isSelected = false
                            }
                            val selectedWard = newList.first { item ->
                                item.ward_name == name
                            }
                            val index = newList.indexOf(selectedWard)

                            newList[index] =
                                selectedWard.copy(isSelected = !selectedWard.isSelected)

                            ward.value = newList
                            //Add name to selectedText
                            val list = selectedText.value.toMutableList()
                            if (list.size < 3)
                                list.add(2, selectedWard.ward_name)
                            else
                                list[2] = selectedWard.ward_name
                            selectedText.value = list
                            onSelectWard(index)

                            enableSaveButton.value = true
                            //Clear keyboard
                            searchText.value = ""
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    onSelected: () -> Unit,
    isSelected: Boolean = false,
    text: String
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clickable(
                interactionSource, null
            ) {
                onSelected()
            },
    ) {
        Icon(
            imageVector = Icons.Default.Circle,
            null,
            modifier = Modifier.size(8.dp),
            tint = if (isSelected) EDSColors.primaryColor else EDSColors.grayX3
        )
        Text(
            text = text,
            color = if (isSelected) EDSColors.primaryColor else EDSColors.blackColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickTopBar(
    navController: NavController,
    searchText: MutableState<String>,
    enableBtn: MutableState<Boolean>,
    callback: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    LaunchedEffect(key1 = searchText.value) {
        if (searchText.value.isNotEmpty()) {
            delay(1000)
            callback(searchText.value)
        }
    }

    TopAppBar(
        actions = {
            TextButton(
                onClick = {
                    onSaveClick()
                },
                enabled = enableBtn.value,
                colors = ButtonDefaults.textButtonColors(
                    disabledContentColor = EDSColors.grayX2,
                    contentColor = EDSColors.primaryColor
                )
            ) {
                Text(
                    text = "Save",
                )
            }
        },
        title = {
            BasicTextField(
                value = searchText.value,
                onValueChange = {
                    searchText.value = it
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                cursorBrush = SolidColor(EDSColors.darkCyan)
            ) { innerTextField ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            EDSColors.white,
                            RoundedCornerShape(50)
                        )
                        .border(
                            width = 1.dp, EDSColors.primaryColor,
                            RoundedCornerShape(50)
                        )
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth(.85f),
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(.85f)
                            .padding(start = 16.dp, end = 8.dp)
                    ) {
                        innerTextField()
                    }
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                            .background(
                                EDSColors.primaryColor,
                                RoundedCornerShape(
                                    topEndPercent = 50,
                                    bottomEndPercent = 50
                                )
                            )

                    ) {
                        Icon(
                            Icons.Default.Search, contentDescription = null,
                            tint = EDSColors.white,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = EDSColors.primaryColor
                )
            }
        }
    )
}

@Preview
@Composable
fun ProvinceListPreview() {
    val data = remember {
        listOf(
            ProvinceItem(),
            ProvinceItem(),
            ProvinceItem(),
            ProvinceItem(),
            ProvinceItem(),
            ProvinceItem(),
        )

    }
    Surface {
        ProvinceList(
            listProvince = data, {}
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvinceList(
    listProvince: List<ProvinceItem>,
    onSelected: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "City / Province",
                style = EDSTextStyle.H2Thin(
                    EDSColors.gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(EDSColors.grayX3)
                    .padding(12.dp),
            )
        }
        listProvince.forEachIndexed { index, province ->
            item {
                LocationItem(
                    modifier = Modifier.padding(horizontal = 16.dp),

                    text = province.provinceName,
                    isSelected = province.isSelected,
                    onClick = {
                        onSelected(province.provinceName)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistrictList(
    listDistrict: List<DistrictItem>,
    onSelected: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "District",
                style = EDSTextStyle.H2Thin(
                    EDSColors.gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(EDSColors.grayX3)
                    .padding(12.dp),
            )
        }
        listDistrict.forEachIndexed { index, district ->
            item {
                LocationItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = district.district_name,
                    isSelected = district.isSelected,
                    onClick = {
                        onSelected(district.district_name)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WardList(
    listWard: List<WardItem>,
    onSelected: (String) -> Unit
) {
    if (listWard.isEmpty())
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Text(
                text = "No ward at this district",
                style = EDSTextStyle.H2Thin(
                    EDSColors.gray
                ),
                modifier = Modifier
                    .padding(12.dp),
            )
        }
    else
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Ward",
                    style = EDSTextStyle.H2Thin(
                        EDSColors.gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(EDSColors.grayX3)
                        .padding(12.dp),
                )
            }
            listWard.forEachIndexed { index, ward ->
                item {
                    LocationItem(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = ward.ward_name,
                        isSelected = ward.isSelected,
                        onClick = {
                            onSelected(ward.ward_name)
                        }
                    )
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDialog(
    modifier: Modifier = Modifier,
    listProvince: MutableState<List<ProvinceItem>>,
    onDisMiss: () -> Unit = {},
    onConfirm: (String) -> Unit = {}
) {
    val searchText = remember { mutableStateOf("") }
    var selectedProvince = remember { mutableStateOf("") }
    Log.d("Test select", selectedProvince.toString())

    AlertDialog(
        modifier = modifier,
        title = {
            Text(text = "Province")
        },
        containerColor = EDSColors.white,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                OutlinedTextField(
                    value = searchText.value,
                    onValueChange = {
                        searchText.value = it
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(30),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = EDSColors.primaryColor,
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLeadingIconColor = EDSColors.primaryColor,
                    ),
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    listProvince.value.forEachIndexed { index, province ->
                        item {
                            LocationItem(
                                text = province.provinceName,
                                isSelected = province.isSelected,
                                onClick = {

                                }
                            )
                        }
                    }
                }
            }
        },

        onDismissRequest = {
            onDisMiss()
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(
                    ""
                )
            }) {
                Text("Accept")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDisMiss()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun LocationItem(
    modifier: Modifier = Modifier,
    text: String, isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(
        modifier = modifier
            .padding(vertical = 8.dp)
            .clickable(
                interactionSource, null
            ) {
                onClick()
            },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = EDSTextStyle.H2Reg(
                    if (isSelected) EDSColors.primaryColor else EDSColors.blackColor
                )
            )
            if (isSelected)
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = EDSColors.primaryColor
                )
        }
        Divider(
            color = EDSColors.grayX3
        )
    }
}