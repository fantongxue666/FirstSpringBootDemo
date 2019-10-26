package com.qianlong.service;

import com.qianlong.dao.SelectMapper;
import com.qianlong.entity.Seller;
import com.qianlong.entity.Shangpin;
import com.qianlong.entity.Stu;

import com.qianlong.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface SelectService {
    List<Map> pageListss( Integer pn);

/**
 * 注册
 */
int zhuce(User user);
/**
 * 登陆
 */
List<User> getUser(User user);
    /**
     * 根据手机号查询用户信息
     */
    List<Map> getList(String account);
    /**
     * 后台卖家登陆
     */
    List<Seller> seller(Seller seller);
    /**
     * 根据账号查卖家姓名
     */
    List<Map> getName(String account);
    /**
     * 卖家查询商品信息
     */
    List<Shangpin> getSP(String phone);
    /**
     * 上架商品（上传商品图片）
     */
    int insertSP(Shangpin shangpin);
    /**
     * 前台图片按分类展示
     */
    List<Map> getPic(String kind);
    /**
     * 商品分页查询总条数
     */
    int getCount(String phone);
    /**
     * 商品分页查询
     */
    List<Map> pageList(Map map);
    /**
     * 根据id查询商品信息
     */
    List<Map> spMessage(Integer id);
    /**
     * 根据账号查询收货地址
     */
    List<Map> getAddre(String account);
    /**
     * 查询默认地址
     */
    List<Map> getMRAddress(String account);
    /**
     * 修改为默认地址
     */
    int update(Integer id);
    /**
     * 首先把所有收货地址所有的是否默认字段全部改为0，也就是全部不是默认的
     */
    int updatetwo(String account);
    /**
     * 省
     */
    List<Map> getProvince();
    /**
     * 市
     */
    List<Map> getCity(String provincecode);
    /**
     * 区
     */
    List<Map> getArea(String citycode);
    /**
     * 查name
     */
    List<Map> pro(String code);
    /**
     * 查name市
     */
    List<Map> cit(String code);
    /**
     * 查name区
     */
    List<Map> area(String code);
    /**
     * 添加收货地址
     */
    int addAddress(Map map);
    /**
     * 后台订单页面
     */
    List<Map> getOrder(String phone);
    /**
     * 前台商品下订单
     */
    int addOrder(Map map);
    /**
     * 根据用户账号查询代发货订单
     */
    List<Map> getOrderByAccount(String account);
    /**
     * 查询待发货数量
     */
    int dfhNum(String account);
    /**
     * 卖家确认发货
     */
    int qrfh(String id);
    /**
     * 根据用户账号查询代收货订单
     */
    List<Map> shdd(String account);
    /**
     * 查询待收货数量
     */
    int dshNum(String account);
    /**
     * 用户确认收货
     */
    int qrsh(Integer id);
    /**
     * 用户查看待评价信息
     */
    List<Map> dpjxx(String account);
    /**
     * 查询待评价数量
     */
    int dpjNum(String account);
}
