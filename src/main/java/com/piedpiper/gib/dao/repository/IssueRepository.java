package com.piedpiper.gib.dao.repository;

import com.piedpiper.gib.dao.entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<IssueEntity, String>{
}
