package com.erminesoft.repository;

import com.erminesoft.model.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebsiteRepository extends JpaRepository<WebSite, Long> {
}
