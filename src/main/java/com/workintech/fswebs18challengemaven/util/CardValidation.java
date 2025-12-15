package com.workintech.fswebs18challengemaven.util;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import org.springframework.http.HttpStatus;

public class CardValidation {

    // Bu metot, kart üzerinde iş kurallarını merkezî bir noktadan doğrulamak için kullanılır.
    public static void validate(Card card) {
        if (card == null) {
            throw new CardException("Card nesnesi null olamaz", HttpStatus.BAD_REQUEST);
        }

        // Bir kart aynı anda hem type hem value değeri taşıyamaz.
        if (card.getType() != null && card.getValue() != null) {
            throw new CardException("Bir kart aynı anda hem type hem value içeremez", HttpStatus.BAD_REQUEST);
        }

        // Joker kartlar için özel kural: value ve color alanlarının boş olması gerekir.
        if (card.getType() == Type.JOKER) {
            if (card.getValue() != null || card.getColor() != null) {
                throw new CardException("JOKER tipi kartlarda value ve color atanamaz", HttpStatus.BAD_REQUEST);
            }
        }
    }
}

