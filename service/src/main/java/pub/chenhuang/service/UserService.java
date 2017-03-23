package pub.chenhuang.service;

import org.springframework.stereotype.Service;
import pub.chenhuang.dao.UseraccountMapper;
import pub.chenhuang.pojo.Useraccount;

import javax.annotation.Resource;

/**
 * Created by cjw on 2017/3/16.
 */
@Service
public class UserService {
    @Resource
    private UseraccountMapper useraccountMapper;

    public Useraccount getUser(){
        return useraccountMapper.selectByPrimaryKey(1);
    }

}
