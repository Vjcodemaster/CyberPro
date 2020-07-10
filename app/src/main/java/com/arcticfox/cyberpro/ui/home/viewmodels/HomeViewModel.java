package com.arcticfox.cyberpro.ui.home.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData sPageTitle = new MutableLiveData();


    public void setPageTitle(String sPageTitle){
        this.sPageTitle.postValue(sPageTitle);
    }

    public LiveData<String> getPageTitle(){
        return sPageTitle;
    }
}
