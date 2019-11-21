package com.dsm.iriscalendar.ui.signUp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dsm.iriscalendar.R;
import com.dsm.iriscalendar.base.BaseActivity;
import com.dsm.iriscalendar.databinding.ActivitySignUpBinding;
import com.dsm.iriscalendar.ui.main.MainActivity;

import javax.inject.Inject;

public class SignUpActivity extends BaseActivity<ActivitySignUpBinding> {

    @Inject SignUpViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setViewModel(viewModel);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_sign_up;
    }

    @Override
    public void viewInit() {
    }

    @Override
    public void observeViewModel() {
        viewModel.getIntentMainEvent().observe(this, t -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        viewModel.getToastEvent().observe(this, stringId -> Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show());
    }
}
