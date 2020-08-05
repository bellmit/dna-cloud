package com.bazl.dna.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bazl.dna.sys.entity.SysUser;

public interface SysUserMapper {

	public List<Map<String, String>> findGrantList(@Param("offset") Long offset, @Param("length") Integer length,
			@Param("userName") String userName, @Param("roleName") String roleName, @Param("realName") String realName);

	public Integer countGrantList(@Param("offset") Long offset, @Param("length") Integer length,
			@Param("userName") String userName, @Param("roleName") String roleName, @Param("realName") String realName);

	public Integer countList(@Param("userName") String userName, @Param("realName") String realName,
			@Param("phone") String phone, @Param("orgId") String orgId);
	
	public List<SysUser> findList(@Param("userName") String userName, @Param("realName") String realName,
			@Param("phone") String phone, @Param("orgId") String orgId, 
			@Param("offset") int offset, @Param("rows") int rows);
}
