package com.qzing.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangwh on 2019/8/29.
 */
public class TestSolrInput {

    //solr 服务器地址
    public static final String solrServerUrl = "http://localhost:8983/solr";
    //solrhome下的core
    public static final String solrCroeHome = "core1";

    public static String[] docs = {"Solr是一个独立的企业级搜索应用服务器",
            "它对外提供类似于Web-service的API接口",
            "用户可以通过http请求",
            "向搜索引擎服务器提交一定格式的XML文件生成索引",
            "也可以通过Http Get操作提出查找请求",
            "并得到XML格式的返回结果","gggg东软慧聚","东gg软g慧g聚","东  软慧  聚","  东软慧聚124",
            "dongruanhuiju","gggg东软gggg慧gg聚gg","东gg软g慧g聚 产一","东  软慧  聚 地址","  东软慧聚125",
            "中软慧聚125","慧聚东软股份有限公司","慧聚东软","东方软件慧聚","东部软智慧才聚股份有限公司","奇秦科技","奇%……秦_+科/>技","奇！秦2科  技",
            "东方通讯股份公司", "易方达股份公司", "四方伟业信息技术股份有限公司", "蔡恒国际股份有限公司", "中科二院股份有限公司",

            "东软慧聚","东软慧聚股份有限公司"};

    public static void main(String[] args) throws IOException, SolrServerException {
        System.out.println("solr insert test");
        SolrClient client = new HttpSolrClient.Builder(solrServerUrl + "/" + solrCroeHome).build();

        List<SolrInputDocument> solrDocs = new ArrayList<>();
        for (int i = 0; i < docs.length; i++) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", "new"+1000+i);
            doc.addField("content_text", docs[i]);
            if(i>10){

            doc.addField("name", docs[i]+"齐心");
            doc.addField("logo", docs[i]+"品牌");
            }

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
}
