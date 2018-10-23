package com.example.yun.xmlgui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class XmlGui extends AppCompatActivity {
    final String tag = XmlGui.class.getName();
    /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnRunForm = (Button) this.findViewById(R.id.btnRunForm);
        btnRunForm.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                EditText formNumber = (EditText) findViewById(R.id.formNumber);
                Log.i(tag,"Attempting to process Form #
                                [" + formNumber.getText().toString() + "]");
                        Intent newFormInfo = new Intent(XmlGui.this,RunForm.class);
                newFormInfo.putExtra("formNumber",
                        formNumber.getText().toString());
                startActivity(newFormInfo);
            }
        });


    }
}
