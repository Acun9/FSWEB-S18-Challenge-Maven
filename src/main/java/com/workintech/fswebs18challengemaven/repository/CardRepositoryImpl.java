package com.workintech.fswebs18challengemaven.repository;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CardRepositoryImpl implements CardRepository {

    private final EntityManager entityManager;

    public CardRepositoryImpl(EntityManager entityManager) {
        // EntityManager Spring tarafından yönetildiği için, burada sadece field ataması yapıyoruz.
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Card save(Card card) {
        // Yeni kartı veritabanına eklerken transaction altında çalışmak veri tutarlılığı sağlar.
        entityManager.persist(card);
        return card;
    }

    @Override
    public List<Card> findByColor(String color) {
        // Renk bilgisine göre JPQL sorgusu hazırlayıp sonucu listeliyoruz.
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE c.color = :color", Card.class);
        query.setParameter("color", Color.valueOf(color));
        List<Card> result = query.getResultList();
        if (result.isEmpty()) {
            // Sonuç bulunamadığında domain'e özel bir hata fırlatıyoruz.
            throw new CardException("Card not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @Override
    public List<Card> findAll() {
        // Tüm kartları getirir (sadece okuma işlemi olduğu için ayrıca @Transactional zorunlu değil).
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c", Card.class);
        return query.getResultList();
    }

    @Override
    public List<Card> findByValue(Integer value) {
        // Value alanına göre kart listesini filtreler.
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE c.value = :value", Card.class);
        query.setParameter("value", value);
        return query.getResultList();
    }

    @Override
    public List<Card> findByType(String type) {
        // Tip bilgisine göre JPQL sorgusu hazırlar.
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE c.type = :type", Card.class);
        query.setParameter("type", type);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Card update(Card card) {
        // merge metodu, var olan kaydı güncellenmiş versiyon ile birleştirir, bu işlem de transaction gerektirir.
        return entityManager.merge(card);
    }

    @Override
    @Transactional
    public Card remove(Long id) {
        // Önce ilgili id'ye sahip kaydı bulup ardından siliyoruz, silme işlemi transaction altında yönetiliyor.
        Card found = entityManager.find(Card.class, id);
        if (found != null) {
            entityManager.remove(found);
        }
        return found;
    }
}
