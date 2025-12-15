package com.workintech.fswebs18challengemaven.controller;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.repository.CardRepository;
import com.workintech.fswebs18challengemaven.util.CardValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardRepository cardRepository;

    @GetMapping
    public List<Card> findAll() {
        // tüm kart listesini döner
        return cardRepository.findAll();
    }

    @GetMapping("/byColor/{color}")
    public List<Card> findByColor(@PathVariable String color) {
        // renk bilgisine göre filtrelenmiş kart listesi
        return cardRepository.findByColor(color);
    }

    @GetMapping("/byType/{type}")
    public List<Card> findByType(@PathVariable String type) {
        // kart tipine göre filtrelenmiş kart listesi
        return cardRepository.findByType(type);
    }

    @GetMapping("/byValue/{value}")
    public List<Card> findByValue(@PathVariable Integer value) {
        // value bilgisine göre filtrelenmiş kart listesi
        return cardRepository.findByValue(value);
    }

    @PostMapping
    @Transactional
    public Card save(@RequestBody Card card) {
        // yeni kart oluşturma isteği için doğrulama
        CardValidation.validate(card);
        return cardRepository.save(card);
    }

    @PutMapping("/")
    @Transactional
    public ResponseEntity<Card> update(@RequestBody Card card) {
        // kart güncelleme isteği için doğrulama
        CardValidation.validate(card);
        Card updated = cardRepository.update(card);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void remove(@PathVariable Long id) {
        // ilgili id değerindeki kartı silen endpoint
        cardRepository.remove(id);
    }
}
