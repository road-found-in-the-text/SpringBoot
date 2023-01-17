package com.example.umc3_teamproject.config.oauth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ApplePublicKeyRes {

    private List<Key> keys;

    @Getter
    @Setter
    public static class Key {
        private String kty; //key type 사용되는 값 -RSA 등
        private String kid; //key id key rolling이 일어나는 지점에 각 key 구분
        private String use; //public key use -sig(signature) / enc(encryption)
        private String alg; //algorithm (RS256 등)
        private String n; //RSA modules
        private String e; //RSA public exponent
    }



    public Optional<Key> getMatchedKeyBy(String kid, String alg) {
        return this.keys.stream()
                .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
                .findFirst();
    }
}