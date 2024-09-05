package com.simpleshowassignment.userProject.repository;

import com.simpleshowassignment.userProject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface UserPagingAndSortingRepository extends PagingAndSortingRepository<User, UUID> {
    @Query("SELECT u from User u WHERE u.Id = :userId")
    Page<User> findByUserId(@Param("userId") String userId, Pageable page);
}
