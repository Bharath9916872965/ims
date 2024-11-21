package com.vts.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.model.ImsNotification;

public interface NominationRepository extends JpaRepository<ImsNotification, Long>{


}
