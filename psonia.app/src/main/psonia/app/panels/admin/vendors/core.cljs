(ns psonia.app.panels.admin.vendors.core
  (:require [psonia.app.layouts.site :as site]
            [re-frame.core :as re-frame]
            [reagent.ratom :as ratom]
            [reagent.core :as r]
            [psonia.app.panels.components :refer [multi-level-navbar static-sidebar]]
            [psonia.app.panels.admin.vendors.components :refer [vendor-list]]
            [psonia.app.panels.cropper :refer [cropper-widget]]))

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
    (let [{:keys [name]} (first @(re-frame/subscribe [:vendors]))]
      [:<>
       [multi-level-navbar]
       [site/page-title name breadcrumb]
       [:div.container.mb-5.pb-3
       [:div.bg-light.box-shadow-lg.rounded-lg.overflow-hidden
        [:div.row
         [:section.col-lg-12.pt-lg-4.pb-4.mb-3
          [:h2.h3.py-2.text-center.text-sm-left
           "New / Edit Vendor"]
          [:div.bg-secondary.rounded-lg.p-4.mb-4
           ;; Image selection
           [:div.media.align-items-center
            [:img
             {:alt "Createx Studio",
              :width "90",
              :src "img/marketplace/account/avatar.png"}]
            [:div.media-body.pl-3
             [cropper-widget {:id  "cropModal"
                              :src "/150.jpg"}]
             [:div.p.mb-0.font-size-ms.text-muted
              "Upload JPG, GIF or PNG image. 300 x 300 required."]]]]
           ;; Form
           [:div.row
            [:div.col-sm-12
             [:div.form-group
              [:label {:for "vendor-name"}
               "Vendor Name"]
              [:input#vendor-name.form-control {:type "text"
                                                :value name}]]]]]]]]])))

(re-frame/reg-sub
 :vendors
 (fn [db]
   (mapcat identity (vals (:vendors db)))))

