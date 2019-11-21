package com.dsm.iriscalendar.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.dsm.iriscalendar.R;
import com.dsm.iriscalendar.base.BaseViewModel;
import com.dsm.iriscalendar.data.repository.login.LoginRepository;
import com.dsm.iriscalendar.util.SingleLiveEvent;

public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<String> id = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public MediatorLiveData<Boolean> isLoginEnable = new MediatorLiveData<>();

    private SingleLiveEvent<Integer> toastEvent = new SingleLiveEvent<>();
    public LiveData<Integer> getToastEvent() {
        return toastEvent;
    }

    private SingleLiveEvent<Void> finishActivityEvent = new SingleLiveEvent<>();
    public LiveData<Void> getFinishActivityEvent() {
        return finishActivityEvent;
    }

    private SingleLiveEvent<Void> intentMainEvent = new SingleLiveEvent<>();
    public LiveData<Void> getIntentMainEvent() {
        return intentMainEvent;
    }

    private LoginRepository loginRepository;

    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;

        id.setValue("");
        password.setValue("");

        isLoginEnable.addSource(id, value -> {
            assert password.getValue() != null;
            if (!value.trim().isEmpty() && !password.getValue().trim().isEmpty()) isLoginEnable.setValue(true);
            else isLoginEnable.setValue(false);
        });

        isLoginEnable.addSource(password, value -> {
            assert id.getValue() != null;
            if (!id.getValue().trim().isEmpty() && !value.trim().isEmpty()) isLoginEnable.setValue(true);
            else isLoginEnable.setValue(false);
        });
    }

    public void login() {
        assert id.getValue() != null && password.getValue() != null;
        String id = this.id.getValue().trim();
        String password = this.password.getValue().trim();

        if (id.length() < 6) {
            toastEvent.setValue(R.string.error_short_id);
            return;
        } else if (password.length() < 8) {
            toastEvent.setValue(R.string.error_short_password);
            return;
        }

        addDisposable(
                loginRepository.login(id, password)
                        .subscribe(code -> {
                            switch (code) {
                                case 200:
                                    finishActivityEvent.call();
                                    intentMainEvent.call();
                                    break;
                                case 400:
                                    toastEvent.setValue(R.string.error_invalid_value);
                                    break;
                                default:
                                    toastEvent.setValue(R.string.error_server_error);
                            }
                        }, throwable -> toastEvent.setValue(R.string.error_server_error))
        );
    }
}