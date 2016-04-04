package winker.dal.mapper;

import winker.dal.model.WinkerUser;

public interface WinkerUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WinkerUser record);

    int insertSelective(WinkerUser record);

    WinkerUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WinkerUser record);

    int updateByPrimaryKey(WinkerUser record);
    
    Integer validateUserByLoginIdAndPwd(WinkerUser record); 
}