package com.qzing.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestSolr2{
    //solr 服务器地址
    public static final String solrServerUrl = "http://localhost:8983/solr";
    //solrhome下的core
    public static final String solrCroeHome = "core1";

    public static String[] docs = {"Solr是一个独立的企业级搜索应用服务器",
            "它对外提供类似于Web-service的API接口",
            "用户可以通过http请求",
            "向搜索引擎服务器提交一定格式的XML文件生成索引",
            "也可以通过Http Get操作提出查找请求",
            "并得到XML格式的返回结果","gggg东软慧聚","东gg软g慧g聚","东  软慧  聚","  东软慧聚124","东软慧聚","东软慧聚股份有限公司"};
    public static void main(String[] args) {
        System.out.println("solr insert test");
    SolrClient client = new HttpSolrClient.Builder(solrServerUrl + "/" + solrCroeHome).build();

    List<SolrInputDocument> solrDocs = new ArrayList<>();
    for (int i = 0; i < docs.length; i++) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", i);
        doc.addField("content_text", docs[i]);
        solrDocs.add(doc);
    }

    try {
        client.add(solrDocs);
        client.commit();
        System.out.println("Insert Done");
    } catch (SolrServerException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

//    @Test
    public void testSolrQuery() throws IOException, SolrServerException {
        SolrClient client =// new HttpSolrClient(solrServerUrl + "/" + solrCroeHome);
                new HttpSolrClient.Builder(solrServerUrl + "/" + solrCroeHome).build();
        SolrQuery query = new SolrQuery("content_text:类似");

        // 设置查询关键字
        /* query.setQuery("Content:* AND Spare3:1 "); */
        // 指定查询返回字段
        /* query.setParam("fl", "Content,IndexTime"); */
        // 设置高亮
//        query.setHighlight(true).setHighlightSimplePre("<span class='red'>")
//                .setHighlightSimplePost("</span>");
//        query.setParam("hl.fl", "Content");//设置高亮字段
//        query.setParam("fl", "ID,Published");


        //排除条件      - NOT
        //wbQuery.addFilterQuery("OriginType:wb -Spare3:0");
        //wbQuery.addFilterQuery("OriginType:wb NOT Spare3:0");
        // 时间条件过滤
        /* query.addFilterQuery("Content:超哥"); */
        /*
         * query.addFilterQuery(
         * "Published:[1995-12-31T23:59:59.999Z TO 2016-03-06T00:00:00Z]");
         **/

        // 实现分页的查询
        query.setStart(0);
        query.setRows(10);


        // 设定排序，如果需要对field进行排序就必须在schema.xml中对该field配置stored="true"属性
        //set会清空原来的sort条件，add不会清空原来的，会在原来的基础上添加 sort=Published asc,Author asc(多条件排序)
//        query.setSort(IContentCommon.IndexField.Published.getName(),
//                SolrQuery.ORDER.asc);
//
//        query.addSort(IContentCommon.IndexField.Published.getName(),
//                SolrQuery.ORDER.asc);

        QueryResponse response = client.query(query);
        System.out.println(query);
        SolrDocumentList docs = response.getResults();
        System.out.println(docs.getNumFound());
        for(SolrDocument doc : docs){
            Object id = doc.get("id");
            System.out.println("id:" + id + ", content:" + doc.get("content_text"));

        }
        client.close();

    }

//    @Test
    public void testSolrDelete() throws IOException, SolrServerException {
        SolrClient client = new HttpSolrClient.Builder(solrServerUrl + "/" + solrCroeHome)
                .build();

        UpdateResponse response = client.deleteById("0");
        int status = response.getStatus();
        System.out.println("status:" + status);
        client.commit();
        client.close();
    }
}