package com.bazl.dna.lims.stats.controller;

import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.model.po.LoaPermission;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.po.OrgInfo;
import com.bazl.dna.lims.service.DictItemService;
import com.bazl.dna.lims.service.LoaPermissionService;
import com.bazl.dna.lims.service.OrgInfoService;
import com.bazl.dna.lims.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sun on 2018/12/19.
 */
@Controller
public class LoginController extends BaseController{

    @Autowired
    OrgInfoService orgInfoService;
    @Autowired
    LoaPermissionService permissionService;

    @Autowired
    DictItemService dictItemService;

    @RequestMapping("/login")
    public ModelAndView login(){
        //List<OrgInfo> orgInfos = orgInfoService.selectAll();
        ModelAndView view = new ModelAndView();
        //view.addObject("orgInfos", orgInfos);
        view.setViewName("login");
        return view;
    }

    @RequestMapping("/loginUser")
    public ModelAndView loginUser(String username,String password,HttpSession session) {
       // List<OrgInfo> orgInfos = orgInfoService.selectAll();
        ModelAndView view = new ModelAndView();
        //view.addObject("orgInfos", orgInfos);
        //授权认证
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            //完成登录
            subject.login(usernamePasswordToken);
            //获得用户对象
            LoaUserInfo user=(LoaUserInfo) subject.getPrincipal();
            //存入session
            session.setAttribute("user", user);
            //字典项所有数据存入session
            //List<DictItem> listDictItem = dictItemService.selectAllCode();
            //session.setAttribute("listDictItem", listDictItem);
           /* view.addObject("user", user);
            view.addObject("permissionList", getPermissionList());*/
            view.setViewName("redirect:/indexJsp");
            return view;
        } catch(Exception e) {
            logger.error(e.getMessage());
            view.addObject("meg", "用户名或密码错误！");
            view.setViewName("login");
            return view;
        }
    }

    @RequestMapping("/loginFromLims")
    public ModelAndView loginFromLims(HttpServletRequest request, String username, String password) {
        ModelAndView view = new ModelAndView();

        //授权认证
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            //完成登录
            subject.login(usernamePasswordToken);

            //获得用户对象
            LoaUserInfo user=(LoaUserInfo) subject.getPrincipal();

            //存入session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            view.addObject("user", user);
            view.addObject("permissionList", getPermissionList());
            view.setViewName("redirect:/indexJsp");
            return view;
        } catch(Exception e) {
            logger.error(e.getMessage());
            view.addObject("meg", "用户名或密码错误！");
            view.setViewName("login");
            return view;
        }
    }

    @RequestMapping("/indexJsp")
    public ModelAndView indexJsp(){
        ModelAndView view=new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        //获得用户对象
        LoaUserInfo user=(LoaUserInfo) subject.getPrincipal();

        view.addObject("user", user);
        view.addObject("permissionList", getPermissionList());

        view.setViewName("index");
        return  view;
    }


    /**
     * 获取二级菜单列表
     * @return
     */
    private List<LoaPermission> getPermissionList(){
        List<LoaPermission> permissionList = permissionService.selectDataStats();
        List<LoaPermission> result = new ArrayList<>();
        for(LoaPermission permission : permissionList){
            if(permission.getRootFlag().equals(Constants.permission_root_flase)){
                List<LoaPermission> nodes = new ArrayList<>();
                for(LoaPermission permissionSec : permissionList){
                    if(StringUtils.isNotBlank(permissionSec.getParentId()) && permissionSec.getParentId().equals(permission.getPermissionId())){
                        nodes.add(permissionSec);
                    }
                }
                if(ListUtils.isNotNullAndEmptyList(nodes)){
                    permission.setPermissionList(nodes);
                }
                result.add(permission);
            }
        }
        return permissionList;
    }

    @RequestMapping("/loginOut")
    public ModelAndView loginOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        List<OrgInfo> orgInfos = orgInfoService.selectAll();
        ModelAndView view = new ModelAndView();
        view.addObject("orgInfos", orgInfos);
        view.setViewName("login");
        return view;
    }

}
