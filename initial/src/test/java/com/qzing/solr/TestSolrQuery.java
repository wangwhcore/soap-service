package com.qzing.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;

/**
 * Created by wangwh on 2019/8/29.
 */
public class TestSolrQuery {
        //solr 服务器地址
        public static final String solrServerUrl = "http://localhost:8983/solr";
        //solrhome下的core
        public static final String solrCroeHome = "core1";
    public static void main(String[] args) throws IOException, SolrServerException {
        SolrClient client = new HttpSolrClient.Builder(solrServerUrl + "/" + solrCroeHome).build();

        SolrQuery query = new SolrQuery("content_text:慧聚 东软 "); //and name:齐心");
        query.setParam("hl.fl", "content_text");//设置高亮字段
        // 实现分页的查询
        query.setStart(0);
        query.setRows(100);
//        query.setSort("_version_",
//                SolrQuery.ORDER.desc);
        QueryResponse response = client.query(query);
        System.out.println(query);
        SolrDocumentList docs = response.getResults();
        System.out.println(docs.getNumFound());
        for(SolrDocument doc : docs){
            Object id = doc.get("id");
            System.out.println("id:" + id + ", content:" + doc.get("content_text")
//                    + " ==="+doc.get("name")
//                    +"  =="+doc.get("logo")+doc
            );

        }
        client.close();
    }
}
