package com.qianlong.bean;

import org.mybatis.spring.annotation.MapperScan;

import java.util.*;
public class TestDao {
   private static Map<Integer,Object> map=null;
    static{
        map=new HashMap<Integer, Object>();
        map.put(1,new Test(1,"张三",1,18,"河南鹤壁"));
        map.put(2,new Test(2,"李四",0,56,"河南郑州"));
        map.put(3,new Test(3,"王五",1,32,"河南洛阳"));
    }

    private static Integer initId=4;
    public void save(Test test){
        if(test.getId()==null){
            test.setId(initId++);
        }

    }
    public Collection<Object> getAll(){
        return map.values();
    }

    public Object getById(Integer id){
        return map.get(id);
    }

    public void delete(Integer id){
        map.remove(id);
    }

    public static void main(String[] args) {
    TestDao dao=new TestDao();
        Collection<Object> all = dao.getAll();
        System.err.println(all);
    }

}
