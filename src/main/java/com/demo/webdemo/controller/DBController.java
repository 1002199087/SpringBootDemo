package com.demo.webdemo.controller;

import com.demo.webdemo.common.Constant;
import com.demo.webdemo.entity.CompanyDao;
import com.demo.webdemo.entity.db.Company;
import com.demo.webdemo.entity.db.User;
import com.demo.webdemo.entity.response.FindCompanyListResponse;
import com.demo.webdemo.entity.response.FindUserListResponse;
import com.demo.webdemo.entity.response.FindUserResponse;
import com.demo.webdemo.entity.response.SimpeResponseDao;
import com.demo.webdemo.repository.CompanyRepository;
import com.demo.webdemo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/db")
public class DBController implements Constant {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public CompanyRepository companyRepository;

    /**
     * 查询所有的数据
     *
     * @return
     */
    @RequestMapping(value = "/userAll")
    public List<User> userAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    /**
     * 增加数据
     *
     * @return
     */
    @RequestMapping(value = "/addUser")
    public SimpeResponseDao addUser(@RequestParam("name") String name, @RequestParam("sex") String sex,
                                    @RequestParam("age") int age) {
        int reforeCount = userRepository.findAll().size();
        User user = new User();
        user.setName(name);
        user.setSex(sex);
        user.setAge(age);
        userRepository.save(user);
        int afterCount = userRepository.findAll().size();
        SimpeResponseDao responseDao = new SimpeResponseDao();
        if (afterCount - reforeCount == 1) {
            responseDao.setCode("0000");
            responseDao.setMsg("添加成功");
            return responseDao;
        } else {
            responseDao.setCode("0001");
            responseDao.setMsg("添加失败");
            return responseDao;
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delUser")
    public SimpeResponseDao delUser(@RequestParam("id") int id) {
        // 方式一
        /*int beforeCount = userRepository.findAll().size();
        userRepository.deleteById(id);
        int afterCount = userRepository.findAll().size();
        SimpeResponseDao responseDao = new SimpeResponseDao();
        if (beforeCount - afterCount == 1) {
            responseDao.setCode("0000");
            responseDao.setMsg("删除成功");
            return responseDao;
        } else {
            responseDao.setCode("0001");
            responseDao.setMsg("删除失败");
            return responseDao;
        }*/

        //方式二，判断是否存在需要删除的条目
        Optional<User> optional = userRepository.findById(id);
        SimpeResponseDao responseDao = new SimpeResponseDao();
        if (optional.isPresent()) {
            int beforeCount = userRepository.findAll().size();
            User user = optional.get();
            userRepository.delete(user);
            int afterCount = userRepository.findAll().size();
            if (beforeCount - afterCount == 1) {
                responseDao.setCode("0000");
                responseDao.setMsg("删除成功");
            } else {
                responseDao.setCode("0001");
                responseDao.setMsg("删除失败");
            }
        } else {
            responseDao.setCode("0002");
            responseDao.setMsg("无数据，删除失败");
        }
        return responseDao;
    }

    /**
     * 修改数据
     *
     * @param id
     * @param name
     * @param sex
     * @param age
     * @return
     */
    @RequestMapping("/editUser")
    public SimpeResponseDao editUser(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam(
            "sex") String sex, @RequestParam("age") int age) {
        SimpeResponseDao responseDao = new SimpeResponseDao();
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setId(id);
            user.setName(name);
            user.setSex(sex);
            user.setAge(age);
            userRepository.save(user);
            responseDao.setCode("0000");
            responseDao.setMsg("修改成功");
        } else {
            responseDao.setCode("0001");
            responseDao.setMsg("无数据，修改失败");
        }
        return responseDao;
    }

    /**
     * 根据id查询user
     *
     * @param id
     * @return
     */
    @RequestMapping("/findUserById")
    public FindUserResponse findUserById(@RequestParam("id") int id) {
        FindUserResponse response = new FindUserResponse();
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            response.setCode("0000");
            response.setMsg("查询成功");
            response.setUser(user);
        } else {
            response.setCode("0001");
            response.setMsg("查询无此数据");
        }
        return response;
    }

    /**
     * 通过姓名查询
     *
     * @param name
     * @return
     */
    @RequestMapping("/findUserByName")
    public FindUserListResponse findUserByName(@RequestParam("name") String name) {
        FindUserListResponse response = new FindUserListResponse();
        List<User> users = userRepository.findByName(name);
        if (users == null || users.size() == 0) {
            response.setCode("0001");
            response.setMsg("查询无此数据");
        } else {
            response.setCode("0000");
            response.setMsg("查询成功");
            response.setUsers(users);
        }
        return response;
    }

    /**
     * 姓名模糊查询
     *
     * @param name
     * @return
     */
    @RequestMapping("/findUserByLikeName")
    public FindUserListResponse findUserByLikeName(@RequestParam("likeName") String name) {
        FindUserListResponse response = new FindUserListResponse();
        List<User> users = userRepository.findByLikeName(name);
        if (users == null || users.size() == 0) {
            response.setCode("0001");
            response.setMsg("查询无此数据");
        } else {
            response.setCode("0000");
            response.setMsg("查询成功");
            response.setUsers(users);
        }
        return response;
    }

    /**
     * 查询公司列表，带对应的用户
     *
     * @return
     */
    @RequestMapping("/findCompaniesWithUsers")
    public FindCompanyListResponse findCompaniesWithUsers() {
        FindCompanyListResponse response = new FindCompanyListResponse();
        List<CompanyDao> companyDaos = new ArrayList<>();
        List<Company> companies = companyRepository.findAll();
        if (companies == null || companies.size() == 0) {
            response.setCode("0001");
            response.setMsg("无公司数据");
        } else {
            for (Company company : companies) {
                CompanyDao companyDao = new CompanyDao();
                BeanUtils.copyProperties(company, companyDao);
                List<User> users = userRepository.findByFactoryId(company.getId());
                companyDao.setUsers(users);
                companyDaos.add(companyDao);
            }
            response.setCode("0000");
            response.setMsg("查询成功");
            response.setCompanies(companyDaos);
        }
        return response;
    }
}
