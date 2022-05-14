package br.com.sw2you.realmeet.domain.repository;

import br.com.sw2you.realmeet.domain.entity.Room;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByIdAndActive(Long id, Boolean active);

    //verificar se o nome da room não existe no banco, para cadastrar nova
    Optional<Room> findByNameAndActive(String name, Boolean active);

    //desativar uma room, delete sem deletar
    @Modifying(clearAutomatically = true, flushAutomatically = true) //Informa para o banco que sera uma query de modificação, delete ou create. Garante que o dado sera atualizado após a execução da query
    @Query("UPDATE Room r SET r.active = false WHERE r.id = :roomId")
    void deactivate(@Param("roomId") Long roomId);
}