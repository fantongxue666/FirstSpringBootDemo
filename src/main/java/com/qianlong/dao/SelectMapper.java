package com.qianlong.dao;

import com.qianlong.entity.Seller;
import com.qianlong.entity.Shangpin;
import com.qianlong.entity.Stu;

import com.qianlong.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface SelectMapper {
    /**
     * 注册
     */
    @Insert(value = "insert into user(account,password,money,vip,score) values(#{account},#{password},0,'普通会员',0)")
    int zhuce(User user);
    /**
     * 登陆
     */
    @Select(value = "select * from user where account=#{account} and password=#{password}")
    List<User> getUser(User user);
    /**
     * 根据手机号查询用户信息
     */
    @Select(value = "select * from user where account=#{account}")
    List<Map> getList(String account);
    /**
     * 后台卖家登陆
     */
    @Select(value = "select * from seller where account=#{account} and password=#{password}")
    List<Seller> seller(Seller seller);
    /**
     * 根据账号查卖家姓名
     */
    @Select(value = "select name from seller where account=#{account}")
    List<Map> getName(String account);
    /**
     * 卖家查询商品信息
     */
    @Select(value = "select * from shangpin where sellerAccount=#{phone}")
    List<Shangpin> getSP(String phone);
    /**
     * 上架商品（上传商品图片）
     */
    @Insert(value = "insert into shangpin(name,size,price,sellerAccount,number,color," +
            "preprice,picpath,time,kind,score) values(#{name},#{size},#{price}," +
            "#{sellerAccount},#{number},#{color},#{preprice},#{picpath},#{time},#{kind},#{score})")
    int insertSP(Shangpin shangpin);
    /**
     * 前台图片按分类展示
     */
    @Select(value = "select * from shangpin where kind=#{kind}")
    List<Map> getPic(String kind);
    /**
     * 商品查询总条数
     */
    @Select(value = "select count(*) as cou from shangpin group by sellerAccount having sellerAccount>=1 and sellerAccount=#{phone};")
    int getCount(String phone);
    /**
     * 商品分页查询
     */
    @Select(value = "select * from shangpin where sellerAccount=#{phone} limit #{pageNo},#{pageSize}")
    List<Map> pageList(Map map);
    /**
     * 根据id查询商品信息
     */
    @Select(value = "select * from shangpin where id=#{id}")
    List<Map> spMessage(Integer id);
    /**
     * 根据账号查询收货地址
     */
    @Select(value = "select * from address where account=#{account}")
    List<Map> getAddre(String account);

    @Select(value = "select * from address")
    List<Map> pageListss();
    /**
     * 查询默认地址
     */
    @Select(value = "select * from address where moren=1 and account=#{account}")
    List<Map> getMRAddress(String account);
    /**
     * 修改为默认地址，把0改为1
     */
    @Update(value = "update address set moren=1 where id=#{id}")
    int update(Integer id);
    /**
     * 首先把所有收货地址所有的是否默认字段全部改为0，也就是全部不是默认的
     */
    @Update(value = "update address set moren=0 where moren=1 and account=#{account};")
    int updatetwo(String account);
    /**
     * 省
     */
    @Select(value = "select * from province")
    List<Map> getProvince();
    /**
     * 市
     */
    @Select(value = "select * from city where provincecode=#{provincecode}")
    List<Map> getCity(String provincecode);
    /**
     * 区
     */
    @Select(value = "select * from area where citycode=#{citycode}")
    List<Map> getArea(String citycode);
    /**
     * 查name省
     */
    @Select(value = "select name from province where code=#{code}")
    List<Map> pro(String code);
    /**
     * 查name市
     */
    @Select(value = "select name from city where code=#{code}")
    List<Map> cit(String code);
    /**
     * 查name区
     */
    @Select(value = "select name from area where code=#{code}")
    List<Map> area(String code);
    /**
     * 添加收货地址
     */
    @Insert(value = "insert into address(recevier,location,detail,phone,account,moren) values(#{recevier},#{location},#{detail},#{phone},#{account},0)")
    int addAddress(Map map);
    /**
     * 后台订单页面
     */
    @Select(value = "SELECT * FROM `order` where maijia=#{phone};")
    List<Map> getOrder(String phone);
    /**
     * 前台商品下订单
     */
    @Insert(value = "insert into `order`(name,color,maijia,price,account,address,score,status,number,src) values(#{name},#{color},#{maijia},#{price},#{account},#{address},#{score},'等待发货',#{number},#{src})")
    int addOrder(Map map);
    /**
     * 根据用户账号查询代发货订单
     */
    @Select(value = "select * from `order` where account=#{account} and status='等待发货';")
    List<Map> getOrderByAccount(String account);
    /**
     * 查询待发货数量
     */
    @Select(value = "select count(*) from `order` where account=#{account} and status='等待发货';")
    int dfhNum(String account);
    /**
     * 卖家确认发货
     */
    @Update(value = "update `order` set status='等待收货' where id=#{id}")
    int qrfh(String id);
    /**
     * 根据用户账号查询代收货订单
     */
    @Select(value = "select * from `order` where account=#{account} and status='等待收货';")
    List<Map> shdd(String account);
    /**
     * 查询待收货数量
     */
    @Select(value = "select count(*) from `order` where account=#{account} and status='等待收货';")
    int dshNum(String account);
    /**
     * 用户确认收货
     */
    @Update(value = "update `order` set status='已收货' where id=#{id}")
    int qrsh(Integer id);
    /**
     * 用户查看待评价信息
     */
    @Select(value = "select * from `order` where status='已收货' and account=#{account}")
    List<Map> dpjxx(String account);
    /**
     * 查询待评价数量
     */
    @Select(value = "select count(*) from `order` where account=#{account} and status='已收货';")
    int dpjNum(String account);

    //###############################################################

    /**
     * 把用户发送的消息存到数据库中
     * @param map
     * @return
     */
    @Insert(value = "insert into chat(account,islook,message,addtime) values(#{account},1,#{message},#{addtime})")
    int chatInsert(Map map);
    /**
     * 查看所有未读消息的条数
     */
    @Select(value = "select count(*) from chat where islook=1")
    int selectCount();
    /**
     * 查询用户账号和对饮的未读消息的条数
     */
    @Select("select account,count(*) as count from chat where islook=1 group by account")
    List<Map> getWeidu();
    /**
     * 根据用户账号查询未读消息
     */
    @Select(value = "select * from chat where account=#{account} and islook=1")
    List<Map> getxiaoxi(String account);
    /**
     * 把该账号的所有未读消息改为已读
     */
    @Update(value = "update chat set islook=0 where account=#{account}")
    int changeYD(String account);
}
