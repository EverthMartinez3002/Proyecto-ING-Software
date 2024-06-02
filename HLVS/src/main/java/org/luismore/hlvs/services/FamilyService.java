package org.luismore.hlvs.services;

import org.luismore.hlvs.dtos.FamilyMemberDto;
import org.luismore.hlvs.entities.FamilyMember;

import java.util.List;

public interface FamilyService {
    List<FamilyMember> listFamilyMembers(Long userId);
    FamilyMember addFamilyMember(Long userId, FamilyMemberDto familyMemberDto);
}
