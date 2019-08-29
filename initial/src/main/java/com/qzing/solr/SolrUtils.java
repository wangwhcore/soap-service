package com.qzing.solr;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by wangwh on 2019/8/28.
 */
public class SolrUtils {

    protected static final Logger LOG = Logger.getLogger(SolrUtils.class);

    private final static String BASE_URL = "http://localhost:8983/solr/sunjs";

    /**
     * 创建SolrServer对象
     * <p>
     * 该对象有两个可以使用，都是线程安全的 1、CommonsHttpSolrServer：启动web服务器使用的，通过http请求的
     * 2、EmbeddedSolrServer：内嵌式的，导入solr的jar包就可以使用了
     * 3、solr 4.0之后好像添加了不少东西，其中CommonsHttpSolrServer这个类改名为HttpSolrClient
     *
     * @return
     */
    public static SolrClient createSolrServer() {
        return new HttpSolrClient.Builder(BASE_URL).build();
    }

    public static Integer addDucument(TBlogArticle article) {
        if (article == null) {
            return 30850;
        }
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", article.getLong("id"));
        doc.addField("category_id", article.getLong("category_id"));
        doc.addField("uuid", article.getStr("uuid"));
        doc.addField("visit_num", article.getInt("visit_num"));
        doc.addField("pic_url", article.getStr("pic_url"));
        doc.addField("author", article.getStr("author"));
        doc.addField("tags", article.getStr("tags"));
        doc.addField("tags_name", article.getStr("tags_name"));
        doc.addField("title", article.getStr("title"));
        doc.addField("digest", article.getStr("digest"));
        doc.addField("add_time", article.getDate("add_time"));
        docs.add(doc);
        SolrClient solrClient = createSolrServer();
        try {
            solrClient.add(docs);
            UpdateResponse rspcommit = solrClient.commit();
            return rspcommit.getStatus() == 0 ? 30800 : 30850;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info(ToolsUtils.print("solr", "索引失败", article.getStr("title"), e.getMessage()));
        } finally {
            try {
                LOG.info(ToolsUtils.print("solr", "索引成功", article.getStr("title")));
                solrClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 30850;
    }

    public static Integer deleteById(Object id) {
        if (id != null && StringUtils.isEmpty(id + "")) {
            return 30892;
        }
        SolrClient solrClient = createSolrServer();
        try {
            if (id.equals("*")) {
                //删除所有
                // Preparing the Solr document
                solrClient.deleteByQuery("*");
                UpdateResponse rspcommit = solrClient.commit();
                return rspcommit.getStatus() == 0 ? 30891 : 30892;
            } else {
                solrClient.deleteById(id + "");
                UpdateResponse rspcommit = solrClient.commit();
                return rspcommit.getStatus() == 0 ? 30891 : 30892;
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            LOG.info(ToolsUtils.print("solr", "删除索引失败", "ID:" + id, e.getMessage()));
        } finally {
            try {
                LOG.info(ToolsUtils.print("solr", "删除索引成功", "ID:" + id));
                solrClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 30892;
    }



//    public static Page<TBlogArticle> queryFromSolr(Integer pageNumber, Integer pageSize, String reqparam) {
//        Page<TBlogArticle> page = new Page<TBlogArticle>();
//        if (StringUtils.isEmpty(reqparam)) {
//            reqparam = "*";
//        }
//
//        SolrQuery query = new SolrQuery();
//        query.setHighlight(true);
//        query.addHighlightField("digest");
//        query.addHighlightField("title");
//        query.setHighlightSimplePre("<font color=\"red\">");
//        query.setHighlightSimplePost("</font>");
////        query.setHighlightSnippets(1);
////        query.setHighlightFragsize(2);
//        query.setStart((pageNumber - 1) * pageSize);
//        query.setRows(pageSize);
//        query.set("q", "title:" + reqparam + " OR " + "digest:" + reqparam);
//        List<TBlogArticle> articles = new ArrayList<TBlogArticle>();
//        SolrClient solrClient = createSolrServer();
//        try {
//            QueryResponse response = solrClient.query(query);
//            SolrDocumentList docList = response.getResults();
//            Map<String, Map<String, List<String>>> highlightMap = response.getHighlighting();
//
//            Iterator<SolrDocument> it = docList.iterator();
//            while (it.hasNext()) {
//                SolrDocument doc = it.next();
//                Long id = Long.valueOf(doc.getFieldValue("id").toString());
//                Long category_id = Long.valueOf(doc.getFieldValue("category_id") == null ? "0" : doc.getFieldValue("category_id").toString());
//                Integer visit_num = Integer.valueOf(doc.getFieldValue("visit_num") == null ? "0" : doc.getFieldValue("visit_num").toString());
//                String uuid = doc.getFieldValue("uuid") == null ? null : doc.getFieldValue("uuid").toString();
//                String pic_url = doc.getFieldValue("pic_url") == null ? null : doc.getFieldValue("pic_url").toString();
//                String author = doc.getFieldValue("author") == null ? null : doc.getFieldValue("author").toString();
//                String tags = doc.getFieldValue("tags") == null ? null : doc.getFieldValue("tags").toString();
//                String tags_name = doc.getFieldValue("tags_name") == null ? null : doc.getFieldValue("tags_name").toString();
//                String title = doc.getFieldValue("title") == null ? null : doc.getFieldValue("title").toString();
//                String digest = doc.getFieldValue("digest") == null ? null : doc.getFieldValue("digest").toString();
//                Date add_time = (Date) doc.getFieldValue("add_time");
//
//                TBlogArticle article = new TBlogArticle();
//                article.set("id", id);
//                article.set("category_id", category_id);
//                article.set("uuid", uuid);
//                article.set("visit_num", visit_num);
//                article.set("pic_url", pic_url);
//                article.set("author", author);
//                article.set("tags", tags);
//                article.put("tags_name", tags_name);
//                article.set("title", title);
//                article.set("digest", digest);
//                article.put("title_html", title);
//                article.put("digest_html", digest);
////                article.set("add_time", add_time);
//
//                List<String> titleList = highlightMap.get(id + "").get("title");
//                List<String> digestList = highlightMap.get(id + "").get("digest");
//                //获取并设置高亮的字段title
//                if (titleList != null && titleList.size() > 0) {
//                    article.put("title_html", titleList.get(0));
//                }
//                //获取并设置高亮的字段content
//                if (digestList != null && digestList.size() > 0) {
//                    article.put("digest_html", digestList.get(0));
//                }
//                articles.add(article);
//            }
//            Integer totalRow = Integer.valueOf(docList.getNumFound() + "");
//            Integer totalPage = (int) (totalRow % pageSize == 0 ? totalRow / pageSize : Math.ceil((double) totalRow / (double) pageSize));
//            page = new Page<TBlogArticle>(articles, pageNumber, pageSize, totalPage, totalRow);
//        } catch (SolrServerException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                solrClient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return page;
//    }
}
