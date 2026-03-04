package ebd.api_ebd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Cargo;
import ebd.api_ebd.exception.NotFoundException;
import ebd.api_ebd.repository.CargoRepository;
import jakarta.transaction.Transactional;

@Service
public class CargoService {
    // Serviço de Cargo

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @Transactional
    public Cargo criar(Cargo cargo) {
        // Garante criação com ID automático do banco
        cargo.setId(null);
        return cargoRepository.save(cargo);
    }

    @Transactional
    public Cargo atualizar(Integer id, Cargo dados) {
        Cargo cargo = cargoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));
        
        cargo.setNome(dados.getNome());
        return cargoRepository.save(cargo);
    }

    public Cargo buscarPorId(Integer id) {
        return cargoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));
    }

    public List<Cargo> listarTodos() {
        return cargoRepository.findAll();
    }

    @Transactional
    public void deletar(Integer id) {
        Cargo cargo = cargoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));
        
        cargoRepository.delete(cargo);
    }
}
