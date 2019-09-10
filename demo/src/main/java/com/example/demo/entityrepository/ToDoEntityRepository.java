package com.example.demo.entityrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ToDoEntity;

@Repository
public interface ToDoEntityRepository extends JpaRepository<ToDoEntity,Integer> {

}
