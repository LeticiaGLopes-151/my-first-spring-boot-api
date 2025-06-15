package com.example.myfirstapp.zip.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myfirstapp.zip.entities.Order; // Importe esta classe
import com.example.myfirstapp.zip.entities.Payment;
import com.example.myfirstapp.zip.repositories.OrderRepository; // Importe esta classe
import com.example.myfirstapp.zip.repositories.PaymentRepository;
import com.example.myfirstapp.zip.exceptions.ResourceNotFoundException; // Pacote correto
import com.example.myfirstapp.zip.exceptions.DatabaseException; // Adicione se for tratar DatabaseException aqui

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private OrderRepository orderRepository; // Injeção do OrderRepository

    public List<Payment> findAll() {
        return repository.findAll();
    }

    public Payment findById(Long id) {
        Optional<Payment> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Payment insert(Payment obj) {
        // Para Payments com @MapsId, o ID do Payment vem do ID do Order.
        // O Payment não tem ID próprio gerado automaticamente.
        // Precisamos garantir que o Order associado já existe e seja uma entidade gerenciada.

        // 1. Busque o Order real do banco de dados usando o ID fornecido no Payment
        // Se o Order não for encontrado, lança ResourceNotFoundException
        Order order = orderRepository.findById(obj.getOrder().getId())
                                     .orElseThrow(() -> new ResourceNotFoundException(obj.getOrder().getId()));

        // 2. Associe o Order gerenciado ao Payment
        obj.setOrder(order);

        // 3. Associe o Payment ao Order (se o Order for o lado que possui a lista ou a referência do Payment)
        // Isso é crucial para o cascade funcionar se o Order for o dono da relação de cascade
        order.setPayment(obj);

        // 4. Salve o Order. Como o Order tem 'cascade = CascadeType.ALL' para Payment,
        // o Payment será persistido/atualizado junto com o Order.
        orderRepository.save(order);

        // Retornamos o objeto Payment, que agora deve ter seu ID preenchido
        // pelo ID do Order após o save.
        return obj;
    }

    public void delete(Long id) {
        try {
            if (!repository.existsById(id)) {
                throw new ResourceNotFoundException(id);
            }
            repository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            // Se houver uma exceção de integridade de dados (ex: Payment ainda ligado a Order por outro lado)
            // Lançar uma DatabaseException personalizada.
            throw new DatabaseException("Erro ao deletar pagamento: " + e.getMessage());
        }
    }

    public Payment update(Long id, Payment obj) {
        try {
            // Para atualizar, primeiro verificamos se o Payment existe.
            // Usamos findById para ter o objeto real e evitar EntityNotFoundException tardia.
            Payment entity = repository.findById(id)
                                       .orElseThrow(() -> new ResourceNotFoundException(id));

            // Atualiza os dados da entidade existente com os dados do objeto recebido
            updateData(entity, obj);

            // Salva a entidade atualizada
            return repository.save(entity);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (EntityNotFoundException e) { // Captura EntityNotFoundException que pode vir de getReferenceById se usado em outro lugar
            throw new ResourceNotFoundException(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar pagamento: " + e.getMessage(), e);
        }
    }

    private void updateData(Payment entity, Payment obj) {
        // Atualiza o momento do pagamento.
        entity.setMoment(obj.getMoment());

        // IMPORTANTE: Não se pode alterar o 'order' associado a um 'Payment' que usa @MapsId
        // sem alterar o ID do Payment, pois o ID do Payment é o ID do Order.
        // Se fosse para mudar o Order, seria um novo Payment.
    }
}