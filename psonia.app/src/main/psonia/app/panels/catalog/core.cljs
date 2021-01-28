(ns psonia.app.panels.catalog.core
  (:require [psonia.app.panels.catalog.components :as comp-catalog]
            [psonia.app.layouts.site :as site]
            [psonia.app.panels.catalog.data :as data]
            [re-frame.core :as re-frame]
            [reagent.ratom :as ratom]
            [psonia.app.panels.categories.components :refer [categories-widget]]
            [psonia.app.panels.components :refer [multi-level-navbar]]
            [psonia.app.panels.catalog.components :refer [product-gallery product-details]]))

(def breadcrumb [{:name "Home"
                  :url  "#"
                  :icon :home}])

(defn list-all [navbar]
  (let [products (re-frame/subscribe [:products])]
    (fn []
      [:<>
       [multi-level-navbar]
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

(defn view-product
  "Uses light gallery to create a product gallery."
  [params]
  (let [product (re-frame/subscribe [:psonia.catalog/product (params :id)])]
    [:<>
     [multi-level-navbar]
     [site/page-title (:name @product)]
     [:div.container
      [:div.bg-light.box-shadow-lg.rounded-lg.px-4.py-3.mb-5
       [:div.px-lg
        [:div.row
         [:div.col-lg-7.pr-lg-0.pt-lg-4
          ;; Product Gallery
          [product-gallery ""]]
         [:div.col-lg-5.pt-4.pt-lg-0
          ;; Product Details
          [product-details @product]]]]]]]))

(def routes
  ["/products"
   [""
    {:name :app/products
     :view #'list-all}]
   ["/:id"
    {:name :app.products/view
     :view #'view-product}]])
