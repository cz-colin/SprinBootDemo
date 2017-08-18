package com.cloudzon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudzon.domain.BroadcastMessage;

@Repository("broadcastMessageRepository")
public interface BroadcastMessageRepository extends JpaRepository<BroadcastMessage, Long> {

}
