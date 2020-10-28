(ns psonia.app.entities.vendors
  (:require [cljs.spec.alpha :as s]))

(s/def ::spec (s/keys :req-un [::name ::email ::id-number ::fica ::address ::tax-number]))

(s/def ::address (s/keys :req-un [::line-one ::line-two ::line-three]))

(s/def ::fica (s/keys :req-un [::proof-of-residence ::id-document ::income-tax-number ::proof-of-bank]))

(s/def ::name string?)
(s/def ::email string?)
(s/def ::id-number string?)
(s/def ::line-one string?)
(s/def ::line-two string?)
(s/def ::line-three string?)
(s/def ::proof-of-residence string?)
(s/def ::id-document string?)
(s/def ::income-tax-number string?)
(s/def ::proof-of-bank string?)
(s/def ::id (s/and int? pos?))
(s/def ::tax-number string?)
