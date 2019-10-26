package com.qianlong.dao;

import com.qianlong.entity.TreeNode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PowerMapper {

    /**
     * 根据手机号查询对应的所有权限
     * @return
     */
    @Select(value = "select id,name,parentid,state,iconcls,url from tb_power  where id in (select powerid from tb_dept_power where deptno=(select deptno from emp where emptel=#{emptel}))")
    List<TreeNode> getPowerList(String emptel);

    /**
     * 查询员工
     * @return
     */
    @Select(value = "select * from emp")
    List<Map> empList();
    @Select(value = "select * from emp where empid=#{id}")
    List<Map> empLists(Integer id);

    /**
     * 查询所有的权限表数据
     */
    @Select(value = "select id,name,parentid,state,iconcls,url from tb_power")
    List<TreeNode> powerList();
    /**
     * 查询角色拥有的权限
     */
    @Select(value = "select powerid from tb_dept_power where deptno=#{deptno}")
    List<Map> powerByRoleIdList(String deptno);
    /**
     * 保存授权
     */
    @Insert(value = "insert into tb_dept_power(deptno,powerid) values (#{deptno},#{intId})")
    int savePower(@Param("intId") Integer intId,@Param("deptno") String deptno);
    /**
     * 保存授权之前删除所有权限
     */
    @Delete(value = "delete from tb_dept_power where deptno=#{deptno}")
    int deletePower(String deptno);
}
