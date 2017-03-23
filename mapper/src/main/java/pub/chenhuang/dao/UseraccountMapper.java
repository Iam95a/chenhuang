package pub.chenhuang.dao;

import pub.chenhuang.pojo.Useraccount;

public interface UseraccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Useraccount record);

    int insertSelective(Useraccount record);

    Useraccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Useraccount record);

    int updateByPrimaryKey(Useraccount record);
}