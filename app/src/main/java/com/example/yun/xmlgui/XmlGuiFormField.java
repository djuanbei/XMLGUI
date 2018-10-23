package com.example.yun.xmlgui;

public class XmlGuiFormField {

    String name;
    String label;
    String type;
    boolean required;
    String options;

    Object obj; //holds the ui implementation or EditText for example

    //getters & setters




    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public String getOptions() {
        return options;
    }

    public Object getObj() {
        return obj;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("Field Name:  "+this.name+"\n");
        sb.append("Field Label: "+this.label+"\n");
        sb.append("Field Type:  "+this.type+"\n");
        sb.append("Required? : "+this.required+"\n");
        sb.append("Options : "+this.options+"\n");
        sb.append("Value : "+(String)this.getData()+"\n");
        return sb.toString();
    }


    public Object getData()
    {
        if (type.equals("text") || type.equals("numeric"))
        {
            if (obj != null) {
                XmlGuiEditBox b = (XmlGuiEditBox) obj;
                return b.getValue();
            }
        }
        if (type.equals("choice")) {
            if (obj != null) {
                XmlGuiPickOne po = (XmlGuiPickOne) obj;
                return po.getValue();
            }
        }

        // You could add logic for other UI elements here
        return null;
    }


}
