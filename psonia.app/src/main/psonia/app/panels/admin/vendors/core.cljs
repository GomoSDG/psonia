(ns psonia.app.panels.admin.vendors.core
  (:require [psonia.app.layouts.site :as site]
            [re-frame.core :as re-frame]
            [reagent.ratom :as ratom]
            [reagent.core :as r]
            [psonia.app.panels.components :refer [multi-level-navbar static-sidebar tablist tab]]
            [psonia.app.panels.admin.vendors.components :refer [vendor-list vendor-general-tab vendor-fica-documents-tab]]))

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
                      :href   "#"
                      :active true}
                     {:name "Hello to you too"
                      :href "#"}]}]]
          [:section.col-lg-8.pt-lg-4.pb-4.mb-3
           [:div.pt-2.px-4.pl-lg-0.pr-xl-5
            [:h2.h3.py-2.text-center.text-sm-left
             "All Vendors"]
            [:div.table-responsive.font-size-md
             [vendor-list @vendors]]]]]]]])))

(defn vendor-view []
  (fn []
    (let [{:keys [name status] :as vendor} (first @(re-frame/subscribe [:vendors]))]
      (fn []
        [:<>
         [multi-level-navbar]
         [site/page-title name breadcrumb]
         [:div.container.mb-5.pb-3
          [:div.bg-light.box-shadow-lg.rounded-lg.overflow-hidden
           [:div.row
            ;; Sidebar
            [static-sidebar
             [{:name  "Something"
               :items [{:name   "Hello World"
                        :icon   "czi-home"
                        :href   "#"
                        :active true}
                       {:name "Hello to you too"
                        :href "#"}]}]]

            ;; Vendor view
            [:section.col-lg-8.pt-lg-4.pb-4.mb-3
             [:div.pt-2.px-4.pl-lg-0.pr-xl-5
              [:h2.h3.py-2.text-center.text-sm-left
               "New / Edit Vendor"]

              [tablist [(tab :general
                             "General Details"
                             [vendor-general-tab {:id "general"}
                              vendor])
                        (tab :fica-documents
                             "Fica Documents"
                             [vendor-fica-documents-tab {:id "fica-documents"}
                              vendor])]]]]]]]]))))

(re-frame/reg-sub
 :vendors
 (fn [db]
   (mapcat identity (vals (:vendors db)))))

