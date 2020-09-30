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

(defn multi-level-navbar [main]
  [:header.box-shadow-sm
   [:div.navbar-sticky.bg-light
    [:div.navbar.navbar-expand-lg.navbar-light
     [:div.container
      [site/brand "Super Street Market"]
      [:div.input-group-overlay.d-none.d-lg-flex.mx-4
       [:input.form-control.appended-form-control
        {:placeholder "Search for products", :type "text"}]
       [:div.input-group-append-overlay
        [:span.input-group-text [:i.czi-search]]]]]]

    [:div.navbar.navbar-expand-lg.navbar-light.bg-light
     [:div.container
      [main]]]]])

(defn panel [navbar]
  (let [products (re-frame/subscribe [:products])]
    (fn []
      [:<>
       [multi-level-navbar
        navbar]
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
