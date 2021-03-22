package com.example.bookstore.ui.used;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class usedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public usedViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}