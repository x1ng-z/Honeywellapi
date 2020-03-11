package honeywell;

import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//javah -classpath out/production/Honeywellapi -d ./jni honeywell.HoneyWellServe
public class HoneyWellServe {

    private String hostname =null;
    private Map<String,Tag> tagpool=new ConcurrentHashMap();
    private long[] di;
    private long[] pi;

    public HoneyWellServe(String hostname) {
        this.hostname = hostname;
    }

    public  void  registerTag(String tagname){
        Tag tag=new Tag();
        tag.setTagName(tagname);
        tagpool.put(tagname,tag);
    }

    public  void  registerTags(List<String> tagnames){
        for(String tagname:tagnames){
            Tag tag=new Tag();
            tag.setTagName(tagname);
            tagpool.put(tagname,tag);
        }
    }

    public void initialTag(){
        int loop=0;
        StringBuilder devicenames=new StringBuilder();
        StringBuilder paramnames=new StringBuilder();
        for(Map.Entry<String, Tag> mapEnt:tagpool.entrySet()){
            Tag tag=mapEnt.getValue();
            devicenames.append(tag.getDeviceName()+",");
            paramnames.append(tag.getParamName()+",");
        }
        //logger.info(devicenames.toString().substring(0,devicenames.length()-1));
        //logger.info(paramnames.toString().substring(0,paramnames.length()-1));;

        di=new long[tagpool.size()];
        pi=new long[tagpool.size()];

        getDeviceId(hostname,devicenames.toString().substring(0,devicenames.length()-1),tagpool.size(),di);
        getDeviceParamterId(hostname,di,paramnames.toString().substring(0,paramnames.length()-1),tagpool.size(),pi);
        loop=0;
        for(Map.Entry<String, Tag> mapEnt:tagpool.entrySet()){
            Tag tag=mapEnt.getValue();
            tag.setDeviceId(di[loop]);
            tag.setParamId(pi[loop]);
        }
    }

    public Map<String,String> getAllTagValue(){
        int loop=0;
        String result=getDeviceValues(hostname,di,pi);
        Pattern pattern=Pattern.compile("^\\{(.*)\\}$");
        Matcher matcher =pattern.matcher(result);
        if(matcher.find()){
           String[] valus=matcher.group(1).split(",");
            Map<String,String> results=new HashMap<String,String>();
            for(Map.Entry<String, Tag> mapEnt:tagpool.entrySet()){
                Tag tag=mapEnt.getValue();
                tag.setValue(valus[loop]);
                results.put(tag.getTagName(),valus[loop]);
                loop++;
            }

            return results;
        }else {
            return null;
        }

    }


    public Map<String,String>getPartTagValue(List<String> tagNames){
        int loop=0;
       List<Long> di=new ArrayList<>();
       List<Long> pi=new ArrayList<>();
       List<String>validetag=new ArrayList<>();

        for(String tag:tagNames){
            Tag tagclazz=tagpool.get(tag);
            if(tagclazz!=null){
                di.add(tagclazz.getDeviceId());
                pi.add(tagclazz.getParamId());
                validetag.add(tagclazz.getTagName());
            }
        }
        if(di.size()!=0){
           long[] pickdi= new long[di.size()];
           long[] pickpi=new long[pi.size()];
           for(loop=0;loop<di.size();++loop) {
               pickdi[loop] = di.get(loop);
               pickpi[loop] = pi.get(loop);
           }
          String result = getDeviceValues(hostname,pickdi,pickpi);

            Pattern pattern=Pattern.compile("^\\{(.*)\\}$");
            Matcher matcher =pattern.matcher(result);
            if(matcher.find()){
                String[] valus=matcher.group(1).split(",");
                Map<String,String> results=new HashMap<String,String>();
                loop=0;
                for(String tagname:validetag){
                    Tag tag=tagpool.get(tagname);
                    tag.setValue(valus[loop++]);
                    results.put(tag.getTagName(),valus[loop++]);
                }

                return results;
            }else {
                return null;
            }

        }else {
            return null;
        }
    }



    private native  void getDeviceId(String hostname,String deviceNames,int deviceNum,long[] deviceIds);
    private native void getDeviceParamterId(String hostname,long[] deviceToParam,String parameterNames,int paramNum,long[] paramterIds);
    private native String getDeviceValues(String hostname,long[]deviceIds,long[] paramterIds);


    public Map<String, Tag> getTagpool() {
        return tagpool;
    }
}