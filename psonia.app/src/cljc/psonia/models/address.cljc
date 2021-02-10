(ns psonia.models.address
  (:require #?(:cljs [cljs.spec.alpha :as s]
               :clj  [clj.spec.alpha :as s]
               [clojure.string :as str])))

(s/def :psonia/address (s/keys :req [:psonia.address/city
                                     :psonia.address/street-address
                                     :psonia.address/postal-code
                                     :psonia.address/building
                                     :psonia.address/province
                                     :psonia.address/id]

                               :opt [:psonia.address/unit
                                     :psonia.address/suburb]))

(def provinces #{"GAUTENG" "FREE STATE" "LIMPOPO" "NORTH WEST" "WESTERN CAPE" "EASTERN CAPE"})


(s/def :psonia.address/city           (s/and string? #(<= (count %) 50) #(> (count %) 10)))
(s/def :psonia.address/street-address (s/and string? #(<= (count %) 50) #(> (count %) 10)))
(s/def :psonia.address/postal-code    (s/and int? #(< % 9999)))
(s/def :psonia.address/unit           (s/and int? #(< % 9999)))
(s/def :psonia.address/suburb         (s/and string? #(<= (count %) 50) #(> (count %) 10)))
(s/def :psonia.address/id             (s/and int? pos?))
(s/def :psonia.address/building       (s/and string? #(<= (count %) 50) #(> (count %) 10)))
(s/def :psonia.address/province       provinces)

(defn ->address-string
  [{:psonia.address/keys [unit
                          building
                          street-address
                          suburb
                          city
                          province
                          postal-code]}]
  (apply str (interpose ", " (remove nil? [unit building street-address suburb city province postal-code]))))
