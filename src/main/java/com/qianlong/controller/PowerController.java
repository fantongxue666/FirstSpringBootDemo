package com.qianlong.controller;

import com.alibaba.fastjson.JSONObject;
import com.qianlong.entity.TreeNode;
import com.qianlong.service.PowerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/power")
public class PowerController {

    @Autowired
    private PowerService powerService;
    /**
     * 权限树json数据，这个接口是index.html页面访问的，目的是渲染权限树
     * 显示不同角色对应的不同权限
     * @return
     */
    @ResponseBody
    @RequestMapping("/tree")
    public List<TreeNode> getList(HttpSession session){
        String phone = (String) session.getAttribute("phone");
        return powerService.getPowerList(phone);
    }
    /**
     * 授权，得到角色对应的权限（多选框加上勾勾）
     * 这个也是去拿权限树列表
     * 1，这个权限是整张权限表的权限
     * 2，返回的权限json数据带有checked的属性被赋值了（因为页面多选框判断勾勾）
     */
    @RequestMapping("/checkedTree")
    @ResponseBody
    public List<TreeNode> getCheckedList(String deptno){
        return powerService.getCheckedList(deptno);
    }

    /**
     * 查询emp表
     */
    @RequestMapping("getEmp")
    public String getEmp(Model model){
        List<Map> maps = powerService.empList();
        model.addAttribute("list",maps);
        return "back/givePower";
    }
    @RequestMapping("getEmpById")
    @ResponseBody
    public List<Map> getEmps(Integer id){
        List<Map> maps = powerService.empLists(id);
        return maps;
    }
    /**
     * 授权页面
     */
    @RequestMapping("/givePower")
    public String giveP(){
        return "back/dept";
    }
    /**
     * 点击保存授权，接收授权时勾选的权限对应id，并保存授权
     */
    @RequestMapping("/savePower")
    @ResponseBody
    public JSONObject savePower(String tempArr, String deptno){//参数：页面的权限对应id（html页面拼接成了“12,5,7”格式的字符串），部门编号
        int j=0;
        //把拼接成了“12,5,7”格式的字符串转为数组的格式
        String[] arr=tempArr.split(",");
        //创建一个list集合，对arr数组进行遍历，把数组中的内容全部放到list集合里
        List<String> ids=new ArrayList<String>(arr.length);
        for(String s:arr){
            ids.add(s);
        }
        //保存之前删除所有权限
        powerService.deletePower(deptno);
        //对权限对应id的集合进行遍历，然后进行插入
        for(String id:ids){
        Integer intId=Integer.valueOf(id);
        //在tb_dept_power表插入数据，给角色保存权限
        int i = powerService.savePower(intId, deptno);
        j+=1;
        }
        JSONObject jsonObject=new JSONObject();

        if(j>0){
            jsonObject.put("success","恭喜，保存权限成功！");
            return jsonObject;
        }else{

            jsonObject.put("error","不好意思，您保存失败了！");
            return jsonObject;
        }

    }

}
