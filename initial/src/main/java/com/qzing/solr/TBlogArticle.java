package com.qzing.solr;

/**
 * Created by wangwh on 2019/8/28.
 */
public class TBlogArticle {
    String longs ;
    String strs;
    String ints;
    String dates;
    public String getLong(String longs){return  this.longs;}
    public String getStr(String strs){return this.strs;}
    public String getInt(String ints){return this.ints;}
    public String getDate(String dates){return this.dates;}

    public void setLongs(String longs){
        this.longs = longs;
    }
    public void setStrs(String strs){
        this.strs = strs;
    }
    public void setInts(String ints){
        this.ints = ints;
    }
    public void set(String type,Object value){
        System.out.print(type);
    }

    public void put(String type,Object value){
        System.out.print(type);
    }
}
