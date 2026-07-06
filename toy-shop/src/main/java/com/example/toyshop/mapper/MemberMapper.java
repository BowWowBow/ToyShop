package com.example.toyshop.mapper;

import com.example.toyshop.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    void insertMember(Member member);

    Member findByLoginId(String loginId);

    Member findById(Long id);

    void updateMember(Member member);

    void dormantMember(Long id);

    void releaseDormant(Long id);

    Member findIdByNameAndEmail(@Param("name") String name,
                                @Param("email") String email);

    Member findPasswordMember(@Param("loginId") String loginId,
                              @Param("name") String name,
                              @Param("email") String email);

    void updatePassword(@Param("id") Long id,
                        @Param("password") String password);

    void changePassword(@Param("id") Long id,
                        @Param("password") String password);

    List<Member> findAll();

    List<Member> findAdminMemberList(@Param("startDate") String startDate,
                                     @Param("endDate") String endDate,
                                     @Param("sort") String sort,
                                     @Param("dir") String dir);


}