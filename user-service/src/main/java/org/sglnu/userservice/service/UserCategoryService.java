package org.sglnu.userservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sglnu.userservice.domain.UserCategory;
import org.sglnu.userservice.repository.UserCategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCategoryService {

    private final UserCategoryRepository userCategoryRepository;

    @Transactional
    public UserCategory save(UserCategory userCategory){
        return userCategoryRepository.save(userCategory);
    }
}
