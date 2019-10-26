package com.qianlong.service;

import com.qianlong.entity.TreeNode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PowerService {


    List<TreeNode> getPowerList(String emptel);
    /**
     * 查询员工
     * @return
     */
    List<Map> empList();

    List<Map> empLists(Integer id);

    public List<TreeNode> getCheckedList(String deptno);

    /**
     * 保存授权之前删除所有权限
     */
    int deletePower(String deptno);
    /**
     * 保存授权
     */
    int savePower(Integer intId,String deptno);
}
