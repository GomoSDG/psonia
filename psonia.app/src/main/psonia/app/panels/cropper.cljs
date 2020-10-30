(ns psonia.app.panels.cropper
  (:require ["cropperjs" :as Cropper]
            [reagent.ratom :as ratom]
            ["jquery" :as $]))

(defn crop [cropper data]
  (fn []))

(defn cropper [{:keys [src crop-fn aspect-ratio cropper init]
                :or   {aspect-ratio 19 / 6
                       cropper      (ratom/atom nil)
                       init         (ratom/atom nil)}}]
  (let []
    (fn []
      [:div.container
       [:div.row
        [:div.col-sm-11
         [:img {:src   src
                :style {:max-width "100%"
                        :display   "block"}
                :ref   #(reset! init (fn [] (reset! cropper (Cropper. % #js {"aspectRatio" aspect-ratio}))))}]]]
       [:div.row.mt-2
        [:div.col-sm-12
         [:button.btn.btn-light.btn-shadow.btn-sm.mb-2
          {:type     "button"}
          [:i.czi-loading.mr-2]
          "Change"]]]])))

(defn cropper-modal [{:keys [id]
                      :as   options}]
  (let [crop (ratom/atom nil)
        init (ratom/atom nil)]
    ;; jquery setup

    (fn []
      ;; jquery setup
      

      [:<>
       [:div {:style {:position "absolute"}}
        [:div.modal {:tabIndex "-1"
                     :role     "dialog"
                     :id       id
                     :ref #(-> ($ (str "#" id))
                               (.on  "shown.bs.modal"  @init))}
         [:div.modal-dialog.modal-dialog-centered {:role "document"}
          [:div.modal-content
           [:div.modal-header
            [:h5.modal-title "Update Image"]
            [:button.close {:type         "button"
                            :data-dismiss "modal"
                            :aria-label   "close"}
             [:span {:aria-hidden             true
                     :dangerouslySetInnerHTML {:__html "&times;"}}]]]
           [:div.modal-body
            [cropper (assoc options :cropper crop :aspect-ratio 1 :init init)]]
           [:div.modal-footer
            [:button.btn.btn-primary.btn-sm {:type "button"}
             "Save changes"]]]]]]
       [:button.btn.btn-outline-secondary {:type        "button"
                                           :data-toggle "modal"
                                           :data-target (str "#" id)}
        "Upload Image"]])))
