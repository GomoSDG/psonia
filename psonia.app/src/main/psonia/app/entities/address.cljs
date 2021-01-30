(ns psonia.app.entities.address
  (:require [cljs.spec.alpha :as s]))

(s/def ::spec (s/keys :reg-un [::city ::street-address ::postal-code ::id]
                      :opt-un [::unit ::suburb]))


(s/def ::city (s/and string? #(<= (count %) 50) #(> (count %) 10)))
(s/def ::street-address (s/and string? #(<= (count %) 50) #(> (count %) 10)))
(s/def ::postal-code (s/and int? #(< % 9999)))
(s/def ::unit (s/and int? #(< % 9999)))
(s/def ::suburb (s/and string? #(<= (count %) 50) #(> (count %) 10)))
(s/def ::id (s/and int? pos?))
