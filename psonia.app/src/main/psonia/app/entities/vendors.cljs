(ns psonia.app.entities.vendors
  (:require [cljs.spec.alpha :as s]))

(s/def ::spec (s/keys :req-un [::name ::address ::active ::created-on ::contact-details ::fica-documents]))

(s/def ::address (s/keys :req-un [::line-one ::line-two ::line-three]))

(s/def ::fica-documents (s/keys :req-un [::proof-of-residence ::id-number ::id-document ::tax-number ::sars-document ::proof-of-bank]))

(s/def ::contact-details (s/keys :req-un [::cellphone-number ::email]))

(s/def ::name (s/and string? #(> (count %) 10)))
(s/def ::cellphone-number (s/and string? #(> (count %) 10)))
(s/def ::email (s/and string? #(> (count %) 10)))
(s/def ::id-number (s/and string? #(> (count %) 10)))
(s/def ::line-one (s/and string? #(> (count %) 10)))
(s/def ::line-two (s/and string? #(> (count %) 10)))
(s/def ::line-three (s/and string? #(> (count %) 10)))
(s/def ::proof-of-residence (s/and string? #(> (count %) 10)))
(s/def ::id-document (s/and string? #(> (count %) 10)))
(s/def ::sars-document (s/and string? #(> (count %) 10)))
(s/def ::proof-of-bank (s/and string? #(> (count %) 10)))
(s/def ::id (s/and int? pos?))
(s/def ::tax-number (s/and string? #(> (count %) 10)))
(s/def ::active boolean?)
(s/def ::created-on (s/and string? #(> (count %) 10)))
