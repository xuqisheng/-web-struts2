package wingsoft.custom;

import com.opensymphony.xwork2.ActionSupport;

public class TypeListAction extends ActionSupport {
    String json;
    public String getJson() {
        return json;
    }
    public void setJson(String json) {
        this.json = json;
    }

    String getDataFromDB(){
        System.out.println("0_! getTheDataOf TypeListAction");
        return "getList";
    }
}
