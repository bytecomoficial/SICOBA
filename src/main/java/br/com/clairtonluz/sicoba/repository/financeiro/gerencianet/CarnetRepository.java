package br.com.clairtonluz.sicoba.repository.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.Carnet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by clairtonluz on 09/01/16.
 */

@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Integer> {

    List<Carnet> findByCliente_id(Integer clienteId);

    Carnet findOptionalByCarnetId(int carnetId);
}
