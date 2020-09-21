(ns psonia.app.panels.catalog.core
  (:require [psonia.app.panels.catalog.components :as comp-catalog]
            [psonia.app.layouts.site :as site]
            [re-frame.core :as re-frame]
            [reagent.ratom :as ratom]
            [psonia.app.panels.categories.components :refer [categories-widget]]))

(def breadcrumb [{:name "Home"
                  :url  "#"
                  :icon :add}
                 {:name "Products"
                  :url  "#"
                  :icon :home}])

(defn panel []
  (let [products (re-frame/subscribe [:products])]
    (fn []
      [:<>
       [site/page-title "Products" breadcrumb]
       [:div.container
        [:div.row
         [:aside.col-lg-4
          [site/sidebar
           [categories-widget]]]
         (comp-catalog/product-grid-with-toolbox @products)]]])))

(re-frame/reg-sub
 :products
 (fn [db]
   (mapcat identity (vals (:products db)))))
