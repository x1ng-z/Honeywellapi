package honeywell;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tag {
    private String tagName;
    private Long deviceId;
    private Long paramId;
    private String value;


    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDeviceName(){
        int i=0;
        for(char fd:tagName.toCharArray()){
            if(fd=='.'){
                break;
            }
            i++;
        }

        if(i==tagName.length()){
            throw new NullPointerException("device name error");
        }else {
            return tagName.substring(0,i).toLowerCase();
        }
    }


    public String getParamName(){

        int i=0;
        for(char fd:tagName.toCharArray()){
            if(fd=='.'){
                break;
            }
            i++;
        }

        if(i==tagName.length()){
            throw new NullPointerException("device name error");
        }else {
            return tagName.substring(i+1,tagName.length()).toLowerCase();
        }
    }





}
