(ns psonia.app.panels.admin.vendors.core
  (:require [psonia.app.layouts.site :as site]
            [re-frame.core :as re-frame]
            [reagent.ratom :as ratom]
            [psonia.app.panels.components :refer [multi-level-navbar static-sidebar]]
            [psonia.app.panels.admin.vendors.components :refer [vendor-list]]))

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
  (let [vendors (re-frame/subscribe [:vendors])]
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
                      :href "#"
                      :active true}
                     {:name "Hello to you too"
                      :href "#"}]}]]
          [:section.col-lg-8.pt-lg-4.pb-4.mb-3
           [:div.pt-2.px-4.pl-lg-0.pr-xl-5
            [:h2.h3.py-2.text-center.text-sm-left
             "All Vendors"]
            [:div.table-responsive.font-size-md
             [vendor-list @vendors]]]]]]]])))

(re-frame/reg-sub
 :vendors
 (fn [db]
   (mapcat identity (vals (:vendors db)))))

