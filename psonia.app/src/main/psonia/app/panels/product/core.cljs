(ns psonia.app.panels.product.core
  (:require [psonia.app.panels.components :refer [multi-level-navbar static-sidebar init-dropdown]]
            [psonia.app.layouts.site :as site]
            [psonia.app.panels.categories.components :refer [categories-widget]]
            [re-frame.core :as re-frame]
            [psonia.app.panels.catalog.components :refer [product-gallery product-details]]))

;; Panel

(defn panel
  "Uses light gallery to create a product gallery."
  [& args]
  (js/console.log (clj->js args))
  (let [product (first @(re-frame/subscribe [:products]))]
    [:<>
     [multi-level-navbar]
     [site/page-title (:name product)]
     [:div.container
      [:div.bg-light.box-shadow-lg.rounded-lg.px-4.py-3.mb-5
       [:div.px-lg
        [:div.row
         [:div.col-lg-7.pr-lg-0.pt-lg-4
          ;; Product Gallery
          [product-gallery ""]]
         [:div.col-lg-5.pt-4.pt-lg-0
          ;; Product Details
          [product-details product]]]]]]]))
