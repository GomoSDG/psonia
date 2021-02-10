(ns psonia.models.credit-card
  (:require [clojure.string :as str]
            #?(:cljs [cljs.spec.alpha :as s]
               :clj  [clj.spec.alpha :as s])))

(def expiry? (comp #(re-matches #"^[0-9]+/[0-9]+$" %) #(str/replace % #" " "") str))

(def string-number? (comp #(re-matches #"^[0-9]+$" %) str))

(def space-separated-number? (comp string-number? #(str/replace % #" " "") str))

(s/def :psonia/credit-card (s/keys :req [:psonia.credit-card/number
                                         :psonia.credit-card/cvc
                                         :psonia.credit-card/expiry
                                         :psonia.credit-card/full-name]))

(s/def :psonia.credit-card/number    space-separated-number?)
(s/def :psonia.credit-card/cvc       (s/and string-number? #(<= 3 (count %) 4)))
(s/def :psonia.credit-card/expiry    expiry?)
(s/def :psonia.credit-card/full-name string?)

(defn valid-card?
  [card]
  (s/valid? :psonia/credit-card card))

(defn valid-expiry?
  [expiry]
  (s/valid? :psonia.credit-card/expiry expiry))

(defn valid-cvc?
  [cvc]
  (s/valid? :psonia.credit-card/cvc cvc))

(defn valid-full-name?
  [full-name]
  (s/valid? :psonia.credit-card/full-name full-name))

(defn valid-card-number?
  [card-number]
  (s/valid? :psonia.credit-card/number card-number))
