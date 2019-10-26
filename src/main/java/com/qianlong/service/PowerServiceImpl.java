package com.qianlong.service;

import com.qianlong.dao.PowerMapper;
import com.qianlong.entity.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    private PowerMapper powerMapper;

    /**
     * 查询对应权限的权限树的所有数据
     * @param emptel
     * @return
     */
    @Override
    public List<TreeNode> getPowerList(String emptel) {
        //根据手机号查询对应的权限
        List<TreeNode> powerList = powerMapper.getPowerList(emptel);
        //临时集合
        List<TreeNode> tempList=new ArrayList<TreeNode>();
        if(powerList!=null&&powerList.size()>0){
            //对角色对应的权限遍历
            for(TreeNode treeNode:powerList){
                //说明是一级节点
                if(treeNode.getParentId()==0){
                    //添加到临时集合并返回
                    tempList.add(treeNode);
                    //递归绑定子节点，就是自己找自己的孩子
                    bindChildren(treeNode,powerList);
                }
            }
        }
            return tempList;
    }
    /**
     * 递归绑定所有子节点（这个方法可以通用，就是自己找自己的孩子）
     * @param treeNode
     * @param powerList
     */
    public void bindChildren(TreeNode treeNode,List<TreeNode> powerList){
        for(TreeNode childrenTreeNode:powerList){
            if(treeNode.getId()==childrenTreeNode.getParentId()){
                List<TreeNode> children = treeNode.getChildren();
                if(children==null){
                    List<TreeNode> childTempList=new ArrayList<TreeNode>();
                    childTempList.add(childrenTreeNode);
                    treeNode.setChildren(childTempList);
                }else{
                    children.add(childrenTreeNode);
                }
                bindChildren(childrenTreeNode,powerList);
            }
        }
    }

    /**
     * 查询emp表
     * @return
     */
    @Override
    public List<Map> empList() {
        return powerMapper.empList();
    }
    @Override
    public List<Map> empLists(Integer id) {
        return powerMapper.empLists(id);
    }

    @Override
    public List<TreeNode> getCheckedList(String deptno) {
        //查询整张权限表所有的权限数据
        List<TreeNode> powerList = powerMapper.powerList();
        //去部门权限关联表中，根据deptno查询该角色拥有的对应的权限（就是roleId的集合）
        List<Map> powerByRoleIdList = powerMapper.powerByRoleIdList(deptno);

        if(powerList!=null&&powerList.size()>0){
            //对所有权限进行遍历
            for(TreeNode powers:powerList){
                if(powerByRoleIdList!=null&&powerByRoleIdList.size()>0){
                    //再对roleId的集合进行遍历
                    for(Map map:powerByRoleIdList){
                        //如果权限表的一条数据的powerid等于roleId的值，则该权限表的这条数据的checked属性设为“checked”（这里是字符串格式）
                        if(map.get("powerid").equals(powers.getId())){
                            powers.setChecked("checked");
                        }
                    }
                }
            }

        }
        //建一个临时集合
       List<TreeNode> tempList=new ArrayList<TreeNode>();
        if(powerList.size()>0&&powerList!=null){
            //上面已经把所有的权限数据都已经设置checked属性了
            //然后对已经被设置checked属性的所有权限数据进行遍历
            for(TreeNode treeNode:powerList){
                //如果是一级节点，把该节点添加到临时集合
                if(treeNode.getParentId() == 0){//说明是一级节点
                    tempList.add(treeNode);
                    //递归绑定子节点
                    bindChildren(treeNode,powerList);
                }
            }
        }
        //最后返回
        return tempList;
    }

    @Override
    public int deletePower(String deptno) {
        return powerMapper.deletePower(deptno);
    }


    @Override
    public int savePower(Integer intId, String deptno) {
        return powerMapper.savePower(intId,deptno);
    }


}
