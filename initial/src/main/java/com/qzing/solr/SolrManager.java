package com.qzing.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * Created by wangwh on 2019/8/28.
 */
public class SolrManager {
    public SolrClient solr;
    public SolrManager(){
        String urlString = "http://localhost:8983/solr/techproducts";
        solr = new HttpSolrClient.Builder(urlString).build();
    }
    public void queryFromSolr(){

    }
}
