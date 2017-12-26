package com.kozak.Repository;

import com.kozak.domain.Apple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Repository
public interface AppleRepository extends JpaRepository<Apple, Long> {

}