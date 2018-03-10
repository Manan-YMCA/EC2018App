package com.manan.dev.ec2018app.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manan.dev.ec2018app.R;

import java.util.zip.Inflater;

/**
 * Created by yatindhingra on 02/03/18.
 */

public class FragmentOtpChecker extends DialogFragment {

    EditText et1, et2, et3, et4, et5, et6;
    TextView submitOtp, resendOtp;
    String otp;
    private String smsOtp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.otp_checker_fragment_layout, container, false);

        et1 = (EditText) rootView.findViewById(R.id.et_otp_dig_1);
        et2 = (EditText) rootView.findViewById(R.id.et_otp_dig_2);
        et3 = (EditText) rootView.findViewById(R.id.et_otp_dig_3);
        et4 = (EditText) rootView.findViewById(R.id.et_otp_dig_4);
        et5 = (EditText) rootView.findViewById(R.id.et_otp_dig_5);
        et6 = (EditText) rootView.findViewById(R.id.et_otp_dig_6);
        submitOtp = (TextView) rootView.findViewById(R.id.tv_check_otp);
        resendOtp = (TextView) rootView.findViewById(R.id.tv_otp_resend);

        addOnTextChangeListener();
        smsOtp = getOTP();

        submitOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveEnteredText();
                otpCheckStatus activity = (otpCheckStatus) getActivity();
                if (otp.equals("123456")) {
                    activity.updateResult(true);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Incorrect Otp", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsOtp = getOTP();
            }
        });

        return rootView;
    }

    private String getOTP() {
        //TODO
        //send sms and get otp
        return "123456";
    }

    private void addOnTextChangeListener() {
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    et2.requestFocus();
                }
            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    et3.requestFocus();
                } else if (s.toString().length() == 0) {
                    et2.requestFocus();
                }
            }
        });

        et2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(et2.getText().toString().length() == 0 && keyCode == KeyEvent.KEYCODE_DEL){
                    et1.requestFocus();
                }
                return false;
            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    et4.requestFocus();
                } else if (s.toString().length() == 0) {
                    et3.requestFocus();
                }
            }
        });

        et3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(et3.getText().toString().length() == 0 && keyCode == KeyEvent.KEYCODE_DEL){
                    et2.requestFocus();
                }
                return false;
            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    et5.requestFocus();
                } else if (s.toString().length() == 0) {
                    et4.requestFocus();
                }
            }
        });

        et4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (et4.getText().toString().length() == 0 && keyCode == KeyEvent.KEYCODE_DEL) {
                    et3.requestFocus();
                }
                return false;
            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    et6.requestFocus();
                } else if (s.toString().length() == 0) {
                    et5.requestFocus();
                }
            }
        });

        et5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (et5.getText().toString().length() == 0 && keyCode == KeyEvent.KEYCODE_DEL) {
                    et4.requestFocus();
                }
                return false;
            }
        });

        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    et6.requestFocus();
                } else if (s.toString().length() == 1) {
                    retrieveEnteredText();
                    otpCheckStatus activity = (otpCheckStatus) getActivity();
                    if (otp.equals("123456")) {
                        activity.updateResult(true);
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Incorrect Otp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        et6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (et6.getText().toString().length() == 0 && keyCode == KeyEvent.KEYCODE_DEL) {
                    et5.requestFocus();
                }
                return false;
            }
        });
    }

    private void retrieveEnteredText() {
        otp = "";
        otp += et1.getText().toString();
        otp += et2.getText().toString();
        otp += et3.getText().toString();
        otp += et4.getText().toString();
        otp += et5.getText().toString();
        otp += et6.getText().toString();
    }

    public interface otpCheckStatus {
        void updateResult(boolean status);
    }

    @Override
    public void onDestroy() {
        otpCheckStatus activity = (otpCheckStatus) getActivity();
        activity.updateResult(false);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        otpCheckStatus activity = (otpCheckStatus) getActivity();
        activity.updateResult(false);
        super.onDestroyView();
    }
}
