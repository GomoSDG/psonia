(ns psonia.app.panels.cart.data
  (:require [re-frame.core :refer [reg-event-db reg-sub]
                           :as re-frame]))

(def interceptors)

(reg-event-db
 :psonia.cart/add-to-cart
 [re-frame/trim-v]
 (fn [db [qty product]]
   (let [cart         (:psonia/cart db)
         updated-cart (if (contains? cart (product :id))

                        ;; Add qty to existing count.
                        (update-in cart [(product :id) :qty] + qty)

                        ;; Add new entry to cart.
                        (assoc cart (product :id) {:id    (product :id)
                                                   :price (product :price)
                                                   :qty   qty}))]
     ;; TODO: store cart in app storage
     (assoc db :psonia/cart updated-cart))))

(reg-event-db
 :psonia.cart/update-product-qty
 [re-frame/trim-v]
 (fn [db [qty product]]
   (assoc-in db [:psonia/cart (product :id) :qty] qty)))

(reg-event-db
 :psonia.cart/remove-item
 [re-frame/trim-v]
 (fn [db [product]]
   (update db :psonia/cart dissoc (product :id) )))

(reg-sub
 :psonia.cart/item-count
 (fn [db]
   (count (get db :psonia/cart))))

(reg-sub
 :psonia.cart/total-price
 (fn [db]
   (let [products (map #(* (:price %)
                           (:qty %))
                       (-> (db :psonia/cart)
                           vals))]
     (apply + products))))

(reg-sub
 :psonia.cart/items
 (fn [db]
   (vals (db :psonia/cart))))
