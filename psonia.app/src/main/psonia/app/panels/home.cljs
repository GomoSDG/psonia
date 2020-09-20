(ns psonia.app.panels.home
  (:require [psonia.app.panels.catalog.components :as comp-catalog]
            [re-frame.core :as re-frame]
            [reagent.ratom :as ratom]))

(defn home []
  (let [products (re-frame/subscribe [:products])]
    (fn []
      (js/console.log "Stuff!" (clj->js  @products))
      [:<>
       [:h1 "Hello, Gomosdg!"]
       [:h2 "Products"]
       [:section.px-lg-3.pt-4
        (comp-catalog/product-grid @products)]])))

(re-frame/reg-sub
 :products
 (fn [db]
   (mapcat identity (vals (:products db)))))
