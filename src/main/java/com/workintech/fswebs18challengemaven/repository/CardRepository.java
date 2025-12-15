package com.workintech.fswebs18challengemaven.repository;

import com.workintech.fswebs18challengemaven.entity.Card;

import java.util.List;

// kart veritabanı işlemleri için repository arayüzü
// JPA ile manuel olarak çalışacak temel CRUD benzeri metotlar
public interface CardRepository {

    // yeni bir kartı veritabanına kaydetmek için kullanılan metot
    Card save(Card card);

    // veritabanındaki tüm kartları döndüren metot
    List<Card> findAll();

    // kartın rengine göre filtreleme yapan metot
    List<Card> findByColor(String color);

    // kartın değerine göre filtreleme yapan metot
    List<Card> findByValue(Integer value);

    // kartın tipine göre filtreleme yapan metot
    List<Card> findByType(String type);

    // var olan kartı güncellemek için kullanılan metot
    Card update(Card card);

    // id'ye göre kartı silip sildiği kartı geri dönen metot
    Card remove(Long id);
}
