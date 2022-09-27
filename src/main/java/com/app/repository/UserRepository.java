package com.app.repository;

import com.app.dto.IUserListDto;
import com.app.entities.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Page<IUserListDto> findByOrderByIdAsc(Pageable pageable, Class<IUserListDto> class1);

	Page<IUserListDto> findByNameIgnoreCaseContaining(String search, Pageable pageable, Class<IUserListDto> class1);

	UserEntity findByEmail(String email);

}
