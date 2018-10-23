package com.example.yun.xmlgui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;

public class RunForm extends AppCompatActivity {

    /** Called when the activity is first created. */
    String tag = RunForm.class.getName();
    XmlGuiForm theForm;
    ProgressDialog progressDialog;
    Handler progressHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String formNumber = "";
        Intent startingIntent = getIntent();
        if(startingIntent == null) {
            Log.e(tag,"No Intent?  We're not supposed to be here...");
            finish();
            return;
        }
        formNumber = startingIntent.getStringExtra("formNumber");
        Log.i(tag,"Running Form [" + formNumber + "]");
        if (GetFormData(formNumber)) {
            DisplayForm();
        }
        else
        {
            Log.e(tag,"Couldn't parse the Form.");
            AlertDialog.Builder bd = new AlertDialog.Builder(this);
            AlertDialog ad = bd.create();
            ad.setTitle("Error");
            ad.setMessage("Could not parse the Form data");
            ad.show();

        }


    }

    private boolean GetFormData(String formNumber) {
        try {
            Log.i(tag,"ProcessForm");
            URL url = new URL("http://www.example.com/xmlgui" + formNumber + ".xml");
            Log.i(tag,url.toString());
            InputStream is = url.openConnection().getInputStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();

            Document dom = db.parse(is);
            Element root = dom.getDocumentElement();
            NodeList forms = root.getElementsByTagName("form");
            if (forms.getLength() < 1) {
                // nothing here??
                Log.e(tag,"No form, let's bail");
                return false;
            }
            Node form = forms.item(0);
            theForm = new XmlGuiForm();

            // process form level
            NamedNodeMap map = form.getAttributes();
            theForm.setFormNumber(map.getNamedItem("id").getNodeValue());
            theForm.setFormName(map.getNamedItem("name").getNodeValue());
            if (map.getNamedItem("submitTo") != null)
                theForm.setSubmitTo(map.getNamedItem("submitTo").getNodeValue());
            else
                theForm.setSubmitTo("loopback");

            // now process the fields
            NodeList fields = root.getElementsByTagName("field");
            for (int i=0;i<fields.getLength();i++) {
                Node fieldNode = fields.item(i);
                NamedNodeMap attr = fieldNode.getAttributes();
                XmlGuiFormField tempField =  new XmlGuiFormField();
                tempField.setName(attr.getNamedItem("name").getNodeValue());
                tempField.setLabel(attr.getNamedItem("label").getNodeValue());
                tempField.setType(attr.getNamedItem("type").getNodeValue());
                if (attr.getNamedItem("required").getNodeValue().equals("Y"))
                    tempField.setRequired(true);
                else
                    tempField.setRequired(false);
                tempField.setOptions(attr.getNamedItem("options").getNodeValue());
                theForm.getFields().add(tempField);
            }

            Log.i(tag,theForm.toString());
            return true;
        } catch (Exception e) {
            Log.e(tag,"Error occurred in ProcessForm:" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
