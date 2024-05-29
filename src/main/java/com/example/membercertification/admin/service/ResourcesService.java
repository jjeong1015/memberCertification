package com.example.membercertification.admin.service;


import com.example.membercertification.domain.entity.Resources;

import java.util.List;

public interface ResourcesService { // ResourcesService : 사용자의 권한 정보를 관리하기 위한 인터페이스
    Resources getResources(long id);
    List<Resources> getResources();

    void createResources(Resources Resources);

    void deleteResources(long id);
}
