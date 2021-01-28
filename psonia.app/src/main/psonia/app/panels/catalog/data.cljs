(ns psonia.app.panels.catalog.data
  (:require [re-frame.core :refer [reg-sub reg-event-db]
                           :as re-frame]))

;; TODO: correct catalog.

(reg-sub
 :psonia.catalog/product
 (fn [db [_ id]]
   (first (get-in db [:products {:id (int id)}]))))
