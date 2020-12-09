(ns psonia.app.panels.admin.vendors.components
  (:require [reagent.ratom :as ratom]
            [psonia.app.panels.cropper :refer [cropper-widget]]))

(defn vendor-list-item [item]
  (let [{name       :name
         active     :active
         created-on :created-on} item]

    [:tr
     [:td.py-3.align-middle
      [:div.media.align-items-center
       [:img.mr-2 {:src "https://via.placeholder.com/20"}]
       [:div.media-body
        name]]]
     [:td.py-3.align-middle
      active]
     [:td.py-3.align-middle
      created-on]
     [:td.py-3.align-middle
      [:a.nav-link-style.mr-2 {:href                "#"
                               :data-toggle         "tooltip"
                               :data-original-title "Edit"}
       [:i.czi-edit]]
      [:a.nav-link-style.mr-2.text-danger {:href                "#"
                                           :data-toggle         "tooltip"
                                           :data-original-title "Remove"
                                           :title               ""}
       [:i.czi-trash]]]]))

(defn vendor-general-tab 
  ([options {:keys [name status] :as vendor}]
   (fn []
    (let [image (ratom/atom "")
          set-image #(reset! image %)]
      [:div.tab-pane.fade.active.show {:id (:id options)
                           :role "tabpanel"}
       [:div.bg-secondary.rounded-lg.p-4.mb-4
        ;; Image selection
        [:div.media.align-items-center
         [:img {:alt  "Createx Studio",
                :width "90",
                :src (or @image)}]
         [:div.media-body.pl-3
          [cropper-widget {:id  "cropModal"
                           :crop-fn set-image
                           :src "/150.jpg"}]
          [:div.p.mb-0.font-size-ms.text-muted
           "Upload Vendor Image"]]]]
       ;; Form
       [:div.row
        [:div.col-sm-12
         ;; Vendor Name
         [:div.form-group
          [:label {:for "vendor-name"}
           "Vendor Name"]
          [:input#vendor-name.form-control {:type "text"
                                            :value name}]]
         ;; Vendor Status
         [:div.form-group
          [:label {:for "vendor-status"}
           "Status"]
          [:input#vendor-status.form-control {:readOnly true
                                              :value status}]]]]])))
  ([vendor]
   (vendor-general-tab nil vendor)))



(defn vendor-list [vendors]
  (fn []
    [:table.table.table-hover.mb-0
     [:thead
      [:tr
       [:th "Name"]
       [:th "Active"]
       [:th "Created On"]
       [:th]]]
     [:tbody
      (for [i vendors]
        ^{:key (:id i)} [vendor-list-item i])]]))
