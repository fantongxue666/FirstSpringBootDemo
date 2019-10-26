package com.qianlong.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianlong.service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/page")
public class PageController {
    @Autowired
    SelectService selectService;
    @RequestMapping("list")
    public String list(Integer pn,Model model){

        List<String[]> resource=new ArrayList<String[]>();
        resource.add(new String[]{"男","樊江锋","努力学习的好榜样"});
        resource.add(new String[]{"女","张志华","每天玩游戏"});
        resource.add(new String[]{"男","袁梦阳","也开始正经学习了"});
        for (String[] i:resource){
            System.out.println(Arrays.toString(i));
            for (int j = 0; j < i.length; j++) {
                String first=i[j];

            }
        }

        List<Map> list=selectService.pageListss(pn);
        model.addAttribute("list",list);
        return "test";
    }


}
