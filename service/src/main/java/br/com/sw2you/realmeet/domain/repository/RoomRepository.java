package br.com.sw2you.realmeet.domain.repository;

import br.com.sw2you.realmeet.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {}