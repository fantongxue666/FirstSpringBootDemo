package com.qianlong.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianlong.dao.SelectMapper;

import com.qianlong.entity.Seller;
import com.qianlong.entity.Shangpin;
import com.qianlong.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SelectServiceImpl implements SelectService {
    @Autowired
    private SelectMapper selectMapper;

    @Override
    public List<Map> pageListss(Integer pn) {
        if(pn==null){
            pn=1;
        }
        PageHelper.startPage(pn,3);
        List<Map> pageList=selectMapper.pageListss();
        PageInfo<Map> pageInfo=new PageInfo(pageList);
        return pageList;
    }

    @Override
    public int zhuce(User user) {
        int i=selectMapper.zhuce(user);
        return i;
    }

    @Override
    public List<Map> getName(String account) {
        return selectMapper.getName(account);
    }

    @Override
    public List<Shangpin> getSP(String phone) {
        return selectMapper.getSP(phone);
    }

    @Override
    public List<Seller> seller(Seller seller) {
        return selectMapper.seller(seller);
    }

    @Override
    public List<User> getUser(User user) {
        return selectMapper.getUser(user);
    }

    @Override
    public int insertSP(Shangpin shangpin) {
        int i=selectMapper.insertSP(shangpin);
        return i;
    }

    @Override
    public List<Map> pageList(Map map) {
        return selectMapper.pageList(map);
    }

    @Override
    public List<Map> spMessage(Integer id) {
        return selectMapper.spMessage(id);
    }

    @Override
    public int update(Integer id) {
        return selectMapper.update(id);
    }

    @Override
    public List<Map> cit(String code) {
        return selectMapper.cit(code);
    }

    @Override
    public int addAddress(Map map) {
        return selectMapper.addAddress(map);
    }

    @Override
    public List<Map> area(String code) {
        return selectMapper.area(code);
    }

    @Override
    public List<Map> pro(String code) {
        return selectMapper.pro(code);
    }

    @Override
    public List<Map> getArea(String citycode) {
        return selectMapper.getArea(citycode);
    }

    @Override
    public List<Map> getCity(String provincecode) {
        return selectMapper.getCity(provincecode);
    }

    @Override
    public List<Map> getProvince() {
        return selectMapper.getProvince();
    }

    @Override
    public int dfhNum(String account) {
        return selectMapper.dfhNum(account);
    }

    @Override
    public List<Map> getOrderByAccount(String account) {
        return selectMapper.getOrderByAccount(account);
    }

    @Override
    public int addOrder(Map map) {
        return selectMapper.addOrder(map);
    }

    @Override
    public int dshNum(String account) {
        int i=selectMapper.dshNum(account);
        return i;
    }

    @Override
    public int dpjNum(String account) {
        int i=selectMapper.dpjNum(account);
        return i;
    }

    @Override
    public int chatInsert(Map map) {
        return selectMapper.chatInsert(map);
    }

    @Override
    public List<Map> dpjxx(String account) {
        return selectMapper.dpjxx(account);
    }

    @Override
    public int qrsh(Integer id) {
        int i=selectMapper.qrsh(id);
        return i;
    }

    @Override
    public List<Map> shdd(String account) {
        return selectMapper.shdd(account);
    }

    @Override
    public int qrfh(String id) {
        int i=selectMapper.qrfh(id);
        return i;
    }

    @Override
    public List<Map> getOrder(String phone) {
        return selectMapper.getOrder(phone);
    }

    @Override
    public int updatetwo(String account) {
        return selectMapper.updatetwo(account);
    }

    @Override
    public List<Map> getMRAddress(String account) {
        return selectMapper.getMRAddress(account);
    }

    @Override
    public List<Map> getAddre(String account) {
        return selectMapper.getAddre(account);
    }

    @Override
    public int getCount(String phone) {
        int i=selectMapper.getCount(phone);
        return i;
    }

    @Override
    public List<Map> getPic(String kind) {
        return selectMapper.getPic(kind);
    }

    @Override
    public List<Map> getList(String account) {
        return selectMapper.getList(account);
    }
}
