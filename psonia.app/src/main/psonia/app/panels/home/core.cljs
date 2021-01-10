(ns psonia.app.panels.catalog.core
  (:require [re-frame.core :as re-frame]
            [reagent.ratom :as ratom]
            [psonia.app.layouts.site :as site]
            [psonia.app.panels.components :refer [multi-level-navbar]]))

(defn panel [navbar]
  (let [products (re-frame/subscribe [:products])]
    (fn []
      [:<>
       [multi-level-navbar]
       [site/page-title "Products" breadcrumb]
       [:div.container
        [:div.row
         [:aside.col-lg-4
          [:h1 "Hello World!"]]]]])))
