(ns psonia.app.panels.cropper
  (:require ["cropperjs" :as Cropper]
            [reagent.ratom :as ratom]))

(defn init-cropper [e & {:keys [aspect-ratio]
                         :or {aspect-ration 1}}]
  (let [img (.-target e)]
    (Cropper. img #js {"aspectRatio" aspect-ratio})))

(defn crop [cropper data]
  (fn []))

(defn cropper [{:keys [src crop-fn aspect-ration]
                :or   {aspect-ratio 1}}]
  (let [cropper (ratom/atom nil)]
    (fn []
      [:div.container
       [:div.row
        [:div.col-sm-12.mx-auto
         [:img {:src     src
                :style   {:max-width  "100%"
                          :display    "block"
                          :max-height 400}
                :on-load #(reset! cropper (init-cropper %))}]]
        [:div.col-sm-12
         [:a.btn.btn-primary  {:on-click #(when-let [c @cropper]
                                            )
                               :type     "button"}
          "Button for test"]]]])))

(defn cropper-modal-button [{:keys [id]}]
  [:button.btn.btn-outline-secondary {:type        "button"
                                      :data-toggle "modal"
                                      :data-target (str "#" id)}
   "Upload Image"])

(defn cropper-modal [{:keys [id]
                      :as   options}]
  (fn []
    [:<>
     [:div {:style {:position "absolute"
                    :z-index  1060}}
      [:div.modal {:tabindex "-1"
                   :role     "dialog"
                   :id       id}
       [:div.modal-dialog.modal-dialog-centered {:role "document"}
       [:div.modal-content
        [:div.modal-header
         [:h5.modal-title "Update Image"]
         [:button.close {:type         "button"
                         :data-dismiss "modal"
                         :aria-label   "close"}
          [:span {:aria-hidden true}
           "&times;"]]]
        [:div.modal-body
         [cropper options]]
        [:div.modal-footer
         [:button.btn.btn-primary.btn-sm {:type "button"}
          "Save changes"]]]]]]]))
