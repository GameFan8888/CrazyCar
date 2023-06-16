package com.tastsong.crazycar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tastsong.crazycar.common.Result;
import com.tastsong.crazycar.model.UserModel;
import com.tastsong.crazycar.service.LoginService;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@Scope("prototype")
@Slf4j
@RequestMapping(value = "/v2/Background")
public class BackgroundUser {
    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/userInfo")
    public JSONObject userInfo() throws Exception {
        JSONObject data = new JSONObject();
        data.putOpt("roles", "admin");
        data.putOpt("introduction", "I am a super administrator");
        data.putOpt("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.putOpt("name", "Super Admin");
        log.info(data.toString());
        return data;
    }

    @PostMapping(value = "/logout")
    public Object logout() throws Exception {
        return Result.success();
    }

    @GetMapping(value = "getUserByUserName")
    public Object getUserByUserName(@RequestParam("user_name") String user_name) throws Exception {
        JSONObject data = new JSONObject();
        JSONArray items = new JSONArray();
        items.add(loginService.getUserByName(user_name));
        data.putOpt("items", items);
        data.putOpt("total", 1);
        return data;
    }

    @PostMapping(value = "updateUser")
    public Object updateUser(@RequestBody JSONObject body) throws Exception{
        UserModel userModel = new UserModel();
        userModel.uid = body.getInt("uid");
        userModel.user_name = body.getStr("user_name");
        userModel.star = body.getInt("star");
        userModel.is_vip = body.getBool("is_vip");
        loginService.updateUser(userModel);
        return loginService.getUserByName(userModel.user_name);
    }
    
    String asyncRouters = "[\n" + //
            "    {\n" + //
            "        \"redirect\":\"/form/index\",\n" + //
            "        \"level\":0,\n" + //
            "        \"parentId\":0,\n" + //
            "        \"path\":\"/form\",\n" + //
            "        \"component\":\"Layout\",\n" + //
            "        \"createTime\":\"2020-02-07T08:29:13.000+00:00\",\n" + //
            "        \"children\":[\n" + //
            "            {\n" + //
            "                \"path\":\"form\",\n" + //
            "                \"component\":\"form/index\",\n" + //
            "                \"meta\":{\n" + //
            "                    \"roles\":[\n" + //
            "                        \"admin\"\n" + //
            "                    ],\n" + //
            "                    \"icon\":\"form\",\n" + //
            "                    \"title\":\"form\"\n" + //
            "                },\n" + //
            "                \"name\":\"form\"\n" + //
            "            }\n" + //
            "        ],\n" + //
            "        \"meta\":{\n" + //
            "            \"icon\":\"form\",\n" + //
            "            \"title\":\"form\"\n" + //
            "        },\n" + //
            "        \"name\":\"form\",\n" + //
            "        \"id\":21\n" + //
            "    }\n" + //
            "]";

    String allRouters = "[\n" + //
            "    {\n" + //
            "        \"redirect\":\"/form/index\",\n" + //
            "        \"level\":0,\n" + //
            "        \"parentId\":0,\n" + //
            "        \"path\":\"/form\",\n" + //
            "        \"component\":\"Layout\",\n" + //
            "        \"createTime\":\"2020-02-07T08:29:13.000+00:00\",\n" + //
            "        \"children\":[\n" + //
            "            {\n" + //
            "                \"path\":\"form\",\n" + //
            "                \"component\":\"form/index\",\n" + //
            "                \"meta\":{\n" + //
            "                    \"roles\":[\n" + //
            "                        \"admin\"\n" + //
            "                    ],\n" + //
            "                    \"icon\":\"form\",\n" + //
            "                    \"title\":\"form\"\n" + //
            "                },\n" + //
            "                \"name\":\"form\"\n" + //
            "            }\n" + //
            "        ],\n" + //
            "        \"meta\":{\n" + //
            "            \"icon\":\"form\",\n" + //
            "            \"title\":\"form\"\n" + //
            "        },\n" + //
            "        \"name\":\"form\",\n" + //
            "        \"id\":21\n" + //
            "    },\n" + //
            "    {\n" + //
            "        \"path\":\"/permission\",\n" + //
            "        \"component\":\"layout/Layout\",\n" + //
            "        \"redirect\":\"/permission/index\",\n" + //
            "        \"alwaysShow\":true,\n" + //
            "        \"meta\":{\n" + //
            "            \"title\":\"Permission\",\n" + //
            "            \"icon\":\"lock\",\n" + //
            "            \"roles\":[\n" + //
            "                \"admin\",\n" + //
            "                \"editor\"\n" + //
            "            ]\n" + //
            "        },\n" + //
            "        \"children\":[\n" + //
            "            {\n" + //
            "                \"path\":\"page\",\n" + //
            "                \"component\":\"views/permission/page\",\n" + //
            "                \"name\":\"PagePermission\",\n" + //
            "                \"meta\":{\n" + //
            "                    \"title\":\"Page Permission\",\n" + //
            "                    \"roles\":[\n" + //
            "                        \"admin\"\n" + //
            "                    ]\n" + //
            "                }\n" + //
            "            },\n" + //
            "            {\n" + //
            "                \"path\":\"directive\",\n" + //
            "                \"component\":\"views/permission/directive\",\n" + //
            "                \"name\":\"DirectivePermission\",\n" + //
            "                \"meta\":{\n" + //
            "                    \"title\":\"Directive Permission\"\n" + //
            "                }\n" + //
            "            },\n" + //
            "            {\n" + //
            "                \"path\":\"role\",\n" + //
            "                \"component\":\"views/permission/role\",\n" + //
            "                \"name\":\"RolePermission\",\n" + //
            "                \"meta\":{\n" + //
            "                    \"title\":\"Role Permission\",\n" + //
            "                    \"roles\":[\n" + //
            "                        \"admin\"\n" + //
            "                    ]\n" + //
            "                }\n" + //
            "            }\n" + //
            "        ]\n" + //
            "    },\n" + //
            "    {\n" + //
            "        \"path\":\"/icon\",\n" + //
            "        \"component\":\"layout/Layout\",\n" + //
            "        \"children\":[\n" + //
            "            {\n" + //
            "                \"path\":\"index\",\n" + //
            "                \"component\":\"views/icons/index\",\n" + //
            "                \"name\":\"Icons\",\n" + //
            "                \"meta\":{\n" + //
            "                    \"title\":\"Icons\",\n" + //
            "                    \"icon\":\"icon\",\n" + //
            "                    \"noCache\":true\n" + //
            "                }\n" + //
            "            }\n" + //
            "        ]\n" + //
            "    }\n" + //
            "]";            

    @GetMapping(value = "getNav")
    public Object getNav() throws Exception {
        JSONArray items = new JSONArray();
        items = JSONUtil.parseArray(asyncRouters);
       
        return items;
    }

    @GetMapping(value = "getAllRoutes")
    public Object getAllRoutes() throws Exception {
        
        JSONArray items = new JSONArray();
        items = JSONUtil.parseArray(allRouters);
       
        return items;
    }


    @GetMapping(value = "getRoutes")
    public Object getRoutes() throws Exception {
        log.info(allRouters);
        JSONArray items = new JSONArray();
        items = JSONUtil.parseArray(asyncRouters);
       
        return items;
    }

    @GetMapping(value = "getRoles")
    public Object getRoles() throws Exception {
            String roles = "[\n" + //
                    "  {\n" + //
                    "    key: 'admin',\n" + //
                    "    name: 'admin',\n" + //
                    "    description: 'Super Administrator. Have access to view all pages.',\n" + //
                    "    routes: [{\n" + //
                    "      path: '',\n" + //
                    "      redirect: 'dashboard',\n" + //
                    "      children: [\n" + //
                    "        {\n" + //
                    "          path: 'dashboard',\n" + //
                    "          name: 'Dashboard',\n" + //
                    "          meta: { title: 'dashboard', icon: 'dashboard' }\n" + //
                    "        }\n" + //
                    "      ]\n" + //
                    "    }]\n" + //
                    "  },\n" + //
                    "  {\n" + //
                    "    key: 'editor',\n" + //
                    "    name: 'editor',\n" + //
                    "    description: 'Normal Editor. Can see all pages except permission page',\n" + //
                    "    routes: [{\n" + //
                    "      path: '',\n" + //
                    "      redirect: 'dashboard',\n" + //
                    "      children: [\n" + //
                    "        {\n" + //
                    "          path: 'dashboard',\n" + //
                    "          name: 'Dashboard',\n" + //
                    "          meta: { title: 'dashboard', icon: 'dashboard' }\n" + //
                    "        }\n" + //
                    "      ]\n" + //
                    "    }]\n" + //
                    "  },\n" + //
                    "  {\n" + //
                    "    key: 'visitor',\n" + //
                    "    name: 'visitor',\n" + //
                    "    description: 'Just a visitor. Can only see the home page and the document page',\n" + //
                    "    routes: [{\n" + //
                    "      path: '',\n" + //
                    "      redirect: 'dashboard',\n" + //
                    "      children: [\n" + //
                    "        {\n" + //
                    "          path: 'dashboard',\n" + //
                    "          name: 'Dashboard',\n" + //
                    "          meta: { title: 'dashboard', icon: 'dashboard' }\n" + //
                    "        }\n" + //
                    "      ]\n" + //
                    "    }]\n" + //
                    "  }\n" + //
                    "]";
        JSONArray items = new JSONArray();
        items = JSONUtil.parseArray(roles);
       
        return items;
    }

    @GetMapping(value = "addRole")
    public Object addRole(@RequestBody JSONObject body) throws Exception {
        Integer[] id = {500, 666};
        return id;
    }

    @PostMapping(value = "updateRole")
    public Object updateRole(@RequestBody JSONObject body) throws Exception {
        Integer[] id = {500, 666};
        return id;
    }

    @PostMapping(value = "deleteRole")
    public Object deleteRole(@RequestBody JSONObject body) throws Exception {
        Integer[] id = {500, 666};
        return id;
    }
}
