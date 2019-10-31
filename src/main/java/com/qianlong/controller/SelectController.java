package com.qianlong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qianlong.entity.Cart;
import com.qianlong.entity.Seller;
import com.qianlong.entity.Shangpin;
import com.qianlong.entity.User;
import com.qianlong.service.SelectService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/demo")
public class SelectController {
    private int money;
    private String vip;
    private int score;
    private String phone;
    private String name;
    private int tempPageNo;
    @Autowired
    private SelectService selectService;

    @RequestMapping("/toChat")
    public String toChat(){
        return "chat";
    }
    /**
     * 跳转主页
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 跳转后台登陆页面
     */
    @RequestMapping("/backlogin")
    public String backlogin() {
        return "login";
    }

    /**
     * 跳转购买页面
     */
    @RequestMapping("/buy")
    public String buy() {
        return "item_show";
    }

    /**
     * 跳转登陆页面
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 跳转注册页面
     */
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 注册
     */
    @RequestMapping("/zhuce")
    public String zhuce(String account, String password) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        selectService.zhuce(user);
        Map map = new HashMap();
        map.put("success", "恭喜您注册成功");
        return "register";

    }

    /**
     * 登陆
     */
    @RequestMapping("/denglu")
    public String denglu(String account, String password, HttpSession session, Model model) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        List<User> getList = selectService.getUser(user);
        if (getList.size() > 0) {
            List<Map> list = selectService.getList(account);
            money = (int) list.get(0).get("money");
            vip = (String) list.get(0).get("vip");
            score = (int) list.get(0).get("score");
            session.setAttribute("account", account);
            return "redirect:/";
        } else {
            model.addAttribute("error", "登陆失败，估计是你没有注册，赶紧滚去注册");
            return "login";
        }
    }

    /**
     * 判断有无session
     */
    @ResponseBody
    @RequestMapping("/getSession")
    public Map getSession(HttpSession session) {
        String account = (String) session.getAttribute("account");
        Map map = new HashMap();
        map.put("account", account);
        map.put("money", money);
        map.put("vip", vip);
        map.put("score", score);
        return map;
    }

    /**
     * 跳转我的U袋页面
     */
    @RequestMapping("myUD")
    public String myUD() {
        return "udai_welcome";
    }

    /**
     * 清除session并推出
     */
    @RequestMapping("quit")
    public String quit(HttpSession session) {
        session.removeAttribute("account");
        session.removeAttribute("cartMap");
        return "redirect:/";
    }

    /**
     * 后台页面
     */
    @RequestMapping("backMain")
    public String backMain() {
        return "back/index";
    }

    /**
     * 后台登陆页
     */
    @RequestMapping("backLogin")
    public String backLogin() {
        return "back/login";
    }

    /**
     * 后台商品管理页面
     */
    @RequestMapping("backShangPin")
    public String backShangPin() {
        return "back/shangpin";
    }

    /**
     * 后台商品上架页面
     */
    @RequestMapping("backAddSP")
    public String backAddSP() {
        return "back/addSP";
    }

    /**
     * 后台卖家登陆
     */
    @RequestMapping("seToLogin")
    public String seToLogin(String account, String password, HttpSession session, Model model) {
        Seller seller = new Seller();
        seller.setAccount(account);
        seller.setPassword(password);
        List<Seller> getList = selectService.seller(seller);
        if (getList.size() > 0) {
            session.setAttribute("phone", seller.getAccount());
            phone = (String) session.getAttribute("phone");
            List<Map> name = selectService.getName(account);
            session.setAttribute("name", name.get(0));
            return "back/index";
        } else {
            model.addAttribute("error", "账号密码都他妈错了，银行卡号会错不会啊？");
            return "back/login";
        }
    }

    /**
     * 得到卖家的姓名
     */
    @RequestMapping("/getName")
    @ResponseBody
    public Object isSession(HttpSession session) {
        Object getName = session.getAttribute("name");
        return getName;
    }

    /**
     * 卖家查商品信息
     */
    @RequestMapping("/getSP")
    public String getSP(Model model, HttpSession session) {
        Object ph = session.getAttribute("phone");
        if (ph != null) {
            List<Shangpin> list = selectService.getSP(phone);
            model.addAttribute("list", list);
            return "back/shangpin";
        }
        return "back/shangpin";
    }

    /**
     * 商品图片上传
     */
    @RequestMapping("/upload")
    public String upload(@RequestParam(value = "pic") MultipartFile pic, @RequestParam Map param, Model model) throws ParseException {
        System.err.println("传过来的值" + param);
        if (pic.isEmpty()) {
            System.err.println("上传文件不可为空");
        }
        String fileName = pic.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.err.println("suffixName:" + suffixName);
        String filepath = "D:/IdeaProjects/FirstSpringBootDemo/src/main/resources/static/images/";
        fileName = UUID.randomUUID() + suffixName;
        System.err.println("fileName:" + fileName);
        File dest = new File(filepath + fileName);
        try {
            pic.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Shangpin shangpin = new Shangpin();
        //这里就不想改了，把fileName当成picpath传过去算了
        String picpath = fileName;
        shangpin.setPicpath(picpath);
        shangpin.setName((String) param.get("name"));
        shangpin.setSize((String) param.get("size"));
        double price = Double.parseDouble(param.get("price").toString());
        shangpin.setPrice(price);
        shangpin.setNumber(Integer.valueOf(param.get("number").toString()));
        shangpin.setColor((String) param.get("color"));
        double preprice = Double.parseDouble(param.get("preprice").toString());
        shangpin.setPreprice(preprice);
        shangpin.setKind((String) param.get("kind"));
        String sellerAccount = phone;
        shangpin.setSellerAccount(sellerAccount);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        shangpin.setTime(simpleDateFormat.parse((String) param.get("time")));
        String temp = (String) param.get("score");
        Integer score = Integer.valueOf(temp);
        shangpin.setScore(score);
        int i = selectService.insertSP(shangpin);
        if (i > 0) {
            model.addAttribute("info", "商品上传成功!");
            return "forward:page";
        }
        model.addAttribute("info", "商品上传失败!");
        return "forward:page";
    }

    /**
     * 前台按照分类显示商品图片
     */
    @RequestMapping("getPic")
    @ResponseBody
    public List<Map> getPic(String kind) {
        List<Map> picList = selectService.getPic(kind);
        return picList;
    }

    /**
     * 后台清缓存退出
     */
    @RequestMapping("/exit")
    public String exit(HttpSession session) {
        session.removeAttribute("phone");
        session.removeAttribute("name");
        return "back/login";
    }

    /**
     * 后台商品页面分页
     */
    @RequestMapping("/page")
    public String page(String pageNumber, Model model) {
        String spPage = pageNumber;
        //设置每页条数
        int pageSize = 5;
        //页数
        int pageNo = 0;
        if (spPage == null) {
                pageNo = 1;
        } else {
            pageNo = Integer.valueOf(spPage);
            if (pageNo < 1) {
                pageNo = 1;
            }
        }
        //设置最大页数
        int totalCount = 0;
        int count = selectService.getCount(phone);
        if (count > 0) {
            totalCount = count;
        }
        int maxPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        if (pageNo > maxPage) {
            pageNo = maxPage;
        }
        tempPageNo = (pageNo - 1) * pageSize;
        //计算总数量
        //分页查询
        Map map = new HashMap();
        map.put("pageNo", tempPageNo);
        map.put("pageSize", pageSize);
        map.put("phone", phone);
        List<Map> list = selectService.pageList(map);
        //最后把信息放入model转发到页面把信息带过去
        model.addAttribute("list", list);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("maxPage", maxPage);
        return "back/shangpin";

    }

    /**
     * 根据id获取商品信息
     */
    @RequestMapping("/getSPMessage")
    @ResponseBody
    public List<Map> getSPMessage(Integer id) {
        List<Map> list = selectService.spMessage(id);
        return list;
    }

    /**
     * 跳转支付页面
     */
    @RequestMapping("/toPay")
    public String toPay() {
        return "udai_shopcart_pay";
    }

    /**
     * 跳转购物车页面
     */
    @RequestMapping("/shoppingCar")
    public String car() {
        return "udai_shopcart";
    }

    /**
     * 跳转修改地址页面
     */
    @RequestMapping("/editAddress")
    public String editAddress() {
        return "udai_address_edit";
    }

    /**
     * 跳转添加新地址页面
     */
    @RequestMapping("/addAddress")
    public String addAddress(String account,Model model) {
        List<Map> list=selectService.getAddre(account);
        model.addAttribute("list",list);
        return "udai_address";
    }

    /**
     * 把颜色/尺寸/购买数量写入session
     */
    @RequestMapping("/toSession")
    @ResponseBody
    public int get(HttpSession session, String color, String size, String i, Integer spID,String maijia) {
        session.setAttribute("size", size);
        session.setAttribute("color", color);
        session.setAttribute("i", i);
        session.setAttribute("spID", spID);
        session.setAttribute("maijia",maijia);
        return 1;
    }

    /**
     * 再把信息拿出来
     */
    @RequestMapping("/getSecondSession")
    @ResponseBody
    public Map getSecond(HttpSession session) {
        String color = (String) session.getAttribute("color");
        String size = (String) session.getAttribute("size");
        String i = (String) session.getAttribute("i");
        Integer spID = (Integer) session.getAttribute("spID");
        Map map = new HashMap();
        map.put("color", color);
        map.put("size", size);
        map.put("i", i);
        map.put("spID", spID);
        return map;
    }

    /**
     * 我他妈就不信了
     */
    @RequestMapping("/getPCsecond")
    @ResponseBody
    public List<Map> getPCsecond(String ids) {
        Integer id=Integer.valueOf(ids);
        List<Map> list = selectService.spMessage(id);
        return list;
    }
    /**
     * 得到默认地址
     */
    @RequestMapping("getMRaddress")
    @ResponseBody
    public List<Map> getMR(String account){
        List<Map> getList=selectService.getMRAddress(account);
        return getList;
    }
    /**
     * 改为默认地址
     */
    @RequestMapping("/toUpdateAddress")
    @ResponseBody
    public int toUpdate(Integer id,String account){
        int j=selectService.updatetwo(account);
        int i=selectService.update(id);
        if(j>0){
            if(i>0){
                return i;
            }
        }
        return 0;
    }
    /**
     * 省
     */
    @RequestMapping("/getProvince")
    @ResponseBody
    public List<Map> getPro(){
        List<Map> getProvince=selectService.getProvince();
        return getProvince;
    }
    /**
     * 市
     */
    @RequestMapping("/getCity")
    @ResponseBody
    public List<Map> getCity(String provincecode){
        List<Map> getCity=selectService.getCity(provincecode);
        return getCity;
    }
    /**
     * 区
     */
    @RequestMapping("/getArea")
    @ResponseBody
    public List<Map> getArea(String citycode){
        List<Map> getArea=selectService.getArea(citycode);
        return getArea;
    }
    /**
     * 得到字符串省
     */
    @RequestMapping("getStringProvince")
    @ResponseBody
    public List<Map> getStringP(String code){
        List<Map> list=selectService.pro(code);
        return list;
    }
    /**
     * 得到字符串市
     */
    @RequestMapping("getStringCity")
    @ResponseBody
    public List<Map> getStringC(String code){
        List<Map> list=selectService.cit(code);
        return list;
    }
    /**
     * 得到字符串区
     */
    @RequestMapping("getStringArea")
    @ResponseBody
    public List<Map> getStringA(String code){
        List<Map> list=selectService.area(code);
        return list;
    }
    /**
     * 添加收货地址
     */
    @RequestMapping("/malegebi")
    public String addAddress(@RequestParam Map map,Model model){
        int i=selectService.addAddress(map);
        if(i>0){
            model.addAttribute("info","添加收货地址成功");
            return "forward:addAddress";
        }
        return "";
    }
    /**
     * 添加到购物车
     */
@RequestMapping("/shoppingCart")
@ResponseBody
    public int shoppingCart(String id,HttpSession session,String color,String size){
    Integer ids=Integer.valueOf(id);
    //根据id获取商品对象
    List<Map> list=selectService.spMessage(ids);
    Shangpin shangpin=new Shangpin();
    shangpin.setPicpath((String) list.get(0).get("picpath"));
    shangpin.setColor(color);
    shangpin.setSize(size);
    shangpin.setName((String)list.get(0).get("name"));
    shangpin.setPrice((Double) list.get(0).get("price"));
    shangpin.setId((Integer) list.get(0).get("id"));
    shangpin.setSellerAccount((String) list.get(0).get("sellerAccount"));
    //得到账号，为了后来在session中根据账号查询属于自己的购物车的商品
    String sellerAccount= (String) list.get(0).get("sellerAccount");
    //获取购物车
    Map<String, Cart> cartMap=(Map<String, Cart>)session.getAttribute("cartMap");
    //第一次添加商品到购物车
    if(cartMap==null){
        cartMap=new HashMap<String, Cart>();//实例化map对象
        //实例化购物车对象
        Cart cart=new Cart();
        cart.setShangpin(shangpin);
        cart.setNumber(1);
        //保存商品对象到map集合中
        cartMap.put(id,cart);
    }else{//第一次以后的操作
        Cart cart=cartMap.get("id");//根据商品id，获取购物车实体类
        if(cart!=null){//存在相同的商品
            cart.setNumber(cart.getNumber()+1);

        }else{
            cart=new Cart();
            cart.setShangpin(shangpin);
            cart.setNumber(1);
            cartMap.put(id,cart);
        }
    }
    //然后保存到session中
    session.setAttribute("cartMap",cartMap);
    return 1;
}
/**
 * 从session中取出购物车信息，并转发到购物车页面展示商品信息
 */
@RequestMapping("getShoppingCar")
    public String getShoppingCar(HttpSession session,Model model){
    Map<Integer,Cart> cartMap =(Map<Integer,Cart>)session.getAttribute("cartMap");
    model.addAttribute("carList",cartMap);
    return "udai_shopcart";
}
/**
 * 根据商品id删除购物车的某个商品
 */
@RequestMapping("/toDelSPById")
    public String toDelSP(String id,HttpSession session,Model model){
    System.err.println("id="+id);
    Map<Integer,Cart> cartList=(Map<Integer,Cart>)session.getAttribute("cartMap");
    Iterator iterator = cartList.keySet().iterator();
    while (iterator.hasNext()) {
        String key = (String) iterator.next();
        if (key.equals(id)) {
            iterator.remove();
            cartList.remove(key);
        }
    }
    session.setAttribute("carMap",cartList);
    model.addAttribute("carList",cartList);
    return "udai_shopcart";
}
/**
 * 计算购物车商品的数量，得到session中map的键值对的个数
 */
@RequestMapping("/getNum")
    @ResponseBody
    public Integer getNum(HttpSession session){
    Map<Integer,Cart> cartList=(Map<Integer,Cart>)session.getAttribute("cartMap");
    if(cartList==null){
        cartList=new HashMap<Integer, Cart>();
    }
    if(cartList.size()>0){
       int i=cartList.size();
       return i;
   }else{
        return 0;
    }
}
/**
 * 后台订单页面
 */
@RequestMapping("/fahuo")
    public String fahuo(){
    return "back/makeOrder";
}
/**
 * 得到订单，返回到后台订单页面
 */
@RequestMapping("getOrder")
    public String getOrder(Model model){
    List<Map> getOrder=selectService.getOrder(phone);
    model.addAttribute("list",getOrder);
    return "back/makeOrder";
}
/**
 * 前台客户下订单
 */
@RequestMapping("/makeOrder")
    @ResponseBody
    public int makeOrder(HttpSession session,String name,String color,double price,String account,String address,Integer score,String number,String src){
    Map map=new HashMap();
    map.put("maijia",session.getAttribute("maijia"));
    map.put("name",name);
    map.put("color",color);
    map.put("price",price);
    map.put("account",account);
    map.put("address",address);
    map.put("score",score);
    map.put("number",number);
    map.put("src",src);
    System.err.println(map);
    int i=selectService.addOrder(map);
    if(i>0){
        return i;
    }
    return 0;
}
/**
 * 根据用户账号查询代发货订单/收货订单/待评价订单
 */
@RequestMapping("/findOrderByAccount")
    public String findOrder(Model model,HttpSession session){
    String account = (String) session.getAttribute("account");
    List<Map> list=selectService.getOrderByAccount(account);
    model.addAttribute("list",list);
    List<Map> ssddList=selectService.shdd(account);
    model.addAttribute("shddList",ssddList);
    List<Map> dpjList=selectService.dpjxx(account);
    model.addAttribute("dpjList",dpjList);
    return "udai_welcome";
}
/**
 * 查询待发货数量
 */
@RequestMapping("/findDFHNum")
    @ResponseBody
    public int findDFH(HttpSession session){
    String account = (String) session.getAttribute("account");
    int i=selectService.dfhNum(account);
    if(i>0){
        return i;
    }else{
        return 0;
    }
}
/**
 * 卖家确认发货
 */
@RequestMapping("/qrfh")
    public String qrfh(String id){
    int i=selectService.qrfh(id);
    if(i>0){
        return "forward:getOrder";
    }
    return "";
}
    /**
     * 查询待收货数量
     */
    @RequestMapping("/findDSHNum")
    @ResponseBody
    public int findDSHNum(HttpSession session){
        String account = (String) session.getAttribute("account");
        int i=selectService.dshNum(account);
        if(i>0){
            return i;
        }else{
            return 0;
        }
    }
    /**
     * 用户确认收货
     */
    @RequestMapping("/QRSH")
    @ResponseBody
    public int qrsh(Integer id){
        int i=selectService.qrsh(id);
        if(i>0){
            return i;
        }else{
            return 0;
        }
    }
    /**
     * 查询待评价数量
     */
    @RequestMapping("/findDPJNum")
    @ResponseBody
    public int findDPJNum(HttpSession session){
        String account = (String) session.getAttribute("account");
        int i=selectService.dpjNum(account);
        if(i>0){
            return i;
        }else{
            return 0;
        }
    }

    /**
     * 远程调用接口测试
     */
    @RequestMapping("/jsonTest")
    @ResponseBody
    public JSONObject doHttpGet(){
        // 需要访问的接口路径
        String url="https://jsonview.com/example.json";
        // 配置请求信息（请求时间）
        RequestConfig rc = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
        // 获取使用DefaultHttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 返回结果
        String result = null;
        if(url!=null){
            try {
                // 创建HttpGet对象，将URL通过构造方法传入HttpGet对象
                HttpGet httpGet=new HttpGet(url);
                // 将配置好请求信息附加到http请求中
                httpGet.setConfig(rc);
                // 执行DefaultHttpClient对象的execute方法发送GET请求，通过CloseableHttpResponse接口的实例，可以获取服务器返回的信息
                CloseableHttpResponse response = httpclient.execute(httpGet);
                try {
                    // 得到返回对象
                    HttpEntity entity = response.getEntity();
                    if(entity!=null){
                        // 获取返回结果
                        result = EntityUtils.toString(entity);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    // 关闭到客户端的连接
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    // 关闭http请求
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //将得到的字符串转换成json对象(请求的本来就是把json格式数据以字符串的格式输出了)
        JSONObject jsonObject= JSON.parseObject(result);
        //返回json格式
        return jsonObject;
    }
        @RequestMapping("/kefu")
            public String tokefu(){
                return "back/kfgl";
        }

    //###################################################

    @RequestMapping("tiaoshu")
    @ResponseBody
    public int getTS(){
        int i = selectService.selectCount();
        return i;
    }
    @RequestMapping("getweidu")
    @ResponseBody
    public List<Map> getweidu(){
        return selectService.getWeidu();
    }
    @RequestMapping("/getxiaoxi")
    @ResponseBody
    public List<Map> getxiaoxi(String account){
        return selectService.getxiaoxi(account);
    }
    @RequestMapping("/changeYD")
    @ResponseBody
    public int changeYD(String account){
        return selectService.changeYD(account);
    }


    @Autowired
    FastFileStorageClient fastFileStorageClient;

    /**
     *fastDFS服务器测试文件上传
     */
    @RequestMapping("/fastdfs")
    @ResponseBody
    public String fastdfs(@RequestParam(value = "test") MultipartFile test) throws IOException {

        //文件名
        String fileName=test.getOriginalFilename();
        //后缀名
        String extName=fileName.substring(fileName.lastIndexOf(".")+1);
        //四个参数（输入流，文件大小，后缀名，null）,返回一个路径
        StorePath storePath = fastFileStorageClient.uploadFile(test.getInputStream(),test.getSize(), extName, null);
        //不同路径
        System.out.println(storePath.getFullPath());
        System.out.println(storePath.getPath());
        System.out.println(storePath.getGroup());
        return "图片上传成功，并调皮的给您返回一个路径";
    }

}