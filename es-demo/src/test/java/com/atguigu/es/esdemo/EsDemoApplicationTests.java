package com.atguigu.es.esdemo;

import io.searchbox.client.JestClient;
import io.searchbox.core.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class EsDemoApplicationTests {
    @Autowired
    JestClient jestClient;

    @Test
    void contextLoads() {
        System.out.println(jestClient);
    }

    /**
     * 向es中插入数据
     *
     * @throws IOException
     */
    @Test
    public void textJest() throws IOException {
        //update delect search index
        //crud.builder(携带数据).其他信息.build();
        User user = new User();
        user.setEmail("123456@");
        user.setUserName("atguigu");
        Index build = new Index.Builder(user).index("user").type("type").build();
        DocumentResult execute = jestClient.execute(build);
        System.out.println("执行完成" + execute.isSucceeded());
    }

    @Test
    public void query() throws IOException {
        String queryJson = "{\n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  }\n" +
                "}\n";
        Search user = new Search.Builder(queryJson).addIndex("user").build();
        SearchResult execute = jestClient.execute(user);

        System.out.println("查询完成" + execute.isSucceeded() + "最大得分：" + execute.getMaxScore() + "总记录数：" + execute.getTotal());
        System.out.println("查询到的数据：");
        List<SearchResult.Hit<User, Void>> hits = execute.getHits(User.class);
        hits.forEach((hit)->{
            User source = hit.source;
            System.out.println(source.toString());
        });
    }


    @Test
    public  void deleteProductFromEs() {
        Delete delete = new Delete.Builder("7").index("product")
                .type("product")
                .build();
        try {
            DocumentResult execute = jestClient.execute(delete);
            if(execute.isSucceeded()){
              //log.info("商品：{} ==》ES下架完成",id);
            }else {
                //deleteProductFromEs(id);
               // log.error("商品：{} ==》ES下架失败",id);
            }
        }catch (Exception e){
            //deleteProductFromEs(id);
          //  log.error("商品：{} ==》ES下架失败",id);
        }

    }
}



class User {
    private String userName;
    private String email;

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
