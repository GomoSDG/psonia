(ns psonia.models.shipping
  (:require  #?(:cljs [cljs.spec.alpha :as s]
                :clj  [clj.spec.alpha :as s])))

(s/def :psonia/shipping (s/keys :req [:psonia.shipping/method :psonia/address]))

(def delivery-methods #{:delivery :collection})

(s/def :psonia.shipping/method delivery-methods)

(defn valid-shipping-method?
  [val]
  (s/valid? val :psonia.shipping/method))

(defn valid-shipping?
  [val]
  (s/valid? val :psonia/shipping))

(defn valid-address?
  [val]
  (s/valid? val :psonia/address))
