package com.qzing.solr;


/**
 * Created by wangwh on 2019/8/29.
 */
public class ToolsUtils {
    public static String print(String model,String msg,String errormsg){
        System.out.println(model+msg+errormsg);
        return model+msg+errormsg;
    }
    public static String print(String app,String model,String msg,String errormsg){
        System.out.println(app+model+msg+errormsg);
        return model+msg+errormsg;
    }
}
