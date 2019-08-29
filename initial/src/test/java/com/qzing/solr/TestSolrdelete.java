package com.qzing.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.io.IOException;

/**
 * Created by wangwh on 2019/8/29.
 */
public class TestSolrdelete {
    //solr 服务器地址
    public static final String solrServerUrl = "http://localhost:8983/solr";
    //solrhome下的core
    public static final String solrCroeHome = "core1";
    public static void main(String[] args) throws IOException, SolrServerException {
        System.out.println();
        SolrClient client = new HttpSolrClient.Builder(solrServerUrl + "/" + solrCroeHome)
                .build();

//        UpdateResponse response = client.deleteById("0");

        UpdateResponse response = client.deleteByQuery("*:*" );
        int status = response.getStatus();
        System.out.println("status:" + status);
        client.commit();
        client.close();
    }
}
