(ns psonia.app.panels.cart.data
  (:require [re-frame.core :refer [reg-event-db reg-sub]
                           :as re-frame]))

(def interceptors)

(reg-event-db
 :psonia.cart/add-to-cart
 [re-frame/trim-v]
 (fn [db [qty product]]
   (let [cart (:psonia/cart db)
         cart-entry {:price (product :price)
                     :qty   qty}
         updated-cart (assoc cart (product :id) qty)]
     ;; TODO: store cart in app storage
     (assoc db :psonia/cart updated-cart))))

(reg-sub
 :psonia.cart/item-count
 (fn [db]
   (count (get db :psonia/cart))))

(reg-sub
 :psonia.cart/total-price
 (fn [db]
   (let [products (map :price (db :psonia/cart))
         price    (reduce + products)]
     price)))
