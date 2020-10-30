(ns psonia.app.panels.cropper
  (:require ["cropperjs" :as Cropper]
            [reagent.ratom :as ratom]
            ["jquery" :as $]))

(defn crop [cropper data]
  (fn []))

(defn load-image [data-url id]
  (fn [e]
    (let [reader (js/FileReader.)
          file   (aget (.-files (.-target e)) 0)]
      (when file
        (.readAsDataURL reader file)
        (set! (.-onload reader) #(reset! data-url (.-result reader)))
        (-> ($ (str "#" id))
            (.modal "show"))))))

(defn cropper [{:keys [src crop-fn aspect-ratio cropper id]
                :or   {aspect-ratio 19 / 6
                       cropper      (ratom/atom nil)}}]
  (let []
    (fn []
      [:div.container
       [:div.row
        [:div.col-sm-11
         [:img {:src   @src
                :style {:max-width "100%"
                        :display   "block"}
                :on-load   #(reset! cropper (Cropper. (.-target %) #js {"aspectRatio" aspect-ratio}))}]]]])))

(defn cropper-widget [{:keys [id]
                      :as   options}]
  (let [crop     (ratom/atom nil)
        init     (ratom/atom nil)
        data-url (ratom/atom nil)
        shown?   (ratom/atom nil)]
    (fn []
      [:<>
       [:div {:style {:position "absolute"}}
        [:div.modal {:tabIndex "-1"
                     :role     "dialog"
                     :id       id
                     :ref      #(-> ($ (str "#" id))
                                    (.on  "shown.bs.modal"  (fn []
                                                              (reset! shown? true)))
                                    (.on "hide.bs.modal" (fn []
                                                           (reset! shown? false)
                                                           (.destroy @crop)
                                                           (reset! data-url nil))))}
         [:div.modal-dialog.modal-dialog-centered {:role "document"}
          [:div.modal-content
           [:div.modal-header
            [:h5.modal-title "Crop Image"]
            [:button.close {:type         "button"
                            :data-dismiss "modal"
                            :aria-label   "close"}
             [:span {:aria-hidden             true
                     :dangerouslySetInnerHTML {:__html "&times;"}}]]]
           [:div.modal-body
            (when @shown?
              [cropper (assoc options :cropper crop :aspect-ratio 1 :src data-url)])]
           [:div.modal-footer
            [:button.btn.btn-primary.btn-sm {:type "button"
                                             :on-click (fn []
                                                         (js/console.log (-> (.getCroppedCanvas @crop)
                                                                             (.toDataURL)))
                                                         (-> ($ (str "#" id))
                                                             (.modal "hide")))}
             "Save changes"]]]]]]
       [:<>
        [:input#file {:type      "file"
                      :hidden    true
                      :on-change (load-image data-url id)}]
        [:label.btn.btn-light.btn-shadow.btn-sm.mb-2
         {:type "button"
          :for  "file"}
         [:i.czi-loading.mr-2]
         "Change"]]])))
