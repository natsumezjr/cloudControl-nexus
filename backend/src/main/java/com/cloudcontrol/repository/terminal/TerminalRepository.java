package com.cloudcontrol.repository.terminal;

import com.cloudcontrol.entity.terminal.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Integer> {
    Optional<Terminal> findBySerialNo(int serialNo);
    Optional<Terminal> findByDeviceName(String deviceName);
    Optional<Terminal> findByAccountNameAndPassword(String accountName, String password);
} 