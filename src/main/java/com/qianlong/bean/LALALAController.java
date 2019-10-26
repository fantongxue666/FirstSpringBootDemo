package com.qianlong.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LALALAController {
   // @Autowired
    private TestDao dao = new TestDao();

    @RequestMapping("begin")
    public String toBegin(){
        return "test/list";
    }
    @RequestMapping("toAdd")
    public String toA(Model model){
        Map map=new HashMap();
        map.put(1,"河南鹤壁");
        map.put(2,"河南郑州");
        map.put(3,"河南新乡");
        model.addAttribute("address",map);
        return "test/add";
    }

    //查询操作
    @RequestMapping("toSelect")
    public String getAl(Model model){
       Collection<Object> collection=dao.getAll();
       model.addAttribute("list",collection);
       return "test/list";
    }
    //查询地址

}
