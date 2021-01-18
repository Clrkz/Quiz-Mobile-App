package com.example.quizapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class createQuiz extends Fragment {
Button btn_next;
EditText et_qname;
 Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_quiz, null);
        btn_next = v.findViewById(R.id.btn_next);
        et_qname = v.findViewById(R.id.et_qname);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qName = et_qname.getText().toString();
                if(TextUtils.isEmpty(qName)){
                    et_qname.setError("Enter Quiz Name");
                    et_qname.requestFocus();
                    return;
                }else{
                    AddQuizHandler.quizName = qName;
                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new createFragment()).commit();
                }
            }
        });
        return v;
    }
}
