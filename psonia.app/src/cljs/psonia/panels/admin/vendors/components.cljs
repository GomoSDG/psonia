(ns psonia.panels.admin.vendors.components
  (:require [reagent.ratom :as ratom]
            [psonia.panels.cropper :refer [cropper-widget]]
            [fork.re-frame :as fork]
            [psonia.panels.components :refer [file-uploader]]))

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
  ([options {:keys [name status active? contact-details] :as vendor}]
   (let [image     (ratom/atom "")
         set-image #(reset! image %)]
     (fn [options vendor]
       [:div.tab-pane.fade.active.show {:id   (:id options)
                                        :role "tabpanel"}
        [:div.bg-secondary.rounded-lg.p-4.mb-4
         ;; Image selection
         [:div.media.align-items-center
          [:img {:alt   "Createx Studio",
                 :width "90",
                 :src   (or @image)}]
          [:div.media-body.pl-3
           [cropper-widget {:id      "cropModal"
                            :crop-fn set-image
                            :src     "/150.jpg"}]
           [:div.p.mb-0.font-size-ms.text-muted
            "Upload Vendor Image"]]]]
        ;; Form
        [:div.row
         [:div.col-sm-12

          ;; Vendor Name
          [fork/form
           {:initial-values {"name" name}}
           (fn [{:keys [values handle-change handle-blur handle-submit]}]
             [:div.card
              [:div.card-body
               [:h6.card-title "Name"]
               [:div.form-group
                [:label {:for "vendor-name"}
                 "Vendor Name"]
                [:input#vendor-name.form-control {:type      "text"
                                                  :name      "name"
                                                  :value     (values "name")
                                                  :on-change handle-change
                                                  :on-blur   handle-blur}]]
               [:button.btn.btn-success.float-right
                {:on-click handle-submit}
                "Save Changes."]]])]

          ;; Contact Details
          [fork/form
           {:initial-values  contact-details
            :keywordize-keys true}
           (fn [{:keys [values handle-change handle-blur handle-submit]}]
             [:div.card.mt-3
              [:div.card-body
               [:h6.card-title "Contact Details"]
               [:div.form-group
                [:label {:for "cellphone-number"}
                 "Cellphone Number"]
                [:input#cellphone-number.form-control {:type      "text"
                                                       :name      :cellphone-number
                                                       :value     (values :cellphone-number)
                                                       :on-change handle-change
                                                       :on-blur   handle-blur}]]
               [:div.form-group
                [:label {:for "email"}
                 "E-mail"]
                [:input#email.form-control {:type      "text"
                                            :name      :email
                                            :value     (values :email)
                                            :on-change handle-change
                                            :on-blur   handle-blur}]]
               [:button.btn.btn-success.float-right
                "Save Changes"]]])]

          ;; Vendor Status
          [:div.card.mt-3
           [:div.card-body
            [:h6.card-title "Status"]
            [:div.form-group
             [:label {:for "vendor-status"}
              "Status"]
             [:input#vendor-status.form-control {:readOnly true
                                                 :value    (:status-name status)}]]
            [:div.form-group.float-right
             (when (:can-activate? status)
               [:button.btn.btn-shadow.btn-success
                "Activate"])
             (when (:can-deactivate? status)
               [:button.btn.btn-shadow.btn-danger
                "Deactivate"])]]]]]])))
  ([vendor]
   (vendor-general-tab nil vendor)))

(defn vendor-fica-documents-tab
  ([options vendor]
   (let []
     (fn [options vendor]
       [:div.tab-pane.fade.active.show {:id   (:id options)
                                        :role "tabpanel"}
        ;; Form
        [:div.row
         [:div.col-sm-12
          [:div.card
           [:div.card-body
            [:h6.card-title "Identity Document"]
            [:p "Upload vendor ID Document."]
            [file-uploader js/console.log]
            [:button.btn.btn-success.float-right.mt-2
             "Save Changes."]]]

          [:div.card.mt-3
           [:div.card-body
            [:h6.card-title "Proof of address"]
            [:p "Uploade Proof of address."]
            [file-uploader js/console.log]
            [:button.btn.btn-success.float-right.mt-2
             "Save Changes."]]]]]])))
  ([vendor]
   (vendor-fica-documents-tab nil vendor)))

(defn vendor-address-details
  ([option vendor]
   (let []
     (fn [options vendors]
       [:div.tab-pane.fade.active.show {:id   (:id options)
                                        :role "tabpanel"}
        ])))
  ([vendor]
   (vendor-address-details nil vendor)))



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
