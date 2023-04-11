package br.senai.sp.jandira.doetempo

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CreateCampanhaViewModel : ViewModel() {

    var state by mutableStateOf(CreateCampanhaScreenState())
        private set


    fun updateSelectedImageList(listOfImages: List<Uri>) {
        val updatedImageList = state.listOfSelectedImages.toMutableList()
        viewModelScope.launch {
            updatedImageList += listOfImages
            state = state.copy(
                listOfSelectedImages = updatedImageList.distinct()
            )
        }
    }

    fun onItemRemove(index: Int) {
        val updatedImageList = state.listOfSelectedImages.toMutableList()
        viewModelScope.launch {
            updatedImageList.removeAt(index)
            state = state.copy(
                listOfSelectedImages = updatedImageList.distinct()
            )
        }
    }



    var isDialogShown by mutableStateOf(false)
        private set

    fun onAddClick(){
        isDialogShown = true
    }

    fun onDimissiDialog(){
        isDialogShown = false
    }

    var isDialogShownCampanha by mutableStateOf(false)
        private set

    fun onAddClickCampanha(){
        isDialogShownCampanha = true
    }

    fun onDismissDialogCampanha(){
        isDialogShownCampanha = false
    }

}