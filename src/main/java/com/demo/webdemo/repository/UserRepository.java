package com.demo.webdemo.repository;

import com.demo.webdemo.entity.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 根据姓名查询
     *
     * @param name
     * @return
     */
    @Query(value = "select u.* from User u where u.name = ?1", nativeQuery = true)
    List<User> findByName(String name);

    /**
     * 根据姓名模糊查询
     *
     * @param nameLike
     * @return
     */
    @Query(value = "select u.* from User u where u.name like %?1%", nativeQuery = true)
    List<User> findByLikeName(String nameLike);

    /**
     * 根据factoryId查询
     *
     * @param factoryId
     * @return
     */
    @Query(value = "select u.* from User u where u.factoryId = ?1", nativeQuery = true)
    List<User> findByFactoryId(int factoryId);
}
