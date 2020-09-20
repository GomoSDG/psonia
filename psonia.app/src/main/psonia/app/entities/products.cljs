(ns psonia.app.entities.products
  (:require [cljs.spec.alpha :as s]))

(s/def ::spec (s/keys :req-un [::price ::name ::id ::image-b64 ::category]
                      :opt-un [::on-promotion ::original-price ::avg-rating]))

(s/def ::category #{"Home" "Clothing"})

(s/def ::price (s/and float? pos?))
(s/def ::original-price (s/and float? pos?))
(s/def ::name (s/and string? #(<= (count %) 50) #(> (count %) 10)))
(s/def ::on-promotion boolean?)
(s/def ::avg-rating (s/and int? #(<= 1 % 5)))
(s/def ::id (s/and int? pos?))
(s/def ::image-b64 (s/and string? #(>= (count %) 60)))

