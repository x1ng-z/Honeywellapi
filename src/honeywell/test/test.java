package honeywell.test;


import honeywell.HoneyWellServe;
import honeywell.Tag;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static Logger logger = Logger.getLogger(test.class);
    static {
        PropertyConfigurator.configure(ClassLoader.getSystemResource("honeywell/log4j.properties"));
        System.loadLibrary("napitst32");
    }
    public static void main(String[] args) {


        HoneyWellServe honeyWellServe=new HoneyWellServe("localhost");//200.0.0.152

        List<String> tagnames=new ArrayList<>();
        tagnames.add("F_TT11.DACA.PV");
        tagnames.add("Q06G_PLJS.zsd.pv");
        honeyWellServe.registerTags(tagnames);

        honeyWellServe.initialTag();

        honeyWellServe.getAllTagValue();




//        String devicename="F_TT11,Q06G_PLJS".toLowerCase();
//        long[] deviceIds=new long[2];
//        HoneyWellServe honeyWellServe=new HoneyWellServe("localhost");
//        honeyWellServe.getDeviceId("localhost",devicename,2,deviceIds);//localhost
//        for(long value:deviceIds){
//            logger.info("device value is: "+value);
//        }
//
//
//        //long[] devicetoparam=new long[]{0,0};
//        String paramNames="DACA.PV,zsd.pv".toLowerCase();
//        long[] paramIds=new long[]{0,0};
//        honeyWellServe.getDeviceParamterId("localhost",deviceIds,paramNames,2,paramIds);
//        for(long value:paramIds){
//            logger.info("param value is: "+value);
//        }
//
//        logger.info("all value: "+honeyWellServe.getDeviceValues("localhost",deviceIds,paramIds));

    }
}
