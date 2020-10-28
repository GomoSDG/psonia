(ns psonia.app.panels.admin.vendors.core
  (:require [psonia.app.layouts.site :as site]
            [re-frame.core :as re-frame]
            [reagent.ratom :as ratom]
            [psonia.app.panels.components :refer [multi-level-navbar static-sidebar]]))

(def breadcrumb [{:name "Home"
                  :url  "#"
                  :icon :home}
                 {:name "Admin"
                  :url  "#"
                  :icon :home}
                 {:name "Vendors"
                  :url  "#"
                  :icon :home}])

(defn list-all []
  (fn []
    [:<>
     [multi-level-navbar]
     [site/page-title "Vendors" breadcrumb]
     [:div.container.mb-5.pb-3
      [:div.bg-light.box-shadow-lg.rounded-lg.overflow-hidden
       [:div.row
        [static-sidebar 
         [{:name  "Something"
           :items [{:name   "Hello World"
                    :icon   "czi-home"
                    :active true}
                   {:name "Hello to you too"}]}]]]]]]))

