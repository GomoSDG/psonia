(ns psonia.app.entities.vendors
  (:require [cljs.spec.alpha :as s]))

(s/def ::spec (s/keys :req-un [::name ::email ::id-number ::fica ::address ::tax-number ::active ::created-on]))

(s/def ::address (s/keys :req-un [::line-one ::line-two ::line-three]))

(s/def ::fica (s/keys :req-un [::proof-of-residence ::id-document ::income-tax-number ::proof-of-bank]))

(s/def ::name (s/and string? #(> (count %) 10)))
(s/def ::email (s/and string? #(> (count %) 10)))
(s/def ::id-number (s/and string? #(> (count %) 10)))
(s/def ::line-one (s/and string? #(> (count %) 10)))
(s/def ::line-two (s/and string? #(> (count %) 10)))
(s/def ::line-three (s/and string? #(> (count %) 10)))
(s/def ::proof-of-residence (s/and string? #(> (count %) 10)))
(s/def ::id-document (s/and string? #(> (count %) 10)))
(s/def ::income-tax-number (s/and string? #(> (count %) 10)))
(s/def ::proof-of-bank (s/and string? #(> (count %) 10)))
(s/def ::id (s/and int? pos?))
(s/def ::tax-number (s/and string? #(> (count %) 10)))
(s/def ::active boolean?)
(s/def ::created-on (s/and string? #(> (count %) 10)))
